package sheet3;


import MinJV.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;
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
		
		Panel losButtons = new Panel(new FlowLayout(FlowLayout.CENTER));
		
		Button Star = new Button("Star");
		losButtons.add(Star);
		//Star.addActionListener(null);
		Button Link = new Button("Link");
		losButtons.add(Link);
		//Link.addActionListener(null);
		
		pjip.add(losButtons);
		pjip.addLine(1);
	}
	
	private PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
	//private PgVectorField selction = geo.getSelectedVectorField(); 
	

}
