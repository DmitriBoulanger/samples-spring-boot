package de.dbo.samples.springboot.data.elasticsearch.client.domain2;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface CustomerRepository extends ElasticsearchRepository<Customer,String> {
    List<Customer> findCustomersByAge(int age);  
    List<Customer> findCustomersByName(String name);
}
