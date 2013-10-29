package MinJV;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public abstract class CB implements ActionListener {

    abstract public void run(ActionEvent ev);

    public void actionPerformed(ActionEvent ev) {
        run(ev);
    }
}

