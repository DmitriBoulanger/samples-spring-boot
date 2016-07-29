package de.dbo.samples.springboot.camel.router;

import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootRouter extends FatJarRouter {

    @Autowired
    private HealthEndpoint health;

    @Override
    public void configure() {
        from("timer:trigger")
                .transform().simple("ref:myBean")
                .to("log:out");

        from("timer:status")
            .bean(health, "invoke")
            .log("Health is ${body}");
    }

    @Bean
    String myBean() {
        return "I'm Spring bean!";
    }

}
