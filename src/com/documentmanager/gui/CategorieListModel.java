package com.documentmanager.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.documentmanager.models.CategorieMotClef;

public class CategorieListModel extends AbstractListModel implements ComboBoxModel {

	private ArrayList<CategorieMotClef> categories;
	private Object selection;

	public CategorieListModel(ArrayList<CategorieMotClef> categories) {
		this.categories = categories;
	}
	
	@Override
	public Object getElementAt(int arg0) {
		return categories.get(arg0);
	}

	@Override
	public int getSize() {
		return categories.size();
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
