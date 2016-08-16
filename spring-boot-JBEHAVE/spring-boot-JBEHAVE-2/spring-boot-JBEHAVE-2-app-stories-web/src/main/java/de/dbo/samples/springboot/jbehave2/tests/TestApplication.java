package de.dbo.samples.springboot.jbehave2.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */

@SpringBootApplication
public class TestApplication {
    private static final Logger log = LoggerFactory.getLogger(TestApplication.class);

    public TestApplication() {
        log.info("created");
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
        ctx.close();
    }

}
