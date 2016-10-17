package de.ityx.response.it.docker.testimpl;

import java.util.Map;

import de.ityx.response.it.docker.image.ImageSource;

import java.io.File;
import java.util.HashMap;

public final class TestImageSources extends TestAbstraction {

    public static Map<String, ImageSource> imageSources(final String repositotyPath) {

	final Map<String, ImageSource> ret = new HashMap<String, ImageSource>();

	ImageSource imageSource = null;

	// imageSource = new ImageSource("case-service", "case-service",
	// new File(sourceDockerDirectory("case-service"), "case-service.war"),
	// repositotyPath);
	// ret.put(imageSource.getName(), imageSource);

	imageSource = new ImageSource("discovery-microservice", "discovery",
		new File(sourceDockerDirectory("discovery-microservice"), "discovery-microservice-0.1.0.jar"),
		repositotyPath);
	ret.put(imageSource.getName(), imageSource);

	imageSource = new ImageSource("api-gateway-microservice", "gateway",
		new File(sourceDockerDirectory("api-gateway-microservice"), "api-gateway-microservice-0.1.0.jar"),
		repositotyPath);
	ret.put(imageSource.getName(), imageSource);

	imageSource = new ImageSource("config-microservice", "configserver",
		new File(sourceDockerDirectory("config-microservice"), "config-microservice-0.1.0.jar"),
		repositotyPath);
	ret.put(imageSource.getName(), imageSource);

	imageSource = new ImageSource("hystrix-dashboard", "hystrix",
		new File(sourceDockerDirectory("hystrix-dashboard"), "hystrix-dashboard-0.1.0.jar"), repositotyPath);
	ret.put(imageSource.getName(), imageSource);

	imageSource = new ImageSource("movies-ui", "moviesui",
		new File(sourceDockerDirectory("movies-ui"), "movies-ui-0.1.0.jar"), repositotyPath);
	ret.put(imageSource.getName(), imageSource);

	return ret;

    }

}
