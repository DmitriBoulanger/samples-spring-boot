package de.dbo.samples.springboot.jbehave2.app3.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Greeting {
    private static final Logger log = LoggerFactory.getLogger(Greeting.class);

    private long                id;
    private String              content;
    private long                cnt;

    public Greeting() {

    }

    public Greeting(long id, String content, long cnt) {
        this.id = id;
        this.content = content;
        this.cnt = cnt;
        log.info("created. ID=" + id + ": " + content);
    }

    // =======================================================================================
    //                       Getters and Setters
    // =======================================================================================

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getCnt() {
        return cnt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

}
