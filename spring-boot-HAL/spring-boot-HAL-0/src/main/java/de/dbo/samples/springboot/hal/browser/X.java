package de.dbo.samples.springboot.hal.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

public class X implements BeanFactoryPostProcessor, PriorityOrdered {
    private static final Logger LOG = LoggerFactory.getLogger(X.class);

    @Override
    public int getOrder() {
	return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	System.setProperty("abc", "ABC");
	LOG.info("abc=" + System.getProperty("abc"));
	
	
    }

}
