package com.documentmanager.models;

import java.io.Serializable;

public class ElectronicDocument extends Document implements Serializable{
	String link;
	
	public ElectronicDocument(String nom, String link) {
		super(nom);
		this.link = link;
	}
	
	public DocumentTypeEnum getDocumentType() {
		return DocumentTypeEnum.documentElectronique;
	}
}
