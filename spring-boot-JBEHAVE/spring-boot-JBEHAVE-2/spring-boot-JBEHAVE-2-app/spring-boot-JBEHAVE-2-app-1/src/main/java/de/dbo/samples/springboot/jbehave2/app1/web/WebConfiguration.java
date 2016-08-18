package de.dbo.samples.springboot.jbehave2.app1.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"de.dbo.samples.springboot.jbehave2.app1.web.customer", "de.dbo.samples.springboot.jbehave2.app1.web.shoping"})
public class WebConfiguration {

}
