package sheet6;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jv.object.PsDebug;
import jv.object.PsUpdateIf;
import jv.project.PjProject_IP;

public class PjDomainColoring_IP extends PjProject_IP implements ActionListener {
	protected PjDomainColoring myProject;
	protected Panel sliderPanel;

	public PjDomainColoring_IP() {
		super();
		if (getClass() == PjDomainColoring_IP.class)
			init();
	}

	public void init() {
		super.init();
	}

	public void setParent(PsUpdateIf parent) {
		super.setParent(parent);
		myProject = (PjDomainColoring) parent;
		setTitle(myProject.getName());

		// Add function IP to panel.
		add(myProject.m_function.getInfoPanel());
	}

	public boolean update(Object event) {
		if (myProject == null) {
			if (PsDebug.WARNING)
				PsDebug.warning("missing parent, setParent not called");
			return false;
		}
		return super.update(event);
	}

	public void actionPerformed(ActionEvent event) {
		if (myProject == null)
			return;
		Object source = event.getSource();

	}
}
