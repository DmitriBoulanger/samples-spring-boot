package de.dbo.samples.springboot.jbehave2.IT.commons.server;

/**
 * Test Container used in Integration Tests.
 * A container can be embedded (typically created in Spring-Boot environment) 
 * or it can be a remote server running in a virtual machine
 * 
 * @see TestContainerProperties
 * @see TestContainerSystemProperties
 * @see TestContainerType
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */
public interface TestContainer {

    public Integer port();

    public String host();

    /**
     * @return context-path of the deployed applications (sevices)
     */
    public String context();

    /**
     * @return URL to access services, controllers, etc.
     */
    public String contextURL();

    /**
     * 
     * @return pretty-print of the container configuration
     */
    public String print();

    /**
     * 
     * @return name used for debugging/logging
     */
    public String name();

   
    public TestContainer clone(final String name);

}