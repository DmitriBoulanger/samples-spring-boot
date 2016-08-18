package de.dbo.samples.springboot.jbehave2.IT.web;

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
 * Configuration of Integrated Test-Application.
 * Firstly, Sources of all components to be the integrated are completely indicated
 * in this configuration.
 *
 * Second, the corresponding steps-implementations are loaded in
 * the application context of Integrated Test-Application
 *
 * Next, the data-repository in the A3 is indicated and loaded with test-data
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */

@Configuration
@ComponentScan(value = {
        // Steps-Factory for all Applications to be integrated
        "de.dbo.samples.springboot.jbehave2.IT.jb.web"
        // Application 1
        , "de.dbo.samples.springboot.jbehave2.app1"
        // Application 2
        , "de.dbo.samples.springboot.jbehave2.app2"
        // Application 3
        , "de.dbo.samples.springboot.jbehave2.app3"
})
@EnableElasticsearchRepositories(basePackages = {"de.dbo.samples.springboot.jbehave2.app3.domain"})
public class ITestApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ITestApplicationConfiguration.class);

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

        log.error("created - TEST", new Exception("test error with exception just to see it"));
        log.trace(LoggingInfo.printAvailableLoggers(120).toString());

        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("data/customers.json")});
        return factory;
    }

}
