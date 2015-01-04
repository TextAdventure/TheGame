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
	// Die SpielWelt ist static.
	
	/**
	 * Eine AddAusgangAktion fuegt einem Ort den Ausgang hinzu.
	 * @param ort Der Ort, dem ein Ausgang hinzugefuegt wird.
	 * @param ausgang Der neue Ausgang fuer den Ort.
	 */
	public OrtAddAusgangAktion(Ort ort, Ausgang ausgang) {
		this.ort = ort;
		this.ausgang = ausgang;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt einem Ort einen Ausgang hinzu).
	 */	
	@Override
	public void ausfuehren() {
		ort.addAusgang(ausgang);
	}
	
}
