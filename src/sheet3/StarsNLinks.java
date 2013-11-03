package sheet3;


import MinJV.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import jv.geom.PgElementSet;
import jv.object.PsDebug;
import jv.object.PsPanel;
import jv.vecmath.PiVector;

@SuppressWarnings("serial")

public class StarsNLinks extends MinJV {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StarsNLinks app = new StarsNLinks();
		if (args.length != 0) {
	        app.loadModel(args[0]);
	    } else {
	        app.loadModel(null);
	    }

	}
	
	public StarsNLinks(){
		PsPanel pjip = this.project.getInfoPanel();
		pjip.addTitle("Stars 'n' Links");
		pjip.addLine(1);
	}
	
	private PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
	//private PgVectorField selction = geo.getSelectedVectorField(); 
	
	private Button addButton(String name) {
		final Button button = new Button(name);
		return button;
	}

}
