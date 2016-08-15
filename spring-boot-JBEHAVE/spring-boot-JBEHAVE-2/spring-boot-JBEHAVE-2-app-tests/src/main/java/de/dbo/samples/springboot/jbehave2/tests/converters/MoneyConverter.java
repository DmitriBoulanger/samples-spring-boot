package de.dbo.samples.springboot.jbehave2.tests.converters;

import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.steps.ParameterConverters;
import org.springframework.util.StringUtils;

import de.dbo.samples.springboot.jbehave2.app.domain.Money;

@Converter
public class MoneyConverter {

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
