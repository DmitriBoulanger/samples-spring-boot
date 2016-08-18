package de.dbo.samples.springboot.jbehave2.test.app2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class JUnitTestServer {

    @Autowired
    Environment environment;

    public Integer getPort() {
	final String port = environment.getProperty("local.server.port");
	if (null==port) {
	    return null;
	}
        return Integer.parseInt(port);
    }
    
    public String getHost() {
        return "localhost";
    }

    public String print() {
        return  getHost() + ":" + getPort();
    }
}
