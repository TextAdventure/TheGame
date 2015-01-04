package game.logic.aktion;

import game.UntersuchbaresObjekt;

public class UntersuchbaresObjektSetBeschreibungAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Das UntersuchbareObjekt, dessen Beschreibung geaendert werden soll.
	private UntersuchbaresObjekt untersuchbaresObjekt;
	// Die neue Beschreibung fuer das Objekt.
	private String beschreibung;
	// Die SpielWelt ist static.
	
	/**
	 * Eine SetUntersuchbaresObjektBeschreibungAktion aendert die Beschreibung eines UntersuchbaresObjekt zu einer neuen Beschreibung.
	 * @param untersuchbaresObjekt Das UntersuchbareObjekt, dessen Beschreibung geaendert werden soll.
	 * @param beschreibung Die neue Beschreibung fuer das UntersuchbareObjekt.
	 */
	public UntersuchbaresObjektSetBeschreibungAktion(UntersuchbaresObjekt untersuchbaresObjekt, String beschreibung) {
		this.untersuchbaresObjekt = untersuchbaresObjekt;
		this.beschreibung = beschreibung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(aendert die Beschreibung eines UntersuchbarenObjekts).
	 */
	@Override
	public void ausfuehren() {
		untersuchbaresObjekt.setBeschreibung(beschreibung);
	}
	
}
