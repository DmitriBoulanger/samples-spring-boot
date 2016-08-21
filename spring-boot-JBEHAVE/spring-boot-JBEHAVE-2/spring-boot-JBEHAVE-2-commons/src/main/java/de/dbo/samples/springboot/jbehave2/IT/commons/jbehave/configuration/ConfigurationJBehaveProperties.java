package de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration;

/* Utilities */
import de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Print;
import de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Print.ConfigurationPropertyTriple;
/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring-Boot*/
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(name="JBehave Configuration Properties", value={"classpath:jbehave.properties"}, ignoreResourceNotFound=true)
public class ConfigurationJBehaveProperties implements ConfigurationJBehaveSystemProperties {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationJBehaveProperties.class);
    
    public static final boolean DEFAULT_FAIL_IF_PENDING_STEP_FOUND = false;
    
    public static final int DEFAULT_STORIES_THREAD_CNT = 1;
    public static final int DEFAULT_STORIES_TIMEOUT   = 100;
    public static final boolean DEFAULT_STORIES_CONSOLE = false;
    
    public static final boolean DEFAULT_LOGGING_THRID_PARTIES_SILENT = true;
    

    // =========================================================================================================
    //                                  PROPERTIES
    // =========================================================================================================

    @Value("${" + SYSTEM_PROPERTY_JBEHAVE_FAIL_IF_PENDING_STEP_FOUND + ":${" + FAIL_IF_PENDING_STEP_FOUND +":" + DEFAULT_FAIL_IF_PENDING_STEP_FOUND +"}}")
    protected boolean stepsFailIfPendingStepFound;

    @Value("${" + SYSTEM_PROPERTY_JBEHAVE_STORIES_THREAD_CNT + ":${"+STORIES_THREAD_CNT+":" + DEFAULT_STORIES_THREAD_CNT +"}}")
    protected int storiesThreadCnt;
    
    @Value("${" + SYSTEM_PROPERTY_JBEHAVE_STORIES_TIMEOUT + ":${"+ STORIES_TIMEOUT +":" + DEFAULT_STORIES_TIMEOUT + "}}")
    protected int storiesTimeout;

    @Value("${" + SYSTEM_PROPERTY_JBEHAVE_STORIES_CONSOLE + ":${"+STORIES_CONSOLE+":" + DEFAULT_STORIES_CONSOLE + "}}")
    protected boolean storiesConsole;
    
    @Value("${" + SYSTEM_PROPERTY_JBEHAVE_LOGGING_THRID_PARTIES__SILENT + ":${"+LOGGING_THRID_PARTIES_SILENT+":" + DEFAULT_LOGGING_THRID_PARTIES_SILENT + "}}")
    protected boolean loggingThirdPartiesSilent;

    public ConfigurationJBehaveProperties() {
	log.info("created. HashCode=[" + hashCode() + "]");
    }

    /**
     * if the return value is true, then any pending step triggers test-failure
     */
    public boolean isStepsFailIfPendingStepFound() {
	return stepsFailIfPendingStepFound;
    }

    /**
     * maximal number of threads to run stories
     */
    public int getStoriesThreadCnt() {
	return storiesThreadCnt;
    }
    
    /**
     * story timeout, seconds
     */
    public int getStoriesTimeout() {
	return storiesTimeout;
    }


    /**
     * if the return value is false then JBehave story-reporting to the console is disabled
     */
    public boolean isStoriesConsole() {
	return storiesConsole;
    }
    
    /**
     * if the return value is true then all logging to console from third-parties is disabled
     */
    public boolean isLoggingThirdPartiesSilent() {
	return loggingThirdPartiesSilent;
    }
    
    // =========================================================================================================
    //                                  PRETTY-PRINT
    // =========================================================================================================
    
    public StringBuilder print(final boolean isDebugEnabled) {
	final ConfigurationPropertyTriple triple0 = 
		new ConfigurationPropertyTriple(q2(FAIL_IF_PENDING_STEP_FOUND, DEFAULT_FAIL_IF_PENDING_STEP_FOUND), isStepsFailIfPendingStepFound(), SYSTEM_PROPERTY_JBEHAVE_FAIL_IF_PENDING_STEP_FOUND);
	final ConfigurationPropertyTriple triple1 = 
		new ConfigurationPropertyTriple(q2(STORIES_THREAD_CNT, DEFAULT_STORIES_THREAD_CNT), getStoriesThreadCnt(), SYSTEM_PROPERTY_JBEHAVE_STORIES_THREAD_CNT);
	final ConfigurationPropertyTriple triple4 = 
		new ConfigurationPropertyTriple(q2(STORIES_THREAD_CNT, DEFAULT_STORIES_THREAD_CNT), getStoriesTimeout(), SYSTEM_PROPERTY_JBEHAVE_STORIES_TIMEOUT);
	final ConfigurationPropertyTriple triple2 = 
		new ConfigurationPropertyTriple(q2(STORIES_CONSOLE, DEFAULT_STORIES_CONSOLE), isStoriesConsole(), SYSTEM_PROPERTY_JBEHAVE_STORIES_CONSOLE);
	final ConfigurationPropertyTriple triple3 = 
		new ConfigurationPropertyTriple(q2(LOGGING_THRID_PARTIES_SILENT, DEFAULT_LOGGING_THRID_PARTIES_SILENT), isLoggingThirdPartiesSilent(), SYSTEM_PROPERTY_JBEHAVE_LOGGING_THRID_PARTIES__SILENT);
	
	return Print.configurationProperties("JBehave Properties:",
		new ConfigurationPropertyTriple[]{triple0,triple1,triple2,triple3,triple4});
    }
    
//    private static final String q2(final String name, final String defaltValue) {
//	return name + " (" + defaltValue + ")";
//    }
    
    private static final String q2(final String name, final boolean defaltValue) {
	return name + " (" + defaltValue + ")";
    }
    
    private static final String q2(final String name, final int defaltValue) {
	return name + " (" + defaltValue + ")";
    }
    
    // =========================================================================================================
    //          PropertySourcesPlaceHolderConfigurer is required for @Value("{}") annotations
    // =========================================================================================================
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	return new PropertySourcesPlaceholderConfigurer();
    }
}
