package game.aktion;

import game.Gegenstand;

public class SpielerRemoveGegenstandAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der ausdem Inventar des Spielers entfernt werden soll.
	private Gegenstand gegenstand;
	// Die Anzahl an Gegenstaenden die entfernt werden soll.
	private int anzahl;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine SpielerRemoveGegenstandAktion entfernt eine bestimmte @anzahl eines @gegenstand aus dem Inventar des Spielers
	 * und dabei wird die @meldung angezeigt.
	 * @param gegenstand Dieser Gegenstand wird aus dem Inventar des Spielers entfernt.
	 * @param anzahl Diese Anzahl des Gegenstands wird aus dem Inventar entfernt.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public SpielerRemoveGegenstandAktion(Gegenstand gegenstand, int anzahl, String meldung) {
		this.gegenstand = gegenstand;
		this.anzahl = anzahl;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(entfernt eine bestimmte Anzahl eines Gegenstands ausdem Inventar des Spielers) 
	 * und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		spielwelt.removeGegenstand(gegenstand, anzahl);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		if(!meldung.equalsIgnoreCase(""))
			spielwelt.println(meldung + "\n\n");
	}
	
}
