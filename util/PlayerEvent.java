package util;

import game.entity.Spieler;

/**
 * Dieses Event uebergibt den Spieler an den Listener.
 */
public class PlayerEvent {

	// Der Spieler.
	private Spieler player;
	
	/**
	 * Erstellt ein neues Event nur mit dem Spieler.
	 * @param player Der Spieler, der uebergeben werden soll.
	 */
	public PlayerEvent(Spieler player) {
		this.player = player;
	}
	
	/**
	 * Gibt den Spieler zurueck.
	 * @return Den Spieler.
	 */
	public Spieler getPlayer() {
		return player;
	}
}
