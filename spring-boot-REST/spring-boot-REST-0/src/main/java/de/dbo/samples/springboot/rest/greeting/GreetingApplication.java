package de.dbo.samples.springboot.rest.greeting;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreetingApplication {
    private static final Logger logger = LoggerFactory.getLogger(GreetingApplication.class);

    @PostConstruct
    public void logSomething() {
	logger.warn("GreetingApplication Warning Message");
	logger.error("GreetingApplication Error Message");
    }

    public static void main(String[] args) {
	SpringApplication.run(GreetingApplication.class, args);
    }
}
