package de.dbo.samples.springboot.jbehave2.IT.java;

import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerLocalhost;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerType;

import org.springframework.stereotype.Component;

/**
 * Null Server. In this IT no server is used
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
    
    public TestServer clone(final String name) {
	return new TestServer() {
	    
	    private final String clonename = name;
	    private final String  clonehost = ITestServer.this.getHost();
	    private final Integer cloneport = ITestServer.this.getPort();
	    private final String print = ITestServer.this.print();
	    
	    @Override
	    public String name() {
		return clonename;
	    }
	    
	    @Override
	    public Integer getPort() {
		return cloneport;
	    }

	    @Override
	    public String getHost() {
		return clonehost;
	    }

	    @Override
	    public String print() {
		return print;
	    }

	    @Override
	    public TestServer clone(String name) {
		throw new IllegalStateException("Server-clone cannot be cloned again!");
	    }
	};
		
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
