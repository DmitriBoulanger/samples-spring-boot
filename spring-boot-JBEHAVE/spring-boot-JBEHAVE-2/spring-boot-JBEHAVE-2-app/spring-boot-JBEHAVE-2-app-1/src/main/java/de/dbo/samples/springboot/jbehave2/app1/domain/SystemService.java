package de.dbo.samples.springboot.jbehave2.app1.domain;

import org.springframework.stereotype.Component;

@Component
public class SystemService {

    private final static ShoperIdentifier DEFAULT_CUSTOMER = new ShoperIdentifier("default customer");

    public ShoperIdentifier authenticatedCustomer() {
        return DEFAULT_CUSTOMER;
    }

}
