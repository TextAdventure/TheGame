package game.logic.aktion;

import game.Ort;
import game.items.Gegenstand;

public class OrtAddGegenstandAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort dem ein neuer Gegenstand hinzugefuegt werden soll.
	private Ort ort;
	// Der Gegenstand fuer den Ort.
	private short gegenstand;
	// Die Anzahl des Gegenstand.
	private int anzahl;
	// Die SpielWelt ist static.
	
	/**
	 * Eine AddGegenstandAktion fuegt einem Ort einen Gegenstand Anzahl mal hinzu.
	 * @param ort Der Ort dem ein Gegenstand hinzugefuegt wird. 
	 * @param gegenstand Der Gegenstand fuer den Ort.
	 * @param anzahl Die Anzahl des Gegenstandes.
	 */
	public OrtAddGegenstandAktion(Ort ort, Gegenstand gegenstand, int anzahl) {
		this.ort = ort;
		this.gegenstand = (short) gegenstand.getId();
		this.anzahl = anzahl;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt einem Ort einen Gegenstand in bestimmter Anzahl hinzu).
	 */
	@Override
	public void ausfuehren() {
		ort.addGegenstand(Gegenstand.getGegenstand(gegenstand), anzahl);
	}
	
}
