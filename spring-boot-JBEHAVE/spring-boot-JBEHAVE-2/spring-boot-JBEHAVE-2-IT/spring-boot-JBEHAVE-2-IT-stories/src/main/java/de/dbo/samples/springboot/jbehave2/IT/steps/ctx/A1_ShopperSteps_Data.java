package de.dbo.samples.springboot.jbehave2.IT.steps.ctx;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextData;
import de.dbo.samples.springboot.jbehave2.app1.domain.Shopper;

import com.jayway.restassured.specification.RequestSpecification;

public final class A1_ShopperSteps_Data extends ContextData  {

	private Shopper shopper;
	
	private RequestSpecification requestSpecification;

	public Shopper getShopper() {
	    return shopper;
	}

	public void setShopper(Shopper shopper) {
	    this.shopper = shopper;
	}

	public RequestSpecification getRequestSpecification() {
	    return requestSpecification;
	}

	public void setRequestSpecification(RequestSpecification requestSpecification) {
	    this.requestSpecification = requestSpecification;
	}
}
