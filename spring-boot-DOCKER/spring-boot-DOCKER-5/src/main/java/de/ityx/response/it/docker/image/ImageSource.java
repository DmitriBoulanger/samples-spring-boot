package de.ityx.response.it.docker.image;

import static de.ityx.response.it.docker.util.DockerPrint.NLT;
import static de.ityx.response.it.docker.util.DockerPrint.left;
import static de.ityx.response.it.docker.util.DockerPrint.right;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Source data to be used while creating Docker Image. It includes at list
 * JAR-file and Dockerfile
 * 
 * @author Dmitri Boulanger, Hombach
 *
 *         D. Knuth: Programs are meant to be read by humans and only
 *         incidentally for computers to execute
 *
 */
public class ImageSource {
    private static final Logger LOG = LoggerFactory.getLogger(ImageSource.class);

    public static final String DOCKER_FILENAME = "Dockerfile";

    private final File applicationFile;
    private final File applicationDockerfile;
    private final File baseDirectory;

    private final String repositotyPath;
    private final String composerTitle;

    private String dockerImageId;

    /**
     * creates image source assuming default Dockerfile located in the
     * application directory
     * 
     * @param name
     *            client-local name of the image base-directory
     * @param applicationFile
     *            source application-file
     * @param composerTitle
     *            reference in the composer-file that appears as an container
     *            title
     * @param applicationFile
     *            source application-file
     */
    public ImageSource(final String name, final String composerTitle, final File applicationFile) {
	this(name, composerTitle, applicationFile, new File(applicationFile.getParentFile(), DOCKER_FILENAME), null);
    }

    public ImageSource(final String name, final String composerTitle, final File applicationFile, final String path) {
	this(name, composerTitle, applicationFile, new File(applicationFile.getParentFile(), DOCKER_FILENAME), path);
    }

    /**
     * 
     * @param name
     *            client-local name of the image base-directory
     * @param composerTitle
     *            reference in the composer-file that appears as an container
     *            title
     * @param applicationFile
     *            source application-file
     * @param applicationDockerfile
     *            source application docker-file
     */
    public ImageSource(final String name, final String composerTitle, final File applicationFile,
	    final File applicationDockerfile, final String repositotyPath) {
	this.applicationDockerfile = applicationDockerfile;
	this.applicationFile = applicationFile;
	this.baseDirectory = new File("target/docker-client/" + name + "/");
	this.baseDirectory.mkdirs();
	this.repositotyPath = repositotyPath;
	this.composerTitle = composerTitle;

	assertCorrectnes();
    }

    /**
     * name of this image-source. It is a local directory name with
     * image-sources
     * 
     * @return local name of this image-source
     */
    public final String getName() {
	return baseDirectory.getName();
    }

    public final String getRepositoryPath() {
	return repositotyPath;
    }

    public final File getBaseDirectory() {
	return baseDirectory.getAbsoluteFile();
    }

    /**
     * @return pretty-print
     */
    public final StringBuilder print() {
	final int nameWidth = 40;
	final int lengthWidth = 10;
	final StringBuilder sb = new StringBuilder();
	sb.append(NLT + "Source name:      " + getName());
	sb.append(NLT + "Repositoy path:   " + getRepositoryPath());
	sb.append(NLT + "Composer title:   " + getComposerTitle());
	sb.append(NLT + "Location:         " + getBaseDirectory());
	sb.append(NLT + "Application file: " + right(applicationFile().getName(), nameWidth)
		+ left("" + applicationFile().length(), lengthWidth));
	sb.append(NLT + "Dockerfile:       " + right(dockerFile().getName(), nameWidth)
		+ left("" + dockerFile().length(), lengthWidth));
	return sb;
    }

    /**
     * load sources into the client-local base directory
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public final void load() throws FileNotFoundException, IOException {
	if (null != getBaseDirectory().listFiles() && 0 < getBaseDirectory().listFiles().length) {
	    LOG.warn("Base directory is not empty. Cleaning-up ...");
	    FileUtils.deleteDirectory(getBaseDirectory());
	    FileUtils.forceMkdir(getBaseDirectory());
	}
	FileUtils.copyFile(applicationFile, new File(baseDirectory, applicationFile.getName()));
	FileUtils.copyFile(applicationDockerfile, new File(baseDirectory, applicationDockerfile.getName()));

	assertThat("Copy of the Dockerfile doesn't exist",
		applicationDockerfile.exists() && applicationDockerfile.isFile());
	assertThat("Copy of the Application file doesn't exist",
		applicationFile().exists() && applicationFile().isFile());
    }

    /**
     * removes the local source-image directory
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public final void close() throws FileNotFoundException, IOException {
	if (null != getBaseDirectory()) {
	    FileUtils.deleteDirectory(getBaseDirectory());
	}
    }

    public File applicationFile() {
	return new File(baseDirectory, applicationFile.getName());
    }

    public File dockerFile() {
	return new File(baseDirectory, applicationDockerfile.getName());
    }

    public String getComposerTitle() {
	return composerTitle;
    }

    /**
     * ID of the corresponding docker-image
     * 
     * @return not-null only if the docker-image has been generated
     */
    public String getDockerImageId() {
	return dockerImageId;
    }

    /**
     * sets ID of the corresponding docker-image
     */
    public void setDockerImageId(String dockerImageId) {
	this.dockerImageId = dockerImageId;
    }

    // ==============================================================================================================================
    // PRIVATE IMPLEMENTATION
    // ==============================================================================================================================

    private final void assertCorrectnes() {
	assertThat("Source Dockerfile doesn't exist. Check the file:\n" + applicationDockerfile,
		applicationDockerfile.exists() && applicationDockerfile.isFile());
	assertThat("Source Application file doesn't exist. Check the file:\n" + applicationFile,
		applicationFile.exists() && applicationFile.isFile());
	assertThat("Target Image base-directory doesn't exist. Cannot be created? ",
		baseDirectory.exists() && baseDirectory.isDirectory());
    }

}
