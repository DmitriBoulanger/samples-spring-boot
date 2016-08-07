package de.dbo.samples.springboot.rest.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("PRODUCTION")
@ComponentScan({"de.dbo.samples.springboot.rest.greeting"})
public class GreetingConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GreetingConfiguration.class);

    public GreetingConfiguration() {
        log.info("created - PRODUCTION");
    }


}
