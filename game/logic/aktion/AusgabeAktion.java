package game.logic.aktion;

import game.SpielWelt;

public class AusgabeAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die Meldung, die ausgegeben werden soll.
	private String meldung;
	// Die SpielWelt ist static.
	
	/**
	 * Eine AusgabeAktion gibt lediglich eine Meldung aus.
	 * @param meldung Die Meldung, die dem Spieler angezeigt wird.
	 */
	public AusgabeAktion(String meldung) {
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(gibt nur eine Meldung aus mit einer Leerzeile danach).
	 */
	public void ausfuehren() {
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben, ansonsten wird die Meldung ausgegeben.
		if(!meldung.equalsIgnoreCase(""))
			SpielWelt.WELT.print(meldung);
	}
	
}
