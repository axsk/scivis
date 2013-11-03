package sheet3;


import MinJV.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jv.geom.PgElementSet;
import jv.object.PsDebug;
import jv.object.PsPanel;
import jv.vecmath.PiVector;

@SuppressWarnings("serial")

public class StarsNLinks extends MinJV{
	
	protected Button Star = new Button("Star");
	protected Button Link = new Button("Link");

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

		losButtons.add(Star);
		Star.addActionListener(this);
		
		losButtons.add(Link);
		Link.addActionListener(this);
		
		pjip.add(losButtons);
		pjip.addLine(1);
	}
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == Star){
			PsDebug.message("I am preforming a stary action!");
		} else if(source == Link){
			PsDebug.message("I am preforming a linking action!");
		}
	}
	
	private PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
	//private PgVectorField selction = geo.getSelectedVectorField(); 
	

}
