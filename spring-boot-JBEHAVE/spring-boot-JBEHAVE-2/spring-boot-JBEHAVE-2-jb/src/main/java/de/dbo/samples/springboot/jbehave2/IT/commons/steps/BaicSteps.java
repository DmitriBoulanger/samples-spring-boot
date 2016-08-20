package de.dbo.samples.springboot.jbehave2.IT.commons.steps;

import org.jbehave.core.annotations.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BaicSteps {
    private static final Logger log = LoggerFactory.getLogger(BaicSteps.class);
    
    public BaicSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }
    
    @Given("story openning: $name")
    public void storyOpenning(final String name) {
	log.info("Story opening: " + name);
	
    }
    
    @Given("story closing: $name")
    public void storyClosing(final String name) {
	log.info("Story closing: " + name);
    }
    
   

}
