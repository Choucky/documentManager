package com.documentmanager.models;

import java.io.Serializable;
import java.util.ArrayList;

public class MotClef implements Serializable{
	
	String motClef;
	ArrayList<Document> documents;
	
	public MotClef(String motClef) {
		this.motClef = motClef;
		documents = new ArrayList<Document>();
	}
	
	public String toString() {
		return motClef;
	}
	
}
