package com.documentmanager.models;

import java.util.ArrayList;
import java.util.List;

public class Domaine {
	String nom;
	List<CategorieMotClef> categories;
	
	public Domaine(String nom) {
		this.nom = nom;
		categories = new ArrayList<CategorieMotClef>();
	}
	
}
