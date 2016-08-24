package de.dbo.samples.springboot.jbehave2.IT.commons.server;

/**
 * System Properties to configure the Test Container.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public interface TestContainerSystemProperties {
    
    /**
     * address of the container.
     * Default is 127.0.0.1.
     */
    public static final String SYSTEM_PROPERTY_REMOTE_SERVER_ADDRESS  = "remote.container.address";
    
    /**
     * port of the container.
     * Default is 8080
     */
    public static final String SYSTEM_PROPERTY_REMOTE_SERVER_PORT     = "remote.container.port";

}
