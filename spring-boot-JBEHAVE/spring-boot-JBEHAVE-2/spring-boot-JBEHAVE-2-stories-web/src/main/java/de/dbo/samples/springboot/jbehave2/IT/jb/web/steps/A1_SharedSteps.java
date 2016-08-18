package de.dbo.samples.springboot.jbehave2.IT.jb.web.steps;

import org.jbehave.core.annotations.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.app1.domain.Money;
import de.dbo.samples.springboot.jbehave2.app1.domain.PriceDao;
import de.dbo.samples.springboot.jbehave2.app1.domain.Product;
import de.dbo.samples.springboot.jbehave2.app1.domain.ProductDao;
import de.dbo.samples.springboot.jbehave2.app1.domain.StockKeepingUnit;

@Component
public class A1_SharedSteps {
    private static final Logger log = LoggerFactory.getLogger(A1_SharedSteps.class);

    @Autowired
    private ProductDao          productRepository;

    @Autowired
    private PriceDao            priceRepository;

    public A1_SharedSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Given("product $name with SKU $sku")
    public void product(String name, StockKeepingUnit sku) {
        log.debug("A1 product: name=" + name + " sku=" + sku);
        productRepository.save(new Product(sku, name));
    }

    @Given("product $name price is $price")
    public void price(String name, Money price) {
        log.debug("A1 product: name=" + name + " price=" + price + " ...");
        Product product = productRepository.findByName(name);
        priceRepository.save(product.getSku(), price);
    }
}
