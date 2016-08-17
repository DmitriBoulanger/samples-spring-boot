package de.dbo.samples.springboot.jbehave2.app3;

import de.dbo.samples.springboot.utilities.logging.LoggingInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@TestConfiguration
public class GreetingTestConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GreetingTestConfiguration.class);


    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
	
	log.error("created - TEST", new Exception("test error with exception just to see it"));
	log.trace(LoggingInfo.printAvailableLoggers(120).toString());
	
	Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
	factory.setResources(new Resource[]{new ClassPathResource("data/customers.json")});
	return factory;
    }

}
