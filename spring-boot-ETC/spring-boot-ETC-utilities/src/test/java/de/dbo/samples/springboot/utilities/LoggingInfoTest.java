package de.dbo.samples.springboot.utilities;

import de.dbo.samples.springboot.utilities.logging.LoggingInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingInfoTest {
    private static final Logger log = LoggerFactory.getLogger(LoggingInfoTest.class);
    
    @Test
    public void test() {
	
	log.info("INFO!");
	log.debug("DEBUG!");
	log.warn("WARNING!");
	log.error("ERROR!");
	
	log.info(LoggingInfo.printAppenderAttachments().toString());
	log.info(LoggingInfo.printAvailableLoggers().toString());
	
	if (log.isTraceEnabled()) {
	    log.trace("Internal logger state:");
	    LoggingInfo.printInternalStateToConsole();
	}
	
	assertThat("No expected logger found", LoggingInfo.hasLogger(LoggingInfoTest.class.getName()));
	

	
    }

}
