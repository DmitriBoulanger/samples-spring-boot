package de.ityx.response.it.docker;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ityx.response.it.docker.commander.Commander;
import de.ityx.response.it.docker.composer.Composer;
import de.ityx.response.it.docker.image.ImageSource;
import de.ityx.response.it.docker.testimpl.TestAbstraction;
import de.ityx.response.it.docker.testimpl.TestImageSources;

/**
 * Main test:
 * 
 *  - docker show
 *  - docker clean-up
 *  - composer correctness
 *  - image build
 *  - composer consistency with docker-images
 *  - start-up of the containers following the composer-file
 *  
 * 
 * @author Dmitri Boulanger, Hombach
 *         D. Knuth: Programs are meant to be read by humans and
 *         only incidentally for computers to execute
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainShowTest extends TestAbstraction {
    private static final Logger LOG = LoggerFactory.getLogger(MainShowTest.class);

    private static Commander commander;
    private static final Map<String, ImageSource> imageSources = TestImageSources.imageSources("test/");
    private static final Composer composer = new Composer(testComposerFile());

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
    }
    
    /**
     * verifies consistency of the composer and images sources
     * 
     * @throws Exception
     */
    @Test
    public void t10_verifyConsistencyCreatedImagesAndComposer() throws Exception {
        composer.assertThatImageSourcesCoverContainerSpecication(imageSources);
        LOG.info("Composer-file contents: " + composer.print());
    }
    
    
}
