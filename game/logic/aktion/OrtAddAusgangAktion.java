package game.logic.aktion;

import game.Ausgang;
import game.Ort;

public class OrtAddAusgangAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	// Der Ort dem ein Ausgang hinzugefügt wird.
	private Ort ort;
	// Der neue Ausgang fuer den Ort.
	private Ausgang ausgang;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine AddAusgangAktion fuegt @ort den @ausgang hinzu und dabei wird die @meldung angezeigt.
	 * @param ort Der Ort, dem ein Ausgang hinzugefuegt wird.
	 * @param ausgang Der neue Ausgang fuer den Ort.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public OrtAddAusgangAktion(Ort ort, Ausgang ausgang, String meldung) {
		this.ort = ort;
		this.ausgang = ausgang;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt einem Ort einen Ausgang hinzu) und gibt danach eine Meldung aus.
	 */	
	@Override
	public void ausfuehren() {
		ort.addAusgang(ausgang);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}
