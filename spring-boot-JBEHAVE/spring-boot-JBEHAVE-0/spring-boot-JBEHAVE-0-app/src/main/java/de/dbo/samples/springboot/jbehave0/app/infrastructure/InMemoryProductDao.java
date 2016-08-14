package de.dbo.samples.springboot.jbehave0.app.infrastructure;

import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave0.app.domain.Product;
import de.dbo.samples.springboot.jbehave0.app.domain.ProductDao;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryProductDao implements ProductDao {

    private Map<String, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        products.put(product.getName(), product);
    }

    @Override
    public Product findByName(String name) {
        return products.get(name);
    }
}
