package game;

import java.io.Serializable;

import game.items.Stapel;

/**
 * Ein Drop jeglicher Art, er beinhaltet Gegenstaende und eine Wahrscheinlichkeit fuer das erhalten dieses Drops.
 */
public class Drop implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	// Die Gegenstaende, die erhalten werden koennen.	
	private Stapel[] loot;	
	// Die Wahrscheinlichkeit fuer diesen Drop in Promille als Ganzzahl.
	private int chance;
	
	/**
	 * Ein Drop wird mit einer Chance und dem moeglichen Loot erstellt.
	 * @param chance Die Chance auf den Drop in Promille.
	 * @param loot Die Gegenstaende, die bei diesem Drop fallengelassen werden.
	 */
	public Drop(int chance, Stapel... loot) {
		this.chance = chance;
		this.loot = loot;
	}
	
	/**
	 * Gibt die Chance auf diesen Drop zurueck.
	 * @return Die Chance in Promille.
	 */
	public int getChance() {
		return chance;
	}
	
	/**
	 * Der Loot der erhalten wird, wenn der Drop fallengelassen wird.
	 * @return Den Loot.
	 */
	public Stapel[] getLoot() {
		return loot;
	}
}