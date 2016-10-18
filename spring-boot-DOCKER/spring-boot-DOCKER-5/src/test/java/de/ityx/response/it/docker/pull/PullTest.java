package de.ityx.response.it.docker.pull;

import java.io.FileNotFoundException;
import java.io.IOException;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PullTest extends TestAbstraction {
    private static final Logger LOG = LoggerFactory.getLogger(PullTest.class);
    
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
    public void t00_pullAnOfficialImage() throws FileNotFoundException, IOException {

        PullManager.pullOfficialImage("busybox", null, true /* pull even if exists */ , commander.dockerClient());
        PullManager.pullOfficialImage("busybox", null, false /* don't pull if exists */ , commander.dockerClient());
    }

}
