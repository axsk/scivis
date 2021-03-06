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
import jvx.geom.PgVertexStar;

@SuppressWarnings("serial")
public class StarsNLinks extends MinJV {

    protected Button bStar = new Button("Star");
    protected Button bLink = new Button("Link");
    protected Button bFNeighbor = new Button("F-Neighbor");
    protected Button bReset = new Button("Reset Colors");

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
        app.resetColors();
    }

    public StarsNLinks() {
        PsPanel pjip = this.project.getInfoPanel();
        pjip.addTitle("Stars 'n' Links");

        Panel losButtons1 = new Panel(new FlowLayout(FlowLayout.CENTER));
        Panel losButtons2 = new Panel(new FlowLayout(FlowLayout.CENTER));

        losButtons1.add(bStar);
        bStar.addActionListener(this);

        losButtons1.add(bLink);
        bLink.addActionListener(this);

        losButtons1.add(bFNeighbor);
        bFNeighbor.addActionListener(this);

        losButtons2.add(bReset);
        bReset.addActionListener(this);

        pjip.add(losButtons1);
        pjip.add(losButtons2);
        pjip.addLine(1);
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == bStar) {
            paintStar();
        } else if (source == bLink) {
            paintLink();
        } else if (source == bFNeighbor) {
            paintNeighbor();
        } else if (source == bReset) {
            resetColors();
        }
    }

    public void resetColors() {
        PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();

        for (int i = 0; i < geo.getNumElements(); i++)
            geo.setElementColor(i, Color.CYAN);
        for (int i = 0; i < geo.getNumVertices(); i++)
            geo.setVertexColor(i, Color.BLACK);

        geo.showElementColors(true);
        geo.showVertices(false);
        geo.update(null);
    }

    public void paintStar() {
        PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
        PgVertexStar star = new PgVertexStar();

        for (int vert : geo.getMarkedVertices()) {
            //calculate star
            star.makeVertexStar(geo, vert, -1);
            //paint the star
            for (int face : star.getElement().getEntries()) {
                geo.setElementColor(face, Color.YELLOW);
            }
        }

        geo.showElementColors(true);
        geo.update(null);
    }

    public void paintLink() {
        PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();
        PgVertexStar star = new PgVertexStar();

        for (int vert : geo.getMarkedVertices()) {
            //calculate link
            star.makeVertexStar(geo, vert, -1);
            //paint the link
            for (int link : star.getLink().getEntries()) {
                geo.setVertexColor(link, Color.YELLOW);
            }
        }
        geo.showVertices(true);
        geo.showVertexColors(true);
        geo.update(null);
    }

    public void paintNeighbor() {
        PgElementSet geo = (PgElementSet) jvViewer.getCurrentProject().getGeometry();

        for (int elem = 0; elem < geo.getNumElements(); elem++) {
            if (geo.hasTagElement(elem, PsObject.IS_SELECTED)) {
                //if selected, color the face
                geo.setElementColor(elem, Color.GREEN);
                //and its neighbours
                for (int neighb : geo.getNeighbour(elem).getEntries()) {
                    geo.setElementColor(neighb, Color.GREEN);
                }
            }
        }

        geo.showElementColors(true);
        geo.update(null);
    }

}
