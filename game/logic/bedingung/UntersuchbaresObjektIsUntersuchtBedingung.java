package game.logic.bedingung;

import game.UntersuchbaresObjekt;
import game.logic.Logik;

public class UntersuchbaresObjektIsUntersuchtBedingung extends Logik {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Das UntersuchbareObjekt, welches untersucht sein muss.
	private UntersuchbaresObjekt untersuchbaresObjekt;
	
	/**
	 * Eine UntersuchbaresObjektIsUntersuchtBedingung ueberprueft ein UntersuchbaresObjekt, ob es bereits untersucht wurde.
	 * @param untersuchbaresObjekt Das UntersuchbareObjekt, das ueberprueft werden soll.
	 */
	public UntersuchbaresObjektIsUntersuchtBedingung(UntersuchbaresObjekt untersuchbaresObjekt) {
		this.untersuchbaresObjekt = untersuchbaresObjekt;
	}

	/**
	 * Prueft die Bedingung und gibt entsprechend einen Wert zurueck.
	 * @return true, wenn die Bedingung erfuellt ist, ansonsten false.
	 */
	@Override
	public boolean pruefe() {
		return untersuchbaresObjekt.isUntersucht();
	}
	
}
