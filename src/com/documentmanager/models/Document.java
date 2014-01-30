package com.documentmanager.models;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Document implements Serializable{
	private String titre;
	private ArrayList<CategorieMotClef> categories;
	private ArrayList<Note> note;
}
