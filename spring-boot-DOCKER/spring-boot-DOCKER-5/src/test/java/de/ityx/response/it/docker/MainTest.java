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
import de.ityx.response.it.docker.composer.ComposerContainerSpecification;
import de.ityx.response.it.docker.container.ContainerManager;
import de.ityx.response.it.docker.image.ImageManager;
import de.ityx.response.it.docker.image.ImageSource;
import de.ityx.response.it.docker.testimpl.TestAbstraction;
import de.ityx.response.it.docker.testimpl.TestImageSources;

/**
 * Main test:
 * - docker show
 * - docker clean-up
 * - composer correctness
 * - image build
 * - composer consistency with docker-images
 * - start-up of the containers following the composer-file
 * 
 * @author Dmitri Boulanger, Hombach
 *         D. Knuth: Programs are meant to be read by humans and
 *         only incidentally for computers to execute
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest extends TestAbstraction {
    private static final Logger                   LOG          = LoggerFactory.getLogger(MainTest.class);

    private static Commander                      commander;
    private static final Map<String, ImageSource> imageSources = TestImageSources.imageSources("test/");
    private static final Composer                 composer     = new Composer(testComposerFile());

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
        commander.removeDockerResources( /* but not */ new String[] { "ddev", "dreg.ityx.de", "official" });
    }

    /**
     * verifies consistency of the composer and created images
     * 
     * @throws Exception
     */
    @Test
    public void t10_verifyConsistencyCreatedImagesAndComposer() throws Exception {
        composer.assertThatImageSourcesCoverContainerSpecication(imageSources);
        LOG.info("Composer-file contents: " + composer.print());
    }

    @Test
    public void t20_buildImages() throws Exception {
        for (final String name : imageSources.keySet()) {
            final ImageSource imageSource = imageSources.get(name);
            commander.createImage(imageSource, name.toLowerCase());
        }
    }

    /**
     * verify that all image-references in the composer-file are covered
     * 
     * @throws Exception
     */
    @Test
    public void t21_verifyComposerImageReferences() throws Exception {
        final List<Image> images = commander.avialbleImages();
        composer.assertThatComposerImagesCoveredByavailableImages(images);
    }

    /**
     * start the images following the composer-file
     * 
     * @throws Exception
     */
    @Test
    public void t30_createAndRunContainres() throws Exception {
        final List<String> titles = composer.getContainerTitles();
        for (final String title : titles) {
            final ComposerContainerSpecification containerSpecification = composer.getContainerSpecification(title);
            commander.createAndStartContainer(containerSpecification,  true);
        }
        ContainerManager.showAvaiableContainers(false, commander.dockerClient());
    }

}
