package de.dbo.samples.springboot.utilities.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class LoggingInfo {

  public static StringBuilder print() {
    final StringBuilder sb = new StringBuilder("Available loggers:");
    for(final Logger logger : getLoggerContext().getLoggerList()) {
	sb.append("\n\t - " + padRight(logger.getName(),90));
	final Level level = logger.getEffectiveLevel();
	if (null!=level) {
	    sb.append(padRight(level.toString(),6));
	}
    }
    return sb;
  }
  
  public static boolean hasLogger(final String name) {
      return null!=  getLoggerContext().getLogger(name);
  }
  
  public static void printInternalState() {
      StatusPrinter.print(getLoggerContext());  
      getLoggerContext().getStatusManager().clear();
   }
  
  private static final LoggerContext getLoggerContext() {
//      LoggerContext x;
//      x.getStatusManager().
      return (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
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
