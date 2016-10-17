package de.ityx.response.it.docker.composer;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ityx.response.it.docker.composer.Composer;
import de.ityx.response.it.docker.testimpl.TestAbstraction;
import de.ityx.response.it.docker.testimpl.TestImageSources;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComposerTest extends TestAbstraction {
    private static final Logger LOG = LoggerFactory.getLogger(ComposerTest.class);

    /**
     * read composer-file and verify that it is covered with images-sources
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void t00_readComposeFile() throws FileNotFoundException, IOException {
        final Composer composer = new Composer(testComposerFile());
        LOG.info("Composer-file contents: " + composer.print());
        composer.assertThatImagesourcesCoverContainerSpecication(TestImageSources.imageSources("test/composer"));
    }

}
