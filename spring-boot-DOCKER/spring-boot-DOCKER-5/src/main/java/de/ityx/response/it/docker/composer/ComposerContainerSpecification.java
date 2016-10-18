package de.ityx.response.it.docker.composer;

import static de.ityx.response.it.docker.util.DockerPrint.*;
import java.io.FileNotFoundException;
import java.util.List;

import de.ityx.response.it.docker.image.ImageSource;

public class ComposerContainerSpecification {
    
    private String title;
    private String image;
    private String network;
    private ComposerPorts composePorts;
    private List<String> links;
    private List<String> environment;
    
    private ImageSource imageSource;
    
    ComposerContainerSpecification() {
	
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public List<String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<String> environment) {
        this.environment = environment;
    }

    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public ComposerPorts getPorts() {
        return composePorts;
    }
    public void setPorts(ComposerPorts composePorts) {
        this.composePorts = composePorts;
    }
    
    public List<String> getLinks() {
        return links;
    }
    
    public void setLinks(List<String> links) {
        this.links = links;
    }
    
    public ImageSource getImageSource() {
        return imageSource;
    }

    public void setImageSource(final ImageSource imageSource) {
        this.imageSource = imageSource;
    }
    
    public final StringBuilder print() throws FileNotFoundException {
	final StringBuilder sb = new StringBuilder();
	    sb.append("\n\t - CONTAINER " + title);
	    sb.append("\n\t    - Image        : " + image);
	    sb.append("\n\t    - Image source : " + (null==imageSource? NULL  : imageSource.getName()));
	    sb.append("\n\t    - Image ID     : " + (null== imageSource? NULL : imageSource.getDockerImageId()));
	    sb.append("\n\t    - Network name : " + network);
	    sb.append("\n\t    - Ports        : " + composePorts.print());
	    sb.append("\n\t    - Links        : " + links);
	    sb.append("\n\t    - Environment  : " + environment);
	return sb;
    }
}
