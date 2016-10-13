package de.ityx.response.it.docker;

import java.util.List;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerPort;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientConfig;

public final class ClientPrint {
    
    private static final String NLT = "\n\t - ";
    
    private ClientPrint() {
        
    }

    public static StringBuilder printConfig(final DockerClientConfig config) {
        final StringBuilder ret = new StringBuilder();
        ret.append("\n\t - Docker host:  " + config.getDockerHost());
        ret.append("\n\t - Registry URL: " + config.getRegistryUrl());
        return ret;
    }

    public static StringBuilder printContainers(final List<Container> containers) {
        final StringBuilder ret = new StringBuilder();
        for (final Container container : containers) {
            ret.append(NLT // + container.getId()
                    + printContanerNames(container.getNames())
                    + "\tStatus: " + container.getStatus()
                    + "\tPorts: " + printPorts(container.getPorts()));
        }
        return ret;
    }

    public static StringBuilder printImages(final List<Image> images) {
        final StringBuilder ret = new StringBuilder();
        for (final Image image : images) {
            ret.append(NLT // + image.getId()
                    + " Tags: " + printImageTags(image.getRepoTags()));
        }
        return ret;
    }

    public static StringBuilder printImage(final List<Image> images, final String imageId) {
        final StringBuilder ret = new StringBuilder();
        for (final Image image : images) {
            if (image.getId().contains(imageId)) {
                ret.append(NLT + image.getId() + "  Tags: " + printImageTags(image.getRepoTags()));
            }
        }
        return ret;
    }

    public static StringBuilder printPorts(final ContainerPort[] ports) {
        final StringBuilder ret = new StringBuilder();
        for (final ContainerPort port : ports) {
            ret.append(" " + port.getPrivatePort() + ":" + port.getPublicPort());
        }
        return ret;
    }
    
    public static StringBuilder printExposedPorts(final ExposedPort[] exposedPorts) {
	final StringBuilder ret = new StringBuilder();
	if (null==exposedPorts) {
	    ret.append(NLT + "NULL");
	    return ret;
	}
        for (final ExposedPort exposedPort : exposedPorts) {
            if (null==exposedPorts) {
    	    	ret.append(NLT + "NULL");
    	    } else {
    		ret.append(NLT + right(exposedPort.toString(), 20));
    	    }
        }
        return ret;
    }

    private static StringBuilder printContanerNames(final String[] names) {
        final StringBuilder ret = new StringBuilder();
        for (final String name : names) {
            ret.append(" " + right(name, 20));
        }
        return ret;
    }

    private static StringBuilder printImageTags(final String[] names) {
        final StringBuilder ret = new StringBuilder();
        for (final String name : names) {
            ret.append(" " + name);
        }
        return ret;
    }
    
    public static final String bannerContainerRunning(final String containerId) {
	  return String.format("\n"
	                + "\n===========================================================================================================B"
	                + "\n           Now open your Docker console and run the following commands: "
	                + "\n   "
	                + "\n              docker inspect %s                        "
	                + "\n              docker logs -f %s                        "
	                + "\n  "
	                + "\n===========================================================================================================E",
	                containerId, containerId);
    }

    /**
     * pad with " " to the right to the given length (n)
     * 
     * @param s
     *            string to be padded
     * @param n
     *            length of the output
     * @return padded string having the specified length
     */
    public static String right(final String s, final int n) {
        return String.format("%1$-" + n + "s", s);
    }

    /**
     * pad with " " to the left to the given length (n)
     * 
     * @param s
     *            string to be padded
     * @param n
     *            length of the output
     * @return padded string having the specified length
     */
    public static String left(final String s, final int n) {
        return String.format("%1$" + n + "s", s);
    }

}