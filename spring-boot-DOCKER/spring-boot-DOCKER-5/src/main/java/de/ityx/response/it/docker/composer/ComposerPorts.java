package de.ityx.response.it.docker.composer;

import java.io.FileNotFoundException;
import java.util.List;

public class ComposerPorts {

    private final List<String> ports;

    ComposerPorts(final List<String> ports) {
	this.ports = ports;
    }

    public final StringBuilder print() throws FileNotFoundException {
	final StringBuilder sb = new StringBuilder();
	sb.append(ports);
	return sb;
    }
    
    public Integer getSingleExposedPort() {
	if (null==ports || ports.isEmpty() || 1!=ports.size()) {
	    return null;
	}
	final String[] pair = ports.get(0).split(":");
	final Integer containerPort = new Integer(pair[0]);
	final Integer dockerPort = new Integer(pair[1]);
	if (containerPort.equals(dockerPort)) {
	    return dockerPort;
	} else {
	    return null;
	}
    }
    
    public boolean isNullSingleExposedPort() {
	return null==ports || ports.isEmpty();
    }
}
