package de.dbo.samples.springboot.jbehave2.app1.infrastructure;

import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.app1.domain.Product;
import de.dbo.samples.springboot.jbehave2.app1.domain.ProductDao;

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
