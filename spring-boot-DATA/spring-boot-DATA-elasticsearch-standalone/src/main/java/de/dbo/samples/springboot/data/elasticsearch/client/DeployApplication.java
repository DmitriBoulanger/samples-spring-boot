package de.dbo.samples.springboot.data.elasticsearch.client;

import javax.annotation.PostConstruct;

/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring-Boot */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DeployApplication extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(DeployApplication.class);
    
    private static final Class<? extends SpringBootServletInitializer> APPLICATION_CLASS = DeployApplication.class;

    public static void main(String[] args) {
	final ConfigurableApplicationContext ctx = SpringApplication.run(DeployApplication.class, args);
	ctx.registerShutdownHook();
    }
    
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
	final DeployApplicationConfiguration deployApplicationConfiguration 
		= (DeployApplicationConfiguration) applicationContext.getBean(DeployApplicationConfiguration.class);
	
	log.info("initialized. Using deployment configuration [" + deployApplicationConfiguration.getClass().getName() +"] ...");
    }
    

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(APPLICATION_CLASS);
    }

}
