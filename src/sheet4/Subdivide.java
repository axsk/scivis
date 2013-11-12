package sheet4;

import MinJV.*;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;

import jv.number.PuDouble;
import jv.number.PuInteger;
import jv.object.PsDebug;
import jv.object.PsPanel;
import jv.object.PsUpdateIf;

@SuppressWarnings("serial")
public class Subdivide extends MinJV {

    Button bSubD = new Button("Subdivide");
    Button bReset = new Button("Reset");

    PuDouble[] m_mask = new PuDouble[7];
    PuInteger steps;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Subdivide app = new Subdivide();
        if (args.length != 0) {
            app.loadModel(args[0]);
        } else {
            app.loadModel(null);
        }
        //app.reset();
    }

    public Subdivide() {
        PsPanel pjip = this.project.getInfoPanel();
        pjip.addTitle("Subdividing");

        Panel losButtons = new Panel(new FlowLayout(FlowLayout.CENTER));

        losButtons.add(bReset);
        losButtons.add(bSubD);
        bReset.addActionListener(this);

        pjip.add(losButtons);
        pjip.addLine(1);

        for (int i = 0; i < 7; i++) {
            m_mask[i] = new PuDouble("r" + Integer.toString(i - 3), eventWrapper);
            pjip.add(m_mask[i].getInfoPanel());
        }
        steps = new PuInteger("steps", eventWrapper);
        pjip.add(steps.getInfoPanel());
        this.jvFrame.pack();

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == bReset) {
            // reset 
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
