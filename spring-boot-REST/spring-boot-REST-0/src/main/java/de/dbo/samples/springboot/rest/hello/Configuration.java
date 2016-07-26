package de.dbo.samples.springboot.rest.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Configuration {
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    public Configuration() {
	log.info("created");
    }


    public static void main(String[] args) {
	SpringApplication.run(Configuration.class, args);
    }

}
