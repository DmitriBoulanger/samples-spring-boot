package de.dbo.samples.springboot.jbehave2.IT.commons.stepsimpl;

import de.dbo.samples.springboot.jbehave2.IT.commons.util.io.DummyPrintStream;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;

public abstract class StepsBaseRestAssured extends StepsBase  {

    protected static RequestSpecBuilder requestSpecBuilder() {
	final RequestSpecBuilder ret =  new RequestSpecBuilder();
	return ret;
    }
   
    protected final ResponseLoggingFilter responseLoggingFiler() {
	if (jbehaveProperties.isLoggingThirdPartiesSilent()) {
	    return new ResponseLoggingFilter(new DummyPrintStream());
	} else {
	    return new ResponseLoggingFilter();
	}
    }
    
    protected final RequestLoggingFilter requestLoggingFiler() {
	if (jbehaveProperties.isLoggingThirdPartiesSilent()) {
	    return new RequestLoggingFilter(new DummyPrintStream());
	} else {
	    return new RequestLoggingFilter();
	}
    }
    
}
