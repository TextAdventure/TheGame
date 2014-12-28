package game.logic.aktion;

import game.Ort;

public class OrtSetBeschreibungAktion extends Aktion {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Der Ort, dessen Beschreibung geaendert werden soll.
	private Ort ort;
	// Die neue Beschreibung, die den Ort beschreibt.
	private String beschreibung;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine ChangeOrtBeschreibungAktion aendert die @beschreibung des @ort und dabei wird fuer den Spieler sichtbar die @meldung ausgegeben.
	 * @param ort Der Ort fuer den die Beschreibung geaendert werden soll.
	 * @param beschreibung Die neue Beschreibung fuer den Ort.
	 * @param meldung Diese Meldung wird ausgegben, sobald die Aktion ausgefuehrt wurde.
	 */
	public OrtSetBeschreibungAktion(Ort ort, String beschreibung, String meldung) {
		this.ort = ort;
		this.beschreibung = beschreibung;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die AKtion aus(anedert die Beschreibung eines Ortes) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		ort.setBeschreibung(beschreibung);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}
