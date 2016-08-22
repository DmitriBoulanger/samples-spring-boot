package de.dbo.samples.springboot.jbehave2.IT.web;

import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerLocalhost;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerType;

import org.springframework.stereotype.Component;

@Component
public class ITestServer extends TestServerLocalhost implements TestServer {

    public String name() {
	return "Default embedded Spring-Boot WEB-Container";
    }
    
    public TestServerType type() {
	return TestServerType.EMBEDED_WEB_CONTAINER;
    }
 
}
