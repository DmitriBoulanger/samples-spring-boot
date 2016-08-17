package de.dbo.samples.springboot.jbehave2.app.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"de.dbo.samples.springboot.jbehave2.app.web.customer", "de.dbo.samples.springboot.jbehave2.app.web.shoping"})
public class WebConfiguration {

}
