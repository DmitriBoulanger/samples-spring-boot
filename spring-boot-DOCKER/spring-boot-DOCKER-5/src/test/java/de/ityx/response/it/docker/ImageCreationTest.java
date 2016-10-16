package de.ityx.response.it.docker;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.model.Image;

import de.ityx.response.it.docker.commander.Commander;
import de.ityx.response.it.docker.composer.Composer;
import de.ityx.response.it.docker.image.ImageManager;
import de.ityx.response.it.docker.image.ImageSource;
import de.ityx.response.it.docker.testimpl.TestAbstraction;
import de.ityx.response.it.docker.testimpl.TestImageSources;

/**
 * Image creation and consistency with composer-file.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageCreationTest extends TestAbstraction {
    
    private static Commander commander;
    
    @BeforeClass
    public static void initCommander() {
	commander = new Commander();
	commander.init();
    }
    
    @AfterClass
    public static void closeCommander() throws Exception {
	commander.close();
    }
    
    @Test
    public void t00_cleanDocker() throws Exception {
	 commander.showDockerResources();
	 commander.removeDockerResources( /* but not */ "java" );
    }
    
    @Test
    public void t10_buildImages() throws Exception {
	        
	final Map<String, ImageSource> imageSources = TestImageSources.imageSources("test/");
	for (final String name: imageSources.keySet() ) {
	    final ImageSource imageSource = imageSources.get(name);
	       commander.createImage(imageSource, name.toLowerCase());
	}
    }
    
    /**
     * verify that all image-references in the composer-file are covered
     * @throws Exception
     */
    @Test
    public void t11_checkCompooserImageReferences() throws Exception {
	final List<Image> images =  commander.avialbleImages();
	final Composer composer = new Composer(testComposerFile());
	composer.assertThatCovered(images);
    }

}
