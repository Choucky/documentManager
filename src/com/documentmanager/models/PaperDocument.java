package com.documentmanager.models;

import java.io.Serializable;

public class PaperDocument extends Document implements Serializable{
	
	String commentaire;
	
	public PaperDocument(String titre) {
		super(titre);
		
	}
}
