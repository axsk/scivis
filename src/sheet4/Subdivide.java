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
import jv.object.PsDebug;
import jv.object.PsPanel;
import jv.object.PsUpdateIf;
import jv.vecmath.PdVector;

@SuppressWarnings("serial")
public class Subdivide extends MinJV {

    static int NUMWEIGHTS = 7;
    Button bSubD = new Button("Subdivide");
    Button bReset = new Button("Reset");

    PuDouble[] m_mask = new PuDouble[NUMWEIGHTS];
    PuInteger steps;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Subdivide app = new Subdivide();
        app.loadModel(PsConfig.getCodeBase() + "models/curves/coloredCurve.jvx");
    }

    public Subdivide() {
        PsPanel pjip = this.project.getInfoPanel();
        pjip.addTitle("Subdividing");

        Panel losButtons = new Panel(new FlowLayout(FlowLayout.CENTER));

        losButtons.add(bReset);
        losButtons.add(bSubD);
        bReset.addActionListener(this);
        bSubD.addActionListener(this);

        pjip.add(losButtons);
        pjip.addLine(1);

        for (int i = 0; i < NUMWEIGHTS; i++) {
            m_mask[i] = new PuDouble("r" + Integer.toString(i - 3), eventWrapper);
            m_mask[i].setDefValue(0.2);
            m_mask[i].init();
            pjip.add(m_mask[i].getInfoPanel());
        }
        steps = new PuInteger("steps", eventWrapper);
        steps.setDefValue(2);
        steps.init();
        pjip.add(steps.getInfoPanel());
        this.jvFrame.pack();

    }

    void subdivision() {

        PgPolygonSet polyS = ((PgPolygonSet) this.project.getGeometry());
        PdVector[] orig = polyS.getPolygonVertices(0);

        int dim = orig[0].getSize();
        PdVector temp = new PdVector(dim);

        PdVector[] curr = orig;

        for (int n = 0; n < steps.getValue(); n++) {

            PdVector[] div = new PdVector[curr.length * 2];
            //division step
            for (int i = 0; i < curr.length; i++) {
                div[2 * i] = curr[i];
                div[2 * i + 1] = curr[i];
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
                    temp.multScalar(div[(i + j - 3 + div.length) % div.length], m_mask[j].getValue());
                    curr[i].add(temp);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == bReset) {
            // reset 
        } else if (source == bSubD) {
            subdivision();
        } else {
            PsDebug.message(event.toString());
        }

    }

    JavaViewSucksSoMuchItMakesThisNecesarry eventWrapper = new JavaViewSucksSoMuchItMakesThisNecesarry();

    class JavaViewSucksSoMuchItMakesThisNecesarry implements PsUpdateIf {
        // unfortunately javaview doesnt support javas event management system,
        // but relies on its own update interface, 
        // so we have to implement this wrapper to 
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
