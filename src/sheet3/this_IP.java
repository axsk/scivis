package sheet3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import jv.object.PsDebug;
import jv.object.PsPanel;
import jv.object.PsUpdateIf;
import jv.project.PjProject_IP;


public class this_IP extends PjProject_IP
	implements ActionListener, ItemListener {
	
	public this_IP() {
		super();
		if (getClass() == this_IP.class) {
			init();
		}
	}
	public void init() {
		super.init();
		addTitle("Stars 'n Links");
		
		add(new PsPanel());
		
		addLine(1);
	}
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
