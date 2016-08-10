package de.dbo.samples.springboot.data.elasticsearch.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dbo.samples.springboot.data.elasticsearch.domain.CustomerRepository;

@RestController
@RequestMapping("/hello-world")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong    counter  = new AtomicLong();

    @Autowired
    CustomerRepository          customerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Greeting sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
        System.err.println(
                "Repository count: " + customerRepository.count()

        );

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}