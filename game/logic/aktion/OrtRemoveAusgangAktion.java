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
	// Die SpielWelt ist static.
	
	/**
	 * Eine RemoveAusgangAktion entfernt den Ausgang von dem Ort.
	 * @param ort Der Ort von dem ein Ausgang entfernt wird.
	 * @param ausgang Der Ausgang, der entfernt wird.
	 */
	public OrtRemoveAusgangAktion(Ort ort, Ausgang ausgang) {
		this.ort = ort;
		this.ausgang = ausgang;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(entfernt einen Ausgang von einem Ort).
	 */
	@Override
	public void ausfuehren() {
		ort.removeAusgang(ausgang);
	}

}
