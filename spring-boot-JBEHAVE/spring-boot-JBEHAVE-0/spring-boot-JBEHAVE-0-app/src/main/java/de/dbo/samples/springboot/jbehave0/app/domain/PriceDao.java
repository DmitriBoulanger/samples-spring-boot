package de.dbo.samples.springboot.jbehave0.app.domain;

public interface PriceDao {

    void save(StockKeepingUnit sku, Money price);

    Money findBySku(StockKeepingUnit sku);

}
