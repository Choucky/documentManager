package com.documentmanager.models;

public enum Etoile {
	uneEtoile(1),
	deuxEtoiles(2),
	troisEtoiles(3),
	quatreEtoiles(4),
	cinqEtoiles(5);
	
	private int value;

	private Etoile(int value) {
		this.value=value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		switch(this) {
			case uneEtoile:
				return "★";
			case deuxEtoiles:
				return "★★";
			case troisEtoiles:
				return "★★★";
			case quatreEtoiles:
				return "★★★★";
			case cinqEtoiles:
				return "★★★★★";
		}
		return null;
	}
}
