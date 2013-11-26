package sh6_domaincoloring_empty;

import jv.number.PuComplex;

public class MyColorScheme implements ColorScheme {

	@Override
	public int getColor(PuComplex z) {
		/*
		 * ADD YOUR CHANGES HERE:
		 * Write an interesting color scheme! It should return the color as an integer RGB-value according to the 
		 * format as in java.awt.Color.getRGB().
		 */
	
		
		// This is just an example how to ensure that the resulting color has full (=opaque) alpha channel.
		return 255<<24 | 42;
	}

}
