package de.dbo.samples.springboot.jbehave2.IT.commons.context;

/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContextData {
    private static final Logger log = LoggerFactory.getLogger(ContextData.class);

    public ContextData() {
        if (log.isDebugEnabled()) {
            log.debug("created. HashCode=[" + hashCode() + "]");
        }
    }

}
