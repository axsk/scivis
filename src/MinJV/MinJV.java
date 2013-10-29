package MinJV;

import java.applet.Applet;
import java.awt.*;
import jv.project.PjProject;
import jv.viewer.PvViewer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
abstract class CB implements ActionListener {
    abstract public void run(ActionEvent ev);
    public void actionPerformed(ActionEvent ev) {
        run(ev);
    }
}

@SuppressWarnings("serial")
public class MinJV extends Applet {
    public Frame jvFrame;
    public PvViewer jvViewer;
    
    public MinJV()
    {
        jvFrame = new jv.object.PsMainFrame(this, null);
        jvViewer = new PvViewer(this, jvFrame);
        
        PjProject project = new PjProject("testprojekt");
        jvViewer.addProject(project);
        jvViewer.selectProject(project);

        this.setLayout(new BorderLayout());
        this.add(jvViewer.getDisplay().getCanvas(), BorderLayout.CENTER);
        jvFrame.pack();
        //frame.setSize(800, 600);
        jvFrame.setVisible(true);
    }
    
    public static void main(String args[]) {
        new MinJV();
    }
}
