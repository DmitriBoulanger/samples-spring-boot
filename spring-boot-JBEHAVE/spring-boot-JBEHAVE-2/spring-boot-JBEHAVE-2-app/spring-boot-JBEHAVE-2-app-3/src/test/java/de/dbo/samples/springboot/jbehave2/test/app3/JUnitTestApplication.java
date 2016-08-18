package de.dbo.samples.springboot.jbehave2.test.app3;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import de.dbo.samples.springboot.jbehave2.app3.domain.CustomerRepository;

@SpringBootApplication
public class JUnitTestApplication {
    private static final Logger log = LoggerFactory.getLogger(JUnitTestApplication.class);

    @Autowired
    private CustomerRepository  repository;

    public JUnitTestApplication() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @PostConstruct
    public void init() {
        log.info("repository available. HashCode=[" + repository.hashCode() + "]");
        /*  Attention: at this moment the repository is NOT really operational! */
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
