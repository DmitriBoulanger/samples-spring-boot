package de.dbo.samples.springboot.jbehave2.IT.commons.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration.ConfigurationJBehaveProperties;

/**
 *
 * Spring-Boot Test-Application.
 * The configuration declares the bean to be tested
 *
 * @see ConfigurationJBehavePropertiesTestConfiguration
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */

@Configuration
public class ConfigurationJBehavePropertiesTestConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationJBehavePropertiesTestConfiguration.class);

    public ConfigurationJBehavePropertiesTestConfiguration() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Bean
    public ConfigurationJBehaveProperties configurationJBehaveProperties() {
        return new ConfigurationJBehaveProperties();
    }

}
