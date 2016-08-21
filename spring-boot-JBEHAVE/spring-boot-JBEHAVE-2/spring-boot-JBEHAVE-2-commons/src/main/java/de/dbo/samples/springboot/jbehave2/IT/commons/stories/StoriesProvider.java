package de.dbo.samples.springboot.jbehave2.IT.commons.stories;

import de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Line;
import de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Print;

import java.net.URL;
import java.util.List;

import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoriesProvider {
    private static final Logger log = LoggerFactory.getLogger(StoriesProvider.class);
    
    public List<String> defaultStoryPaths(final Class<?> location) {
	final String include = "**/*.story";
	final String exclude = "**/excluded/*.story";
	final StoryFinder storyFinder = new StoryFinder();
	final URL storiesURL = CodeLocations.codeLocationFromClass(location);
        final List<String> storyPaths = storyFinder.findPaths(storiesURL, include, exclude);
        log.info(Print.printList("Stories found (as include=["+include+"] exclude=["+exclude+"])" + Line.NL + Line.TAB + " URL=" + storiesURL
        	,storyPaths).toString());
        return storyPaths;
    }

}
