package de.ityx.response.it.docker.pull;

import static de.ityx.response.it.docker.util.DockerPrint.DONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.PullImageResultCallback;

import de.ityx.response.it.docker.image.ImageManager;

public class PullManager {
    private static final Logger LOG                   = LoggerFactory.getLogger(PullManager.class);

    public static final String  IMAGE_OFFICIAL_PREFIX = "dreg.ityx.de:5000/official/";
    public static final String  IMAGE_LATEST_SUFFICX  = "latest";

    private PullManager() {

    }

    public static void pullOfficialImage(final String simpleName, final String tag, final boolean alwaysPull, final DockerClient dockerClient)
            throws FileNotFoundException, IOException {
        final String compleImageName = IMAGE_OFFICIAL_PREFIX + simpleName + ":" + (null == tag ? IMAGE_LATEST_SUFFICX : tag);
        final String msg = "Pulling image [" + compleImageName + "] ";
        LOG.info(msg + "....");

        final List<Image> images = dockerClient.listImagesCmd().withImageNameFilter(compleImageName).exec();
        if (null == images || images.isEmpty()) {
            doPullImage(compleImageName,dockerClient);
        }
        else if (!images.isEmpty() && alwaysPull) {
            assertThat("There are several images [" + compleImageName + "] but exactly one expected", images, hasSize(1));
            LOG.info(msg + "exists. Removing ...");
            final Image image = images.get(0);
            dockerClient.removeImageCmd(image.getId()).withForce(true).exec();
            doPullImage(compleImageName,dockerClient);
        } else {
            doPullImage(compleImageName,dockerClient);
        }
            
        LOG.info(msg + DONE);
    }
    
    private static void doPullImage(final String compleImageName, final DockerClient dockerClient) {
        final PullImageCmd pullImageCmd = dockerClient.pullImageCmd(compleImageName);
        pullImageCmd.exec(new PullImageResultCallback()).awaitSuccess();
        final InspectImageResponse inspectImageResponse = dockerClient.inspectImageCmd(compleImageName).exec();
        assertThat("No image-pull confirmation for image image [" + compleImageName + "]", inspectImageResponse, notNullValue());
    }

}
