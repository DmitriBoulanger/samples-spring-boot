package de.ityx.response.it.docker;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.BeforeClass;

public class TestAbstraction {
    
  protected static final Properties TEST_PROPERTIES = new Properties();
    
    @BeforeClass
    public static void init() throws IOException {
	TEST_PROPERTIES.load(ClassLoader.getSystemClassLoader().getResourceAsStream("test.properties"));
    }
    
    public static File testRepository() {
	return new File(TEST_PROPERTIES.getProperty("spring-cloud-microservice-example.directory"));
    }
    
    public static File testComposeFile() {
	return new File(TEST_PROPERTIES.getProperty("compose-file"));
    }
    
    public static File sourceDockerDirectory(final String service) {
	return new File( testRepository(), service + "/target/docker");
    }
    

}
