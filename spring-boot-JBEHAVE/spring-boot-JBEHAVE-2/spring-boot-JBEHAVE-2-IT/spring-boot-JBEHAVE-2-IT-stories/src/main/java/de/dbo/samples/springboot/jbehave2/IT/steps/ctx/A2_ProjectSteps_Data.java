package de.dbo.samples.springboot.jbehave2.IT.steps.ctx;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextData;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;

import com.jayway.restassured.specification.RequestSpecification;

public final class A2_ProjectSteps_Data extends ContextData  {

	private RequestSpecification requestSpecification;
	
	private TestServer testSetver;

	public RequestSpecification getRequestSpecification() {
	    return requestSpecification;
	}

	public void setRequestSpecification(RequestSpecification requestSpecification) {
	    this.requestSpecification = requestSpecification;
	}

	public TestServer getTestSetver() {
	    return testSetver;
	}

	public void setTestSetver(TestServer testSetver) {
	    this.testSetver = testSetver;
	}
	
	
}
