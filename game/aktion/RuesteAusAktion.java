package game.aktion;

import game.Gegenstand;

public class RuesteAusAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der dem Spieler ausgeruestet wird(dieser muss sich im Inventar des Spielers befinden!).
	private Gegenstand gegenstand;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine RuesteAusAktion ruestet dem Spieler einen @gegenstand aus (dieser muss sich im Inventar des Spielers befinden!) und gibt danach eine @meldung aus.
	 * @param gegenstand Der Gegenstand, der dem Spieler ausgeruestet werden soll.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public RuesteAusAktion(Gegenstand gegenstand, String meldung) {
		this.gegenstand = gegenstand;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(ruestet dem Spieler einen Gegenstand aus) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		spielwelt.getSpieler().ruesteAus(gegenstand);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		if(!meldung.equalsIgnoreCase(""))
			spielwelt.println(meldung + "\n\n");
	}
	
}
