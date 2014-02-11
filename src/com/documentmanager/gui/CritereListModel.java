package com.documentmanager.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.documentmanager.models.Critere;

public class CritereListModel extends AbstractListModel implements ComboBoxModel {

	private ArrayList<Critere> criteres;
	private Object selection;
	
	public CritereListModel() {
		criteres = new ArrayList<Critere>();
	}
	
	public CritereListModel(ArrayList<Critere> criteres) {
		this.criteres = criteres;
	}
	
	@Override
	public Object getElementAt(int arg0) {
		return criteres.get(arg0);
	}

	@Override
	public int getSize() {
		return criteres.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		selection = arg0;
		fireContentsChanged();
	}
	
	public void addItem(Critere c) throws CloneNotSupportedException {
		if (criteres.contains(c)) {
			throw new CloneNotSupportedException("Ce critère est déja actif.");
		} else {
			criteres.add(c);
			fireContentsChanged();
		}
	}
	
	public void removeItem(Critere c) {
		criteres.remove(c);
		fireContentsChanged();
	}
	
	private void fireContentsChanged() {
		fireContentsChanged(this, 0, getSize()-1);
	}
	
	public ArrayList<Critere> getCriteres() {
		return (ArrayList<Critere>) criteres.clone();
	}

}
