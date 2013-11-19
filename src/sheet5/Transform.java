package sheet5;

import MinJV.*;
import java.lang.Math;
import java.awt.Button;
import java.awt.event.ActionEvent;
import jv.number.PuComplex;
import jv.geom.PgElementSet;

import jv.number.PuDouble;
import jv.number.PuInteger;
import jv.object.PsConfig;
import jv.object.PsPanel;
import jv.object.PsUpdateIf;
import jv.vecmath.PdVector;

@SuppressWarnings("serial")
public class Transform extends MinJV {

    PuDouble[] m_mask = new PuDouble[6];
    PuInteger steps;
    PdVector[] orig = null;
    Button bReset = new Button("Reset");

    public static void main(String[] args) {
        Transform app = new Transform();
        app.loadModel("../SciVis/sheet5/perfect_earth.jvx");
        //app.loadModel("./sheet5/perfect_earth.jvx");
        ((PgElementSet) app.project.getGeometry()).showEdges(false);
        ((PgElementSet) app.project.getGeometry()).update(null);
    }

    public Transform() {
        //setOriginal((PgPolygonSet) this.project.getGeometry());
        //original.copyPolygonSet((PgPolygonSet) this.project.getGeometry());

        PsPanel pjip = this.project.getInfoPanel();

        String[] Titel = new String[6];
        Titel[0]="uniform";
        Titel[1]="x-Direction";
        Titel[2]="y-Direction";
        Titel[3]="around (.5,.5)";

        pjip.addTitle("Scaling");

        for (int i = 0; i < 3; i++) {
            m_mask[i] = new PuDouble(Titel[i], eventWrapper);
            m_mask[i].setDefBounds(-5, 5, 0.01, 1);
            m_mask[i].setDefValue(1);
            m_mask[i].init();
            pjip.add(m_mask[i].getInfoPanel());
        }

        pjip.addLine(1);

        pjip.addTitle("Translation");

        for (int i = 3; i < 5; i++) {
            m_mask[i] = new PuDouble(Titel[i-2], eventWrapper);
            m_mask[i].setDefBounds(-5, 5, 0.01, 1);
            m_mask[i].setDefValue(0);
            m_mask[i].init();
            pjip.add(m_mask[i].getInfoPanel());
        }

        pjip.addLine(1);

        pjip.addTitle("Rotation");

        m_mask[5] = new PuDouble(Titel[3], eventWrapper);
        m_mask[5].setDefBounds(-5, 5, 0.01, 1);
        m_mask[5].setDefValue(0);
        m_mask[5].init();
        pjip.add(m_mask[5].getInfoPanel());

        pjip.addLine(1);

        bReset.addActionListener(this);
        pjip.add(bReset);

        this.jvFrame.pack();
    }

    void transform(double rot, double x, double y, double sx, double sy) {
        PgElementSet geom = (PgElementSet) this.project.getGeometry();
        PdVector[] v = geom.getVertexTextures(); // RW-access!
        PuComplex op = null;

        if(orig == null)
            orig = PdVector.copyNew(v);

        if(rot != 0)
            op = new PuComplex(Math.cos(rot), Math.sin(rot));

        for(int i = 0; i < v.length; i++) {
            PuComplex p = new PuComplex(orig[i].m_data[0], orig[i].m_data[1]);
            PuComplex offset = new PuComplex(.5, .5);

            if(op != null) {
                p.sub(offset);
                p.mult(op);
                p.add(offset);
            }

            v[i].m_data[0] = sx * p.re() + x;
            v[i].m_data[1] = sy * p.im() + y;
        }
    }


    @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == bReset)
                for(int i = 0; i < 6; i++)
                    m_mask[i].init();
            double[] v = new double[6];
            for(int i = 0; i < 6; i++)
                v[i] = m_mask[i].getValue();
            transform(v[5], v[3], v[4], v[0] * v[1], v[0] * v[2]);
            this.project.getGeometry().update(null);
        }

    JavaViewSucksSoMuchItMakesThisNecesarry eventWrapper = new JavaViewSucksSoMuchItMakesThisNecesarry();

    class JavaViewSucksSoMuchItMakesThisNecesarry implements PsUpdateIf {
        // unfortunately javaview doesnt support javas event management system,
        // but relies on its own update interface,
        // so we have to implement this wrapper
        // think about moving this to minjv

        @Override
            public boolean update(Object event) {
                actionPerformed(new ActionEvent(event, 0, "JVUpdate"));
                return true;
            }

        @Override
            public void setParent(PsUpdateIf parent) {
            }

        @Override
            public PsUpdateIf getFather() {
                return null;
            }

        @Override
            public String getName() {
                return null;
            }
    }

}
