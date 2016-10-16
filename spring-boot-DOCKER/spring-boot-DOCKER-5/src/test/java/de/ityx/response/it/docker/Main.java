package de.ityx.response.it.docker;

import de.ityx.response.it.docker.commander.Commander;
import de.ityx.response.it.docker.testimpl.TestAbstraction;

public final class Main extends TestAbstraction {
   
    public static void main(final String[] args) throws Exception {

        final Commander commander = new Commander();
        commander.init();
        
        commander.showDockerResources();
        commander.removeDockerResources(/* but not */ "java" );

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
        commander.showDockerResources();
        commander.close();

    }

}
