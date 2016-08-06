package de.dbo.samples.springboot.rest.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GreetingConfiguration.class);

    public GreetingConfiguration() {
        log.error("created", new Exception("test error with exception just to see it"));
    }


}
