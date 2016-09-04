package de.dbo.samples.springboot.test.mvc.greeting;

/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring-Boot */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
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
@SpringBootApplication(scanBasePackageClasses = { GreetingTestConfiguration.class})
@EnableAutoConfiguration
public class GreetingTestApplication extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(GreetingTestApplication.class);
    
    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(GreetingTestApplication.class, args);
        ctx.registerShutdownHook();
        log.info("...");
    }

    public GreetingTestApplication() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	
	return builder;
}
    
    

}
