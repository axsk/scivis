package sheet2;

import MinJV.MinJV;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jv.geom.PgElementSet;
import jv.object.PsDebug;

public class EulerCharacteristic extends MinJV implements ActionListener {

    public static void main(String args[]) {
        EulerCharacteristic app = new EulerCharacteristic();

        Button button = new Button("Colorize and Euler");
        button.addActionListener(app);
        app.add(button, BorderLayout.SOUTH);
        app.jvFrame.pack();
    }

    public void actionPerformed(ActionEvent event) {
        PgElementSet geo = (PgElementSet) this.jvViewer.getCurrentProject().getGeometry();

        if (geo == null)
            return;

        // Colorize Shape
        Color colors[] = new Color[2];
        colors[0] = Color.WHITE;
        colors[1] = Color.BLUE;
        for (int i = 0; i <= geo.getNumElements() - 1; i++) {
            geo.setElementColor(i, colors[i % 2]);
        }
        geo.showElementColors(true);
        geo.update(null);

        // Compute Euler Characteristic
        int v = geo.getNumVertices();
        int f = geo.getNumElements();
        int e = f * 3 / 2;
        PsDebug.message(v + " vertices, " + e + " edges, " + f + " faces. thus the euler char. is " + (v - e + f));
    }

}
