package de.dbo.samples.springboot.jbehave2.IT.jb.web.steps;

import static org.junit.Assert.assertEquals;

import java.util.List;

// JBehave
import org.jbehave.core.annotations.AsParameters;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Parameter;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
// SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.app1.domain.Money;
import de.dbo.samples.springboot.jbehave2.app1.domain.Product;
import de.dbo.samples.springboot.jbehave2.app1.domain.ProductDao;
import de.dbo.samples.springboot.jbehave2.app1.domain.ShoppingCart;
import de.dbo.samples.springboot.jbehave2.app1.domain.ShoppingCartService;

@Component
public class A1_ShoppingCartSteps {
    private static final Logger log = LoggerFactory.getLogger(A1_ShoppingCartSteps.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductDao          productRepository;

    public A1_ShoppingCartSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Given("empty shopping cart")
    public void emptyShoppingCart() {
        if (log.isDebugEnabled()) {
            log.debug("emptyShoppingCart ...");
        }
        shoppingCartService.createEmptyShoppingCart();
    }

    @When("products are added to the shopping cart: $productNames")
    public void addProducts(List<ShoppingCartRow> rows) {
        for (ShoppingCartRow row : rows) {
            Product product = productRepository.findByName(row.getProductName());
            shoppingCartService.addProductToShoppingCart(product.getSku(), row.getQuantity());
            if (log.isDebugEnabled()) {
                log.debug("added product " + product.getName());
            }
        }
    }

    @Then("shopping cart is empty")
    public void isEmpty() {
        if (log.isDebugEnabled()) {
            log.debug("isEmpty ...");
        }
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        assertEquals(0, shoppingCart.numberOfItems());
    }

    @Then("the number of products in shopping cart is $numberOfItems")
    public void numberOfItems(int numberOfItems) {
        if (log.isDebugEnabled()) {
            log.debug("numberOfItems " + numberOfItems + "  ...");
        }
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        assertEquals(numberOfItems, shoppingCart.numberOfItems());
    }

    @Then("total price is $price")
    @Pending
    public void totalPrice(Money price) {
        if (log.isDebugEnabled()) {
            log.debug("totalPrice " + price + "  ...");
        }
        // TODO: implement missing functionality and enable step
    }

    @AsParameters
    public static class ShoppingCartRow {

        @Parameter(name = "PRODUCT")
        private String  productName;

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
