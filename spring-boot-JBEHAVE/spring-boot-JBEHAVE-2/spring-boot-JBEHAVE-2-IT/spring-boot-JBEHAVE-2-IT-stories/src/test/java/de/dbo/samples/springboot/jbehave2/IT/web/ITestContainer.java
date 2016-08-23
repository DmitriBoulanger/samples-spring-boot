package de.dbo.samples.springboot.jbehave2.IT.web;

import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestContainer;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestContainerLocalhost;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestContainerType;

@Component
public class ITestContainer extends TestContainerLocalhost implements TestContainer {

    @Override
    public String name() {
        return "Tomcat WEB-Container";
    }

    @Override
    public TestContainerType type() {
        return TestContainerType.REMOTE_WEB_CONTAINER;
    }

    @Override
    public String context() {
        return "/IT123";
    }

}
