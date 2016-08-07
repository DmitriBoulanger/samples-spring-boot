package de.dbo.samples.springboot.utilities.print;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;

/**
 * Pretty-print utilities.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public class Print {
    
    /**
     * pretty-print of the bean-names in the application context
     * 
     * @param applicationContext
     * @return pretty-print table with sorted bean-names
     */
    public static final StringBuilder beanNames(final ApplicationContext applicationContext) {
	final List<String> beanNames = Arrays.asList(applicationContext.getBeanDefinitionNames());
	Collections.sort(beanNames);
	final StringBuilder sb = new StringBuilder("Available beans:");
	for (final String beanName:beanNames) {
	    sb.append("\n\t - " + Pad.right(beanName, 60));
	}
	return sb;
    }
    
}
