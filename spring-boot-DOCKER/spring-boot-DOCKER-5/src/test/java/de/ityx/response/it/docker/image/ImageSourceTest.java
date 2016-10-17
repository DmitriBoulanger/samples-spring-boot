package de.ityx.response.it.docker.image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ityx.response.it.docker.testimpl.TestAbstraction;
import de.ityx.response.it.docker.testimpl.TestImageSources;

/**
 * Image-source management and consistency with composer-file.
 * 
 * @author Dmitri Boulanger, Hombach
 * 
 *         D. Knuth: Programs are meant to be read by humans and
 *         only incidentally for computers to execute
 */
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
        final Map<String, ImageSource> imageSources = TestImageSources.imageSources("test/images/");
        for (final String name : imageSources.keySet()) {
            final ImageSource imageSource = imageSources.get(name);
            imageSource.load();
            LOG.info("Created Image Source: " + imageSource.print());
            imageSource.close();
        }
    }

}
