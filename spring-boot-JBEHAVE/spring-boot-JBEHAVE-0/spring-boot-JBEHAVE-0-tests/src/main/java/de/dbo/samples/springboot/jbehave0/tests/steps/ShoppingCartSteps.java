package de.dbo.samples.springboot.jbehave0.tests.steps;

import org.jbehave.core.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

import de.dbo.samples.springboot.jbehave0.app.domain.*;

@Steps
public class ShoppingCartSteps {
    private static final Logger log = LoggerFactory.getLogger(ShoppingCartSteps.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductDao productRepository;

    @Given("empty shopping cart")
    public void emptyShoppingCart() {
	log.info("emptyShoppingCart ...");
        shoppingCartService.createEmptyShoppingCart();
    }

    @When("products are added to the shopping cart: $productNames")
    public void addProducts(List<ShoppingCartRow> rows) {
        for (ShoppingCartRow row : rows) {
            Product product = productRepository.findByName(row.getProductName());
            shoppingCartService.addProductToShoppingCart(product.getSku(), row.getQuantity());
            log.info("added product " + product.getName());
        }
    }

    @Then("shopping cart is empty")
    public void isEmpty() {
	log.info("isEmpty ...");
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        assertEquals(0, shoppingCart.numberOfItems());
    }

    @Then("the number of products in shopping cart is $numberOfItems")
    public void numberOfItems(int numberOfItems) {
	log.info("numberOfItems " +numberOfItems+ "  ...");
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        assertEquals(numberOfItems, shoppingCart.numberOfItems());
    }

    @Then("total price is $price")
    @Pending
    public void totalPrice(Money price) {
	log.info("totalPrice " + price + "  ...");
        // TODO: implement missing functionality and enable step
    }

    @AsParameters
    public static class ShoppingCartRow {

        @Parameter(name = "PRODUCT")
        private String productName;

        @Parameter(name = "QTY")
        private Integer quantity;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }


}
