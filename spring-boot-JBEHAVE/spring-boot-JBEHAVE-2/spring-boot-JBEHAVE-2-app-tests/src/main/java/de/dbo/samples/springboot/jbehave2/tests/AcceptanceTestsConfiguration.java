package de.dbo.samples.springboot.jbehave2.tests;

// SPRING
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// APPLICATION
import de.dbo.samples.springboot.jbehave2.app.domain.DomainConfiguration;
import de.dbo.samples.springboot.jbehave2.app.infrastructure.InfrastructureConfiguration;

@Configuration
@Import({DomainConfiguration.class, InfrastructureConfiguration.class})
@ComponentScan
public class AcceptanceTestsConfiguration {}
