package com.documentmanager.models;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Document implements Serializable{
	private String titre;
	private ArrayList<CategorieMotClef> categories;
	private ArrayList<Note> note;
	
	public Document(String titre) {
		this.titre = titre;
		categories = new ArrayList<CategorieMotClef>();
		note = new ArrayList<Note>();
	}
	
	@Override
	public String toString() {
		return titre;
	}
	
	public String getDocumentType() {
		return "Document";
	}
}
