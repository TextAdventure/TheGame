package game.logic.aktion;

import game.SpielWelt;
import game.items.Gegenstand;

public class SpielerRemoveGegenstandAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der ausdem Inventar des Spielers entfernt werden soll.
	private short gegenstand;
	// Die Anzahl an Gegenstaenden die entfernt werden soll.
	private int anzahl;
	// Die SpielWelt ist static.
	
	/**
	 * Eine SpielerRemoveGegenstandAktion entfernt eine bestimmte Anzahl eines Gegenstands aus dem Inventar des Spielers.
	 * @param gegenstand Dieser Gegenstand wird aus dem Inventar des Spielers entfernt.
	 * @param anzahl Diese Anzahl des Gegenstands wird aus dem Inventar entfernt.
	 */
	public SpielerRemoveGegenstandAktion(Gegenstand gegenstand, int anzahl) {
		this.gegenstand = (short) gegenstand.getId();
		this.anzahl = anzahl;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(entfernt eine bestimmte Anzahl eines Gegenstands ausdem Inventar des Spielers).
	 */
	@Override
	public void ausfuehren() {
		SpielWelt.WELT.getInventar().removeGegenstand(Gegenstand.getGegenstand(gegenstand), anzahl);
	}
	
}
