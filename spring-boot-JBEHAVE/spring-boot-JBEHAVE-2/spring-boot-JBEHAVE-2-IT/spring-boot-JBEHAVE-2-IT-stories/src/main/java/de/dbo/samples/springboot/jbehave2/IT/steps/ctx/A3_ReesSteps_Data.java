package de.dbo.samples.springboot.jbehave2.IT.steps.ctx;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextData;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;

public final class A3_ReesSteps_Data extends ContextData {

    private String contextURL;

    public void setTestSever(final TestServer testServer) {
        contextURL = testServer.contextURL();
    }

    public String contextURL() {
        return contextURL;
    }

}
