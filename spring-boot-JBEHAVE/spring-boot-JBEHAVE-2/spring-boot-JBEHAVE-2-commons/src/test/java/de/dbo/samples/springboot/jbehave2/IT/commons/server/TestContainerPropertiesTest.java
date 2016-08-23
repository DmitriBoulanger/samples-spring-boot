package de.dbo.samples.springboot.jbehave2.IT.commons.server;

import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Print.lines;
/* Hamcrest */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/* java */
import java.io.IOException;
import java.util.Properties;

/* JUnit */
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring-Boot */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration.ConfigurationJBehaveProperties;
import de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration.ConfigurationJBehaveSystemProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestContainerPropertiesTestApplication.class})
public class TestContainerPropertiesTest implements TestContainerSystemProperties {
    private static final Logger     log  = LoggerFactory.getLogger(TestContainerPropertiesTest.class);

    @BeforeClass
    public static final void setSystemProperties() {
        System.setProperty(SYSTEM_PROPERTY_REMOTE_SERVER_ADDRESS, "x.y.z");
        System.setProperty(SYSTEM_PROPERTY_REMOTE_SERVER_PORT, "7777");

    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testDefaultValuesOfJBehavePropertis() {
//        log.info("Properties in resource [" + JBEHAVE_RESOUCE_NAME + "]:" + lines(JBEHAVE_RESOUCE_PROPERTIES));
        final TestContainerProperties testContainerProperties = applicationContext.getBean(TestContainerProperties.class);
        log.info("Testing values of " + testContainerProperties.print());
//
//        // =========================================================================
//        // Assert that the default values are there
//        // =========================================================================
//
//        assertThat("Default value for story-timeout is not as expected",
//                jbehaveProperties.getStoriesTimeout(),
//                equalTo(ConfigurationJBehaveProperties.DEFAULT_STORIES_TIMEOUT));
//
//        assertThat("Default flag for third-party silence is not as expected",
//                jbehaveProperties.isLoggingThirdPartiesSilent(),
//                equalTo(ConfigurationJBehaveProperties.DEFAULT_LOGGING_THRID_PARTIES_SILENT));
//
//        assertThat("Default flag for stories-console is not as expected",
//                jbehaveProperties.isStoriesConsole(),
//                equalTo(ConfigurationJBehaveProperties.DEFAULT_STORIES_CONSOLE));
//
//        // =========================================================================
//        // Assert that values in system properties overwrite any other values
//        // =========================================================================
//
//        assertThat("System-property flag for Fail-If-Pending-Step-Found is not as expected",
//                jbehaveProperties.isStepsFailIfPendingStepFound(),
//                equalTo(SYSTEM_PROPERTY_VALUE_JBEHAVE_FAIL_IF_PENDING_STEP_FOUND));
//
//        assertThat("System-property value for stories thread-cnt is not as expected",
//                jbehaveProperties.getStoriesThreadCnt(),
//                equalTo(SYSTEM_PROPERTY_VALUE_JBEHAVE_STORIES_THREAD_CNT));

    }
}
