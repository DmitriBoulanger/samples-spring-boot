package de.ityx.response.it.docker.jobs;

import java.util.Set;

import org.apache.commons.lang.NotImplementedException;

import com.github.dockerjava.api.model.Image;

public final class ImageFilter {
    private final String value;
    private final Set<String> values;
    private final boolean any;
    
    public ImageFilter() {
   	this.value = null;
   	this.values = null;
   	this.any = true;
    }
    
    public ImageFilter(final String value, final boolean any) {
	this.value = value;
	this.values = null;
	this.any = any;
    }
    
    public ImageFilter(final Set<String> values, final boolean any) {
	this.value = null;
	this.values = values;
	this.any = any;
    }
    
    private boolean isEmptyFilter() {
	return null == value && (null==values || values.isEmpty());
    }
    
    public boolean isMatch(final Image image) {
   	if (isEmptyFilter()) {
   	    return false;
   	}
   	
   	final String[] tags = image.getRepoTags();
   	if (any) {
   	    return checkValue(tags) || checkValues(tags);
   	} else {
   	    throw new NotImplementedException("AND-filter is not yet implemented");
   	}
    }
    
    private final boolean checkValue(final String[] tags) {
	if (null==value) {
	    return false;
	}
	return checkValue(value,tags);
    }
    
    private static final boolean checkValue(final String value, final String[] tags) {
	if (null==value) {
	    return false;
	}
	
	for (final String tag : tags) {
   	    if (!nn(tag)) {
   		continue;
   	    } else if (-1 != tag.indexOf(value)) {
   		return true;
   	    }
   	}
	return false;
    }
    
    private final boolean checkValues(final String[] tags) {
	if (null==values || values.isEmpty()) {
	    return false;
	}
	
	for (final String value:values) {
	    if (checkValue(value,tags)) {
		return true;
	    }
	}
	
	for (final String tag : tags) {
   	    if (!nn(tag)) {
   		continue;
   	    } else if (-1 != tag.indexOf(value)) {
   		return true;
   	    }
   	}
	return false;
    }
    
    private static boolean nn(final String x) {
	return null != x && 0 != x.trim().length();
    }
}
