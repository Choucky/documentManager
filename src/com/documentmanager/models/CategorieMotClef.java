package com.documentmanager.models;

import java.io.Serializable;
import java.util.ArrayList;

public class CategorieMotClef implements Serializable{
	
	private String nom;
	private ArrayList<MotClef> motClefs;
	
	public CategorieMotClef(String nomCategorie) {
		nom = nomCategorie;
		motClefs = new ArrayList<MotClef>();
	}

	public String getNom() {
		return nom;
	}

	public void addMotClef(String motClef) {
		motClefs.add(new MotClef(motClef));
	}

	public ArrayList<MotClef> getMotClefs() {
		return motClefs;
	}
	
	public String toString() {
		return nom;
	}
	
}
