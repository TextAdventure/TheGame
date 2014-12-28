package game.logic.aktion;

import game.UntersuchbaresObjekt;

public class UntersuchbaresObjektSetBeschreibungAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Das UntersuchbareObjekt, dessen Beschreibung geaendert werden soll.
	private UntersuchbaresObjekt untersuchbaresObjekt;
	// Die neue Beschreibung fuer das Objekt.
	private String beschreibung;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine SetUntersuchbaresObjektBeschreibungAktion aendert die Beschreibung eines @untersuchbaresObjekt zu einer neuen @beschreibung
	 * und dabie wird die @meldung angezeigt.
	 * @param untersuchbaresObjekt Das UntersuchbareObjekt, dessen Beschreibung geaendert werden soll.
	 * @param beschreibung Die neue Beschreibung fuer das UntersuchbareObjekt.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public UntersuchbaresObjektSetBeschreibungAktion(UntersuchbaresObjekt untersuchbaresObjekt, String beschreibung, String meldung) {
		this.untersuchbaresObjekt = untersuchbaresObjekt;
		this.beschreibung = beschreibung;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(aendert die Beschreibung eines UntersuchbarenObjekts) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		untersuchbaresObjekt.setBeschreibung(beschreibung);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}
