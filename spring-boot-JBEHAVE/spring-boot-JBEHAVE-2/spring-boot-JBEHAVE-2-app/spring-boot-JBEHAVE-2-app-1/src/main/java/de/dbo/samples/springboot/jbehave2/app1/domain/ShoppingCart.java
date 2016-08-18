package de.dbo.samples.springboot.jbehave2.app1.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private ShoperIdentifier customerIdentifier;

    private List<ShoppingCartItem> items = new ArrayList<>();

    public ShoppingCart(ShoperIdentifier customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }

    public ShoperIdentifier getCustomerIdentifier() {
        return customerIdentifier;
    }

    public void addItem(ShoppingCartItem item) {
        this.items.add(item);
    }

    public int numberOfItems() {
        return items.size();
    }

}
