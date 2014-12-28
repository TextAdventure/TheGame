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
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
		
	/**
	 * Eine RemoveUntersuchbaresObjektAktion entfernt ein @untersuchbaresObjekt von einem @ort und dabei wird die @meldung angezeigt.
	 * @param ort Der Ort von dem das UntersuchbareObjekt entfernt werden soll.
	 * @param untersuchbaresObjekt Das zuentfernende UntersuchbareObjekt.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public OrtRemoveUntersuchbaresObjektAktion(Ort ort, UntersuchbaresObjekt untersuchbaresObjekt, String meldung) {
		this.ort = ort;
		this.untersuchbaresObjekt = untersuchbaresObjekt;
		this.meldung = meldung;
	}
		
	/**
	 * Diese Methode fuehrt die Aktion aus(entfernt ein UntersuchbaresObjekt von einem Ort) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		ort.removeUntersuchbaresObjekt(untersuchbaresObjekt);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}
