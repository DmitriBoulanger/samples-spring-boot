package de.dbo.samples.springboot.jbehave2.IT.commons.stepsimpl;

import de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration.ConfigurationJBehaveProperties;
import de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration.ConfigurationJBehaveSystemProperties;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.config.LogConfig;

public abstract class StepsBase {
    private static final Logger log = LoggerFactory.getLogger(StepsBase.class);
    private static final Object LOCK = new Object();
    
    static {
	LogConfig.logConfig().enablePrettyPrinting(false);
    }
    
    @Autowired
    private TestContainer testServer;
    
    @Autowired
    protected ConfigurationJBehaveProperties jbehaveProperties;
    
    
    protected TestContainer testServer(final String clonename) {
	synchronized (LOCK) {
	    return testServer.clone(clonename);
	}
    }
}
