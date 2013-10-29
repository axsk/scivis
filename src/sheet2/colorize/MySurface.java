package sheet2.colorize;

import jv.geom.PgElementSet;
import jv.vecmath.PuMath;

/**
 * Subclass of geometry class PgElementSet for demonstration.
 * Default functions describe a catenoid, which can be deformed into a helicoid.
 * This class has two methods to compute the vertices of the surface
 * and the mesh connectivity. Another method is used to compute the
 * vertices during the animation depending on an animation parameter.
 * 
 * @author		Konrad Polthier
 * @version		28.11.00, 1.50 revised (kp) Applet simplified for beginners.<br>
 *					11.03.00, 1.10 revised (kp) Use call getVertices() to access vertices instead direct access to member.<br>
 *					04.08.99, 1.00 created (kp)
 */
public class MySurface extends PgElementSet { 
	/** Number of vertices in u-direction. */
	protected		int	m_numULines;
	/** Number of vertices in v-direction. */
	protected		int	m_numVLines;

	/** Constructor of surface. */
	public MySurface() {
		super(3);	// Call super with dimension of ambient space in which geometry lives.
		init();		// Lowest class must call init()
	}

	/**
	 * Compute static surface with given discretization.
	 * Connectivity is generated for a quadrilateral mesh.
	 * Method is synchronized with computeAnimation() to avoid conflicts between
	 * animation and user interaction via panel.
	 */
	public synchronized void computeSurface(int numULines, int numVLines) {
		// Compute points of surface
		setNumVertices(numULines*numVLines);
		double uFac = 2.*Math.PI/(-1.+numULines);
		double vFac = 4./(-1.+numVLines);
		int ind = 0;
		for (int i=0; i<numULines; i++) {
			double u = uFac*i;
			for (int j=0; j<numVLines; j++) {
				double v = -2.+vFac*j;
				setVertex(ind, Math.cos(u)*PuMath.cosh(v), Math.sin(u)*PuMath.cosh(v), v);
				ind++;
			}
		}
		// Generate connectivity for quadrilateral mesh
		makeQuadrConn(numULines, numVLines);

		// Compute surface normals
		makeElementNormals();
		makeVertexNormals();
		
		// Store discretization for possible later use in animation below.
		m_numULines = numULines;
		m_numVLines = numVLines;
	}
	/**
	 * Generate element and neighbourhood information for a quadrilateral surface.
	 * Method generates only topological information, no coordinates are required.
	 * <p>
	 * Current method is copied 1-1 from PgElementSet just as tutorial example.
	 * Usually, the connectivity is calculated automatically by methods like this.
	 * 
	 * @param		uDiscr		Number of lines in <code>u</code> direction.
	 * @param		vDiscr		Number of lines in <code>v</code> direction.
	 * @see			PgElementSet#makeCylinderConn(int, int)
	 * @author		Konrad Polthier
	 * @version		20.08.99, 1.02 revised (ur) Orientation of all elements changed to be counter-clockwise in (i,j) domain <br>
	 *					13.02.99, 1.01 revised (kp) Orientation of triangles changed to be counter-clockwise and identical to rectangular case.<br>
	 *					19.10.97, 1.00 created (kp)
	 */
	public void makeQuadrConn(int uDiscr, int vDiscr) {
		if (uDiscr<2 || vDiscr<2) return;
		setDimOfElements(4);
		setNumElements((uDiscr-1)*(vDiscr-1));
		int ind=0;
		for(int i=0; i<uDiscr-1; i++) {
			for(int j=0; j<vDiscr-1; j++) {
				// Assign indices of four vertices to element with index ind.
				setElement(ind, i*vDiscr + j, (i+1)*vDiscr + j,
							  (i+1)*vDiscr + j+1, i*vDiscr + j+1);
				ind++;
			}
		}
		// Compute neighbour relation between adjacent elements.
		makeNeighbour();
	}

	/**
	 * Compute new surface depending on parameter which varies in time.
	 * Connectivity is not computed since it does not change since last
	 * call to computeSurface(int, int).
	 * 
	 * Animated surface is an interpolation between a catenoid and a helicoid.
	 * Method is synchronized with computeAnimation() to avoid conflicts between
	 * animation and user interaction via panel.
	 */
	public synchronized void computeAnimation(double a) {
		if (m_numULines<2 || m_numVLines<2)
			return;
		// For efficieny, use a simple test to reduce number of mesh computations.
		if (getNumVertices()!=m_numULines*m_numVLines) {
			// Recompute points of surface
			setNumVertices(m_numULines*m_numVLines);
			// Generate connectivity for quadrilateral mesh
			makeQuadrConn(m_numULines, m_numVLines);
			// Optionally, generate boundary for quadrilateral mesh
			makeQuadrBnd(m_numULines, m_numVLines);
		}
		// compute points
		int ind = 0;
		double ca = Math.cos(a*Math.PI/360.);
		double sa = Math.sin(a*Math.PI/360.);
		double uFac = 2.*Math.PI/(-1.+m_numULines);
		double vFac = 4./(-1.+m_numVLines);
		for (int i=0; i<m_numULines; i++) {
			double u = uFac*i;
			for (int j=0; j<m_numVLines; j++) {
				double v = -2.+vFac*j;
				setVertex(ind,
							  ca*Math.cos(u)*PuMath.cosh(v) + sa*Math.sin(u)*PuMath.sinh(v),
							 -ca*Math.sin(u)*PuMath.cosh(v) + sa*Math.cos(u)*PuMath.sinh(v),
							  ca*v                          + sa*(u-Math.PI));
				ind++;
			}
		}
		// compute surface normals
		makeElementNormals();
		makeVertexNormals();
	}
}
