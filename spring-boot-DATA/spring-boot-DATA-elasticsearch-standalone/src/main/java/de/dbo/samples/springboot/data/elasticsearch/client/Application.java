package de.dbo.samples.springboot.data.elasticsearch.client;

import de.dbo.samples.springboot.data.elasticsearch.client.domain.Employee;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeeRepository;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.Skill;

import java.util.Arrays;

import javax.annotation.PostConstruct;

/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring-Boot */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
/* Spring-Data */
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
	final ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
	ctx.registerShutdownHook();
    }

    @Autowired
    private ApplicationClient client;
    
    public Application() {
	log.info("created");
    }

    @PostConstruct 
    public void runClientOperations() {
	log.info("init() ...");
	client.run();
    }
  
}
