package de.dbo.samples.springboot.jbehave2.IT.commons.stepsimpl;

import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;

import org.springframework.beans.factory.annotation.Autowired;

public class StepsBase {
    
    @Autowired
    protected TestServer testServer;
    
}
