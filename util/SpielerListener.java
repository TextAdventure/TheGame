package util;

import game.entity.Spieler;

/**
 * Kann von einer Klasse implementiert werden, sodass diese Aenderungen des Spielers und dessen Faehigkeiten ueberwachen kann.
 * @author Marvin
 */
public interface SpielerListener {
	/**
	 * Wird immer aufgerufen, wenn sich der Spieler oder seine Faehigkeiten veraendert haben und diese Aenderung weitergegeben werden soll.
	 * @param evt Der "neue" Spieler, der uebergeben werden soll.
	 */
	public abstract void spielerUpdate(Spieler evt); 
}
