package de.dbo.samples.springboot.rest.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"de.dbo.samples.springboot.rest.greeting"})
public class GreetingApplication {
    private static final Logger log = LoggerFactory.getLogger(GreetingApplication.class);


    public static void main(String[] args) {
	log.debug("Starting " + GreetingApplication.class.getSimpleName() + " ...");
	SpringApplication.run(GreetingApplication.class, args);
    }
}
