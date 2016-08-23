package de.dbo.samples.springboot.jbehave2.IT.commons.server;

import de.dbo.samples.springboot.jbehave2.IT.commons.util.print.special.Print;
import de.dbo.samples.springboot.jbehave2.IT.commons.util.print.special.Print.ConfigurationPropertyTriple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(name = "Test Continer Properties", value = {"file:container.properties"}, ignoreResourceNotFound = true)
public class TestContainerProperties implements TestContainerSystemProperties {
    
    public static final int DEFAULT_REMOTE_SERVER_PORT       = 8080;
    public static final String DEFAULT_REMOTE_SERVER_ADDRESS = "127.0.0.1";
    
    @Value("${"+SYSTEM_PROPERTY_REMOTE_SERVER_PORT+":"+ DEFAULT_REMOTE_SERVER_PORT +"}")
    protected int remoteServerPort;
    
    @Value("${"+SYSTEM_PROPERTY_REMOTE_SERVER_ADDRESS+":"+ DEFAULT_REMOTE_SERVER_ADDRESS+"}")
    protected String remoteServerAddress;
    

    public int remoteServerPort() {
        return remoteServerPort;
    }

    public String remoteServerAddress() {
        return remoteServerAddress;
    }
    
    public StringBuilder print() {
        final ConfigurationPropertyTriple triple0 =
            new ConfigurationPropertyTriple(Print.q2("port", DEFAULT_REMOTE_SERVER_PORT), remoteServerPort(),  SYSTEM_PROPERTY_REMOTE_SERVER_PORT);
        
        final ConfigurationPropertyTriple triple1 =
                new ConfigurationPropertyTriple(Print.q2("address", DEFAULT_REMOTE_SERVER_ADDRESS), remoteServerAddress(),  SYSTEM_PROPERTY_REMOTE_SERVER_ADDRESS);
        
        return Print.configurationProperties("Test Container:",
                new ConfigurationPropertyTriple[]{triple0, triple1});
    }
    
    // =========================================================================================================
    //          PropertySourcesPlaceHolderConfigurer is required for @Value("{}") annotations
    // =========================================================================================================

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


}
