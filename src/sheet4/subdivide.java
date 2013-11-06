package sheet4;

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
public class subdivide extends MinJV {

    protected Button bsub = new Button("subdivide");
    protected Button bReset = new Button("Reset");

    /**
     * @param args
     */
    public static void main(String[] args) {
    	subdivide app = new subdivide();
        if (args.length != 0) {
            app.loadModel(args[0]);
        } else {
            app.loadModel(null);
        }
        //app.reset();
    }

    public subdivide() {
        PsPanel pjip = this.project.getInfoPanel();
        pjip.addTitle("Subdividing");

        Panel losButtons = new Panel(new FlowLayout(FlowLayout.CENTER));

        losButtons.add(bReset);
        bReset.addActionListener(this);

        pjip.add(losButtons);
        pjip.addLine(1);
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == bReset) {
            reset();
        }
    }
    
    public void reset(){
    	//reset
    }


}
