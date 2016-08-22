package de.dbo.samples.springboot.jbehave2.IT.commons.util.print.special;

/**
 * Pretty-Print Series: lines for a banner
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public interface Line {
    
    public static final String LINE      = "=====================================================================================================================";
    public static final String LINE_DICK = "#####################################################################################################################";
    
    public static final String TAB = "\t ";
    public static final String TAB_ITEM = TAB + "- ";
    public static final String NL  = "\n";
    
    public static final String LINE_BEGIN = NL + TAB + LINE;
    public static final String LINE_END   = NL + TAB + LINE + NL;
    
    public static final String LINE_DICK_BEGIN = NL + TAB + LINE_DICK;
    public static final String LINE_DICK_END   = NL + TAB + LINE_DICK + NL;
    
}
