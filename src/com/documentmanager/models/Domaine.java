package com.documentmanager.models;

import java.util.ArrayList;
import java.util.List;

public class Domaine {
	String nom;
	ArrayList<CategorieMotClef> categories;
	ArrayList<Critere> criteres;
	
	public Domaine(String nom) {
		this.nom = nom;
		categories = new ArrayList<CategorieMotClef>();
	}

	public String getNom() {
		return nom;
	}
	
}
