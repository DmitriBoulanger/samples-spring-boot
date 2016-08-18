package de.dbo.samples.springboot.jbehave2.jb.converters;

import org.jbehave.core.annotations.AsParameterConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import de.dbo.samples.springboot.jbehave2.app1.domain.StockKeepingUnit;

/**
 * Thread-safe bean.
 * It is used elsewhere while running stories
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
@Component
public class StockKeepingUnitConverter {
    private static final Logger log = LoggerFactory.getLogger(StockKeepingUnitConverter.class);

    public StockKeepingUnitConverter() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @AsParameterConverter
    public StockKeepingUnit convertPercent(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        return new StockKeepingUnit(value.trim());
    }
}
