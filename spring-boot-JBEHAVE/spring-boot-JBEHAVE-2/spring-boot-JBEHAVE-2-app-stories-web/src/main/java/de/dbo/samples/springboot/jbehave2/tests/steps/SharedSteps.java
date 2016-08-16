package de.dbo.samples.springboot.jbehave2.tests.steps;

import org.jbehave.core.annotations.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.app.domain.Money;
import de.dbo.samples.springboot.jbehave2.app.domain.PriceDao;
import de.dbo.samples.springboot.jbehave2.app.domain.Product;
import de.dbo.samples.springboot.jbehave2.app.domain.ProductDao;
import de.dbo.samples.springboot.jbehave2.app.domain.StockKeepingUnit;

@Component
public class SharedSteps {
    private static final Logger log = LoggerFactory.getLogger(SharedSteps.class);

    @Autowired
    private ProductDao          productRepository;

    @Autowired
    private PriceDao            priceRepository;

    public SharedSteps() {
	log.debug("created");
    }

    @Given("product $name with SKU $sku")
    public void product(String name, StockKeepingUnit sku) {
	if (log.isDebugEnabled()) {
	    log.debug("product: name=" + name + " sku=" + sku);
	}
	productRepository.save(new Product(sku, name));
    }

    @Given("product $name price is $price")
    public void price(String name, Money price) {
	if (log.isDebugEnabled()) {
	    log.debug("product: name=" + name + " price=" + price + " ...");
	}
	Product product = productRepository.findByName(name);
	priceRepository.save(product.getSku(), price);
    }
}
