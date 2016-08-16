package de.dbo.samples.springboot.jbehave2.tests;

import org.springframework.stereotype.Component;

@Component
public class TestServer {

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String print() {
        return "localhost:" + port;
    }

}
