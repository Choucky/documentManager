package com.documentmanager.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.documentmanager.models.Document;
import com.documentmanager.models.Domaine;
import com.documentmanager.models.MotClef;

public class DocumentMotClefListModel extends AbstractListModel {
	
	private ArrayList<MotClef> motClefs;
	private Domaine domaine;
	private Document document;

	public DocumentMotClefListModel(Document doc, Domaine dom) {
		document = doc;
		motClefs = document.getMotClefs();
		domaine = dom;
	}
	
	@Override
	public Object getElementAt(int arg0) {
		return new String(domaine.findCategorieOf(motClefs.get(arg0)).toString() + " - " + motClefs.get(arg0).toString());
		//return motClefs.get(arg0);
	}

	@Override
	public int getSize() {
		return motClefs.size();
	}

}
