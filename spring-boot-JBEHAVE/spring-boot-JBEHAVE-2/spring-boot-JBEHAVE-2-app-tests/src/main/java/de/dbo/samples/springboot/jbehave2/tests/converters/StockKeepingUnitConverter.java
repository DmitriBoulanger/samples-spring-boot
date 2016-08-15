package de.dbo.samples.springboot.jbehave2.tests.converters;

import org.jbehave.core.annotations.AsParameterConverter;
import org.springframework.util.StringUtils;

import de.dbo.samples.springboot.jbehave2.app.domain.StockKeepingUnit;

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
