package de.ityx.response.it.docker.image;

import static de.ityx.response.it.docker.util.PrintManager.DONE;
import static de.ityx.response.it.docker.util.PrintManager.printImage;
import static de.ityx.response.it.docker.util.PrintManager.printImageTags;
import static de.ityx.response.it.docker.util.PrintManager.printImages;
import static de.ityx.response.it.docker.util.PrintManager.q;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.BuildImageResultCallback;

import de.ityx.response.it.docker.commander.Commander;

public final class ImageManager {
    private static final Logger     LOG                  = LoggerFactory.getLogger(Commander.class);
    
    private ImageManager() {
	
    }
    
    public static  List<Image> showAvaiableImages(final boolean all, final DockerClient dockerClient) throws Exception {
  	 final String msg = "Available images ";
           LOG.info(msg + "....");
           final List<Image> images = avialbleImages(all,dockerClient);
           final String info;
           if (images.isEmpty()) {
               info = "No images found";
           } else {
               info = "Found " + images.size() + " image(s): " + printImages(images);
           }
           LOG.info(msg + DONE + info);
           return images;
       }
      
    
    public static String createImage(final ImageSource imageSource, final String tag, 
	    final DockerClient dockerClient) throws Exception {
 	final String msg = "Creating image with tag "+  q(tag) + " ";
         LOG.info(msg + "...");
 	 imageSource.load();
         final File baseDirectory = imageSource.getBaseDirectory();
         final BuildImageCmd buildImageCmd = dockerClient.buildImageCmd();
         buildImageCmd.withBaseDirectory(baseDirectory);
         buildImageCmd.withDockerfile(imageSource.dockerFile());
         final String path = imageSource.getRepositoryPath();
         final String actualTag;
         if (null!=path) {
             actualTag = path + tag;
         } else {
             actualTag = tag;
         }
         buildImageCmd.withTag(actualTag);
         final String imageId = buildImageCmd.exec(new BuildImageResultCallback()).awaitImageId();
         LOG.info(msg + DONE + "Image: " + printImage(dockerClient.listImagesCmd().exec(), imageId));
         imageSource.setDockerImageId(imageId);
         imageSource.close();
         return imageId;
     }
    
    /**
     * remove available images. Removes all images but not those specified in
     * the negative filter-string. If the negative filter is not null then any
     * image having occurrence of the filter-string in some tag is NOT removed
     * 
     * @param all
     *            if true then the available images are listed as CLI "docker
     *            images -a"
     * @param negativeFilter
     *            if null then it is ignored
     * @param dockerClient
     * @return
     * @throws Exception
     */
    public static boolean removeAvaiableImages(final boolean all, final String negativeFilter,
	    final DockerClient dockerClient) throws Exception {
	return removeAvaiableImages(avialbleImages(all, dockerClient), negativeFilter, dockerClient);
    }
    
    public static List<Image> avialbleImages(final boolean all, final DockerClient dockerClient) {
	return  dockerClient.listImagesCmd().withShowAll(true).exec();
    }
    
    // ==============================================================================================================================
    // PRIVATE IMPLEMENTATION
    // ==============================================================================================================================

    private static boolean keepImage(final Image image, final String negativeFilter, final String msg) {
	final ImageFilter imageFilter = new ImageFilter(negativeFilter,true);
	if (imageFilter.isMatch(image)) {
	    LOG.info(msg + " - keep the image with tags: " + printImageTags(image.getRepoTags()));
		return true;  
	} else {
	    return false;
	}
    }

    private static boolean removeAvaiableImages(final List<Image> images, final String negativeFilter,
	    final DockerClient dockerClient) throws Exception {
	final String msg = "Removing available images ";
	LOG.info(msg + "....");
	boolean ret = true;
	final List<Image> imagesWithConflict = new ArrayList<Image>();
	for (final Image image : images) {
	    if (keepImage(image, negativeFilter, msg)) {
		continue;
	    }
//	    if (!nn(image.getParentId())) {
//		imagesWithConflict.add(image);
//		continue;
//	    }
	    try {
		dockerClient.removeImageCmd(image.getId()).withForce(true).exec();
	    } catch (com.github.dockerjava.api.exception.NotFoundException imageNotFoundException) {
		continue;
	    } catch (com.github.dockerjava.api.exception.ConflictException imageConflictException) {
		imagesWithConflict.add(image);
	    } catch (Exception e) {
		final String errInfo = msg + " - can't remove image: \n";
		if (LOG.isDebugEnabled()) {
		    LOG.error(errInfo, e);
		} else {
		    LOG.warn(errInfo + e.getMessage());
		}
		ret = false;
	    }
	}
	if (imagesWithConflict.isEmpty()) {
	    return ret;
	}

	final int conflictCnt = imagesWithConflict.size();
	LOG.info(msg + " - removing {} confliction image(s) ...", conflictCnt);
	for (final Image image : imagesWithConflict) {
	    try {
		dockerClient.removeImageCmd(image.getId()).withForce(true).exec();
	    } catch (com.github.dockerjava.api.exception.NotFoundException imageNotFoundException) {
		continue;
	    } catch (Exception e) {
		LOG.warn(msg + " - can't remove image: " + e.getMessage());
		e.printStackTrace();
		ret = false;
	    }
	}

	LOG.info(msg + DONE);
	return ret;
    }
    
}
