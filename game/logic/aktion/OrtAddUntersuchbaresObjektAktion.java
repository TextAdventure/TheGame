package game.logic.aktion;

import game.Ort;
import game.UntersuchbaresObjekt;

public class OrtAddUntersuchbaresObjektAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort, dem ein neues UntersuchbaresObjekt hinzugefuegt wird.
	private Ort ort;
	// Das neue UntersuchbareObjekt an dem Ort.
	private UntersuchbaresObjekt untersuchbaresObjekt;
	// Die SpielWelt ist static.
	
	/**
	 * Eine AddUntersuchbaresObjektAktion fuegt einem Ort ein UntersuchbaresObjekt hinzu.
	 * @param ort Der Ort an dem das UntersuchbareObjekt hinzugefuegt wird.
	 * @param untersuchbaresObjekt Das neue UntersuchbareObjekt fuer den Ort.
	 */
	public OrtAddUntersuchbaresObjektAktion(Ort ort, UntersuchbaresObjekt untersuchbaresObjekt) {
		this.ort = ort;
		this.untersuchbaresObjekt = untersuchbaresObjekt;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt einem Ort ein neues UntersuchbaresObjekt hinzu).
	 */
	@Override
	public void ausfuehren() {
		ort.addUntersuchbaresObjekt(untersuchbaresObjekt);
	}
	
}
