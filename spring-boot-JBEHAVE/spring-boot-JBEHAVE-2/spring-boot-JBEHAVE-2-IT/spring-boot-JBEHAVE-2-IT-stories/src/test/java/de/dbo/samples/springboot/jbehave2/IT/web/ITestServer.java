package de.dbo.samples.springboot.jbehave2.IT.web;

import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerLocalhost;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerType;

@Component
public class ITestServer extends TestServerLocalhost implements TestServer {

    @Override
    public String name() {
        return "Default embedded Spring-Boot WEB-Container";
    }

    @Override
    public TestServerType type() {
        return TestServerType.EMBEDED_WEB_CONTAINER;
    }

    @Override
    public String context() {
        return "/IT123";
    }

}
