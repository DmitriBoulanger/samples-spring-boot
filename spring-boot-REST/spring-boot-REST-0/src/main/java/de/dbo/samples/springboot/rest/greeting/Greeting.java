package de.dbo.samples.springboot.rest.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Greeting {
    private static final Logger log = LoggerFactory.getLogger(Greeting.class);

    private final long          id;
    private final String        content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
        log.info("created. ID=" + id);
        
        
        log.info("INFO");
	log.warn("WARN");
	log.error("ERROR");
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
