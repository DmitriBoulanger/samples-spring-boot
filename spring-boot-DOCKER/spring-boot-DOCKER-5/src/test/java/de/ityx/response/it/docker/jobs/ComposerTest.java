package de.ityx.response.it.docker.jobs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ityx.response.it.docker.TestAbstraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComposerTest extends TestAbstraction {
    private static final Logger LOG= LoggerFactory.getLogger(ComposerTest.class);
    
    /**
     * creating and cleaning Image Source.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void t00_readComposeFile() throws FileNotFoundException, IOException {
	final Composer composer = new Composer(testComposeFile());
	LOG.info("Compose-file contents: " + composer.print());
    }

}
