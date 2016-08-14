package de.dbo.samples.springboot.jbehave0.app.infrastructure;

import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave0.app.domain.CustomerIdentifier;
import de.dbo.samples.springboot.jbehave0.app.domain.ShoppingCart;
import de.dbo.samples.springboot.jbehave0.app.domain.ShoppingCartRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryShoppingCartRepository implements ShoppingCartRepository {

    private Map<CustomerIdentifier, ShoppingCart> shoppingCarts = new HashMap<>();

    @Override
    public void add(ShoppingCart shoppingCart) {
        shoppingCarts.put(shoppingCart.getCustomerIdentifier(), shoppingCart);
    }

    @Override
    public ShoppingCart load(CustomerIdentifier customerIdentifier) {
        return shoppingCarts.get(customerIdentifier);
    }
}
