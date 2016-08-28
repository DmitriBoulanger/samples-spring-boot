package de.dbo.samples.springboot.data.elasticsearch.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataOperationConfirmation {
    private static final Logger log = LoggerFactory.getLogger(DataOperationConfirmation.class);

    private long          id;
    private long          cnt;
    private String        content;

    public DataOperationConfirmation(long id, String content, long cnt) {
        this.id = id;
        this.content = content;
        this.cnt = cnt;
        log.info("created. ID=" + id + " cnt=" + cnt+ " content=[" + content + "]");
    }
   
    public DataOperationConfirmation() {
        this(-1,"",-1);
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

    public void setId(long id) {
        this.id = id;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
