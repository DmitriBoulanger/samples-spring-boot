package de.dbo.samples.springboot.jbehave2.IT;

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
public class ITTestApplication {
    private static final Logger log = LoggerFactory.getLogger(ITTestApplication.class);

    public ITTestApplication() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(ITTestApplication.class, args);
        log.info("ITTest Server " + ctx.getBean(ITTestServer.class).print());
        final long pause = 300;
        try {
            log.info("sleeping " + pause + " sec. ...");
            Thread.sleep(pause * 1000);
        }
        catch(InterruptedException e) {
            log.error("Can't sleep any more:", e);
        }
        ctx.close();
    }
}
