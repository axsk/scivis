package sheet2.colorize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jv.geom.PgElementSet;

import jv.object.PsDebug;
import jv.object.PsUpdateIf;
import jv.project.PjProject_IP;

/**
 * Info panel of tutorial project. Each project, as well as each geometry, may
 * have an info panel for inspecting and steering the instance. Modifications in
 * the panel should result in a call to <code>project.update(Object)</code>.
 * <p>
 * Panel adds simple sliders for modifying integer values of the project. The
 * sliders are children of the project and send <code>update(Object)</code>
 * whenever they change by user interaction.
 * <p>
 * This info panel is optional and can be removed without any changes in the
 * project. Each info panel must follow special naming conventions such that
 * JavaView is able to show it automatically, or on request. The class name must
 * be identical to the class name of the corresponding class with the suffix
 * '_IP' attached. IP means InfoPanel.
 *
 * @see	jv.project.PjProject
 * @author	Konrad Polthier
 * @version	28.11.00, 1.50 revised (kp) Applet simplified for beginners.<br>
 * 20.03.00, 1.10 revised (kp) Sliders moved to project.<br>
 * 05.08.99, 1.00 created (kp)
 */
public class MyProject_IP extends PjProject_IP implements ActionListener {

    protected MyProject myProject;
    protected Panel sliderPanel;
    protected Label lArea;
    protected Button bReset;
    private Button bColor;

    public MyProject_IP() {
        super();
        // Add title label which will later display the title of the project.
        addTitle("");

        // Panel to contain sliders of u-lines and v-lines.
        sliderPanel = new Panel();
        sliderPanel.setLayout(new GridLayout(2, 1));
        add(sliderPanel);

        // Panel with label to display the area of the current surface.
        Panel pArea = new Panel();
        pArea.setLayout(new GridLayout(1, 2));
        pArea.add(new Label("Surface Area"));
        lArea = new Label();
        pArea.add(lArea);
        add(pArea);

        // Add reset button at bottom
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bReset = new Button("Reset");
        bReset.addActionListener(this);
        buttonPanel.add(bReset);

        bColor = new Button("Colorize");
        bColor.addActionListener(this);
        buttonPanel.add(bColor);

        add(buttonPanel);

        if (getClass() == MyProject_IP.class) {
            init();
        }
    }

    public void init() {
        super.init();
    }

    public void setParent(PsUpdateIf parent) {
        super.setParent(parent);
        myProject = (MyProject) parent;
        setTitle(myProject.getName());
        // Add info panels of integers to panel of sliders.
        sliderPanel.add(myProject.m_numULines.getInfoPanel());
        sliderPanel.add(myProject.m_numVLines.getInfoPanel());
    }

    /**
     * Here we arrive from outside world of this panel, e.g. if project has
     * changed somewhere else and must update its panel. Such an update is
     * automatically by superclasses of PjProject.
     */
    public boolean update(Object event) {
        if (myProject == null) {
            if (PsDebug.WARNING) {
                PsDebug.warning("missing parent, setParent not called");
            }
            return false;
        }
        if (event == myProject) {
            lArea.setText(String.valueOf(myProject.m_geom.getArea()));
            return true;
        }
        return super.update(event);
    }

    /**
     * Handle action events invoked from buttons, menu items, text fields.
     */
    public void actionPerformed(ActionEvent event) {
        if (myProject == null) {
            return;
        }
        Object source = event.getSource();
        if (source == bReset) {
            myProject.init();
            myProject.update(this);
        } else if (source == bColor) {
            PgElementSet geo = (PgElementSet) myProject.getGeometry();
            Color colors[] = new Color[2];
            colors[0] = Color.WHITE;
            colors[1] = Color.BLUE;
            geo.showElementBackColor(false);
            geo.showElementColors(true);
            for (int i = 0; i <= geo.getNumElements() - 1; i++) {
                geo.setElementColor(i, colors[i % 2]);
            }

            // Aufgabe 2.4
            int e, v, f;
            v = geo.getNumVertices();
            f = geo.getNumElements();
            e = f * 3 / 2;
            PsDebug.message(v + " vertices, " + e + " edges, " + f + " faces. thus the euler char. is " + (v - e + f));
        }

    }
}
