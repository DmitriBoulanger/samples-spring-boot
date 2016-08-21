package de.dbo.samples.springboot.jbehave2.IT.commons.context;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContextData {
    private static final Logger log = LoggerFactory.getLogger(ContextData.class);
    
    public ContextData() {
	  log.info("created. HashCode=[" + hashCode() + "]");
    }

}
