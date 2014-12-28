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
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine AddGegenstandAktion fuegt einem @ort einen @gegenstand @anzahl mal hinzu und gibt danach eine @meldung aus.
	 * @param ort Der Ort dem ein Gegenstand hinzugefuegt wird. 
	 * @param gegenstand Der Gegenstand fuer den Ort.
	 * @param anzahl Die Anzahl des Gegenstandes.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public OrtAddGegenstandAktion(Ort ort, Gegenstand gegenstand, int anzahl, String meldung) {
		this.ort = ort;
		this.gegenstand = (short) gegenstand.getId();
		this.anzahl = anzahl;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt einem Ort einen Gegenstand in bestimmter Anzahl hinzu) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		ort.addGegenstand(Gegenstand.getGegenstand(gegenstand), anzahl);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}