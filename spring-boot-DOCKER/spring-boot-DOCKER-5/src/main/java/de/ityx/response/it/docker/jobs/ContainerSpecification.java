package de.ityx.response.it.docker.jobs;

import java.io.FileNotFoundException;
import java.util.List;

public class ContainerSpecification {
    
    private String title;
    private String image;
    private List<String> ports;
    private List<String> links;
    
    private ImageSource imageSource;
    
    ContainerSpecification() {
	
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public List<String> getPorts() {
        return ports;
    }
    public void setPorts(List<String> ports) {
        this.ports = ports;
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

    public void setImageSource(ImageSource imageSource) {
        this.imageSource = imageSource;
    }
    
    public final StringBuilder print() throws FileNotFoundException {
	final StringBuilder sb = new StringBuilder();
	    sb.append("\n\t - CONTAINER " + title);
	    sb.append("\n\t    - Image " + image);
	    sb.append("\n\t    - Ports " + ports);
	    sb.append("\n\t    - Links " + links);
	return sb;
    }
}
