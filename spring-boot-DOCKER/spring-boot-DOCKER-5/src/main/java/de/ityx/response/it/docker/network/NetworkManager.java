package de.ityx.response.it.docker.network;

import static de.ityx.response.it.docker.util.DockerPrint.DONE;
import static de.ityx.response.it.docker.util.DockerPrint.prinPorts;
import static de.ityx.response.it.docker.util.DockerPrint.printContainers;
import static de.ityx.response.it.docker.util.DockerPrint.printContainerNames;
import static de.ityx.response.it.docker.util.DockerPrint.q;
import static de.ityx.response.it.docker.util.DockerPrint.*;
import static de.ityx.response.it.docker.util.Utils.nn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.ListNetworksCmd;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;

import de.ityx.response.it.docker.commander.Commander;
import de.ityx.response.it.docker.image.ImageFilter;

public class NetworkManager {
    private static final Logger LOG = LoggerFactory.getLogger(Commander.class);
    
    public static final String DOCKER_NETWWORK_DEFAULT_BRIDGE =  "bridge";

    private NetworkManager() {

    }

    public static List<Network> showAvaiableNetworks(final boolean all, final DockerClient dockerClient)
            throws Exception {
        final String msg = "Available networks ";
        LOG.info(msg + "....");
        final List<Network> networks = avialbleNetworks(null, null, dockerClient);
        final String info;
        if (networks.isEmpty()) {
            info = "No networks found";
        }
        else {
            info = "Found " + q(networks.size()) + " networks: " + printNetworks(networks);
        }
        LOG.info(msg + DONE + info);
        return networks;
    }

    public static List<Network> avialbleNetworks(final String networkId, final String networkName, final DockerClient dockerClient) {
        final ListNetworksCmd listNetworksCmd = dockerClient.listNetworksCmd();
        if (!nn(networkId)) {
            listNetworksCmd.withIdFilter(networkId);
        }
        if (!nn(networkId)) {
            listNetworksCmd.withNameFilter(networkName);
        }
        return listNetworksCmd.exec();
    }
    
