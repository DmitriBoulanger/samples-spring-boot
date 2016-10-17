package de.ityx.response.it.docker.container;

import static de.ityx.response.it.docker.util.DockerPrint.printList;
import static de.ityx.response.it.docker.util.Utils.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.NotImplementedException;

import com.github.dockerjava.api.model.Container;

public final class ContainerFilter {
    private final Set<String> keys;
    private final boolean     any;

    public ContainerFilter() {
        this.keys = null;
        this.any = true;
    }

    public ContainerFilter(final String key, final boolean any) {
        this.keys = Arrays.stream(new String[]{key}).collect(Collectors.toSet());
        this.any = any;
    }

    public ContainerFilter(final Set<String> keys, final boolean any) {
        this.keys = keys;
        this.any = any;
    }
    
    public ContainerFilter(final String[] keys, final boolean any) {
        this.keys = Arrays.stream(keys).collect(Collectors.toSet());
        this.any = any;
    }

    public boolean isMatch(final Container container) {
        if (isEmpty(keys)) {
            return false;
        }

        final String[] names = container.getNames();
        if (any) {
            return keyFound(keys,names);
            
        }
        else {
            throw new NotImplementedException("AND-filter is not yet implemented");
        }
    }
}
