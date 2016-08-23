package de.dbo.samples.springboot.jbehave2.IT.web;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import de.dbo.samples.springboot.jbehave2.app3.domain.CustomerRepository;

/**
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */
@SpringBootApplication
public class ITestApplication {
    private static final Logger log = LoggerFactory.getLogger(ITestApplication.class);

    public ITestApplication() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Autowired
    public CustomerRepository customerRepository;

    @PostConstruct
    public void init() {
        log.info("repository available. HashCode=[" + customerRepository.hashCode() + "]");
        /*  Attention: at this moment the repository is NOT really operational! */
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(ITestApplication.class, args);
        ctx.registerShutdownHook();
        log.info("ITest Server " + ctx.getBean(ITestContainer.class).print());
    }
}
