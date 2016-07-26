package de.dbo.samples.springboot.rest.hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
@RequestMapping("/hello-world")
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    
    public Controller() {
	 log.info("created");
    }

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody 
    Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
	final long requestId = counter.incrementAndGet();
	log.info("processing request ID=" + requestId + " ...");
        return new Greeting(requestId, String.format(TEMPLATE, name));
    }

}
