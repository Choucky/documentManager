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
	
}
