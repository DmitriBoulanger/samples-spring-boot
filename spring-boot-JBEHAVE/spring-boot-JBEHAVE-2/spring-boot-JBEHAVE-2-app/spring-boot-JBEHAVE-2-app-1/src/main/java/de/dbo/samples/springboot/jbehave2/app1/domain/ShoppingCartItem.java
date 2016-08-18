package de.dbo.samples.springboot.jbehave2.app1.domain;

public class ShoppingCartItem {

    private StockKeepingUnit sku;

    private int quantity;

    private Money price;

    public ShoppingCartItem(StockKeepingUnit sku, int quantity, Money price) {
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;
    }

    public StockKeepingUnit getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }
}
