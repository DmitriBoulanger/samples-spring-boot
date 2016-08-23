package de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration;

/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring-Boot */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * Spring-Boot Test-Application.
 * It has to be a normal application with auto-configuration in the classpath
 *
 * @see ConfigurationJBehavePropertiesTest
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
@SpringBootApplication(exclude={WebClientAutoConfiguration.class})
public class ConfigurationJBehavePropertiesTestApplication {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationJBehavePropertiesTestApplication.class);

    public ConfigurationJBehavePropertiesTestApplication() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(ConfigurationJBehavePropertiesTestApplication.class, args);
        ctx.registerShutdownHook();
    }
}
