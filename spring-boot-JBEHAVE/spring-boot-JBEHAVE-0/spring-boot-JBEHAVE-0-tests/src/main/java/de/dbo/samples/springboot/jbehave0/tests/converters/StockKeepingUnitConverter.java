package de.dbo.samples.springboot.jbehave0.tests.converters;

import de.dbo.samples.springboot.jbehave0.app.domain.StockKeepingUnit;

import org.jbehave.core.annotations.AsParameterConverter;
import org.springframework.util.StringUtils;

@Converter
public class StockKeepingUnitConverter {

    @AsParameterConverter
    public StockKeepingUnit convertPercent(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        return new StockKeepingUnit(value.trim());
    }
}
