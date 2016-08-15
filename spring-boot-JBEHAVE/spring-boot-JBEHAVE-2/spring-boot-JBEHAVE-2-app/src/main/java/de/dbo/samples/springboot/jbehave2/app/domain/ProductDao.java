package de.dbo.samples.springboot.jbehave2.app.domain;

public interface ProductDao {

    void save(Product product);

    Product findByName(String name);

}
