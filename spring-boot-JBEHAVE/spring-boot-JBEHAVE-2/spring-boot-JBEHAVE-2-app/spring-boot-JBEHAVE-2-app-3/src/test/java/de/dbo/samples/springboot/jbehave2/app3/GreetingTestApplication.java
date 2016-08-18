package de.dbo.samples.springboot.jbehave2.app3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GreetingTestApplication {

    public static void main(String[] args) throws Exception {
         final ConfigurableApplicationContext ctx =  SpringApplication.run(GreetingTestApplication.class, args);
//         ctx.close();
    }

}
