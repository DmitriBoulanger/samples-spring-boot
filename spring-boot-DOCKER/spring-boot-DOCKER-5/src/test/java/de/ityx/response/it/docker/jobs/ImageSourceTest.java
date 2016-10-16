package de.ityx.response.it.docker.jobs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ityx.response.it.docker.Client;
import de.ityx.response.it.docker.ImageSources;
import de.ityx.response.it.docker.TestAbstraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageSourceTest extends TestAbstraction {
    private static final Logger LOG = LoggerFactory.getLogger(ImageSourceTest.class);
    
    /**
     * creating and cleaning Image Source.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void t00_createAndCloseImageDataSources() throws FileNotFoundException, IOException {
	final Map<String, ImageSource> imageSources = ImageSources.imageSources();
	for (final String name: imageSources.keySet() ) {
	    final ImageSource imageSource = imageSources.get(name);
	         imageSource.load();
		LOG.info("Created Image Source: " + imageSource.print());
		imageSource.close();
	}
    }
    
    @Test
    public void t01_cleanDocker() throws Exception {
	 final Client client = new Client();
	 client.init();
	 client.showDockerResources();
	 client.removeDockerResources( /* but not */ "java" );
    }
    
    
    @Test
    public void t10_buildImages() throws Exception {
	 final Client client = new Client();
	 client.init();
	        
	final Map<String, ImageSource> imageSources = ImageSources.imageSources();
	for (final String name: imageSources.keySet() ) {
	    final ImageSource imageSource = imageSources.get(name);
	       client.createImage(imageSource, name.toLowerCase());
	}
	
	client.close();
    }

}
