package de.ityx.response.it.docker;

import de.ityx.response.it.docker.jobs.ImageSource;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.HashMap;

public final class ImageSources extends TestAbstraction {
    
    public static Map<String, ImageSource> imageSources() {
	final Map<String, ImageSource> ret = new HashMap<String, ImageSource>();
	
	final String path = "test/";
	
	ImageSource imageSource = null;
	
	imageSource = new ImageSource("Discovery",
	   new File(sourceDockerDirectory("discovery-microservice"), "discovery-microservice-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("Gateway",
	   new File(sourceDockerDirectory("api-gateway-microservice"), "api-gateway-microservice-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("Consul",
	   new File(sourceDockerDirectory("consul-microservice"), "consul-microservice-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("Config",
	   new File(sourceDockerDirectory("config-microservice"), "config-microservice-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("Hystrix",
	   new File(sourceDockerDirectory("hystrix-dashboard"), "hystrix-dashboard-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("Movie",
	   new File(sourceDockerDirectory("movie-microservice"), "movie-microservice-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("MoviesUI",
	   new File(sourceDockerDirectory("movies-ui"), "movies-ui-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("Recommendation",
	   new File(sourceDockerDirectory("recommendation-microservice"), "recommendation-microservice-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("Users",
	   new File(sourceDockerDirectory("users-microservice"), "users-microservice-0.1.0.jar"), path);
        ret.put(imageSource.getName(), imageSource);	
	
        return ret;
	
    }

}
