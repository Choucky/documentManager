package com.documentmanager.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.documentmanager.models.MotClef;

public class MotClefListModel extends AbstractListModel implements ComboBoxModel {

	private ArrayList<MotClef> motclefs;
	private Object selection;

	public MotClefListModel(ArrayList<MotClef> motclefs) {
		this.motclefs = motclefs;
	}
	
	@Override
	public Object getElementAt(int arg0) {
		return motclefs.get(arg0);
	}

	@Override
	public int getSize() {
		return motclefs.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		selection = arg0;
		fireContentsChanged(this, 0, getSize()-1);
	}

}
