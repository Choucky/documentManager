package com.documentmanager.models;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Document implements Serializable{
	private String titre;
	private ArrayList<MotClef> motClefs;
	private ArrayList<Note> notes;
	
	public Document(String titre) {
		this.titre = titre;
		motClefs = new ArrayList<MotClef>();
		notes = new ArrayList<Note>();
	}
	
	@Override
	public String toString() {
		return titre;
	}
	
	public DocumentTypeEnum getDocumentType() {
		return DocumentTypeEnum.document;
	}
	
	public void addNote(Etoile star, Document document, Critere critere) {
		notes.add(new Note(star, document, critere));
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	public Note findNoteOf(Critere c) {
		for (Note n : notes) {
			if (c.equals(n.getCritere())) {
				return n;
			}
		}
		return null;
	}

	public void deleteNote(Note n) {
		notes.remove(n);
	}
	
	public void addMotClef(MotClef m) {
		motClefs.add(m);
	}
	
	public ArrayList<MotClef> getMotClefs() {
		return motClefs;
	}

	public void removeMotClef(MotClef mot) {
		motClefs.remove(mot);
	}
	
	public boolean matches(ArrayList<Critere> criteres, ArrayList<MotClef> mots) {
		//Critères
		ArrayList<Critere> criteresDocument = new ArrayList<Critere>();
		for (Note n : notes) {
			if (!criteresDocument.contains(n.getCritere())) {
				criteresDocument.add(n.getCritere());
			}
		}
		
		for (Critere c : criteres) {
			if (!criteresDocument.contains(c)) {
				return false;
			}
		}
		
		//Mots clefs
		for (MotClef m : mots) {
			if (!motClefs.contains(m)) {
				return false;
			}
		}
		
		return true;
	}
	
}
