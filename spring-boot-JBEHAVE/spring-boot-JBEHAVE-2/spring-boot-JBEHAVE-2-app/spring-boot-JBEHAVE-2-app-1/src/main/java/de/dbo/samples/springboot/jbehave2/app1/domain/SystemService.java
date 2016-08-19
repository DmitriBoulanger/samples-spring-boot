package de.dbo.samples.springboot.jbehave2.app1.domain;

import org.springframework.stereotype.Component;

@Component
public class SystemService {

    private final static ShopperIdentifier DEFAULT_CUSTOMER = new ShopperIdentifier("default customer");

    public ShopperIdentifier authenticatedCustomer() {
        return DEFAULT_CUSTOMER;
    }

}
