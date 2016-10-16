package de.ityx.response.it.docker.testimpl;

import java.util.List;
import java.util.Map;

import de.ityx.response.it.docker.image.ImageSource;

import java.io.File;
import java.util.HashMap;

public final class TestImageSources extends TestAbstraction {
    
    public static Map<String, ImageSource> imageSources(final String repositotyPath) {
  
	final Map<String, ImageSource> ret = new HashMap<String, ImageSource>();
	
	ImageSource imageSource = null;
	
	imageSource = new ImageSource("discovery-microservice", "discovery",
	   new File(sourceDockerDirectory("discovery-microservice"), "discovery-microservice-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("api-gateway-microservice", "gateway",
	   new File(sourceDockerDirectory("api-gateway-microservice"), "api-gateway-microservice-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("consul-microservice", "consul",
	   new File(sourceDockerDirectory("consul-microservice"), "consul-microservice-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("config-microservice", "configserver",
	   new File(sourceDockerDirectory("config-microservice"), "config-microservice-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("hystrix-dashboard", "hystrix",
	   new File(sourceDockerDirectory("hystrix-dashboard"), "hystrix-dashboard-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("movie-microservice", "movie",
	   new File(sourceDockerDirectory("movie-microservice"), "movie-microservice-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("movies-ui", "moviesui",
	   new File(sourceDockerDirectory("movies-ui"), "movies-ui-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("recommendation-microservice", "recommendation",
	   new File(sourceDockerDirectory("recommendation-microservice"), "recommendation-microservice-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);
        
        imageSource  = new ImageSource("users-microservice", "user",
	   new File(sourceDockerDirectory("users-microservice"), "users-microservice-0.1.0.jar"), repositotyPath);
        ret.put(imageSource.getName(), imageSource);	
	
        return ret;
	
    }

}
