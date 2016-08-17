package de.dbo.samples.springboot.jbehave2.app.domain;

import org.springframework.stereotype.Component;

@Component
public class SystemService {

    private final static CustomerIdentifier DEFAULT_CUSTOMER = new CustomerIdentifier("default customer");

    public CustomerIdentifier authenticatedCustomer() {
        return DEFAULT_CUSTOMER;
    }

}
