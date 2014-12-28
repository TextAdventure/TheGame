package game.aktion;


public class AusgabeAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die Meldung wird von Aktion uebernommen und ausser dieser ist nichts noetig.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine AusgabeAktion gibt lediglich eine @meldung aus.
	 * @param meldung Die Meldung, die dem Spieler angezeigt wird.
	 */
	public AusgabeAktion(String meldung) {
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(gibt nur eine Meldung aus).
	 */
	public void ausfuehren() {
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		if(!meldung.equalsIgnoreCase(""))
			spielwelt.println(meldung + "\n\n");
	}
	
}
