package de.dbo.samples.springboot.rest.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application to demonstrate testing.
 * It will intentionally not start without MySQL, the test will still run.
 */
@SpringBootApplication
public class SampleTestApplication {

    public static void main(String[] args) {
	SpringApplication.run(SampleTestApplication.class, args);
    }

}
