package de.dbo.samples.springboot.jbehave2.app1.infrastructure;

import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.app1.domain.Money;
import de.dbo.samples.springboot.jbehave2.app1.domain.PriceDao;
import de.dbo.samples.springboot.jbehave2.app1.domain.StockKeepingUnit;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryPriceDao implements PriceDao {

    private Map<StockKeepingUnit, Money> prices = new HashMap<>();


    @Override
    public void save(StockKeepingUnit sku, Money price) {
        prices.put(sku, price);
    }

    @Override
    public Money findBySku(StockKeepingUnit sku) {
        return prices.get(sku);
    }
}
