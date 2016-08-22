package de.dbo.samples.springboot.jbehave2.IT.commons.util.print.special;

/**
 * Pretty-Print padding utilities.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public class Pad {
    
    /**
     * pad with " " to the right to the given length (n)
     * @param s string to be padded
     * @param n length of the output
     * @return padded string having the specified length
     */
     public static String right(final String s, int n) {
       return String.format("%1$-" + n + "s", s);
     }

     /** 
      * pad with " " to the left to the given length (n)
      * @param s string to be padded
      * @param n length of the output
      * @return padded string having the specified length
      */
     public static String left(final String s, int n) {
       return String.format("%1$" + n + "s", s);
     }

  

}
