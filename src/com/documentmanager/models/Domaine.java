package com.documentmanager.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Domaine implements Serializable{
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
	
	public void addCategorieMotClef(String nomCategorie) {
		categories.add(new CategorieMotClef(nomCategorie));
	}
	
	public String[] getCategoriesMotClef() {
		String[] result = new String[categories.size()];
		for (int i=0;i<categories.size();i++) {
			result[i] = categories.get(i).getNom();
		}
		return result;
	}
	
}
