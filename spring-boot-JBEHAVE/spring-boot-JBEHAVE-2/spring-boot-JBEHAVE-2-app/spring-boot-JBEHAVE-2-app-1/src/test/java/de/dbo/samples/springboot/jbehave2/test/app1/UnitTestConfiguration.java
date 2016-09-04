package de.dbo.samples.springboot.jbehave2.test.app1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"de.dbo.samples.springboot.mvc.greeting.mvc, de.dbo.samples.springboot.mvc.greeting.cfg"})
public class UnitTestConfiguration {
 
}
