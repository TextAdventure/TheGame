package game;

import java.io.Serializable;

import game.items.Stapel;

/**
 * Ein Drop, er beinhaltet Gegenstaende und eine Wahrscheinlichkeit fuer das erhalten dieses Drops.
 * @author Marvin
 */
public class Drop implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Die Gegenstaende, die erhalten werden koennen.	
	private Stapel[] gegenstaende;	
	// Die Wahrscheinlichkeit fuer diesen Drop in Promille als Ganzzahl.
	private int chance;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein Drop wird mit einer Chance und den moeglichen Gegenstaenden erstellt.
	 * @param chance Die Chance auf den Drop in Promille.
	 * @param gegenstaende Die Gegenstaende, die bei diesem Drop fallengelassen werden.
	 */
	public Drop(int chance, Stapel... gegenstaende) {
		this.chance = chance;
		this.gegenstaende = gegenstaende;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt die Chance auf diesen Drop zurueck.
	 * @return Die Chance in Promille.
	 */
	public int getChance() {
		return chance;
	}
	
	/**
	 * Die Gegenstaende, die erhalten werden, wenn der Drop fallengelassen wird.
	 * @return Die Gegenstaende.
	 */
	public Stapel[] getGegenstaende() {
		return gegenstaende;
	}
}