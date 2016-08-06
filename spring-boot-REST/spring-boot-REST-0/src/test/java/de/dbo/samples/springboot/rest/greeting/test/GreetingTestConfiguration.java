package de.dbo.samples.springboot.rest.greeting.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"de.dbo.samples.springboot.rest.greeting.test"})
public class GreetingTestConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GreetingTestConfiguration.class);

    public GreetingTestConfiguration() {
        log.error("created - test", new Exception("test error with exception just to see it"));
    }


}
