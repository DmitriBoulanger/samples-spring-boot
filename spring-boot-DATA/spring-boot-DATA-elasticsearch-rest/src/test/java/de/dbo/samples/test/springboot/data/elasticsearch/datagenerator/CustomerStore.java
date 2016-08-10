package de.dbo.samples.test.springboot.data.elasticsearch.datagenerator;

import java.io.IOException;

import de.dbo.samples.springboot.data.elasticsearch.domain.Customer;

public class CustomerStore extends JSONStore<Customer> {

    public CustomerStore() throws IOException {
        super(new CustomerDataGenerator());
    }

    @Override
    public void store(Customer t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

}
