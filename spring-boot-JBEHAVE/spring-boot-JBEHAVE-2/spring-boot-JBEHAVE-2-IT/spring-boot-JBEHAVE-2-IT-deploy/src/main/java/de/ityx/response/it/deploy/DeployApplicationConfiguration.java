package de.ityx.response.it.deploy;

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
 * Deployment Configuration of Integrated Test-DeployApplication.
 * Sources of all components to be the integrated are completely indicated in this configuration.
 * The data-repository in the A3 is indicated and loaded with test-data
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 */

@Configuration
@ComponentScan(value = {"de.ityx.response.it.deploy"
	
        // DeployApplication 1
        , "de.dbo.samples.springboot.jbehave2.app1"
        // DeployApplication 2
        , "de.dbo.samples.springboot.jbehave2.app2"
        // DeployApplication 3
        , "de.dbo.samples.springboot.jbehave2.app3"
})
@EnableElasticsearchRepositories(basePackages = {"de.dbo.samples.springboot.jbehave2.app3.domain"})
public class DeployApplicationConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DeployApplicationConfiguration.class);

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

        if (LOG.isTraceEnabled()) {
            LOG.trace(LoggingInfo.printAvailableLoggers(120).toString());
        }

        final Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("data/customers.json")});
        return factory;
    }
}
