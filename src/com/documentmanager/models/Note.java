package com.documentmanager.models;

import java.io.Serializable;

public class Note implements Serializable{
	private Etoile star;
	private Document document;
	private Critere critere;
	
	public Note(Etoile star, Document document, Critere critere) {
		this.star = star;
		this.document = document;
		this.critere = critere;
	}
	
	public String toString() {
		return critere + " - " + star;
	}
	
	public Critere getCritere() {
		return critere;
	}
	
	public void setEtoile(Etoile e) {
		star = e;
	}
	
}
