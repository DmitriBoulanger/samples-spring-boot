package de.dbo.samples.springboot.jbehave2.IT.commons.server;

/**
 * Test Server used in Integration Tests
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public interface TestServer {

    public Integer getPort();

    public String getHost();

    public String print();
    
    public String name();

}