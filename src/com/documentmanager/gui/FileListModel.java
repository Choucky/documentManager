package com.documentmanager.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.documentmanager.models.Document;

public class FileListModel extends AbstractListModel {
	
	ArrayList<Document> documents;
	
	public FileListModel() {
		documents = new ArrayList<Document>();
	}
	
	public void add(Document d) throws IllegalArgumentException {
		if (documents.contains(d)) {
			throw new IllegalArgumentException("Le fichier est déja dans la liste.");
		}
		documents.add(d);
		fireContentsChanged(this, 0, getSize()-1);
	}
	
	public void clear() {
		documents.clear();
	}
	
	@Override
	public Object getElementAt(int arg0) {
		return documents.get(arg0);
	}

	@Override
	public int getSize() {
		return documents.size();
	}

}
