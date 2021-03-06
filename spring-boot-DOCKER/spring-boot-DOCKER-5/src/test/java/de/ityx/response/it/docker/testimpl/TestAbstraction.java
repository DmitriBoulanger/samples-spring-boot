package de.ityx.response.it.docker.testimpl;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.BeforeClass;

public class TestAbstraction {

    protected static final Properties TEST_PROPERTIES = new Properties();

    @BeforeClass
    public static void init() throws IOException {
        TEST_PROPERTIES.load(ClassLoader.getSystemClassLoader().getResourceAsStream("test.properties"));
    }

    public static File testRepository() {
        return new File(TEST_PROPERTIES.getProperty("image-sources-directory"));
    }

    public static File testComposerFile() {
        return new File(TEST_PROPERTIES.getProperty("composer"));
    }

    public static File sourceDockerDirectory(final String service) {
        return new File(testRepository(), service + "/target/docker");
    }

}
