package de.dbo.samples.springboot.jbehave2.IT.commons.stories;

import java.util.List;

import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoriesProvider {
    private static final Logger log = LoggerFactory.getLogger(StoriesProvider.class);
    
    public List<String> defaultStoryPaths(final Class fromClass) {
        final List<String> storyPaths =
            new StoryFinder(). findPaths(CodeLocations.codeLocationFromClass(fromClass), "**/*.story", "**/excluded/*.story");
        final StringBuilder sb = new StringBuilder("Stories found:");
        for (final String path : storyPaths) {
            sb.append("\n\t - " + path);
        }
        log.info(sb.toString());
        return storyPaths;
    }

}
