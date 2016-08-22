package de.dbo.samples.springboot.jbehave2.IT.deoloy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import de.dbo.samples.springboot.utilities.logging.LoggingInfo;

/**
 * Deployment Configuration of Integrated Test-Application.
 * Sources of all components to be the integrated are completely indicated in this configuration.
 * The data-repository in the A3 is indicated and loaded with test-data
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 */

@Configuration
@ComponentScan(value = {"de.dbo.samples.springboot.jbehave2.IT.deploy"
	
        // Application 1
        , "de.dbo.samples.springboot.jbehave2.app1"
        // Application 2
        , "de.dbo.samples.springboot.jbehave2.app2"
        // Application 3
        , "de.dbo.samples.springboot.jbehave2.app3"
})
@EnableElasticsearchRepositories(basePackages = {"de.dbo.samples.springboot.jbehave2.app3.domain"})
public class ApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

        if (log.isTraceEnabled()) {
            log.trace(LoggingInfo.printAvailableLoggers(120).toString());
        }

        final Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("data/customers.json")});
        return factory;
    }
}
