package de.dbo.samples.springboot.jbehave0.tests.converters;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Converter {

}