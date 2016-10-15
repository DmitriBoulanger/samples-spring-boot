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
import java.util.concurrent.TimeUnit;

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
import com.github.dockerjava.api.command.WaitContainerCmd;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.api.model.UpdateContainerResponse;
import com.github.dockerjava.api.model.Volume;

// DOCKER CORE
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;

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
	 final String msg = "Available containers ";
 	 LOG.info(LINENL + msg + "....");
         final List<Container> containers = dockerClient.listContainersCmd().exec();
         LOG.info(msg + "DONE. Found {} containers: " + printContainers(containers), containers.size());
         return containers;
     }
    
    public  List<Image> getAvaiableImages() throws Exception {
	 final String msg = "Available images ";
         LOG.info(LINENL + msg + "....");
         final List<Image> images = dockerClient.listImagesCmd().withShowAll(true).exec();
         LOG.info(msg + "DONE. Found {} images: " + printImages(images), images.size());
         return images;
     }
    
    public  void removeAvaiableContainers() throws Exception {
	removeAvaiableContainers(getAvaiableContainers());
    }
    
    public  void removeAvaiableContainers(List<Container> containers) throws Exception {
	 final String msg = "Removing available containers ";
 	 LOG.info(LINENL + msg + "....");
 	 for (final Container container: containers) {
 	     if (container.getStatus().equalsIgnoreCase("running")) {
 		dockerClient.killContainerCmd(container.getId()).exec();
 	     } else {
 	    	dockerClient.removeContainerCmd(container.getId()).withForce(true)  .exec();
 	     }
 	    
 	 }
 	LOG.info(msg + "DONE");
     }
    
    public boolean removeAvaiableImages(final String negativeFilter) throws Exception {
	return removeAvaiableImages(getAvaiableImages(), negativeFilter);
    }
    
    public boolean removeAvaiableImages(List<Image> images, final String negativeFilter) throws Exception {
	 final String msg = "Removing available images ";
 	 LOG.info(LINENL + msg + "....");
 	 boolean ret = true;
 	 for (final Image image: images) {
 	     final String[] tags = image.getRepoTags();
 	     for (final String tag:tags) {
 		 if (tag.contains(negativeFilter)) {
 		    LOG.info(msg + " - no deletion of the image {} ", tag);
 		 } else {
 	 	    try {
			dockerClient.removeImageCmd(image.getId()).withForce(true).exec();
		    } catch (Exception e) {
			LOG.warn(msg + " - can't remove image: " + e.getMessage());
			ret = false;
		    }
 		 }
 	     }
 	 }
 	LOG.info(msg + "DONE");
 	return ret;
     }
    
    public String createImage(final String imageSource, final String tag) throws Exception {
	final String msg = "Creating image  " + tag + " ";
        LOG.info(LINENL + "...");
        final BuildImageCmd buildImageCmd = dockerClient.buildImageCmd();
        final File baseDirectory = new File("src/main/resources/" + imageSource);
        buildImageCmd.withBaseDirectory(baseDirectory);
        buildImageCmd.withDockerfile(new File(baseDirectory, "Dockerfile"));
        buildImageCmd.withTag(tag);
    
        final String imageId = buildImageCmd.exec(new BuildImageResultCallback()).awaitImageId();
        LOG.info("Image {} created", imageId);
        LOG.info("Creating image DONE. Image: " + printImage(dockerClient.listImagesCmd().exec(), imageId));
        LOG.info(LINENL + "DONE. imageId="+imageId);
        return imageId;
    }

    public String createContainer(final String imageId, final int containerPort, final String name) throws Exception {
	final String msg = "Creating container " + name + " ";
	LOG.info(LINENL + msg + " ...");
        final CreateContainerCmd  createContainerCmd = dockerClient.createContainerCmd(imageId);
        createContainerCmd.withName(name);
        configureWithAutogeneratedExposedPort(createContainerCmd,8761);
        final CreateContainerResponse createContainerResponse = createContainerCmd.exec();
        final String containerId = createContainerResponse.getId();
        LOG.info(LINENL + msg + " DONE. continerId = " + containerId);
        return containerId;
    }
    
    private static final void configureWithAutogeneratedExposedPort(final CreateContainerCmd createContainerCmd, int containerPort) {

	        createContainerCmd.withExposedPorts(ExposedPort.tcp(containerPort));
	        
	        final Binding binding = Binding.bindIp("0.0.0.0");
	        final Ports ports = new Ports();
	        ports.bind(ExposedPort.tcp(containerPort), binding); 
	        final HostConfig hostConfig = new HostConfig();
	        hostConfig.withPortBindings(ports);
	        createContainerCmd.withHostConfig(hostConfig);
	        
    }
    
    public void startContainer(String containerId) throws Exception {
	LOG.info(LINENL + "Starting container ...");
        final StartContainerCmd  startContainerCmd = dockerClient.startContainerCmd(containerId);
        startContainerCmd.exec();
    }
    
    public void inspectContainer(String containerId) throws Exception {
	LOG.info(LINENL + "Inspecting container ...");
        final InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId).exec();
        LOG.info("Inspecting container DONE: "
        	+ "\n\t - Running: " + inspectContainerResponse.getState().getRunning()
        	+ "\n\t - Status:  " + inspectContainerResponse.getState().getStatus()
        	+ "\n\t - Ports:   " + prinPorts(inspectContainerResponse.getNetworkSettings().getPorts()));
    }
    
    public void waitContainer(final String containerId) throws Exception {
	LOG.info(LINENL + "Waiting container ...");
	final long start = System.currentTimeMillis();
        final WaitContainerCmd  waitContainerCmd = dockerClient.waitContainerCmd(containerId);
        final WaitContainerResultCallback waitContainerResultCallback = waitContainerCmd.exec(new WaitContainerResultCallback());
//        waitContainerResultCallback.awaitStarted(30, TimeUnit.SECONDS);
       
        UpdateContainerResponse updateContainerResponse = dockerClient.updateContainerCmd(containerId).exec();

        dockerClient.close();

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
