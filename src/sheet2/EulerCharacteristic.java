package sheet2;

import MinJV.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import jv.geom.PgElementSet;
import jv.object.PsDebug;
import jv.vecmath.PiVector;

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
                int e = geo.getNumEdges();
                
                PiVector elements[] = geo.getElements();
                
                int mf = elements.length;
                int me = 3 * mf / 2;
                
                int mv = 0;
                int j = 0;
                for (int i = 0; i < mf; i++){
                	for (j = 0; j < elements[i].getSize(); j++){
                		if (elements[i].m_data[j] > mv){
                			mv = elements[i].m_data[j];
                		}
                	}
                }
                mv += 1;

                /*PiVector[] neigh = geo.getNeighbours();
                int e = 0;
                int doubled = 0; // increased by one for each edge that will be counted twice
                for (int i = 0; i < neigh.length; i++) {
                    e += neigh[i].getSize();
                    for(int j = 0; j < neigh[i].getSize(); j++)
                        if(neigh[i].m_data[j] != -1)
                            doubled++;
                }
                e -= doubled/2;
                */
                
                PsDebug.message("NewVertices: " + mv + " NewEdges: " + me + " NewFaces: " + mf);

                PsDebug.message(v + " vertices, " + e + " edges, " + f + " faces. thus the euler char. is " + (v - e + f));
            }
        });
        this.add(button, BorderLayout.SOUTH);
        this.jvFrame.pack();
    }
}
