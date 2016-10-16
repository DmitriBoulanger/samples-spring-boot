package de.ityx.response.it.docker.commander;

import static de.ityx.response.it.docker.util.PrintManager.DONE;
import static de.ityx.response.it.docker.util.PrintManager.LINENL;
import static de.ityx.response.it.docker.util.PrintManager.NLT;
import static de.ityx.response.it.docker.util.PrintManager.printConfig;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//DOCKER API
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

import de.ityx.response.it.docker.container.ContainerManager;
import de.ityx.response.it.docker.image.ImageManager;
import de.ityx.response.it.docker.image.ImageSource;

/**
 * @author Dmitri Boulanger, Hombach
 *         D. Knuth: Programs are meant to be read by humans and
 *         only incidentally for computers to execute
 */
public class Commander {
    private static final Logger     LOG                  = LoggerFactory.getLogger(Commander.class);

    protected DockerClient         dockerClient;
    protected CommandFactory dockerCmdExecFactory = new CommandFactory(DockerClientBuilder.getDefaultDockerCmdExecFactory());
    
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
	ContainerManager.showAvaiableContainers(true,dockerClient);
	ImageManager.showAvaiableImages(true,dockerClient);
    }
    
    public List<Image> avialbleImages() throws Exception {
	return ImageManager.showAvaiableImages(false,dockerClient);
    }
    
    public void removeDockerResources(final String negativeImageFilter) throws Exception {
	final String msg = "Renoving docker resources  ";
        LOG.info(LINENL + msg + "....");
        ContainerManager.removeAvaiableContainers(true, dockerClient);
        ImageManager.removeAvaiableImages(true, negativeImageFilter, dockerClient);
    }

    public String createImage(final ImageSource imageSource, final String tag) throws Exception {
	return ImageManager.createImage(imageSource, tag, dockerClient);
    }
    
    public String createAndStartContainer(final String name, final String imageId, final int containerPort, 
	    final boolean randomExposedPort) throws Exception {
	final String msg = "Creating container [" + name + "] ";
	LOG.info(LINENL + msg + " ...");
        final CreateContainerCmd  createContainerCmd = dockerClient.createContainerCmd(imageId);
        createContainerCmd.withName(name);
        if (randomExposedPort) {
            ContainerManager.configureWithRandomExposedPort(createContainerCmd,containerPort);
        } else {
            ContainerManager.configureWithFixedExposedPort(createContainerCmd,containerPort);
        }
        final CreateContainerResponse createContainerResponse = createContainerCmd.exec();
        final String containerId = createContainerResponse.getId();
        LOG.info(msg + DONE + " Container" + NLT + containerId);
        ContainerManager.startContainer(containerId, dockerClient);
        ContainerManager.waitContainer(containerId, 20, dockerClient);
        ContainerManager.inspectContainer(containerId, dockerClient);
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

    /**
     * Checks to see if a specific port is available.
     *
     * @param port
     *            the port to check for availability
     */
    public static Boolean available(final int port) {
        if (port < 1100 || port > 60000) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        }
        catch(IOException ignored) {
            ignored.printStackTrace();
        }
        finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                }
                catch(IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

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
