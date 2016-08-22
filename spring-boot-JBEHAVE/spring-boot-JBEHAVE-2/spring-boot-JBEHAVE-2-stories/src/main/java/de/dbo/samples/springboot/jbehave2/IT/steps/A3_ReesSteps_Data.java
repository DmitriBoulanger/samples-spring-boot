package de.dbo.samples.springboot.jbehave2.IT.steps;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextData;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;


public final class A3_ReesSteps_Data extends ContextData  {
    
    private String host;
    
    private Integer port;
    
    public void setTestSever(final TestServer testServer) {
	host = testServer.getHost();
	port = testServer.getPort();
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
    
    
    
    

   

}
