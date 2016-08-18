package de.dbo.samples.springboot.jbehave2.test.app1;

/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring-Boot */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * JUnit-Application for the app1-components.
 * A Developer Tool
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
@SpringBootApplication(scanBasePackageClasses = {JUnitTestConfiguration.class})
public class JUnitTestApplication {
    private static final Logger log = LoggerFactory.getLogger(JUnitTestApplication.class);

    public JUnitTestApplication() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(JUnitTestApplication.class, args);
        log.info("Test Server " + ctx.getBean(JUnitTestServer.class).print());
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
