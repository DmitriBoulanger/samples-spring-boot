package de.dbo.samples.springboot.jbehave2.app1.domain;

public interface PriceDao {

    void save(StockKeepingUnit sku, Money price);

    Money findBySku(StockKeepingUnit sku);

}
