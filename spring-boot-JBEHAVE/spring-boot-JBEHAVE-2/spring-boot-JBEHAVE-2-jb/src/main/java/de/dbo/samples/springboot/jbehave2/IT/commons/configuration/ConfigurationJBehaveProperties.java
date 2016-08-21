package de.dbo.samples.springboot.jbehave2.IT.commons.configuration;

import de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Pad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(name="JBehave Configuration Properties", value={"classpath:jbehave.properties"}, ignoreResourceNotFound=true)
public class ConfigurationJBehaveProperties implements ConfigurationJBehaveSystemProperties {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationJBehaveProperties.class);

    /*
     * PropertySourcesPlaceHolderConfigurer is required for @Value("{}") annotations.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	return new PropertySourcesPlaceholderConfigurer();
    }

    // =========================================================================================================
    //                                  PROPERTIES
    // =========================================================================================================

    @Value("${" + SYSTEM_PROPERTY_JBEHAVE_FAIL_IF_PENDING_STEP_FOUND + ":${" + FAIL_IF_PENDING_STEP_FOUND +":false}}")
    protected boolean stepsFailIfPendingStepFound;

    @Value("${" + SYSTEM_PROPERTY_JBEHAVE_STORIES_THREAD_CNT + ":${"+STORIES_THREAD_CNT+":1}}")
    protected int storiesThreadCnt;

    @Value("${" + SYSTEM_PROPERTY_JBEHAVE_STORIES_CONSOLE + ":${"+STORIES_CONSOLE+":false}}")
    protected boolean storiesConsole;

    public ConfigurationJBehaveProperties() {
	log.info("created. HashCode=[" + hashCode() + "]");
    }

    public boolean isStepsFailIfPendingStepFound() {
	return stepsFailIfPendingStepFound;
    }

    public int getStoriesThreadCnt() {
	return storiesThreadCnt;
    }

    public boolean isStoriesConsole() {
	return storiesConsole;
    }

    //    public void setStepsFailIfPendingStepFound(boolean stepsFailIfPendingStepFound) {
    //        this.stepsFailIfPendingStepFound = stepsFailIfPendingStepFound;
    //    }

    //    public void setStoriesThreadCnt(int storiesThreadCnt) {
    //        this.storiesThreadCnt = storiesThreadCnt;
    //    }

    //    public void setStoriesConsole(boolean storiesConsole) {
    //        this.storiesConsole = storiesConsole;
    //    }


    // =========================================================================================================
    //                                  PRETTY-PRINT
    // =========================================================================================================
    
    public StringBuilder print(boolean isDebugEnabled) {
	final StringBuilder sb = new StringBuilder("JBehave Properties:");
	appendPropery(sb, FAIL_IF_PENDING_STEP_FOUND, "" + isStepsFailIfPendingStepFound(), SYSTEM_PROPERTY_JBEHAVE_FAIL_IF_PENDING_STEP_FOUND, isDebugEnabled);
	appendPropery(sb, STORIES_THREAD_CNT, "" + getStoriesThreadCnt(), SYSTEM_PROPERTY_JBEHAVE_STORIES_THREAD_CNT, isDebugEnabled);
	appendPropery(sb, STORIES_CONSOLE, "" + isStoriesConsole(), SYSTEM_PROPERTY_JBEHAVE_STORIES_CONSOLE, isDebugEnabled);
	return sb;    
    }

    private static final int SPACE1 = 30;
    private static final int SPACE2 = 40;
    private static final int SPACE3 = 6;
    
    private static final void appendPropery(final StringBuilder sb, 
	    String property, String propertyValue, String system, boolean isDebugEnabled ) {
	sb.append("\n\t - " + Pad.right(property,SPACE1)    +    " = " + Pad.right(propertyValue,SPACE3));
	if (!isDebugEnabled) {
	    return;
	}

	final String systemValue = System.getProperty(system);
	final String marker;
	if (null==systemValue) {
	    marker = "    #####  ";
	} else {
	    marker = "   <=====  ";
	}
	sb.append(marker + Pad.right( system, SPACE2) + " = " + systemValue);
    }


}
