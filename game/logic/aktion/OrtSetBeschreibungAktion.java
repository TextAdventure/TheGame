package game.logic.aktion;

import game.Ort;

public class OrtSetBeschreibungAktion extends Aktion {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Der Ort, dessen Beschreibung geaendert werden soll.
	private Ort ort;
	// Die neue Beschreibung, die den Ort beschreibt.
	private String beschreibung;
	// Die SpielWelt ist static.
	
	/**
	 * Eine OrtSetBeschreibungAktion aendert die Beschreibung eines Orts.
	 * @param ort Der Ort fuer den die Beschreibung geaendert werden soll.
	 * @param beschreibung Die neue Beschreibung fuer den Ort.
	 */
	public OrtSetBeschreibungAktion(Ort ort, String beschreibung) {
		this.ort = ort;
		this.beschreibung = beschreibung;
	}
	
	/**
	 * Diese Methode fuehrt die AKtion aus(anedert die Beschreibung eines Ortes).
	 */
	@Override
	public void ausfuehren() {
		ort.setBeschreibung(beschreibung);
	}
	
}
