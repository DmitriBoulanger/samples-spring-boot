package de.dbo.samples.springboot.jbehave2.IT.web;

import de.dbo.samples.springboot.jbehave2.IT.commons.TestServer;
import de.dbo.samples.springboot.jbehave2.IT.commons.TestServerLocalhost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ITestServer extends TestServerLocalhost implements TestServer {

    @Autowired
    Environment environment;
    
    public String name() {
	return "WEB Container";
    }
 
}
