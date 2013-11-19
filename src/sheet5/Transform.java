package sheet5;

import MinJV.*;
import java.lang.Math;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import jv.number.PuComplex;
import jv.geom.PgElementSet;

import jv.number.PuDouble;
import jv.number.PuInteger;
import jv.object.PsConfig;
import jv.object.PsPanel;
import jv.object.PsUpdateIf;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;
import jv.project.PgJvxSrc;
import jv.object.PsDebug;

@SuppressWarnings("serial")
public class Transform extends MinJV {

    PuDouble[] m_mask = new PuDouble[6];
    PuInteger steps;

    public static void main(String[] args) {
        Transform app = new Transform();
        app.loadModel("./sheet5/perfect_earth.jvx");
        //app.project.getGeometry().getMaterialPanel().
        //PgJvxSrc setting = (PgJvxSrc) app.project.getGeometry();
        //setting.isShowingBnd(0);
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
            m_mask[i].setDefBounds(-5, 5, 0.1, 1);
            m_mask[i].setDefValue(1);
            m_mask[i].init();
            pjip.add(m_mask[i].getInfoPanel());
        }

        pjip.addLine(1);

        pjip.addTitle("Translation");

        for (int i = 3; i < 5; i++) {
            m_mask[i] = new PuDouble(Titel[i-2], eventWrapper);
            m_mask[i].setDefBounds(-5, 5, 0.1, 1);
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

        this.jvFrame.pack();


    }

    void transforming(PuComplex op, double x, double y, double sx, double sy) {
        PgElementSet geom = (PgElementSet) this.project.getGeometry();
        PdVector[] v = geom.getVertexTextures(); // RW-access!
        for(int i = 0; i < v.length; i++) {
            PuComplex p = new PuComplex(v[i].m_data[0], v[i].m_data[1]);
            p.mult(op);
            v[i].m_data[0] = sx * p.re() + x;
            v[i].m_data[1] = sy * p.im() + y;
        }
        geom.update(null);
    }


    @Override
        public void actionPerformed(ActionEvent event) {
            PuComplex op = new PuComplex(1, 0);
            op.div(op.abs()); // Normalize

            Object source = event.getSource();
            double value = ((PuDouble) source).getValue();
            if(source == m_mask[0]) {
                transforming(op, 0, 0, value, value);
            }
            if(source == m_mask[1]) {
                transforming(op, 0, 0, value, 1);
            }
            if(source == m_mask[2]) {
                transforming(op, 0, 0, 1, value);
            }
            if(source == m_mask[3]) {
                transforming(op, value, 0, 1, 1);
            }
            if(source == m_mask[4]) {
                transforming(op, 0, value, 1, 1);
            }
            if(source == m_mask[5]) {
                op.set(Math.cos(value), Math.sin(value));
                transforming(op, 0, 0, 1, 1);
            }
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
