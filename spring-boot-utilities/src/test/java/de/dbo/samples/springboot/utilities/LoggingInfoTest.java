package de.dbo.samples.springboot.utilities;

import de.dbo.samples.springboot.utilities.logging.LoggingInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingInfoTest {
    private static final Logger log = LoggerFactory.getLogger(LoggingInfoTest.class);
    
    @Test
    public void test() {
	log.info(LoggingInfo.print().toString());
	log.info("Internal logger state:\n");
	LoggingInfo.printInternalState();
	
	assertThat("No expected logger found", LoggingInfo.hasLogger(LoggingInfoTest.class.getName()));
	
	log.info("INFO!");
	log.warn("WARNING!");
	log.error("ERROR!");

	
    }

}
