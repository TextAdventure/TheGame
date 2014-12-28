package game.aktion;

import game.Gegenstand;

public class SpielerAddGegenstandAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der dem Spieler hinzugefuegt wird.
	private Gegenstand gegenstand;
	// Die Anzahl die dem Spieler hinzugefuegt wird.
	private int anzahl;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine SpielerAddGegenstandAktion fuegt dem Inventar des Spieler eine @anzahl von @gegenstand hinzu 
	 * und dabei wird die @meldung angezeigt.
	 * @param gegenstand
	 * @param anzahl
	 * @param meldung
	 */
	public SpielerAddGegenstandAktion(Gegenstand gegenstand, int anzahl, String meldung) {
		this.gegenstand = gegenstand;
		this.anzahl = anzahl;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt dem Inventar des Spielers einen Gegenstand hinzu) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		spielwelt.addGegenstand(gegenstand, anzahl);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		if(!meldung.equalsIgnoreCase(""))
			spielwelt.println(meldung + "\n\n");
	}
	
}