    public static final void connectContainerToCustomNetwork(final String containerId,  final String customNetworkName, final DockerClient dockerClient) {
        final String msg = "connecting container to custom network ["+customNetworkName+"]";
        LOG.info(msg + "....");
        
        // disconnect from default bridge-network
        final List<Network> networks = avialbleNetworks(null, DOCKER_NETWWORK_DEFAULT_BRIDGE, dockerClient); 
        if (null!=networks && !networks.isEmpty()) {
            for (final Network network:networks) {
                dockerClient.disconnectFromNetworkCmd()
                .withContainerId(containerId)
                .withForce(true)
                .withNetworkId(network.getId())
                .exec();
            }
        }

        final List<Network> responseNecustomeNetworks = NetworkManager.avialbleNetworks(null, customNetworkName, dockerClient);
        assertThat("Custom networks are not available (null)", responseNecustomeNetworks, notNullValue());
        assertThat("Custom network name ["+ customNetworkName +"] cannot found or there more than one with the same name", responseNecustomeNetworks, hasSize(1));
        final Network customNetwork = responseNecustomeNetworks.get(0);
        dockerClient.connectToNetworkCmd()
            .withNetworkId(customNetwork.getId())
            .withContainerId(containerId).exec();
        

        LOG.info(msg + DONE);
    }
    

//    /**
//     * remove available Docker containers.
//     * 
//     * @param all
//     *            if true then the available containers are listed as CLI
//     *            "docker ps -a"
//     * @param dockerClient
//     * @return true only if no problems occur
//     * @throws Exception
//     */
//    public static boolean removeAvaiableContainers(final boolean all, final String[] negativeFilters, final DockerClient dockerClient)
//            throws Exception {
//        return removeAvaiableContainers(avialbleContainers(all, dockerClient), negativeFilters, dockerClient);
//    }
//
//    public static void startContainer(final String containerId, final DockerClient dockerClient) throws Exception {
//        final String msg = "Starting container ";
//        LOG.info(msg + "..");
//        final StartContainerCmd startContainerCmd = dockerClient.startContainerCmd(containerId);
//        startContainerCmd.exec();
//        LOG.info(msg + DONE);
//    }
//
//    public static void waitContainer(final String containerId, int timeoutSeconds, final DockerClient dockerClient)
//            throws Exception {
//        final String msg = "Waiting container ";
//        LOG.info(msg + "....");
//        final long start = System.currentTimeMillis();
//        final long timeout = timeoutSeconds * 1000;
//        while (true) {
//            final InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId)
//                    .exec();
//            final boolean running = inspectContainerResponse.getState().getRunning();
//            if (running) {
//                break;
//            }
//            final String error = inspectContainerResponse.getState().getError();
//            if (null != error && 0 != error.trim().length()) {
//                throw new IllegalStateException("Error " + q(error) + " while wating for container start-up");
//            }
//            if ((System.currentTimeMillis() - start) > timeout) {
//                throw new IllegalStateException(
//                        "Timeout [" + timeoutSeconds + " sec.] while wating for container start-up");
//            }
//            Thread.sleep(200);
//        }
//        LOG.info(msg + DONE + "Elapsed " + (System.currentTimeMillis() - start) + " ms.");
//    }
//
//    public static void inspectContainer(String containerId, final DockerClient dockerClient) throws Exception {
//        final String msg = "Inspecting container ";
//        LOG.info(msg + "...");
//        final InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId).exec();
//        LOG.info(msg + DONE + "\n\t - Running: " + inspectContainerResponse.getState().getRunning() + "\n\t - Status:  "
//                + inspectContainerResponse.getState().getStatus() + "\n\t - Ports:   "
//                + prinPorts(inspectContainerResponse.getNetworkSettings().getPorts()));
//    }
//
//    // ==============================================================================================================================
//    // PORT CONFIGURATION
//    // ==============================================================================================================================
//
//    public static final void configureWithRandomExposedPort(final CreateContainerCmd createContainerCmd,
//            int containerPort) {
//        createContainerCmd.withExposedPorts(ExposedPort.tcp(containerPort));
//        final Binding binding = Binding.bindIp("0.0.0.0");
//        final Ports ports = new Ports();
//        ports.bind(ExposedPort.tcp(containerPort), binding);
//        final HostConfig hostConfig = new HostConfig();
//        hostConfig.withPortBindings(ports);
//        createContainerCmd.withHostConfig(hostConfig);
//    }
//
//    public static final void configureWithFixedExposedPort(final CreateContainerCmd createContainerCmd,
//            int containerPort) {
//        createContainerCmd.withExposedPorts(ExposedPort.tcp(containerPort));
//        final Binding binding = Binding.bindIpAndPort("0.0.0.0", containerPort);
//        final Ports ports = new Ports();
//        ports.bind(ExposedPort.tcp(containerPort), binding);
//        final HostConfig hostConfig = new HostConfig();
//        hostConfig.withPortBindings(ports);
//        createContainerCmd.withHostConfig(hostConfig);
//    }
//
//    // ==============================================================================================================================
//    // PRIVATE IMPLEMENTATION
//    // ==============================================================================================================================
//
//    private static boolean removeAvaiableContainers(final List<Container> containers, final String[] negativeFilters, final DockerClient dockerClient)
//            throws Exception {
//        final String msg = "Removing available containers ";
//        LOG.info(msg + "....");
//        boolean ret = true;
//        for (final Container container : containers) {
//            if (keepContainer(container, negativeFilters, msg)) {
//                continue;
//            }
//
//            if (container.getStatus().equalsIgnoreCase("running")) {
//                dockerClient.killContainerCmd(container.getId()).exec();
//            }
//            dockerClient.removeContainerCmd(container.getId()).withForce(true).exec();
//        }
//        LOG.info(msg + DONE);
//        return ret;
//    }
//
//    private static boolean keepContainer(final Container container, final String[] negativeFilters, final String msg) {
//        final NetworFilter networFilter = new NetworFilter(negativeFilters, true);
//        if (networFilter.isMatch(container)) {
//            LOG.info(msg + " - keeping container: " + printContainerNames(container.getNames()));
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

}
