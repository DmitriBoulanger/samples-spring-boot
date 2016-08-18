package de.dbo.samples.springboot.jbehave2.app3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Greeting {
    private static final Logger log = LoggerFactory.getLogger(Greeting.class);

    private final long          id;
    private final String        content;
    private final long          cnt;

    public Greeting(long id, String content, long cnt) {
        this.id = id;
        this.content = content;
        this.cnt = cnt;
        log.info("created. ID=" + id + ": " + content);
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getCnt() {
        return cnt;
    }

}
