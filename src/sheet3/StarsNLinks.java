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
import jvx.geom.PgVertexStar;
import java.awt.Color;

@SuppressWarnings("serial")

public class StarsNLinks extends MinJV{
	
	protected Button Star = new Button("Star");
	protected Button Link = new Button("Link");
	protected Button fNeighbor = new Button("F-Neighbor");

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
		
		losButtons.add(fNeighbor);
		fNeighbor.addActionListener(this);
		
		pjip.add(losButtons);
		pjip.addLine(1);
	}
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == Star){
			//PsDebug.message("I am preforming a stary action!");
			painStar();
		} else if(source == Link){
			//PsDebug.message("I am preforming a linking action!");
			paintLink();
		} else if(source == fNeighbor){
			paintNeighbor();
		}
	}
	
	public void painStar(){
		PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
		int[] verts = geo.getMarkedVertices();
		PgVertexStar star = new PgVertexStar();
		
		for(int v:verts){
			//getting the star
			star.makeVertexStar(geo, v, -1);
			int[] stare = star.getElement().getEntries();
			
			//painting the star
	        for (int vec: stare) {
	             geo.setElementColor(vec, Color.YELLOW);
	        }
		}
		
		//PsDebug.message(stare.toString());
		geo.showElementColors(true);
        geo.update(null);
	}
	public void paintLink(){
		PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
		int[] verts = geo.getMarkedVertices();
		PgVertexStar star = new PgVertexStar();
		
		for(int v:verts){
			//getting the star
			star.makeVertexStar(geo, v, -1);
			int[] link = star.getLink().getEntries();
			PsDebug.message(link.toString());
			
			//painting the star
	        for (int vec: link) {
	             geo.setVertexColor(vec, Color.YELLOW);
	        }
		}
		geo.showVertices(true);
		geo.showVertexColors(true);
        geo.update(null);
	}
	public void paintNeighbor(){
		//paint the neighbor
	}
	
	private PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
	//private PgVectorField selction = geo.getSelectedVectorField(); 
	

}
