package sheet3;

import MinJV.*;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;

import jv.geom.PgElementSet;
import jv.object.PsObject;
import jv.object.PsPanel;
import jv.vecmath.PiVector;
import jvx.geom.PgVertexStar;

@SuppressWarnings("serial")

public class StarsNLinks extends MinJV {

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

    public StarsNLinks() {
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
        if (source == Star) {
            painStar();
        } else if (source == Link) {
            paintLink();
        } else if (source == fNeighbor) {
            paintNeighbor();
        }
    }

    public void painStar() {
        //paint the star
        PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
        PgVertexStar star = new PgVertexStar();
        for (int marked : geo.getMarkedVertices()) {
            star.makeVertexStar(geo, marked, -1);
            for (int i : star.getElement().getEntries()) {
                geo.setElementColor(i, Color.yellow);
            }
        }
        geo.showElementColors(true);
        geo.update(null);
    }

    public void paintLink() {
        //paint the link     
        PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
        PgVertexStar star = new PgVertexStar();
        for (int marked : geo.getMarkedVertices()) {
            star.makeVertexStar(geo, marked, -1);
            for (int i : star.getLink().getEntries()) {
                geo.setVertexColor(i, Color.orange);
            }
        }
        geo.showVertices(true);
        geo.showVertexColors(true);
        geo.update(null);
    }

    public void paintNeighbor() {
        //paint the neighbor
        PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
        for (int i = 0; i <= geo.getNumElements() - 1; i++) {
            if (geo.hasTagElement(i, PsObject.IS_SELECTED)) {
                PiVector neighs = geo.getNeighbour(i);
                for (int neigh : neighs.getEntries()) {
                    geo.setElementColor(neigh, Color.GREEN);
                }
                geo.setElementColor(i, Color.GREEN);
            }
        }
        geo.showElementColors(true);
        geo.update(null);
    }

    private PgElementSet geo;
}
