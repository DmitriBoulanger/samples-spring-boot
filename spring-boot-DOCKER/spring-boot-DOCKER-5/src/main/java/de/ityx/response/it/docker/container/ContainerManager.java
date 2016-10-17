package de.ityx.response.it.docker.container;

import static de.ityx.response.it.docker.util.DockerPrint.DONE;
import static de.ityx.response.it.docker.util.DockerPrint.prinPorts;
import static de.ityx.response.it.docker.util.DockerPrint.printContainers;
import static de.ityx.response.it.docker.util.DockerPrint.q;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;

import de.ityx.response.it.docker.commander.Commander;

public class ContainerManager {
    private static final Logger LOG = LoggerFactory.getLogger(Commander.class);

    private ContainerManager() {

    }

    public static List<Container> showAvaiableContainers(final boolean all, final DockerClient dockerClient)
            throws Exception {
        final String msg = "Available containers ";
        LOG.info(msg + "....");
        final List<Container> containers = avialbleContainers(all, dockerClient);
        final String info;
        if (containers.isEmpty()) {
            info = "No containers found";
        }
        else {
            info = "Found " + q(containers.size()) + " containers: " + printContainers(containers);
        }
        LOG.info(msg + DONE + info);
        return containers;
    }

    public static List<Container> avialbleContainers(final boolean all, final DockerClient dockerClient) {
        return dockerClient.listContainersCmd().withShowAll(all).exec();
    }

    /**
     * remove available Docker containers.
     * 
     * @param all
     *            if true then the available containers are listed as CLI
     *            "docker ps -a"
     * @param dockerClient
     * @return true only if no problems occur
     * @throws Exception
     */
    public static boolean removeAvaiableContainers(final boolean all, final DockerClient dockerClient)
            throws Exception {
        return removeAvaiableContainers(avialbleContainers(all, dockerClient), dockerClient);
    }

    public static void startContainer(final String containerId, final DockerClient dockerClient) throws Exception {
        final String msg = "Starting container ";
        LOG.info(msg + "..");
        final StartContainerCmd startContainerCmd = dockerClient.startContainerCmd(containerId);
        startContainerCmd.exec();
        LOG.info(msg + DONE);
    }

    public static void waitContainer(final String containerId, int timeoutSeconds, final DockerClient dockerClient)
            throws Exception {
        final String msg = "Waiting container ";
        LOG.info(msg + "....");
        final long start = System.currentTimeMillis();
        final long timeout = timeoutSeconds * 1000;
        while (true) {
            final InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId)
                    .exec();
            final boolean running = inspectContainerResponse.getState().getRunning();
            if (running) {
                break;
            }
            final String error = inspectContainerResponse.getState().getError();
            if (null != error && 0 != error.trim().length()) {
                throw new IllegalStateException("Error " + q(error) + " while wating for container start-up");
            }
            if ((System.currentTimeMillis() - start) > timeout) {
                throw new IllegalStateException(
                        "Timeout [" + timeoutSeconds + " sec.] while wating for container start-up");
            }
            Thread.sleep(200);
        }
        LOG.info(msg + DONE + "Elapsed " + (System.currentTimeMillis() - start) + " ms.");
    }

    public static void inspectContainer(String containerId, final DockerClient dockerClient) throws Exception {
        final String msg = "Inspecting container ";
        LOG.info(msg + "...");
        final InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId).exec();
        LOG.info(msg + DONE + "\n\t - Running: " + inspectContainerResponse.getState().getRunning() + "\n\t - Status:  "
                + inspectContainerResponse.getState().getStatus() + "\n\t - Ports:   "
                + prinPorts(inspectContainerResponse.getNetworkSettings().getPorts()));
    }

    // ==============================================================================================================================
    // PORT CONFIGURATION
    // ==============================================================================================================================

    public static final void configureWithRandomExposedPort(final CreateContainerCmd createContainerCmd,
            int containerPort) {
        createContainerCmd.withExposedPorts(ExposedPort.tcp(containerPort));
        final Binding binding = Binding.bindIp("0.0.0.0");
        final Ports ports = new Ports();
        ports.bind(ExposedPort.tcp(containerPort), binding);
        final HostConfig hostConfig = new HostConfig();
        hostConfig.withPortBindings(ports);
        createContainerCmd.withHostConfig(hostConfig);
    }

    public static final void configureWithFixedExposedPort(final CreateContainerCmd createContainerCmd,
            int containerPort) {
        createContainerCmd.withExposedPorts(ExposedPort.tcp(containerPort));
        final Binding binding = Binding.bindIpAndPort("0.0.0.0", containerPort);
        final Ports ports = new Ports();
        ports.bind(ExposedPort.tcp(containerPort), binding);
        final HostConfig hostConfig = new HostConfig();
        hostConfig.withPortBindings(ports);
        createContainerCmd.withHostConfig(hostConfig);
    }

    // ==============================================================================================================================
    // PRIVATE IMPLEMENTATION
    // ==============================================================================================================================

    private static boolean removeAvaiableContainers(final List<Container> containers, final DockerClient dockerClient)
            throws Exception {
        final String msg = "Removing available containers ";
        LOG.info(msg + "....");
        boolean ret = true;
       for (final Container container : containers) {
            if (occurs("dddev", container.getNames())) {
                continue;
            }
            if (container.getStatus().equalsIgnoreCase("running")) {
                dockerClient.killContainerCmd(container.getId()).exec();
            }
            else {
                dockerClient.removeContainerCmd(container.getId()).withForce(true).exec();
            }
        }
        LOG.info(msg + DONE);
        return ret;
    }
    
    private static final boolean occurs(final String key, final String[] values) {
        for (final String value:values) {
            if (-1!=value.indexOf(key)) {
                return true;
            }
        }
        return false;
    }

}
