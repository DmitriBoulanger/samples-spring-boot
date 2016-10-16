package de.ityx.response.it.docker;

import static de.ityx.response.it.docker.util.PrintManager.*;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ityx.response.it.docker.jobs.ImageSource;

public final class Main extends TestAbstraction {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
   
    public static void main(final String[] args) throws Exception {

        final Client client = new Client();
        client.init();
        
        client.showDockerResources();
        client.removeDockerResources(/* but not */ "java" );

//        // container 1
//        final ImageSource imageSource = new ImageSource("Discovery",
//        	new File(sourceDockerDirectory("discovery-microservice"), "discovery-microservice-0.1.0.jar"));
//        final String imageId = client.createImage(imageSource, "discovery");
//        final String containerId = client.createAndStartContainer("discovery", imageId, 8761,  /* random port*/ false);

        
//         // container 2
//        final String imageId2 = client.createImage("api-gateway-microservice", "gateway");
//        final String containerId2 = client.createContainer(imageId2, 10000, "gateway");
//        client.startContainer(containerId2);
////        client.waitContainer(containerId2);
//        client.inspectContainer(containerId2);
        
       
//        LOG.info(bannerContainerRunning(containerId));

       
        // finish
        client.showDockerResources();
        client.close();

    }

}
