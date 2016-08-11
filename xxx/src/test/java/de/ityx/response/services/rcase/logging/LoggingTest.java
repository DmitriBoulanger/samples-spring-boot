package de.ityx.response.services.rcase.logging;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ityx.response.application.framework.utilities.logging.LoggingInfo;

public class LoggingTest {
    private static final Logger log = LoggerFactory.getLogger(LoggingTest.class);

    @Test
    public void test() {

        log.info("INFO!");
        log.debug("DEBUG!");
        log.warn("WARNING!");
        log.error("ERROR!");

        log.info(LoggingInfo.printAppenderAttachments().toString());
        log.info(LoggingInfo.printAvailableLoggers(100, true).toString());

        log.info("Internal logger state:");
        LoggingInfo.printInternalStateToConsole();

        assertThat("No expected logger found", LoggingInfo.hasLogger(LoggingTest.class.getName()));

    }

}
