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
	
	public String getDocumentType() {
		return "Document";
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
	
}
