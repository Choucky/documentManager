package com.documentmanager.models;

import java.io.Serializable;
import java.util.ArrayList;

public class MotClef implements Serializable{
	
	String motClef;
	ArrayList<Document> documents;
	
	public MotClef(String motClef) {
		this.motClef = motClef;
		documents = new ArrayList<Document>();
	}
	
	public void addDocument(Document doc) throws CloneNotSupportedException {
		for (Document d : documents) {
			if (doc.equals(d)) {
				throw new CloneNotSupportedException("Le mot clef est déja assigné au fichier.");
			}
		}
		documents.add(doc);
	}
	
	public String toString() {
		return motClef;
	}

	public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void removeDocument(Document document) {
		documents.remove(document);
	}
	
}
