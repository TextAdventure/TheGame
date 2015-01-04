package game.logic.aktion;

import game.Ort;

public class OrtSetNamenAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort, dessen Name geaendert werden soll.
	private Ort ort;
	// Der neue Name des Ortes.
	private String name;
	// Die SpielWelt ist static.
	
	/**
	 * Eine ChangeOrtNamenAktion aendert den Namen eines Orts.
	 * @param ort Der Ort, dessen Name geaendert werden soll.
	 * @param name Der neue Name fuer den Ort.
	 */
	public OrtSetNamenAktion(Ort ort, String name) {
		this.ort = ort;
		this.name = name;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(aendert den Namen eines Ortes).
	 */
	@Override
	public void ausfuehren() {
		ort.setName(name);
	}
	
}
