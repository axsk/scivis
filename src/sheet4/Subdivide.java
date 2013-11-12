package sheet4;

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
public class Subdivide extends MinJV {

    static int NUMWEIGHTS = 7;
    Button bSubD = new Button("Subdivide");
    Button bReset = new Button("Reset");
    Button bNorm = new Button("Normalize");
    //PgPolygonSet original;
    PdVector[] orig;

    PuDouble[] m_mask = new PuDouble[NUMWEIGHTS];
    PuInteger steps;

    public static void main(String[] args) {
        Subdivide app = new Subdivide();
        app.loadModel(PsConfig.getCodeBase() + "models/curves/coloredCurve.jvx");
    }

    public Subdivide() {
    	//original  = ((PgPolygonSet) this.project.getGeometry());
    	
        PsPanel pjip = this.project.getInfoPanel();
        pjip.addTitle("Subdividing");

        Panel losButtons = new Panel(new FlowLayout(FlowLayout.CENTER));

        losButtons.add(bReset);
        losButtons.add(bSubD);
        losButtons.add(bNorm);
        bReset.addActionListener(this);
        bSubD.addActionListener(this);
        bNorm.addActionListener(this);

        pjip.add(losButtons);
        pjip.addLine(1);

        for (int i = 0; i < NUMWEIGHTS; i++) {
            m_mask[i] = new PuDouble("r" + Integer.toString(i - 3), eventWrapper);
            //m_mask[i].setBounds(-1d,1d); // TODO: fix
            m_mask[i].setDefBounds(0, 10, 0.1, 1);
            m_mask[i].setDefValue(0);
            m_mask[i].init();
            pjip.add(m_mask[i].getInfoPanel());
        }
        m_mask[NUMWEIGHTS/2].setValue(1);
        steps = new PuInteger("steps", eventWrapper);
        //steps.setBounds(0, 6);
        steps.setDefBounds(1, 6, 1, 1);
        steps.setDefValue(1);
        steps.init();
        pjip.add(steps.getInfoPanel());
        this.jvFrame.pack();

    }

    void subdivision() {

        PgPolygonSet polyS = ((PgPolygonSet) this.project.getGeometry());
        PdVector[] curr = polyS.getPolygonVertices(0);

        int dim = curr[0].getSize();
        PdVector temp = new PdVector(dim);

        for (int n = 0; n < steps.getValue(); n++) {

            PdVector[] div = new PdVector[curr.length * 2];
            //division step
            for (int i = 0; i < curr.length; i++) {
                div[2 * i] = (PdVector) curr[i].clone();
                div[2 * i + 1] = (PdVector) curr[i].clone();
                div[2 * i + 1].add(curr[(i + 1) % curr.length]);
                div[2 * i + 1].multScalar(0.5);
            }

            //averaging step
            curr = new PdVector[div.length];
            for (int i = 0; i < div.length; i++) {
                curr[i] = new PdVector(dim);
            }
            for (int i = 0; i < div.length; i++) {
                for (int j = 0; j < NUMWEIGHTS; j++) {
                    temp.multScalar(div[(i + j - NUMWEIGHTS/2 + div.length) % div.length], m_mask[j].getValue());
                    curr[i].add(temp);
                }
            }
        }
        
        PiVector indexset = new PiVector(curr.length);
        for (int i = 0; i<curr.length;i++){
            indexset.setEntry(i, i);
        }
        polyS.setPolygon(0,indexset);
        polyS.setNumVertices(curr.length);
        polyS.setPolygonVertices(0, curr);
        polyS.update(null);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == bReset) {
        	PgPolygonSet polyS = ((PgPolygonSet) this.project.getGeometry());
        	PiVector indexset = new PiVector(orig.length);
            for (int i = 0; i<orig.length;i++){
                indexset.setEntry(i, i);
            }
        	polyS.setPolygon(0,indexset);
            polyS.setNumVertices(orig.length);
            polyS.setPolygonVertices(0, orig);
            polyS.update(null);
        } else if (source == bSubD) {
            subdivision();
        } else if (source == bNorm) {
            double factor = 0d;            
            for (int i = 0 ; i<NUMWEIGHTS; i++) factor+=m_mask[i].getValue();            
            for (int i = 0 ; i<NUMWEIGHTS; i++) m_mask[i].setValue(m_mask[i].getValue()/factor);            
        } else {
            // this is called if some changes are made to the sliders
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
