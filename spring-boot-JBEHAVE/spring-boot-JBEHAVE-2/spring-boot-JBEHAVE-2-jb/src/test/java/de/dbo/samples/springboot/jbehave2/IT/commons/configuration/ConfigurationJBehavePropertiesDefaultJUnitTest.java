package de.dbo.samples.springboot.jbehave2.IT.commons.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ConfigurationJBehavePropertiesDefaultJUnitTest {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationJBehavePropertiesDefaultJUnitTest.class);
    
    
    @Ignore
    @Test
    public void testDefaultValuesOfJBehavePropertis() {
	ConfigurationJBehaveProperties jbehaveProperties =  new ConfigurationJBehaveProperties();
	log.info("Deafult values of " + jbehaveProperties.print(true).toString());
	
	// assert the default values in ConfigurationJBehaveProperties
	assertThat("Default value for stories thread-counter is not as expected",  jbehaveProperties.getStoriesThreadCnt(), equalTo(1));
	assertThat("Default failure strategy for pending steps is not as expected",  !jbehaveProperties.isStepsFailIfPendingStepFound() );
    }
}
