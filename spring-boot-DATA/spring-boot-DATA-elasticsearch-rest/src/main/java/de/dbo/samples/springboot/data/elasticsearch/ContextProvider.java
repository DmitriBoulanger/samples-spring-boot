package de.dbo.samples.springboot.data.elasticsearch;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.utilities.print.Print;

@Component
@ComponentScan({"de.dbo.samples.springboot.data.elasticsearch"})
public class ContextProvider implements ApplicationContextAware {

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
