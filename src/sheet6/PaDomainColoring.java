package sheet6;

import java.applet.Applet;
import java.awt.*;

import jv.viewer.PvViewer;


public class PaDomainColoring extends Applet {
	/**
	 * If variable m_frame!=null then this applet runs in an application,
	 * otherwise applet runs inside an Html page within a browser.
	 * Further, when running as application then this frame is the container
	 * of the applet.
	 */
	public		Frame				m_frame			= null;
	/**
	 * 3D-viewer window for graphics output, display is embedded into the applet.
	 * This instance variable allows JavaView to perform start, stop and destroy
	 * operations when the corresponding methods of this applet are called,
	 * for example, by a browser.
	 */
	protected	PvViewer			m_viewer;

	/**
	 * Configure and initialize the viewer, load system and user projects.
	 * One of the user projects must be selected here.
	 */
	public void init() {
		// Create viewer for viewing 3d geometries. References to the applet and frame
		// allow JavaView to decide whether program runs as applet or standalone application,
		// and, in the later case, it allows to use the frame as parent frame.
		m_viewer = new PvViewer(this, m_frame);

		// Create and load a project which contains the user application. Putting code
		// in a JavaView project allows to reuse the project in other applications.
		PjDomainColoring myProject = new PjDomainColoring();
		m_viewer.addProject(myProject);
		m_viewer.selectProject(myProject);

		setLayout(new BorderLayout());
		// Get 3d display from viewer and add it to applet
		add(m_viewer.getDisplay().getCanvas(), BorderLayout.CENTER);
	}
	/**
	 * Standalone application support. The main() method acts as the applet's
	 * entry point when it is run as a standalone application. It is ignored
	 * if the applet is run from within an HTML page.
	 */
	public static void main(String args[]) {
		PaDomainColoring app	= new PaDomainColoring();
		// Create toplevel window of application containing the applet
		Frame frame	= new jv.object.PsMainFrame(app, args);
		frame.pack();
		// Store the variable frame inside the applet to indicate
		// that this applet runs as application.
		app.m_frame = frame;
		app.init();
		// In application mode, explicitly call the applet.start() method.
		app.start();
		// Set size of frame when running as application.
		frame.setSize(640, 550);
		frame.setVisible(true);
	}
	
	/**
	 * Does clean-up when applet is destroyed by the browser.
	 * Here we just close and dispose all our control windows.
	 */
	public void destroy()	{ m_viewer.destroy(); }

	/** Start viewer, e.g. start animation if requested */
	public void start()		{ m_viewer.start(); }

	/** Stop viewer, e.g. stop animation if requested */
	public void stop()		{ m_viewer.stop(); }
}
