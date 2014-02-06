package com.documentmanager.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.documentmanager.models.Note;

public class NoteListModel extends AbstractListModel {

	ArrayList<Note> notes;
	
	public NoteListModel() {
		this.notes = new ArrayList<Note>();
	}
	
	public NoteListModel(ArrayList<Note> notes) {
		this.notes = notes;
	}

	@Override
	public Object getElementAt(int arg0) {
		return notes.get(arg0);
	}

	@Override
	public int getSize() {
		return notes.size();
	}
	
	/*public void clear() {
		notes.clear();
	}
	
	public void addNote(Note n) {
		notes.add(n);
	}*/
	
}
