package de.dbo.samples.springboot.jbehave0.tests;

import de.dbo.samples.springboot.jbehave0.app.domain.DomainConfiguration;
import de.dbo.samples.springboot.jbehave0.app.infrastructure.InfrastructureConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DomainConfiguration.class, InfrastructureConfiguration.class})
@ComponentScan
public class AcceptanceTestsConfiguration {
}
