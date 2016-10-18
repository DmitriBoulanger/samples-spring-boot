package de.ityx.response.it.docker.commander;

import static de.ityx.response.it.docker.commander.CommanderConfig.config;
import static de.ityx.response.it.docker.util.DockerPrint.DONE;
import static de.ityx.response.it.docker.util.DockerPrint.LINENL;
import static de.ityx.response.it.docker.util.DockerPrint.NLT;
import static de.ityx.response.it.docker.util.DockerPrint.printConfig;
import static de.ityx.response.it.docker.util.DockerPrint.printList;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// DOCKER API
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.BuildImageResultCallback;

import de.ityx.response.it.docker.composer.ComposerContainerSpecification;
import de.ityx.response.it.docker.container.ContainerManager;
import de.ityx.response.it.docker.image.ImageManager;
import de.ityx.response.it.docker.image.ImageSource;
import de.ityx.response.it.docker.network.NetworkManager;

/**
 * @author Dmitri Boulanger, Hombach
 * 
 *         D. Knuth: Programs are meant to be read by humans and
 *         only incidentally for computers to execute
 */
public class Commander {
    private static final Logger LOG = LoggerFactory.getLogger(Commander.class);

    protected DockerClient   dockerClient         = null;
    protected CommandFactory dockerCmdExecFactory = null;
    
    public Commander() {
        
    }

    public void init() {
        final String msg = "Initialization ";
        LOG.info(LINENL + msg + "...");
        final DockerClientConfig config = config();
        LOG.info(msg + " configuration: " + printConfig(config));
        
        // Command Factory
        dockerCmdExecFactory = new CommandFactory(DockerClientBuilder.getDefaultDockerCmdExecFactory());
        
        // Docker Client
        final DockerClientBuilder dockerClientBuilder =  DockerClientBuilder.getInstance(config);
        dockerClientBuilder.withDockerCmdExecFactory(dockerCmdExecFactory);
        dockerClient = dockerClientBuilder.build();
        
        LOG.info(msg + DONE);
    }

    /**
     * @return configured Docker-client
     */
    public DockerClient dockerClient() {
        return dockerClient;
    }

    /**
     * closes the configured Docker-client
     * @throws Exception
     */
    public void close() throws Exception {
        if (null==dockerClient) {
            return;
        }
        final String msg = "Closing ";
        LOG.info(LINENL + msg + "...");
        dockerClient.close();
        dockerClient = null;
        dockerCmdExecFactory = null;
        LOG.info(msg + DONE);
    }

    public void showDockerResources() throws Exception {
        final String msg = "Available docker resources ";
        LOG.info(LINENL + msg + "....");
        ContainerManager.showAvaiableContainers(false, dockerClient);
        NetworkManager.showAvaiableNetworks(true, dockerClient);
        ImageManager.showAvaiableImages(false, dockerClient);
    }

    public List<Image> avialbleImages() throws Exception {
        return ImageManager.showAvaiableImages(false, dockerClient);
    }

    public void removeDockerResources(final String[] negativeImageFilters) throws Exception {
        final String msg = "Renoving docker resources ";
        LOG.info(LINENL + msg + "but " + printList(negativeImageFilters) + " ....");
        ContainerManager.removeAvaiableContainers(true, negativeImageFilters, dockerClient);
        ImageManager.removeAvaiableImages(true, negativeImageFilters, dockerClient);
    }

    public String createImage(final ImageSource imageSource, final String tag) throws Exception {
        return ImageManager.createImage(imageSource, tag, dockerClient);
    }

    public String createAndStartContainer(final ComposerContainerSpecification containerSpecification, final boolean randomExposedPort) throws Exception  {
        final String name = containerSpecification.getTitle();
        final String msg = "Creating container [" + name + "] ";
        final String imageId = containerSpecification.getImageSource().getDockerImageId();
        final Integer containerPort = containerSpecification.getPorts().getSingleExposedPort();
        final List<String> environment = containerSpecification.getEnvironment();
        final String customNetworkName = containerSpecification.getNetwork();
        
        final CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(imageId);
        createContainerCmd.withName(name);
        createContainerCmd.withNetworkDisabled(false);
        createContainerCmd.withEnv(environment);
        
        if (randomExposedPort) {
            ContainerManager.configureWithRandomExposedPort(createContainerCmd, containerPort);
        }
        else {
            ContainerManager.configureWithFixedExposedPort(createContainerCmd, containerPort);
        }
        final CreateContainerResponse createContainerResponse = createContainerCmd.exec();
        final String containerId = createContainerResponse.getId();
        LOG.info(msg + DONE + " Container ID: " + containerId);
        
        if (null!=customNetworkName) {
            NetworkManager.connectContainerToCustomNetwork(containerId, customNetworkName, dockerClient);
        }
        
        final String msg2 = "Running container [" + name + "] ";
        LOG.info(LINENL + msg2 + " ...");
        
        ContainerManager.startContainer(containerId, dockerClient);
        ContainerManager.waitContainer(containerId, 20, dockerClient);
        ContainerManager.inspectContainer(containerId, dockerClient);
        
        LOG.info(msg2 + DONE + " Container" + NLT + containerId);
        return containerId;
    }

    protected String buildImage(final File baseDir) throws Exception {

        return dockerClient.buildImageCmd(baseDir).withNoCache(true).exec(new BuildImageResultCallback())
                .awaitImageId();
    }

    protected Network findNetwork(final List<Network> networks, final String name) {

        for (Network network : networks) {
            if (StringUtils.equalsIgnoreCase(network.getName(), name)) {
                return network;
            }
        }

        return null;
    }

}
