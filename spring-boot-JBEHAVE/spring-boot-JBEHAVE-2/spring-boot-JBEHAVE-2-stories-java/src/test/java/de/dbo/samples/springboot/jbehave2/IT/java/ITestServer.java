package de.dbo.samples.springboot.jbehave2.IT.java;

import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerLocalhost;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerType;

import org.springframework.stereotype.Component;

/**
 * Null Server.
 * In this IT no server is used
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */

@Component
public final class ITestServer extends TestServerLocalhost implements TestServer {
    
    /**
     * name of this Null-Server
     */
    public String name() {
	return "A1 Null Server";
    }
    
    public TestServerType type() {
	return TestServerType.NULL;
    }

    /**
     * @return null
     */
    public Integer getPort() {
        return null;
    }

    /**
     * @return null
     */
    public String getHost() {
        return null;
    }
}
