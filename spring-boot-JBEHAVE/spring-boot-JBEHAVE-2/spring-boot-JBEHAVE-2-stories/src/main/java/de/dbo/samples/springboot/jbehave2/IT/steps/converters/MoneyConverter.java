package de.dbo.samples.springboot.jbehave2.IT.steps.converters;

import de.dbo.samples.springboot.jbehave2.app1.domain.Money;

/* 
 * JBehave 
 */
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.steps.ParameterConverters;
/* 
 * SLF4J 
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* 
 * Spring 
 */
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MoneyConverter {
    private static final Logger log = LoggerFactory.getLogger(MoneyConverter.class);

    public MoneyConverter() {
        log.info("created");
    }

    @AsParameterConverter
    public Money convertPercent(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        String[] tokens = value.split("\\s");
        if (tokens.length != 2) {
            throw new ParameterConverters.ParameterConvertionFailed("Expected 2 tokens (amount and currency) but got " + tokens.length + ", value: " + value + ".");
        }

        return new Money(tokens[0], tokens[1]);
    }
}
