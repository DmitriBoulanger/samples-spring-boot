package de.ityx.response.it.docker.image;

import static de.ityx.response.it.docker.util.Utils.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.NotImplementedException;

import com.github.dockerjava.api.model.Image;

public final class ImageFilter {
    private final Set<String> keys;
    private final boolean     any;

    public ImageFilter() {
        this.keys = null;
        this.any = true;
    }

    public ImageFilter(final String key, final boolean any) {
        this.keys = Arrays.stream(new String[]{key}).collect(Collectors.toSet());
        this.any = any;
    }

    public ImageFilter(final Set<String> keys, final boolean any) {
        this.keys = keys;
        this.any = any;
    }
    
    public ImageFilter(final String[] keys, final boolean any) {
        this.keys = Arrays.stream(keys).collect(Collectors.toSet());
        this.any = any;
    }

    public boolean isMatch(final Image image) {
        if (isEmpty(keys)) {
            return false;
        }

        final String[] tags = image.getRepoTags();
        if (any) {
            return keyFound(keys,tags);
        }
        else {
            throw new NotImplementedException("AND-filter is not yet implemented");
        }
    }
}
