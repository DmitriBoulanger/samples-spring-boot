package de.dbo.samples.springboot.data.elasticsearch.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dbo.samples.springboot.data.elasticsearch.domain.Customer;
import de.dbo.samples.springboot.data.elasticsearch.domain.CustomerRepository;

@RestController
@RequestMapping("/hello-world")
public class GreetingController {
    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    private static final String template = "Hello, %s!";
    private final AtomicLong    counter  = new AtomicLong();

    @Autowired
    CustomerRepository          customerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Greeting sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {

	final long repositoryCount = customerRepository.count() ;
	if (0==repositoryCount) {
	    log.info("No data in the customer repository: Elastic search repository was not loaded?");
	} 
	else {
	    final List<Customer> customers = customerRepository.findByLastName("Smith");
	    final StringBuilder sb = new StringBuilder("Repository count " +repositoryCount + ": ");
	    for (Customer customer:customers) {
		sb.append("\n\t - ID=" + customer.getId() + " " + customer.getFirstName());
	    }
	    log.info(sb.toString());
	}

	return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}