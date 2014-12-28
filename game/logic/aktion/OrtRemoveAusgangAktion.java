package game.logic.aktion;

import game.Ausgang;
import game.Ort;

public class OrtRemoveAusgangAktion extends Aktion {

	//Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort von dem ein Ausgang entfernt wird.
	private Ort ort;
	// Der Ausgang der sich an diesem Ort noch befindet.
	private Ausgang ausgang;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine RemoveAusgangAktion entfernt den @ausgang von dem @ort, dabei wird die @meldung ausgegeben.
	 * @param ort Der Ort von dem ein Ausgang entfernt wird.
	 * @param ausgang Der Ausgang, der entfernt wird.
	 * @param meldung Die Meldung fuer den Spieler.
	 */
	public OrtRemoveAusgangAktion(Ort ort, Ausgang ausgang, String meldung) {
		this.ort = ort;
		this.ausgang = ausgang;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(entfernt einen Ausgang von einem Ort) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		ort.removeAusgang(ausgang);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}

}
