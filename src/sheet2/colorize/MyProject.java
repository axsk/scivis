package sheet2.colorize;

import java.awt.Color;

import jv.anim.PsAnimation;
import jv.anim.PsTimeEvent;
import jv.number.PuInteger;
import jv.project.PjProject;

/**
 * Demo project for working with JavaView projects and for subclassing of PgElementSet.
 * This sample project shows how to handle timer events and supplies an own computation
 * method for creating the coordinates of an (animated) surface.
 * <p>
 * In this project a classes is embedded in a JavaView project. Nearly all applications
 * in JavaView are embedded into projects, each with a special functionality. Such
 * projects can be reused as a building blocks in other cirumstances, similar to a class.
 * In fact, a project is a subclass  of {@link jv.project.PjProject jv.project.PjProject}
 * which allows to manage a set of geometries, a display(s), and other projects, and
 * to react on animation and pick events.
 * <p>
 * This project also shows how to work with animations. Here an instance of class
 * {@link jv.anim.PsAnimation PsAnimation} is created where the current project registers
 * itself as dynamic object to be able to receive time events. Whenever the time changes
 * the animation manager invokes the method {@link #setTime(PsTimeEvent) setTime(PsTimeEvent)} of
 * this project.
 * 
 * @see			jv.anim.PsAnimation
 * @see			jv.geom.PgElementSet
 * @see			jv.project.PjProject
 * @author		Konrad Polthier
 * @version		25.02.01, 1.51 revised (kp) Obsolete call to method setAnimation() removed.<br>
 *					28.11.00, 1.50 revised (kp) Applet simplified for beginners.<br>
 *					05.08.99, 1.00 created (kp)
 */
public class MyProject extends PjProject {
	protected	MySurface	m_geom;			// Surface of geometry
	protected	PuInteger	m_numULines;	// Discretization along u parameter line with slider
	protected	PuInteger	m_numVLines;	// Discretization along v parameter line with slider

	/**
	 * Constructor, without arguments to allow loading of project from menu.
	 */
	public MyProject() {
		super("MyProject");
		m_geom = new MySurface();				// Create an own geometry
		m_geom.setName("Catenoid");			// Optionally, give it a name

		PsAnimation anim = new PsAnimation();	// Create an animation, which sends timer events
		anim.setName("Associate Family");	// Set title of the animation dialog.
		anim.addTimeListener(this);			// Register this project in animation to receive setTime(time) events.
				// In return, the animation automatically registers itself in this project's superclass
				// since the superclass implements the interface PsTimeListenerIf. This enables this
				// class to access the animation class using super.getAnimation(), and it enables the
				// user to access the animation panel within the display via ctrl-a or menu.

		// Create integers with given label and this project as parent, i.e. sliders
		// are now children of this project and send update(Object) to project whenever they change.
		m_numULines = new PuInteger("Number u-Lines", this);
		m_numVLines = new PuInteger("Number v-Lines", this);

		init();										// Call to init() is required.
	}
	/**
	 * Do initialization of data structures; method is also used to reset instances.
	 */
	public void init() {
		super.init();
		// Initialize integers bounds and values.
		m_numULines.setDefBounds(2, 50, 1, 5);
		m_numULines.setDefValue(15);
		m_numULines.init();
		m_numVLines.setDefBounds(2, 50, 1, 5);
		m_numVLines.setDefValue(9);
		m_numVLines.init();
		
		m_geom.setGlobalElementColor(new Color(255, 37, 113));
		m_geom.showElementColors(false);
		m_geom.setGlobalElementBackColor(new Color(200, 200, 0));
		m_geom.showElementBackColor(true);
		m_geom.computeSurface(m_numULines.getValue(), m_numVLines.getValue());	// Compute initial surface
	}
	/**
	 * Start project, e.g. start an animation. Method is called once when project is
	 * selected in PvViewer#selectProject(). Method is optional. For example, if an
	 * applet calls the start() method of PvViewer, then PvViewer tries to invoke
	 * the start() method of the currently selected project.
	 */
	public void start() {
		addGeometry(m_geom);
		selectGeometry(m_geom);

		if (super.hasAnimation()) {
			PsAnimation anim = super.getAnimation();
			anim.setTimeInterval(0., 720., 10., 20.);
			anim.getAnimationPanel().setVisible(true);	// Show anmiation panel
			anim.start();											// Start animation
		}
		super.start();
	}

	/**
	 * Method is called from animation(-panel) whenever time has changed
	 * since this project is registered as dynamic object in the animation.
	 * Method is optional, but allows a project to update geometry to new current time.
	 */
	public boolean setTime(PsTimeEvent timeEvent) {
		double time = timeEvent.getTime();
		m_geom.computeAnimation(time);		// Compute new vertices of geometry
		m_geom.update(m_geom);					// Send update to geometry to refresh display
		return super.update(this);				// Update info panel of this project
	}

	/**
	 * Update method of project to react on changes in its panel or of its children.
	 * This method is optional, but required if project is parent of a child.
	 * Project becomes parent if child calls <code>child.setParent(this)</code> with
	 * this project as argument. For example, see the constructor of MyProject.
	 * Project must react on child events, or forward them to its superclass.
	 * <p>
	 * Catch events of integer children and recompute surface.
	 */
	public boolean update(Object event) {
		if (event == getInfoPanel()) {
			m_geom.computeSurface(m_numULines.getValue(), m_numVLines.getValue());	// Recompute surface
			m_geom.update(m_geom);
			return super.update(this);
		} else if (event==m_numULines || event==m_numVLines) {
			m_geom.computeSurface(m_numULines.getValue(), m_numVLines.getValue());	// Recompute surface
			m_geom.update(m_geom);
			return super.update(this);
		}
		return super.update(event);
	}
}

