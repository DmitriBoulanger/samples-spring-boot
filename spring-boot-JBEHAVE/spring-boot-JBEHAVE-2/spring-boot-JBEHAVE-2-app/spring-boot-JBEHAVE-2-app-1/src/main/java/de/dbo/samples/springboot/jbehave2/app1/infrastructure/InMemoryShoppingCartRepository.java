package de.dbo.samples.springboot.jbehave2.app1.infrastructure;

import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.app1.domain.ShopperIdentifier;
import de.dbo.samples.springboot.jbehave2.app1.domain.ShoppingCart;
import de.dbo.samples.springboot.jbehave2.app1.domain.ShoppingCartRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryShoppingCartRepository implements ShoppingCartRepository {

    private Map<ShopperIdentifier, ShoppingCart> shoppingCarts = new HashMap<>();

    @Override
    public void add(ShoppingCart shoppingCart) {
        shoppingCarts.put(shoppingCart.getCustomerIdentifier(), shoppingCart);
    }

    @Override
    public ShoppingCart load(ShopperIdentifier customerIdentifier) {
        return shoppingCarts.get(customerIdentifier);
    }
}
