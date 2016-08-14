package de.dbo.samples.springboot.jbehave0.app.domain;

public interface ShoppingCartRepository {

    void add(ShoppingCart shoppingCart);

    ShoppingCart load(CustomerIdentifier customerIdentifier);

}
