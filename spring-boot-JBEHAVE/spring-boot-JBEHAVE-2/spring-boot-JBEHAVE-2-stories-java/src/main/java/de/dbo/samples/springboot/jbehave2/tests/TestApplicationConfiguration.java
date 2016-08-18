package de.dbo.samples.springboot.jbehave2.tests;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"de.dbo.samples.springboot.jbehave2.app1.domain," +
		"de.dbo.samples.springboot.jbehave2.app1.infrastructure"})
public class TestApplicationConfiguration {

}
