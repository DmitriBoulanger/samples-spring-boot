package de.dbo.samples.springboot.jbehave2.IT.commons.util.io;

import java.io.PrintStream;

/**
 * Helper to shut-down console print-streams.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public class DummyPrintStream extends PrintStream {
	
	public DummyPrintStream() {
	    super(new DummyOutputStream());
	}
	
}
