package de.dbo.samples.springboot.rest.greeting;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello-world")
public class GreetingController {
    private static final Logger log      = LoggerFactory.getLogger(GreetingController.class);

    private static final String TEMPLATE = "Hello, %s!";

    private final AtomicLong    counter  = new AtomicLong();

    public GreetingController() {
        log.info("created");
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Greeting sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
        final long requestId = counter.incrementAndGet();
        log.info("processing request ID=" + requestId + " ...");
        return new Greeting(requestId, String.format(TEMPLATE, name));
    }

}
