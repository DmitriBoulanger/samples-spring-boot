package de.dbo.samples.springboot.jbehave2.test.app3;

/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
/* Spring-Data */
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

/* ETC */
import de.dbo.samples.springboot.utilities.logging.LoggingInfo;

/**
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = {"de.dbo.samples.springboot.jbehave2.app3.domain"})
@ComponentScan(basePackages = {"de.dbo.samples.springboot.jbehave2.app3"})
public class UnitTestConfiguration {
    private static final Logger log = LoggerFactory.getLogger(UnitTestConfiguration.class);

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

        log.error("created - TEST", new Exception("test error with exception just to see it"));
        log.trace(LoggingInfo.printAvailableLoggers(120).toString());

        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("data/customers.json")});
        return factory;
    }
}
