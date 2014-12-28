package game.logic.aktion;

import game.Ort;

public class OrtSetNamenAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort, dessen Name geaendert werden soll.
	private Ort ort;
	// Der neue Name des Ortes.
	private String name;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine ChangeOrtNamenAktion aendert den @namen eines @ort und dabei wird die @meldung angezeigt.
	 * @param ort Der Ort, dessen Name geaendert werden soll.
	 * @param name Der neue Name fuer den Ort.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public OrtSetNamenAktion(Ort ort, String name, String meldung) {
		this.ort = ort;
		this.name = name;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(aendert den Namen eines Ortes) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		ort.setName(name);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}
