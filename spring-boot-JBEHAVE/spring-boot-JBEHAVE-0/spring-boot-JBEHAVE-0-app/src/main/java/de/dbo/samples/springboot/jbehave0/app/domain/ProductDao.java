package de.dbo.samples.springboot.jbehave0.app.domain;

public interface ProductDao {

    void save(Product product);

    Product findByName(String name);

}
