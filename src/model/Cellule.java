package model;

public class Cellule {
	private Position position;
	private int etat;

	public Cellule(Position position, int etat) {
		this.position = position;
		this.etat = etat;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getEtat() {
		return this.etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	// public boolean getBooleanState
}
