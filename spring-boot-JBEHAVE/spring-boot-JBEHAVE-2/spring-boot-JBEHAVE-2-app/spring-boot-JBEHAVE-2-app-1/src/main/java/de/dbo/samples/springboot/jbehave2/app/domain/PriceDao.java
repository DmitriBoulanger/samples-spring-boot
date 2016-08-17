package de.dbo.samples.springboot.jbehave2.app.domain;

public interface PriceDao {

    void save(StockKeepingUnit sku, Money price);

    Money findBySku(StockKeepingUnit sku);

}
