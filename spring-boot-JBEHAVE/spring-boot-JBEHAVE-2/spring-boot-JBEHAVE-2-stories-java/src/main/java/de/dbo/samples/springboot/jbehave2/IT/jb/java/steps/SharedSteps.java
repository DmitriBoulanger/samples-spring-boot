package de.dbo.samples.springboot.jbehave2.IT.jb.java.steps;

import de.dbo.samples.springboot.jbehave2.app1.domain.Money;
import de.dbo.samples.springboot.jbehave2.app1.domain.PriceDao;
import de.dbo.samples.springboot.jbehave2.app1.domain.Product;
import de.dbo.samples.springboot.jbehave2.app1.domain.ProductDao;
import de.dbo.samples.springboot.jbehave2.app1.domain.StockKeepingUnit;

import org.jbehave.core.annotations.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SharedSteps {
    private static final Logger log = LoggerFactory.getLogger(SharedSteps.class);

    @Autowired
    private ProductDao          productRepository;

    @Autowired
    private PriceDao            priceRepository;

    public SharedSteps() {
        log.info("created");
    }

    @Given("product $name with SKU $sku")
    public void product(String name, StockKeepingUnit sku) {
        log.info("product: name=" + name + " sku=" + sku);
        productRepository.save(new Product(sku, name));
    }

    @Given("product $name price is $price")
    public void price(String name, Money price) {
        log.info("product: name=" + name + " price=" + price + " ...");
        Product product = productRepository.findByName(name);
        priceRepository.save(product.getSku(), price);
    }
}
