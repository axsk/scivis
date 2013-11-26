package sheet6;

import jv.number.PuComplex;

public interface ColorScheme {
	
	/**
	 *  Computes and returns an integer RGB color value for the point z.  
	 *  
	 * @param z A complex number specifying a point in the plane.
	 * @return An integer RGBA value.
	 */
	int getColor(PuComplex z);
}
