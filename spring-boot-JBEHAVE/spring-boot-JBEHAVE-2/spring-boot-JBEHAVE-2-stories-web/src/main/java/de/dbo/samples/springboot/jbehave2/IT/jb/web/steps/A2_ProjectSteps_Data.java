package de.dbo.samples.springboot.jbehave2.IT.jb.web.steps;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextData;

import com.jayway.restassured.specification.RequestSpecification;

public final class A2_ProjectSteps_Data extends ContextData  {

	private RequestSpecification requestSpecification;


	public RequestSpecification getRequestSpecification() {
	    return requestSpecification;
	}

	public void setRequestSpecification(RequestSpecification requestSpecification) {
	    this.requestSpecification = requestSpecification;
	}
}
