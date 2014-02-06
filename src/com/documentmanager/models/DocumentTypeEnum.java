package com.documentmanager.models;

public enum DocumentTypeEnum {
	document(0),
	documentElectronique(1),
	documentPapier(2);

	private int value;

	private DocumentTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		switch (this) {
			case document :
				return "Document";
			case documentElectronique:
				return "Document electronique";
			case documentPapier:
				return "Document papier";
		}
		return null;
	}
}
