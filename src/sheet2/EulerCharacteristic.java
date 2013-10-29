package sheet2;

import MinJV.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import jv.geom.PgElementSet;
import jv.object.PsDebug;

@SuppressWarnings("serial")
public class EulerCharacteristic extends MinJV {

    public static void main(String args[]) {
        EulerCharacteristic app = new EulerCharacteristic();
        if (args.length != 0) {
            app.loadModel(args[0]);
        } else {
            app.loadModel(null);
        }

    }

    public EulerCharacteristic() {
        addButton();
    }

    private void addButton() {
        final Button button = new Button("Colorize and Euler");
        final EulerCharacteristic outer = this;
        button.addActionListener(new CB() {
            public void run(ActionEvent ev) {
                PgElementSet geo = (PgElementSet) outer.jvViewer.getCurrentProject().getGeometry();

                if (geo == null) {
                    return;
                }

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
        });
        this.add(button, BorderLayout.SOUTH);
        this.jvFrame.pack();
    }
}
