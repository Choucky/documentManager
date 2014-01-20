package com.documentmanager.models;

import java.util.ArrayList;
import java.util.Map;

public class Document {
	private String titre;
	private ArrayList<CategorieMotClef> categories;
	private Map<Critere, Note> notesMap;
}
