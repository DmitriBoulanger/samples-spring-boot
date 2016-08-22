package de.dbo.samples.springboot.jbehave2.IT.commons.util.print;

/**
 * Printable objects.
 * Any printable object can be printed as a column of lines or as a single line
 *
 * @author Dmitri Boulanger, Hombach
 *
 */
public interface Printable {

    public abstract StringBuilder printlines();

    public abstract StringBuilder printline();
}
