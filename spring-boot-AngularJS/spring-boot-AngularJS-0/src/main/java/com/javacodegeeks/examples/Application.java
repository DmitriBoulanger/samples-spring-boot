package com.javacodegeeks.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.javacodegeeks.examples")
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
