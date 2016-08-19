package de.dbo.samples.springboot.jbehave2.IT.commons.context.example;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextAbstraction;

/**
 * Local context of the Customer License Manager
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
public final class XXXContext extends ContextAbstraction {

    private String defaultToken;

    private byte[] slaveLicense;
    private byte[] slaveRequest;

    public String getDefaultToken() {
        return defaultToken;
    }

    public void setDefaultToken(String defaultToken) {
        this.defaultToken = defaultToken;
    }

    public byte[] getSlaveRequest() {
        return slaveRequest;
    }

    public void setSlaveRequest(byte[] slaveRequest) {
        this.slaveRequest = slaveRequest;
    }

    public byte[] getSlaveLicense() {
        return slaveLicense;
    }

}
