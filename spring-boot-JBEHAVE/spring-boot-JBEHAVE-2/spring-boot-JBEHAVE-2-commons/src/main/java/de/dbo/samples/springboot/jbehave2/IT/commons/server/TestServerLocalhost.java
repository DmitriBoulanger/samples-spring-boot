package de.dbo.samples.springboot.jbehave2.IT.commons.server;

import org.springframework.beans.factory.annotation.Autowired;
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
public abstract class TestServerLocalhost implements TestServer {

    /* Spring-Boot Environment */
    @Autowired
    private Environment environment;

    @Override
    public abstract String name();

    public abstract TestServerType type();

    @Override
    public Integer port() {
        final String port = environment.getProperty("local.server.port"); /* from application.properties */
        if (null == port) {
            return null;
        }
        return Integer.parseInt(port);
    }

    @Override
    public String host() {
        return "localhost";
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
    public TestServer clone(final String name) {
        return new TestServer() {

            private final String  clonename  = new String(name);
            private final String  clonehost  = new String(TestServerLocalhost.this.host());
            private final Integer cloneport  = new Integer(TestServerLocalhost.this.port());
            private final String  print      = new String(TestServerLocalhost.this.print() + "as clone [" + clonename + "]");
            private final String  context    = new String(TestServerLocalhost.this.context());
            private final String  contextURL = new String(TestServerLocalhost.this.contextURL());

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
            public TestServer clone(final String name) {
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
