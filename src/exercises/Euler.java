package exercises;

import MinJV.MinJV;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jv.geom.PgElementSet;
import jv.object.PsDebug;

public class Euler extends MinJV implements ActionListener {

    public static void main(String args[]) {
        Euler app = new Euler();

        Button button = new Button("Colorize and Euler");
        button.addActionListener(app);
        app.add(button, BorderLayout.SOUTH);
        app.jvFrame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        PgElementSet geo = (PgElementSet) this.jvViewer.getCurrentProject().getGeometry();
        if (geo != null) {
            // colorize
            Color colors[] = new Color[2];
            colors[0] = Color.WHITE;
            colors[1] = Color.BLUE;
            for (int i = 0; i <= geo.getNumElements() - 1; i++) {
                geo.setElementColor(i, colors[i % 2]);
            }
            geo.showElementColors(true);
            geo.update(null);
            // euler
            int v = geo.getNumVertices();
            int f = geo.getNumElements();
            int e = f * 3 / 2;
            PsDebug.message(v + " vertices, " + e + " edges, " + f + " faces. thus the euler char. is " + (v - e + f));
        }
    }

}
