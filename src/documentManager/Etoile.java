package documentManager;

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
}
