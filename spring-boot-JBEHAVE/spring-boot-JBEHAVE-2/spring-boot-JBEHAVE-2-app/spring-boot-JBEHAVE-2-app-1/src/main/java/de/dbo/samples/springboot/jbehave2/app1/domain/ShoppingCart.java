package de.dbo.samples.springboot.jbehave2.app1.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private ShopperIdentifier customerIdentifier;

    private List<ShoppingCartItem> items = new ArrayList<>();

    public ShoppingCart(ShopperIdentifier customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }

    public ShopperIdentifier getCustomerIdentifier() {
        return customerIdentifier;
    }

    public void addItem(ShoppingCartItem item) {
        this.items.add(item);
    }

    public int numberOfItems() {
        return items.size();
    }

}
