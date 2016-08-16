package de.dbo.samples.springboot.jbehave2.app.web.customer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Customer Service
 */
@RestController
public class CustomerService {

    private final Map<String, Customer> customerIdMap   = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Customer> customerNameMap = Collections.synchronizedMap(new HashMap<>());

    /**
     * Creates and stores a new {@link Customer}.
     * If a customer with the same name already exists, this one will be returned.
     *
     * @param request is used to extract the request url
     * @param customer the input {@link Customer} entity (the customer id will be ignored)
     * @return the new created or existing {@link Customer}
     */
    @RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseEntity<?> createNewCustomer(final HttpServletRequest request,
            @RequestBody final Customer customer) {

        if (StringUtils.isEmpty(customer.getName())) {
            return ResponseEntity.badRequest().body(new ErrorObject("Name must not be empty!"));
        }

        Customer persistentCustomer = customerNameMap.computeIfAbsent(customer.getName(), this::createCustomer);
        customerIdMap.putIfAbsent(persistentCustomer.getId(), persistentCustomer);

        try {
            return ResponseEntity
                    .created(new URI(extractUrl(request) + persistentCustomer.getId()))
                    .body(persistentCustomer);
        }
        catch(URISyntaxException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Returns the {@link Customer} with the specified id
     *
     * @param id the {@link Customer} ID
     * @return {@link Customer}
     */
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomer(@PathVariable final String id) {

        Customer customer = customerIdMap.get(id);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new {@link Customer} with a random UUID and the specified name
     *
     * @param name customer name
     * @return new {@link Customer}
     */
    private Customer createCustomer(String name) {
        return new Customer(UUID.randomUUID().toString(), name);
    }

    /**
     * Extracts the URL String from the HTTP Request
     *
     * @param request
     * @return URL String
     */
    private String extractUrl(final HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        requestURL = requestURL.endsWith("/") ? requestURL : requestURL + "/";
        return requestURL;
    }

}
