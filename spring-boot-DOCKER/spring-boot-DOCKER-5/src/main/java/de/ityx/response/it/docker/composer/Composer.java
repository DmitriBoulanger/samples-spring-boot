package de.ityx.response.it.docker.composer;

import static org.hamcrest.MatcherAssert.assertThat;
import static de.ityx.response.it.docker.util.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

import com.github.dockerjava.api.model.Image;

import de.ityx.response.it.docker.image.ImageSource;

public class Composer {

    private final File                                        composeFile;

    private final Map<String, ComposerContainerSpecification> containerSpecifications = new HashMap<>();

    @SuppressWarnings("rawtypes")
    private final LinkedHashMap                               composeYaml;

    @SuppressWarnings("rawtypes")
    public Composer(final File composeFile) {
        this.composeFile = composeFile;
        assertFileCorrectnes();
        try {
            final Yaml yaml = new Yaml();
            this.composeYaml = (LinkedHashMap) yaml.load(new FileInputStream(composeFile));
        }
        catch(Exception e) {
            throw new IllegalStateException("Failure loading/parsing compose-file: ", e);
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
        for (final Object title : composeYaml.keySet()) {
            ret.add((String) title);
        }
        return ret;
    }

    public final void assertThatImageSourcesCoverContainerSpecication(final Map<String, ImageSource> imageSources)
            throws FileNotFoundException {
        final List<String> imageSourceComposerTitles = new ArrayList<String>();
        for (final ImageSource imageSource : imageSources.values()) {
            final String title = imageSource.getComposerTitle();
            imageSourceComposerTitles.add(title);
            getContainerSpecification(title).setImageSource(imageSource);
        }
        assertThatCovers("Container specification [%s] is not covered by the image-sources", imageSourceComposerTitles,
                getContainerTitles());
    }

    public final void assertThatComposerImagesCoveredByavailableImages(final List<Image> images)
            throws FileNotFoundException {
        final List<String> imageTags = new ArrayList<String>();
        for (final Image image : images) {
            final String tags = tags(image);
            if (null == tags) {
                continue;
            }
            imageTags.add(tags(image));
        }

        final List<String> imageReferences = new ArrayList<String>();
        for (final String containerTitle : getContainerTitles()) {
            imageReferences.add(getContainerSpecification(containerTitle).getImage());
        }

        assertThatCovers("Composer image specification [%s] is not covered by the available images", imageTags,
                imageReferences);
    }

    private static String tags(final Image image) {
        final StringBuilder sb = new StringBuilder();
        final String[] tags = image.getRepoTags();
        if (null == tags) {
            return null;
        }
        for (final String tag : tags) {
            sb.append(tag);
        }
        final String ret = sb.toString();
        final int posVersion = ret.indexOf(":");
        if (-1 != posVersion) {
            return new String(ret.substring(0, posVersion));
        }
        else {
            return ret;
        }
    }

    public final ComposerContainerSpecification getContainerSpecification(final String title)
            throws FileNotFoundException {
        if (null == containerSpecifications.get(title)) {
            final ComposerContainerSpecification containerSpecification = new ComposerContainerSpecification();
            containerSpecification.setTitle(title);
            containerSpecification.setImage(getContainerImage(title));
            containerSpecification.setNetwork(getContainerNetwork(title));
            containerSpecification.setPorts(new ComposerPorts(getContainerPorts(title)));
            containerSpecification.setLinks(getContainerLinks(title));
            containerSpecification.setEnvironment(getContainerEnvironment(title));
            containerSpecifications.put(title, containerSpecification);

        }
        return containerSpecifications.get(title);
    }

    public final StringBuilder printAsItIs() throws FileNotFoundException {
        final StringBuilder sb = new StringBuilder();
        for (final Object x : composeYaml.entrySet()) {
            sb.append("\n\t - " + x);
        }
        return sb;
    }

    public final StringBuilder print() throws FileNotFoundException {
        final List<String> titles = getContainerTitles();
        final StringBuilder sb = new StringBuilder();
        for (final String title : titles) {
            final ComposerContainerSpecification containerSpecification = getContainerSpecification(title);
            sb.append(containerSpecification.print());
        }
        return sb;
    }

    // ==============================================================================================================================
    // PRIVATE IMPLEMENTATION
    // ==============================================================================================================================

    private final void assertFileCorrectnes() {
        assertThat("Compose file doesn't exist. Check the file:\n" + composeFile,
                composeFile.exists() && composeFile.isFile());
    }

    @SuppressWarnings("rawtypes")
    private final String getContainerImage(final String containerTitle) {
        return (String) ((LinkedHashMap) composeYaml.get(containerTitle)).get("image");
    }
    
    @SuppressWarnings("rawtypes")
    private final String getContainerNetwork(final String containerTitle) {
        return (String) ((LinkedHashMap) composeYaml.get(containerTitle)).get("network");
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final List<String> getContainerPorts(final String containerTitle) {
        return (List<String>) ((LinkedHashMap) composeYaml.get(containerTitle)).get("ports");
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final List<String> getContainerEnvironment(final String containerTitle) {
        return (List<String>) ((LinkedHashMap) composeYaml.get(containerTitle)).get("environment");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final List<String> getContainerLinks(final String containerTitle) {
        return (List<String>) ((LinkedHashMap) composeYaml.get(containerTitle)).get("links");
    }

}
