package de.dbo.samples.springboot.jbehave2.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TestServer {

    @Autowired
    Environment environment;

    public int getPort() {
        return Integer.parseInt(environment.getProperty("local.server.port"));
    }
    
    public String getHost() {
        return "localhost";
    }

    public String print() {
        return  getHost() + ":" + getPort();
    }
}
