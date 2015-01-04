package game.logic.aktion;

import game.SpielWelt;
import game.items.Gegenstand;

public class SpielerAddGegenstandAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der dem Spieler hinzugefuegt wird.
	private short gegenstand;
	// Die Anzahl die dem Spieler hinzugefuegt wird.
	private int anzahl;
	// Die SpielWelt ist static.
	
	/**
	 * Eine SpielerAddGegenstandAktion fuegt dem Inventar des Spieler eine Anzahl von Gegenstand hinzu.
	 * @param gegenstand Der Gegenstand, der dem Inventar des Spielers hinzugefuegt werden soll.
	 * @param anzahl Die Anzahl des Gegenstands.
	 */
	public SpielerAddGegenstandAktion(Gegenstand gegenstand, int anzahl) {
		this.gegenstand = (short) gegenstand.getId();
		this.anzahl = anzahl;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt dem Inventar des Spielers einen Gegenstand hinzu).
	 */
	@Override
	public void ausfuehren() {
		SpielWelt.WELT.getInventar().addGegenstand(Gegenstand.getGegenstand(gegenstand), anzahl);
	}
	
}
