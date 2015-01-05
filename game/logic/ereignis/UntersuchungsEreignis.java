package game.logic.ereignis;

import game.UntersuchbaresObjekt;
import game.logic.Logik;
import game.logic.aktion.Aktion;

public class UntersuchungsEreignis extends Ereignis {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die Variablen werden aus Ereignis uebernommen.
	// Das UntersuchbareObjekt, das untersucht werden muss.
	private UntersuchbaresObjekt untersuchbaresObjekt;
	
	/**
	 * Ein UntersuchungsEreignis tritt dann ein, wenn der Spieler das uebergebene UntersuchbareObjekt untersucht.
	 * @param untersuchbaresObjekt Das UntersuchbareObjekt, das untersucht werden muss, sodass das Ereignis ausgeloest wird.
	 * @param zaehler Der Zaehler gibt an, wie oft das Ereignis eintreten darf, bevor es entfernt wird(-1 bedeutet unendlich oft).
	 * @param logik Die Logik, die erfuellt sein muss, sodass die Aktionen ausgefuehrt werden.
	 * @param aktionen Die Aktionen, die ausgefuehrt werden sollen.
	 */
	public UntersuchungsEreignis(UntersuchbaresObjekt untersuchbaresObjekt, int zaehler, Logik logik, Aktion... aktionen) {
		super(zaehler, logik, aktionen);
		
		this.untersuchbaresObjekt = untersuchbaresObjekt;
		
		Ereignis.addUntersuchungsEreignis(this);
	}

	/**
	 * Uebergibt dem Ereignis ein UntersuchbaresObjekt, falls dieses mit dem uebergebenen uebereinstimmt, wird
	 * die Logik geprueft und gegebenenfalls werden die Aktionen ausgefuehrt.
	 * @param untersucht Das Objekt, das der Spieler untersucht hat.
	 */
	protected void eingetreten(UntersuchbaresObjekt untersucht) {
		if(untersuchbaresObjekt.equals(untersucht))
			// Falls super ein true zurueckgibt, muss das Ereignis geloescht werden.
			if(super.eingetreten())
				Ereignis.removeEreignis(this);
	}
	
}