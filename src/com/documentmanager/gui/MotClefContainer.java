package com.documentmanager.gui;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;

import com.documentmanager.models.MotClef;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MotClefContainer extends JPanel {

	/**
	 * Create the panel.
	 */
	public MotClefContainer(ArrayList<MotClef> motclefs,ActionListener action) {
		setPreferredSize(new Dimension(200, 75));
		setMinimumSize(new Dimension(200, 75));
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "MotClefCategorie", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("MotClef1");
		add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("MotClef2");
		add(chckbxNewCheckBox);
		
		for(MotClef motclef : motclefs) {
			JCheckBox chkbox = new JCheckBox(motclef.toString());
			chkbox.addActionListener(action);
			add(chkbox);
		}

	}

}
