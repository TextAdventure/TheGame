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
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine AddUntersuchbaresObjektAktion fuegt einem @ort ein @untersuchbaresObjekt hinzu und dabei wird die @meldung angezeigt.
	 * @param ort Der Ort an dem das UntersuchbareObjekt hinzugefuegt wird.
	 * @param untersuchbaresObjekt Das neue UntersuchbareObjekt fuer den Ort.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public OrtAddUntersuchbaresObjektAktion(Ort ort, UntersuchbaresObjekt untersuchbaresObjekt, String meldung) {
		this.ort = ort;
		this.untersuchbaresObjekt = untersuchbaresObjekt;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt einem Ort ein neues UntersuchbaresObjekt hinzu) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		ort.addUntersuchbaresObjekt(untersuchbaresObjekt);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}
