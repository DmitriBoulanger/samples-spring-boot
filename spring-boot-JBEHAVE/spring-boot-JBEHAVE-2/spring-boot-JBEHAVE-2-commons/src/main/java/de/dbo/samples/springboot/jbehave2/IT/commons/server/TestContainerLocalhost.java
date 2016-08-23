package de.dbo.samples.springboot.jbehave2.IT.commons.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

/**
 * Local server typically initialized in Spring-Boot Tests
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */
public abstract class TestContainerLocalhost implements TestContainer {

    /* Spring-Boot Environment */
    @Autowired
    private Environment environment;
    
    @Autowired
    private TestContainerProperties testContainerProperties;
    
    @Override
    public abstract String name();

    public abstract TestContainerType type();

    @Override
    public Integer port() {
	final TestContainerType type = type();
	final String port;
	switch(type) {
	case EMBEDED_WEB_CONTAINER:
	    port = environment.getProperty("local.server.port"); /* from application.properties */
	    if (null == port) {
		return null;
	    }
	    
	    break;
	case REMOTE_WEB_CONTAINER:
	    return testContainerProperties.remoteServerPort; 
	    
	case NULL:
	    return null;
	    
	    default:
		throw new IllegalStateException("Uknown type of the test-container:" + type.name());
	}
	
	return Integer.parseInt(port);
    }

    @Override
    public String host() {
	final TestContainerType type = type();
	switch(type) {
	case EMBEDED_WEB_CONTAINER:
		return "localhost";

	case REMOTE_WEB_CONTAINER:
	    return environment.getProperty("server.address"); /* from application.properties */
	    
	case NULL:
	    return null;
	    
	    default:
		throw new IllegalStateException("Uknown type of the test-container:" + type.name());
	}
	
    }

    @Override
    public String context() {
        return environment.getProperty("server.context-path");
    }

    @Override
    public String contextURL() {
        return "http://" + host() + ":" + port() + context();
    }

    @Override
    public String print() {
        return type() + " " + name() + "[" + host() + ":" + port() + "]";
    }

    @Override
    public TestContainer clone(final String name) {
        return new TestContainer() {

            private final String  clonename  = new String(name);
            private final String  clonehost  = new String(TestContainerLocalhost.this.host());
            private final Integer cloneport  = new Integer(TestContainerLocalhost.this.port());
            private final String  print      = new String(TestContainerLocalhost.this.print() + "as clone [" + clonename + "]");
            private final String  context    = new String(TestContainerLocalhost.this.context());
            private final String  contextURL = new String(TestContainerLocalhost.this.contextURL());

            @Override
            public String name() {
                return clonename;
            }

            @Override
            public Integer port() {
                return cloneport;
            }

            @Override
            public String host() {
                return clonehost;
            }

            @Override
            public String print() {
                return print;
            }

            @Override
            public TestContainer clone(final String name) {
                throw new IllegalStateException("Server-clone cannot be cloned again. Do it only once!");
            }

            @Override
            public String context() {
                return context;
            }

            @Override
            public String contextURL() {
                return contextURL;
            }
        };
    }
}
