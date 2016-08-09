package de.dbo.samples.springboot.data.elasticsearch.rest;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.dbo.samples.springboot.utilities.logging.LoggingInfo;

@Configuration
@ComponentScan({"de.dbo.samples.springboot.data.greeting.test"})
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
