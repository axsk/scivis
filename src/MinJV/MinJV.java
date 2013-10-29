package MinJV;

import java.applet.Applet;
import java.awt.*;
import jv.loader.PgLoader;
import jv.object.PsConfig;
import jv.project.PjProject;
import jv.viewer.PvViewer;

@SuppressWarnings("serial")
public class MinJV extends Applet {

    static String DEFAULTGEO = "Cow_5804.byu";

    public Frame jvFrame;
    public PvViewer jvViewer;

    public MinJV(String modelFile) {

        jvFrame = new jv.object.PsMainFrame(this, null);
        jvViewer = new PvViewer(this, jvFrame);

        PjProject project = new PjProject("testprojekt");
        jvViewer.addProject(project);
        jvViewer.selectProject(project);

        this.setLayout(new BorderLayout());
        this.add(jvViewer.getDisplay().getCanvas(), BorderLayout.CENTER);
        jvFrame.pack();
        jvFrame.setVisible(true);
    }

    public void loadModel(String modelFile) {
        if (modelFile == null) {
            modelFile = PsConfig.getCodeBase() + DEFAULTGEO;
        }
        PgLoader loader = new PgLoader();
        jvViewer.getCurrentProject().addGeometry((loader.loadGeometry(modelFile))[0]);
    }

    public MinJV() {
        this(null);
    }

    public static void main(String args[]) {
        new MinJV(null);
    }
}
