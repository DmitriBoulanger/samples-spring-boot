package de.dbo.samples.springboot.jbehave2.tests;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"de.dbo.samples.springboot.jbehave2.app.domain," +
        "de.dbo.samples.springboot.jbehave2.app2.domain," +
        "de.dbo.samples.springboot.jbehave2.app.infrastructure," +
        "de.dbo.samples.springboot.jbehave2.app2.infrastructure," +
        "de.dbo.samples.springboot.jbehave2.app.web," +
        "de.dbo.samples.springboot.jbehave2.app2.web"})

public class TestApplicationConfiguration {

}
