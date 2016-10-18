package de.ityx.response.it.docker.commander;

import static de.ityx.response.it.docker.util.DockerPrint.DONE;
import static de.ityx.response.it.docker.util.DockerPrint.LINENL;
import static de.ityx.response.it.docker.util.DockerPrint.NLT;
import static de.ityx.response.it.docker.util.DockerPrint.printConfig;
import static de.ityx.response.it.docker.util.DockerPrint.printList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// DOCKER API
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse.Mount;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.Volume;
// DOCKER CORE
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import de.ityx.response.it.docker.composer.ComposerContainerSpecification;
import de.ityx.response.it.docker.container.ContainerManager;
import de.ityx.response.it.docker.image.ImageManager;
import de.ityx.response.it.docker.image.ImageSource;
import de.ityx.response.it.docker.network.NetworkManager;

/**
 * @author Dmitri Boulanger, Hombach
 *         D. Knuth: Programs are meant to be read by humans and
 *         only incidentally for computers to execute
 */
public class Commander {
    private static final Logger LOG                  = LoggerFactory.getLogger(Commander.class);

    protected DockerClient      dockerClient;
    protected CommandFactory    dockerCmdExecFactory = new CommandFactory(DockerClientBuilder.getDefaultDockerCmdExecFactory());

    public void init() {
        final DefaultDockerClientConfig config = config();
        LOG.info("configuration: " + printConfig(config));
        dockerClient = DockerClientBuilder.getInstance(config).withDockerCmdExecFactory(dockerCmdExecFactory).build();
        LOG.info("initialized");
    }

    public DockerClient dockerClient() {
        return dockerClient;
    }

    public void close() throws Exception {
        final String msg = "Closing ";
        LOG.info(LINENL + msg + "...");
        dockerClient.close();
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

    private DefaultDockerClientConfig config() {
        return config(null);
    }

    protected DefaultDockerClientConfig config(final String password) {
        DefaultDockerClientConfig.Builder builder = DefaultDockerClientConfig.createDefaultConfigBuilder().withRegistryUrl("https://index.docker.io/v1/");
        if (password != null) {
            builder = builder.withRegistryPassword(password);
        }

        return builder.build();
    }

    // UTIL

    protected MountedVolumes mountedVolumes(final Matcher<? super List<Volume>> subMatcher) {
        return new MountedVolumes(subMatcher, "Mounted volumes", "mountedVolumes");
    }

    private static class MountedVolumes extends FeatureMatcher<InspectContainerResponse, List<Volume>> {
        MountedVolumes(final Matcher<? super List<Volume>> subMatcher, final String featureDescription, final String featureName) {
            super(subMatcher, featureDescription, featureName);
        }

        @Override
        public List<Volume> featureValueOf(final InspectContainerResponse item) {
            final List<Volume> volumes = new ArrayList<Volume>();
            for (Mount mount : item.getMounts()) {
                volumes.add(mount.getDestination());
            }
            return volumes;
        }
    }

    protected String containerLog(final String containerId) throws Exception {
        return dockerClient.logContainerCmd(containerId).withStdOut(true).exec(new LogContainerTestCallback())
                .awaitCompletion().toString();
    }

    public static class LogContainerTestCallback extends LogContainerResultCallback {
        protected final StringBuffer log             = new StringBuffer();

        List<Frame>                  collectedFrames = new ArrayList<Frame>();

        boolean                      collectFrames   = false;

        public LogContainerTestCallback() {
            this(false);
        }

        public LogContainerTestCallback(final boolean collectFrames) {
            this.collectFrames = collectFrames;
        }

        @Override
        public void onNext(final Frame frame) {
            if (collectFrames) {
                collectedFrames.add(frame);
            }
            log.append(new String(frame.getPayload()));
        }

        @Override
        public String toString() {
            return log.toString();
        }

        public List<Frame> getCollectedFrames() {
            return collectedFrames;
        }
    }

    protected String buildImage(final File baseDir) throws Exception {

        return dockerClient.buildImageCmd(baseDir).withNoCache(true).exec(new BuildImageResultCallback())
                .awaitImageId();
    }

    protected Network findNetwork(final List<Network> networks, final String name) {

        for (Network network : networks) {
            if (StringUtils.equals(network.getName(), name)) {
                return network;
            }
        }

        return null;
    }

}
