package de.dbo.samples.springboot.jbehave2.IT.java;

import org.springframework.stereotype.Component;

@Component
public class ITestServer {

    public Integer getPort() {
        return null;
    }

    public String getHost() {
        return null;
    }

    public String print() {
        return getHost() + ":" + getPort();
    }
}
