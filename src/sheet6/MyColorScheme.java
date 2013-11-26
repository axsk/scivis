package sheet6;

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
	    return java.awt.Color.HSBtoRGB((float)(z.arg()/(2*3.14)), (float)1., (float)(-1/(z.abs()+1)+1));
	}

}
