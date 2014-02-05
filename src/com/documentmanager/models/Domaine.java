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
	
	public ArrayList<CategorieMotClef> getCategoriesMotClef() {
		return categories;
	}
	
	/*public String[] getCategoriesMotClef() {
		String[] result = new String[categories.size()];
		for (int i=0;i<categories.size();i++) {
			result[i] = categories.get(i).getNom();
		}
		return result;
	}*/
	
	public boolean hasCategories() {
		return categories.size() > 0;
	}

	public void addMotClef(String categorieMotClef, String motClef) {
		findCategoryFromString(categorieMotClef).addMotClef(motClef);
	}
	
	private CategorieMotClef findCategoryFromString(String categorieMotClef) {
		for (CategorieMotClef c : categories) {
			if (c.getNom().equals(categorieMotClef)) {
				return c;
			}
		}
		throw new IllegalArgumentException("Etonnant : la catégorie de mot clef n'existe pas !");
	}

	public ArrayList<MotClef> getMotClefOf(Object categorieMotClef) {
		return findCategoryFromString(categorieMotClef.toString()).getMotClefs();
	}
	
}
