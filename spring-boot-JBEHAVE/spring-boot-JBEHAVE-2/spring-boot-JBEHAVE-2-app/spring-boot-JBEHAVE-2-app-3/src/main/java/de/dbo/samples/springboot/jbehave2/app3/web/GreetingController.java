package de.dbo.samples.springboot.jbehave2.app3.web;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dbo.samples.springboot.jbehave2.app3.domain.Customer;
import de.dbo.samples.springboot.jbehave2.app3.domain.CustomerRepository;
import de.dbo.samples.springboot.jbehave2.app3.domain.Greeting;

@RestController
@RequestMapping("/hello-world")
public class GreetingController {
    private static final Logger log              = LoggerFactory.getLogger(GreetingController.class);

    private static final String RESPONE_TEMPLATE = "Hello, %s! Your visit has been recorded as ";
    private static final String DEFAULT_NAME     = "Stranger";
    private static final String DEFAULT_LASTNAME = "Unknown";
    private final AtomicLong    counter          = new AtomicLong();

    @Autowired
    CustomerRepository          customerRepository;

    public GreetingController() {
        log.info("created. HashCode=[" + hashCode() + "]");
        if (log.isDebugEnabled()) {
            log.error("ok");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Greeting sayHello(@RequestParam(value = "name", required = false, defaultValue = DEFAULT_NAME) String name) {

        final long repositoryCount = customerRepository.count();
        log.info("Repository count is " + repositoryCount);
        if (0 == repositoryCount) {
            log.error("No data in the customer repository: Elastic search repository was not loaded?");
        }
        else if (log.isDebugEnabled()) {
            final Iterable<Customer> customers = customerRepository.findAll();
            final StringBuilder sb = new StringBuilder("Repository contents: ");
            for (Customer customer : customers) {
                sb.append("\n\t - ID=" + customer.getId() + " " + customer.getFirstName());
            }
            log.debug(sb.toString());
        }

        final boolean badName = null != name && name.equals("XXX");
        if (badName) {
            log.error("Bad name:", new Exception("Bad-name Exception just to see it in log"));
        }

        final long id = counter.incrementAndGet();
        final Customer customer;
        if (null == name) {
            customer = new Customer(DEFAULT_NAME + id, DEFAULT_LASTNAME);
        }
        else {
            customer = new Customer(name + id, DEFAULT_LASTNAME);
        }
        customerRepository.save(customer);
        final Greeting greeting = new Greeting(id, String.format(RESPONE_TEMPLATE, name) + customer.toString()
                + (badName ? " You have ugly name!" : ""), customerRepository.count());
        return greeting;
    }

}