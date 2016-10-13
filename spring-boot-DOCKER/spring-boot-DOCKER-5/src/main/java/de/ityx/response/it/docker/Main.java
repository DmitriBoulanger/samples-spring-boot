package de.ityx.response.it.docker;

import static de.ityx.response.it.docker.ClientPrint.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    public Main() {
        
    }
    
    public void init() {
        
    }

    public static void main(final String[] args) throws Exception {

        final Client client = new Client();
        client.init();
        
        // clean-up
        client.removeAvaiableContainers(client.getAvaiableContainers());
        client.removeAvaiableImages(client.getAvaiableImages(), "java");
        
        // container 1
        final String imageId = client.createImage("discovery-microservice", "discovery");
        final String containerId = client.createContainer(imageId);
        client.startContainer(containerId);
        LOG.info(bannerContainerRunning(containerId));
        
        // container 2
        final String imageId2 = client.createImage("testrun", "testrun");
        final String containerId2 = client.createContainer(imageId2);
        client.startContainer(containerId2);
        LOG.info(bannerContainerRunning(containerId2));
       
        // finish
        client.close();

    }

}
