package util;

import game.items.Inventar;

/**
 * Kann von einer Klasse implementiert werden, sodass diese das Inventar erhalten kann und dieses dann verarbeiten.
 * @author Marvin
 */
public interface InventarListener {
	/**
	 * Wird immer aufgerufen, wenn sich das Inventar geaendert hat und diese Aenderung weitergegeben werden soll.
	 * @param evt Das neue Inventar, das uebergeben werden soll.
	 */
	public void inventarUpdate(Inventar evt);
}
