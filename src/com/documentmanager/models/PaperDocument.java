package com.documentmanager.models;

import java.io.Serializable;

public class PaperDocument extends Document implements Serializable{
	
	String commentaire;
	
	public PaperDocument(String titre, String commentaire) {
		super(titre);
		this.commentaire = commentaire;
	}
	
	public String getDocumentType() {
		return "Document papier";
	}
	
}
