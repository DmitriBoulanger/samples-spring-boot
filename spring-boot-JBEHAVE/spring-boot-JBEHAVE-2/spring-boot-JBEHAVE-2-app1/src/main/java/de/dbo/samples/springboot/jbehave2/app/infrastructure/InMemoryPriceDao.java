package de.dbo.samples.springboot.jbehave2.app.infrastructure;

import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.app.domain.Money;
import de.dbo.samples.springboot.jbehave2.app.domain.PriceDao;
import de.dbo.samples.springboot.jbehave2.app.domain.StockKeepingUnit;

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
