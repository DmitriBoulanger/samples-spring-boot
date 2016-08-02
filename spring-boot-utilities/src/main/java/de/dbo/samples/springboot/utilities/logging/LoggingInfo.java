package de.dbo.samples.springboot.utilities.logging;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.util.StatusPrinter;

public class LoggingInfo {

  public static StringBuilder print() {
     final StringBuilder sb = new StringBuilder("Available loggers:");
    final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    final List<ch.qos.logback.classic.Logger> loggers = loggerContext.getLoggerList();
    for(final ch.qos.logback.classic.Logger logger:loggers) {
	final Level level = logger.getLevel();
	sb.append("\n\t - " + padRight(logger.getName(),90));
	if (null!=level) {
	    sb.append(padRight(level.toString(),6));
	}
	
    }
    return sb;
  }
  
  public static boolean hasLogger(final String name) {
      final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
      return null!= loggerContext.getLogger(name);
  }
  
  public static void printInternalState() {
      StatusPrinter.print((Context) LoggerFactory.getILoggerFactory());  
   }
  
  
  /**
   * pad with " " to the right to the given length (n)
   * @param s string to be padded
   * @param n length of the output
   * @return padded string having the specified length
   */
   public static String padRight(final String s, int n) {
     return String.format("%1$-" + n + "s", s);
   }

   /** 
    * pad with " " to the left to the given length (n)
    * @param s string to be padded
    * @param n length of the output
    * @return padded string having the specified length
    */
   public static String padLeft(final String s, int n) {
     return String.format("%1$" + n + "s", s);
   }
}
