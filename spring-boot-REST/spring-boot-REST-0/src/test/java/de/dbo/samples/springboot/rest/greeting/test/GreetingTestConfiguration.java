package de.dbo.samples.springboot.rest.greeting.test;

import de.dbo.samples.springboot.utilities.logging.LoggingInfo;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan({"de.dbo.samples.springboot.rest.greeting.test"})
@Profile("TEST")
public class GreetingTestConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GreetingTestConfiguration.class);
    
    @PostConstruct
    public void logSomeMessges() {
	log.info("Test Information Message");
	log.warn("Test Warning Message");
	log.error("Test Error Message");
    }

    public GreetingTestConfiguration() {
        log.error("created - TEST", new Exception("test error with exception just to see it"));
        log.trace(LoggingInfo.printAvailableLoggers(120).toString());
    }
    
  


}
