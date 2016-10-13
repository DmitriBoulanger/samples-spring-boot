package de.ityx.response.it.docker;

import static de.ityx.response.it.docker.ClientPrint.*;
import static de.ityx.response.it.docker.ClientPrint.printContainers;
import static de.ityx.response.it.docker.ClientPrint.printImage;
import static de.ityx.response.it.docker.ClientPrint.printImages;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//DOCKER API
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse.Mount;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.Volume;

// DOCKER CORE
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;

/**
 * @author Dmitri Boulanger, Hombach
 *         D. Knuth: Programs are meant to be read by humans and
 *         only incidentally for computers to execute
 */
public class Client {

    public static final Logger     LOG                  = LoggerFactory.getLogger(Client.class);

    protected DockerClient         dockerClient;
    protected ClientCmdExecFactory dockerCmdExecFactory = new ClientCmdExecFactory(DockerClientBuilder.getDefaultDockerCmdExecFactory());
    
    public void init() {
	final DefaultDockerClientConfig config = config();
        LOG.info("configuration: " + printConfig(config));
        dockerClient = DockerClientBuilder.getInstance(config).withDockerCmdExecFactory(dockerCmdExecFactory).build();
        LOG.info("initialized");
    }
    
    public  List<Container> getAvaiableContainers() throws Exception {
 	 LOG.info("Getting available containers ....");
         final List<Container> containers = dockerClient.listContainersCmd().exec();
         LOG.info("Getting available containers DONE. Found {} containers: " + printContainers(containers), containers.size());
         return containers;
     }
    
    public  List<Image> getAvaiableImages() throws Exception {
         LOG.info("Getting available images ....");
         final List<Image> images = dockerClient.listImagesCmd().exec();
         LOG.info("Getting available images DONE. Found {} images: " + printImages(images), images.size());
         return images;
     }
    
    public  void removeAvaiableContainers(List<Container> containers) throws Exception {
 	 LOG.info("Removing available containers ....");
 	 for (final Container container: containers) {
 	    dockerClient.stopContainerCmd(container.getId()).exec();
 	    dockerClient.removeContainerCmd(container.getId()).exec();
 	 }
 	LOG.info("Removing available containers DONE");
     }
    
    public  void removeAvaiableImages(List<Image> images, final String negativeFilter) throws Exception {
 	 LOG.info("Removing available images ....");
 	 for (final Image image: images) {
 	     final String[] tags = image.getRepoTags();
 	     for (final String tag:tags) {
 		 if (tag.contains(negativeFilter)) {
 		    LOG.info("Removing available images: no deletion of the image {} ", tag);
 		 } else {
 	 	    dockerClient.removeImageCmd(image.getId());
 		 }
 	     }
 	     
 	     

 	 }
 	LOG.info("Removing available images DONE");
     }
    
    public String createImage(final String imageSource, final String tag) throws Exception {
        LOG.info("Creating image ...");
        final BuildImageCmd buildImageCmd = dockerClient.buildImageCmd();
        final File baseDirectory = new File("src/main/resources/" + imageSource);
        buildImageCmd.withBaseDirectory(baseDirectory);
        buildImageCmd.withDockerfile(new File(baseDirectory, "Dockerfile"));
        buildImageCmd.withTag(tag);
    
        final String imageId = buildImageCmd.exec(new BuildImageResultCallback()).awaitImageId();
        LOG.info("Image {} created", imageId);
        LOG.info("Creating image DONE. Image: " + printImage(dockerClient.listImagesCmd().exec(), imageId));
        return imageId;
    }

    public String createContainer(String imageId) throws Exception {
        
        final CreateContainerCmd  createContainerCmd = dockerClient.createContainerCmd(imageId);
        final CreateContainerResponse createContainerResponse = createContainerCmd.exec();
        final String containerId = createContainerResponse.getId();
        return containerId;
    }
    
    public void startContainer(String containerId) throws Exception {
	LOG.info("Starting container ...");
        final StartContainerCmd  startContainerCmd = dockerClient.startContainerCmd(containerId);
        startContainerCmd.exec();
        
        final InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId).exec();
        final ExposedPort[] exposedPorts = inspectContainerResponse.getConfig().getExposedPorts();
        LOG.info("Starting container DONE. Exposed ports: " + printExposedPorts(exposedPorts));
        
    }

    public void close() throws Exception {
        LOG.info("Closing ...");
        dockerClient.close();
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

    public void beforeMethod(final Method method) {
        LOG.info(String.format("################################## STARTING %s ##################################",
                method.getName()));
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
