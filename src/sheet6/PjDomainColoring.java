package sheet6;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import jv.function.PuComplexFunction;
import jv.geom.PgElementSet;
import jv.number.PuComplex;
import jv.project.PjProject;
import jv.project.PvCameraIf;

/**
 * Empty DomainColoring template for SciVis exercise sheet 6.
 * 
 */
public class PjDomainColoring extends PjProject {

	/** Default image resolution. */
	static final int RES_X = 500;
	static final int RES_Y = 500;

	/**
	 * This array defines a rectangular region in the complex plane, given by
	 * its lower left (first two entries) and upper right corner (last two
	 * entries).
	 */
	static final double[] DOMAIN_BOUNDS = { -1., -1., 1., 1. };

	/** Function object holding the definition of the complex function. */
	protected PuComplexFunction m_function;

	/** Color scheme to be used. */
	protected ColorScheme m_cs;

	/** Element set onto which the texture is mapped. */
	protected PgElementSet m_geom;

	public PjDomainColoring() {
		super("Domain Coloring");

		m_function = new PuComplexFunction(1, 1);
		m_function.setParent(this);
		m_function.setName("Function");
		m_function.setExpression("z");

		m_geom = new PgElementSet(3);

		m_cs = new MyColorScheme();

		init();
	}

	public void init() {
		super.init();

		// Initialize plane geometry
		m_geom.computePlane(10, 10, -1., -1., 1., 1.);
		m_geom.setDimOfTextures(2);
		m_geom.makeVertexTextureFromUV(10, 10, 0);
		m_geom.assureVertexTextures();
		m_geom.showVertexTexture(true);
		m_geom.showEdges(false);
		m_geom.showVertices(false);

	}

	public void start() {
		m_display.setBackgroundColor(Color.BLACK);
		m_display.selectCamera(PvCameraIf.CAMERA_ORTHO_XY);
		m_display.addGeometry(m_geom);
		compute();
	}

	/**
	 * Computes the new texture image and updates the plane geometry texture. 
	 */
	private void compute() {
		Image img = computeTexture();
		m_geom.setTextureImage(img);
		m_geom.update(m_geom);
	}

	/**
	 * Computes an image using the color scheme {@link PjDomainColoring#m_cs} and function {@link #m_function}.
	 * @return
	 */
	private Image computeTexture() {
		int bufSize = RES_X * RES_Y;

		PuComplex z = new PuComplex();
		PuComplex fz;

		// Linear (!) pixel buffer holding rgba-values for every pixel
		int pixel_buffer[] = new int[bufSize];

		for (int j = 0; j < RES_Y; j++) {
			for (int i = 0; i < RES_X; i++) {
				/*
				 * ADD YOUR CHANGES HERE: 1. Compute corresponding point in
				 * rectangle in complex representation, use z.
				 */
				// ...
                                
                                z.re=(DOMAIN_BOUNDS[2]-DOMAIN_BOUNDS[0])/RES_X*i+DOMAIN_BOUNDS[0];
                                z.im=(DOMAIN_BOUNDS[3]-DOMAIN_BOUNDS[1])/RES_Y*j+DOMAIN_BOUNDS[1];

				// Evaluates specified function at point z and compute color.
				fz = m_function.eval(z);
				int col = m_cs.getColor(fz);

				/*
				 * ADD YOUR CHANGES HERE: 2. Figure out how to convert the (i,j)
				 * pixel index to an index of the linear pixel array
				 * pixel_buffer and assign the computed color to this entry.
				 */
				// ...
                                
                                pixel_buffer[j*RES_X+i]=col;
				
			}
		}

		MemoryImageSource mis = new MemoryImageSource(RES_X, RES_Y,
				pixel_buffer, 0, RES_X);
		return Toolkit.getDefaultToolkit().createImage(mis);
	}

	public boolean update(Object event) {
		if (event == getInfoPanel()) {

			return super.update(this);
		} else if (event == m_function) {
			compute();
			return true;
		}
		return super.update(event);
	}

}
