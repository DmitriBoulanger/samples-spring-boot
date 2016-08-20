package de.dbo.samples.springboot.jbehave2.IT.commons.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Local server typically initialized in Spring-Boot Tests
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public abstract class TestServerLocalhost  {

    /* Spring-Boot Environment */
    @Autowired
    private Environment environment;
    
    public abstract String name();
    
    public abstract TestServerType  type();
    
    public Integer getPort() {
	final String port = environment.getProperty("local.server.port"); /* from application.properties */
	if (null==port) {
	    return null;
	}
        return Integer.parseInt(port);
    }
    
    public String getHost() {
        return "localhost";
    }

    public String print() {
        return type() + " " + name() + "[" + getHost() + ":" + getPort() + "]";
    }
}
