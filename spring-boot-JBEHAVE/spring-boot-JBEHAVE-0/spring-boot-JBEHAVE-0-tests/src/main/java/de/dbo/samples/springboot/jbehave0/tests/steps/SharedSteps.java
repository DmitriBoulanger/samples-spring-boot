package de.dbo.samples.springboot.jbehave0.tests.steps;

import de.dbo.samples.springboot.jbehave0.app.domain.*;

import org.jbehave.core.annotations.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Steps
public class SharedSteps {
    private static final Logger log = LoggerFactory.getLogger(SharedSteps.class);

    @Autowired
    private ProductDao productRepository;

    @Autowired
    private PriceDao priceRepository;

    @Given("product $name with SKU $sku")
    public void product(String name, StockKeepingUnit sku) {
	log.info("product: name=" + name + " sku="+ sku);
        productRepository.save(new Product(sku, name));
    }

    @Given("product $name price is $price")
    public void price(String name, Money price) {
	log.info("product: name=" + name + " price="+ price +  " amount=" + price + " ...");
        Product product = productRepository.findByName(name);
        priceRepository.save(product.getSku(), price);
    }
}
