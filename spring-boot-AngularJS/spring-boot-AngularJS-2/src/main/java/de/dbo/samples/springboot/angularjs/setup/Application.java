package de.dbo.samples.springboot.angularjs.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class Application {
    private final static Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
       ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
       ctx.registerShutdownHook();
    }
}
