package de.dbo.samples.docker;

import java.util.List;

import com.github.dockerjava.api.model.*;
import com.github.dockerjava.api.model.ContainerPort;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;

public class ClientPrint {
    
    public static StringBuilder printConfig( final DockerClientConfig config) {
	final StringBuilder ret = new StringBuilder();
	ret.append("\n\t - Docker host:  " + config.getDockerHost());
	ret.append("\n\t - Registry URL: " + config.getRegistryUrl());
	return ret;
    }
    
   
    
    public static StringBuilder printContainers( final List<Container> containers) {
	final StringBuilder ret = new StringBuilder();
	for (final Container container:containers) {
	    ret.append("\n\t - " //+ container.getId() 
             + printContanerNames(container.getNames())
	     + "\tStatus: " + container.getStatus()
	     + "\tPorts: " + printPorts(container.getPorts()));
	}
	return ret;
    }
    
    public static StringBuilder printImages( final List<Image> images) {
	final StringBuilder ret = new StringBuilder();
	for (final Image image:images) {
	    ret.append("\n\t - " // + image.getId() 
	     + " Tags: " + printImageTags(image.getRepoTags()));
	}
	return ret;
    }
    
    public static StringBuilder printPorts( final ContainerPort[] ports) {
	final StringBuilder ret = new StringBuilder();
	for (final ContainerPort port:ports) {
	    ret.append(" " + port.getPrivatePort() + ":" + port.getPublicPort());
	}
	return ret;
    }
    
    private static StringBuilder printContanerNames( final String[] names) {
	final StringBuilder ret = new StringBuilder();
	for (final String name:names) {
	    ret.append(" " + right(name,20));
	}
	return ret;
    }
    
    private static StringBuilder printImageTags( final String[] names) {
 	final StringBuilder ret = new StringBuilder();
 	for (final String name:names) {
 	    ret.append(" " + name);
 	}
 	return ret;
     }
    
    
    /**
     * pad with " " to the right to the given length (n)
     * @param s string to be padded
     * @param n length of the output
     * @return padded string having the specified length
     */
     public static String right(final String s, int n) {
       return String.format("%1$-" + n + "s", s);
     }

     /** 
      * pad with " " to the left to the given length (n)
      * @param s string to be padded
      * @param n length of the output
      * @return padded string having the specified length
      */
     public static String left(final String s, int n) {
       return String.format("%1$" + n + "s", s);
     }
   
}
