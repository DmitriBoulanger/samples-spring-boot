package de.dbo.samples.springboot.rest.greeting.core;

import de.dbo.samples.springboot.utilities.print.Print;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan({"de.dbo.samples.springboot.rest.greeting"})
public class GreetingApplicationContextProvider implements ApplicationContextAware {
   
    private ApplicationContext applicationContext;

    public StringBuilder printBeans() {
	return Print.beanNames(applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;
    }

    public ApplicationContext getContext() {
	return applicationContext;
    }

}
