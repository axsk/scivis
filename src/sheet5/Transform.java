package sheet5;

import MinJV.*;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import jv.geom.PgPolygonSet;

import jv.number.PuDouble;
import jv.number.PuInteger;
import jv.object.PsConfig;
import jv.object.PsPanel;
import jv.object.PsUpdateIf;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;

@SuppressWarnings("serial")
public class Transform extends MinJV {

    static int NUMWEIGHTS = 7;
    Button bSubD = new Button("Subdivide");
    Button bReset = new Button("Reset");
    Button bNorm = new Button("Normalize");
    PgPolygonSet original;
    PdVector[] orig;
    boolean first = true;

    PuDouble[] m_mask = new PuDouble[NUMWEIGHTS];
    PuInteger steps;

    public static void main(String[] args) {
    	Transform app = new Transform();
        app.loadModel(PsConfig.getCodeBase() + "example/perfect_earth.jvx");
        }

    public Transform() {
    	//setOriginal((PgPolygonSet) this.project.getGeometry());
    	//original.copyPolygonSet((PgPolygonSet) this.project.getGeometry());
    	
        PsPanel pjip = this.project.getInfoPanel();
        pjip.addTitle("Transforming");


        for (int i = 0; i < NUMWEIGHTS; i++) {
            m_mask[i] = new PuDouble("r" + Integer.toString(i - 3), eventWrapper);
            //m_mask[i].setBounds(-1d,1d); // TODO: fix
            m_mask[i].setDefBounds(-5, 5, 0.01, 1);
            m_mask[i].setDefValue(0);
            m_mask[i].init();
            pjip.add(m_mask[i].getInfoPanel());
        }
        m_mask[NUMWEIGHTS/2].setValue(1);
        steps = new PuInteger("steps", eventWrapper);
        //steps.setBounds(0, 6);
        steps.setDefBounds(2, 6, 1, 1);
        steps.setDefValue(1);
        steps.init();
        pjip.add(steps.getInfoPanel());
        this.jvFrame.pack();
        

    }
    
    void transforming(){
    	//Transforming stuff
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        transforming();

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
