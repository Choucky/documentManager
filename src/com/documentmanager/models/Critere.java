package com.documentmanager.models;

import java.io.Serializable;

public class Critere implements Serializable{
	String nom;
	
	public Critere(String nom) {
		this.nom = nom;
	}
	
	public String toString() {
		return nom;
	}
}
