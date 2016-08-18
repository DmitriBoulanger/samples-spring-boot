package de.dbo.samples.springboot.jbehave2.app1.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartService {

    @Autowired
    private SystemService systemService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PriceDao priceRepository;

    public void createEmptyShoppingCart() {
        ShoperIdentifier customerIdentifier = systemService.authenticatedCustomer();

        ShoppingCart emptyShoppingCart = new ShoppingCart(customerIdentifier);
        shoppingCartRepository.add(emptyShoppingCart);
    }

    public void addProductToShoppingCart(StockKeepingUnit sku, int quantity) {
        ShoperIdentifier customerIdentifier = systemService.authenticatedCustomer();
        Money price = priceRepository.findBySku(sku);

        ShoppingCart shoppingCart = shoppingCartRepository.load(customerIdentifier);
        shoppingCart.addItem(new ShoppingCartItem(sku, quantity, price));
    }

    public ShoppingCart getShoppingCart() {
        ShoperIdentifier customerIdentifier = systemService.authenticatedCustomer();
        return shoppingCartRepository.load(customerIdentifier);
    }

}
