package de.dbo.samples.springboot.jbehave2.IT.steps.converters;

import de.dbo.samples.springboot.jbehave2.app1.domain.StockKeepingUnit;

import org.jbehave.core.annotations.AsParameterConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StockKeepingUnitConverter {
    private static final Logger log = LoggerFactory.getLogger(StockKeepingUnitConverter.class);

    public StockKeepingUnitConverter() {
        log.info("created");
    }

    @AsParameterConverter
    public StockKeepingUnit convertPercent(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        return new StockKeepingUnit(value.trim());
    }
}
