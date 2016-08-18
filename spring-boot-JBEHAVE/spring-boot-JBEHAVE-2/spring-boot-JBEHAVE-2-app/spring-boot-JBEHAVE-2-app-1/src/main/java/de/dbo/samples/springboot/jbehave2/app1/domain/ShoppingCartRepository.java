package de.dbo.samples.springboot.jbehave2.app1.domain;

public interface ShoppingCartRepository {

    void add(ShoppingCart shoppingCart);

    ShoppingCart load(CustomerIdentifier customerIdentifier);

}
