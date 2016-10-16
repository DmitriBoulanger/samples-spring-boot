package de.ityx.response.it.docker.jobs;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

public class Composer {

    private final File composeFile;
    
    @SuppressWarnings("rawtypes")
    private final LinkedHashMap composeYaml;
    
    @SuppressWarnings("rawtypes")
    public Composer(final File composeFile)  {
	this.composeFile = composeFile;
	assertFileCorrectnes();
	try {
	    final Yaml yaml = new Yaml();
	    this.composeYaml = (LinkedHashMap) yaml.load(new FileInputStream(composeFile));
	} catch (Exception e) {
	   throw new IllegalStateException("Failure loading/parsing compose-file: ",e);
	}
    }
    
    public File getComposeFile() {
        return composeFile;
    }

    @SuppressWarnings("rawtypes")
    public LinkedHashMap getComposeYaml() {
        return composeYaml;
    }
    
    public final List<String> getContainerTitles() {
	final List<String> ret = new ArrayList<String>();
	for (final Object title: composeYaml.keySet()) {
	    ret.add((String) title);
	}
	return ret;
    }
   
    public final ContainerSpecification getContainerSpecification(final String title) throws FileNotFoundException {
	    final ContainerSpecification containerSpecification = new ContainerSpecification();
	    containerSpecification.setTitle(title);
	    containerSpecification.setImage( getContainerImage(title));
	    containerSpecification.setPorts( getContainerPorts(title));
	    containerSpecification.setLinks(  getContainerLinks(title));
	    return containerSpecification;
    }
    
    public final StringBuilder printAsItIs() throws FileNotFoundException {
	final StringBuilder sb = new StringBuilder();
	for (final Object x: composeYaml.entrySet()) {
	    sb.append("\n\t - " + x);
	}
	return sb;
    }
    
    public final StringBuilder print() throws FileNotFoundException {
	final List<String> titles = getContainerTitles();
	final StringBuilder sb = new StringBuilder();
	for (final String title: titles) {
	    final ContainerSpecification containerSpecification = getContainerSpecification(title);
	    sb.append(containerSpecification.print());
	}
	return sb;
    }
    
    // ==============================================================================================================================
    //                  PRIVATE IMPLEMENTATION
    // ==============================================================================================================================
    
    private final void assertFileCorrectnes() {
	assertThat("Compose file doesn't exist. Check the file:\n" +  composeFile,   composeFile.exists() && composeFile.isFile() );
    }
    
    @SuppressWarnings("rawtypes")
    private final String getContainerImage(final String containerTitle) {
	return (String) ((LinkedHashMap) composeYaml.get(containerTitle)).get("image");
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final List<String> getContainerPorts(final String containerTitle) {
 	return (List<String>) ((LinkedHashMap) composeYaml.get(containerTitle)).get("ports");
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final List<String> getContainerLinks(final String containerTitle) {
 	return (List<String>) ((LinkedHashMap) composeYaml.get(containerTitle)).get("links");
    }
    
}
