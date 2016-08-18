package de.dbo.samples.springboot.jbehave2.tests;

import de.dbo.samples.springboot.jbehave2.app3.GreetingTestConfiguration;
import de.dbo.samples.springboot.utilities.logging.LoggingInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
@ComponentScan(value = {
	//
	"de.dbo.samples.springboot.jbehave2.app1",
	//
	"de.dbo.samples.springboot.jbehave2.app2",
	//
        "de.dbo.samples.springboot.jbehave2.app3"})

public class TestApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(TestApplicationConfiguration.class);


//    @Bean
//    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
//	
//	log.error("created - TEST", new Exception("test error with exception just to see it"));
//	log.trace(LoggingInfo.printAvailableLoggers(120).toString());
//	
//	Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
//	factory.setResources(new Resource[]{new ClassPathResource("data/customers.json")});
//	return factory;
//    }

}
