package de.dbo.samples.springboot.rest.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GreetingConfiguration.class);

    public GreetingConfiguration() {
        log.info("created");
    }


}
