package de.dbo.samples.springboot.utilities.logging;


import de.dbo.samples.springboot.utilities.print.Pad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.action.AppenderRefAction;
import ch.qos.logback.core.status.Status;
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
	return printAvailableLoggers(0,false);
    }
    
    /**
     * 
     * @param nameSpace space in characters to be reserved for logger-name
     * @return @return pretty-print (as a table) of all available loggers and their levels
     */
    public static StringBuilder printAvailableLoggers(final int nameSpace) {
   	return printAvailableLoggers(nameSpace,false);
    }
    
  
    /**
     * 
     * @param nameSpace space in characters to be reserved for logger-name
     * @param effective use effective level or not
     * @return pretty-print (as a table) of all available loggers and their levels
     */
    public static StringBuilder printAvailableLoggers(final int nameSpace, boolean effective) {
	final LoggerContext ctx = getLogbackContext();
	final List<String> loggerNames = getLogbackLoggerNames(ctx);
	final StringBuilder sb = new StringBuilder((effective? "Effective":"Defined")
	                      +" logback-loggers (from total " + loggerNames.size() +") in the context ["+ ctx.getName()+"]:");
	final Set<String> filter = new HashSet<String>();
	for(final String loggerName : loggerNames) {
	    final Logger logger = ctx.getLogger(loggerName);
	    final Level level = effective? logger.getEffectiveLevel() : logger.getLevel();
	    if (null==level) {
		continue;
	    }
	    final String normalizedLoggerName = normalizeLoggerName(logger.getName());
	    if (filter.contains(normalizedLoggerName)) {
		continue;
	    }
	    
	    filter.add(normalizedLoggerName);
	    sb.append("\n\t - " + Pad.right(normalizedLoggerName,(0>=nameSpace? DEFAULT_LOGGER_NAME_SPACE : nameSpace)));
	    sb.append(Pad.right(level.toString(),6));
	    if (logger.isAdditive()) {
		sb.append( "   additive" );
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
    }
    
    public static StringBuilder printAppenderAttachments() {
	final List<Status> statusList = getLogbackContext().getStatusManager().getCopyOfStatusList();
	if(statusList.isEmpty()) {
	   return  new StringBuilder("Logback-Appender attachments is empty?");
	}
	final StringBuilder sb = new StringBuilder("Logback-Appender attachments: ");
	for (final Status status:statusList) {
	    final Object origin = status.getOrigin();
	    if (origin instanceof AppenderRefAction ) {
		final Level level = Level.toLevel(status.getEffectiveLevel());
		sb.append("\n\t - " + Pad.right(status.getMessage()
			.replaceAll("Attaching appender named ", "Appender ")
			.replaceAll(AppenderRefAction.class.getName(), "")
			.replaceAll(":", "")
			.trim(),DEFAULT_LOGGER_NAME_SPACE+20) );
		sb.append(Pad.right( level.toString(), 6));
	    }
	}
	return sb;
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
