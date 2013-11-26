package sheet6;

import jv.number.PuComplex;
import java.lang.Math;

public class MyColorScheme implements ColorScheme {

	@Override
	public int getColor(PuComplex z) {
		/*
		 * ADD YOUR CHANGES HERE:
		 * Write an interesting color scheme! It should return the color as an integer RGB-value according to the 
		 * format as in java.awt.Color.getRGB().
		 */
                
		//int max = (z.arg()%(3.14/4)<(3.14/32))?0.5:0;
                
            float rax = ((5*z.abs())-Math.floor(5*z.abs()) < 0.1d) ? 0.7f : 1f;
		// This is just an example how to ensure that the resulting color has full (=opaque) alpha channel.
	    //return java.awt.Color.HSBtoRGB((float)(z.arg()/(2*java.lang.Math.PI)),1f, ax);
            float alx = (((z.arg()/(Math.PI/4))-Math.floor(z.arg()/(Math.PI/4)))*(Math.PI/4) < Math.PI/200) ? 0.7f : 1f;
            return java.awt.Color.HSBtoRGB((float)(z.arg()/(2*java.lang.Math.PI)),rax, (float)(-1/(z.abs()+1)+1)*alx);

            
	}

}
