package de.dbo.samples.springboot.rest.war;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

/**
 * Sample WAR application
 */
@SpringBootApplication
@PropertySource(value = { "WEB-INF/custom.properties" })
public class SampleWarApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SampleWarApplication.class, args);
	}

}
