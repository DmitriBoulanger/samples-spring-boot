package de.dbo.samples.springboot.hal.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
       final ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
       ctx.registerShutdownHook();
    }
}
