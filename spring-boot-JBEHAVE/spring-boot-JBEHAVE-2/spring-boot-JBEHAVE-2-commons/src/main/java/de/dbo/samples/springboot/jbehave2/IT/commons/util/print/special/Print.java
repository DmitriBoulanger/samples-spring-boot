package de.dbo.samples.springboot.jbehave2.IT.commons.util.print.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* Spring-Boot */
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
public class Print implements Line {
    
    /**
     * Pretty print of a string-list.
     * Before printing list is sorted in the standard way
     * 
     * @param name title to appear as a in-line header
     * @param list data to be printed
     * @return pretty-print table
     */
    public static StringBuilder printList(final String name, final List<String> list) {
	final StringBuilder sb = forName(name);
	sb.append(Line.LINE_BEGIN);
	if (list.isEmpty()) {
	    sb.append(NL + TAB + "  ## EMPTY! ## ");
	} else {
	    for (final String item : list) {
		sb.append(NL + TAB_ITEM + item);
	    }
	}
	sb.append(Line.LINE_END);
	return sb;
    }
    
    // ====================================================================================================================
    //                    SPECIAL PRETTY-PRINTS
    // ====================================================================================================================
    
    /**
     * Pretty-print of the bean-names in the application context
     * 
     * @param applicationContext
     * @return pretty-print table with sorted bean-names
     */
    public static final StringBuilder beanNames(final ApplicationContext applicationContext) {
	final List<String> beanNames = Arrays.asList(applicationContext.getBeanDefinitionNames());
	Collections.sort(beanNames);
	return printList("Available beans:", beanNames);
    }
    

    /**
     * Pretty-print of configured properties.
     * 
     * @param name title to appear as a in-line header
     * @param isDebugEnabled 
     * @param triples data to be printed
      * @return pretty-print table
     */
    public static final StringBuilder configurationProperties(final String name, final ConfigurationPropertyTriple[] triples) {
	return configurationProperties(name,  Arrays.asList(triples));
    }
    
    private static StringBuilder forName(String name) {
	return new StringBuilder(name + (name.endsWith(":")? NL:""));
    }

    public static final StringBuilder configurationProperties(final String name, final List<ConfigurationPropertyTriple> triples) {
	final StringBuilder sb = forName(name);
	sb.append(Line.LINE_BEGIN);
	for (ConfigurationPropertyTriple triple: triples) {
	    appendProperyTriple(sb,triple);
	}
	sb.append(Line.LINE_END);
	return sb;
    }
    
    private static final int SPACE1 = 36;
    private static final int SPACE2 = 40;
    private static final int SPACE3 = 8;
    
    private static final void appendProperyTriple(final StringBuilder sb, ConfigurationPropertyTriple triple) {
	sb.append(NL + TAB_ITEM  + Pad.right(triple.property(),SPACE1)    +    " = " + Pad.right(triple.propertyValue(),SPACE3));
	final String systemValue = System.getProperty(triple.systemPropery());
	final String marker;
	if (null==systemValue) {
	    marker = "    #####  ";
	} else {
	    marker = "   <=====  ";
	}
	sb.append(marker + Pad.right( triple.systemPropery(), SPACE2) + " = " + systemValue);
    }
    
    public static class ConfigurationPropertyTriple {
	
	private final String property;
	private final String propertyValue; 
	private final String systemPropery;
	
	 public ConfigurationPropertyTriple( final String property, final String propertyValue, final String systemPropery) {
	        this.property = property;
	        this.propertyValue = propertyValue; 
	        this.systemPropery = systemPropery;
	 }
	 
	 public ConfigurationPropertyTriple( final String property, final boolean propertyValue, final String systemPropery) {
	        this.property = property;
	        this.propertyValue = Boolean.toString(propertyValue); 
	        this.systemPropery = systemPropery;
	 }
	 
	 public ConfigurationPropertyTriple( final String property, final int propertyValue, final String systemPropery) {
	        this.property = property;
	        this.propertyValue = Integer.toString(propertyValue); 
	        this.systemPropery = systemPropery;
	 }

	public String property() {
	    return property;
	}

	public String propertyValue() {
	    return propertyValue;
	}

	public String systemPropery() {
	    return systemPropery;
	}
    };
	
    
    
    
}
