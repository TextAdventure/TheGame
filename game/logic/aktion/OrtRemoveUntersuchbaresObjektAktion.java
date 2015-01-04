package game.logic.aktion;

import game.Ort;
import game.UntersuchbaresObjekt;

public class OrtRemoveUntersuchbaresObjektAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort, von dem ein UntersuchbaresObjekt entfernt werden soll.
	private Ort ort;
	// Das zuentfernende UntersuchbareObjekt.
	private UntersuchbaresObjekt untersuchbaresObjekt;
	// Die SpielWelt ist static.
		
	/**
	 * Eine RemoveUntersuchbaresObjektAktion entfernt ein UntersuchbaresObjekt von einem Ort.
	 * @param ort Der Ort von dem das UntersuchbareObjekt entfernt werden soll.
	 * @param untersuchbaresObjekt Das zuentfernende UntersuchbareObjekt.
	 */
	public OrtRemoveUntersuchbaresObjektAktion(Ort ort, UntersuchbaresObjekt untersuchbaresObjekt) {
		this.ort = ort;
		this.untersuchbaresObjekt = untersuchbaresObjekt;
	}
		
	/**
	 * Diese Methode fuehrt die Aktion aus(entfernt ein UntersuchbaresObjekt von einem Ort).
	 */
	@Override
	public void ausfuehren() {
		ort.removeUntersuchbaresObjekt(untersuchbaresObjekt);
	}
	
}
