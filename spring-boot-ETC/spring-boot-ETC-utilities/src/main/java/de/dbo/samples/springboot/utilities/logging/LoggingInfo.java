package de.dbo.samples.springboot.utilities.logging;


import ch.qos.logback.classic.Logger;

import de.dbo.samples.springboot.utilities.print.Pad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * Utility methods to get Logback informations.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public class LoggingInfo {
    
    /**
     *  default space in characters to be reserved for logger-name when printing loggers as a table
     */
    public static final int DEFAULT_LOGGER_NAME_SPACE = 90;

    /**
     * @return pretty-print (as a table) of all available loggers and their levels
     */
    public static StringBuilder printAvailableLoggers() {
	return printAvailableLoggers(0);
    }
    
  
    /**
     * 
     * @param nameSpace space in characters to be reserved for logger-name
     * @return @return pretty-print (as a table) of all available loggers and their levels
     */
    public static StringBuilder printAvailableLoggers(final int nameSpace) {
	final LoggerContext ctx = getLogbackContext();
	final List<String> loggerNames = getLogbackLoggerNames(ctx);
	final StringBuilder sb = new StringBuilder("Available loggers (" + loggerNames.size() +") in the logback-context ["+ ctx.getName()+"]:");
	final Set<String> filter = new HashSet<String>();
	for(final String loggerName : loggerNames) {
	    final Logger logger = ctx.getLogger(loggerName);
	    final String normalizedLoggerName = normalizeLoggerName(logger.getName());
	    if (filter.contains(normalizedLoggerName)) {
		continue;
	    }
	    
	    filter.add(normalizedLoggerName);
	    sb.append("\n\t - " + Pad.right(normalizedLoggerName,(0>=nameSpace? DEFAULT_LOGGER_NAME_SPACE : nameSpace)));
	    final Level level = logger.getEffectiveLevel();
	    if (null!=level) {
		sb.append(Pad.right(level.toString(),6));
	    }
	}
	return sb;
    }
    
    private static final String normalizeLoggerName(final String origibalLoggerName) {
	final int extensionPostion = origibalLoggerName.indexOf("$");
	if (-1!=extensionPostion) {
	    return origibalLoggerName.substring(0, extensionPostion);
	} else {
	    return origibalLoggerName;
	}
    }

    /**
     * 
     * @param name name of a logger
     * @return true if logger with the specified name exists in the context
     */
    public static boolean hasLogger(final String name) {
	return null!=  getLogbackContext().getLogger(name);
    }

    /**
     * forces logback to print directly to the console its status.
     * Informations can be useful to see errors or reasons for strange behavior of the logger
     */
    public static void printInternalStateToConsole() {
	StatusPrinter.print(getLogbackContext());  
	getLogbackContext().getStatusManager().clear();
    }

    /**
     * forces logback to print directly to the console warnings or errors from its status.
     * Informations can be useful to see errors or reasons for strange behavior of the logger
     */
    public static void printInternalStateWarningsToConsole() {
	StatusPrinter.print(getLogbackContext(), Level.WARN_INT);  
	getLogbackContext().getStatusManager().clear();
    }

    /**
     * with this context one can change behavior of the logback logger,
     * 
     * @return context of the logback logger
     * @throws IllegalStateException if the logger context is null
     */
    public static final LoggerContext getLogbackContext() {
	final LoggerContext ctx =  (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();;
	if (null==ctx) {
	    throw new IllegalStateException("Logback context is null!");
	}
	return ctx;
    }


    public static final List<String> getLogbackLoggerNames (final LoggerContext ctx ) {
	final List<String> ret = new ArrayList<String>();
	for(final Logger logger : ctx.getLoggerList()) {
	    ret.add(logger.getName());
	}
	Collections.sort(ret);
	return ret;
	
    }



}
