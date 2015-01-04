package game.logic.ereignis;

import game.Ort;
import game.logic.Logik;
import game.logic.aktion.Aktion;

public class OrtBetretenEreignis extends Ereignis {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die Variablen werden aus Ereignis uebernommen.
	// Der Ort, der betreten werden muss.
	private Ort ort;
	
	/**
	 * Ein OrtBetretenEreignis tritt dann ein, wenn der Spieler den uebergebenen Ort betritt.
	 * @param ort Der Ort, den der Spieler betreten muss, sodass das Ereignis ausgeloest wird.
	 * @param zaehler Der Zaehler gibt an, wie oft das Ereignis eintreten darf, bevor es entfernt wird(-1 bedeutet unendlich oft).
	 * @param logik Die Logik, die erfuellt sein muss, sodass die Aktionen ausgefuehrt werden.
	 * @param aktionen Die Aktionen, die ausgefuehrt werden sollen.
	 */
	public OrtBetretenEreignis(Ort ort, int zaehler, Logik logik, Aktion... aktionen) {
		super(zaehler, logik, aktionen);
		
		this.ort = ort;
		
		Ereignis.addOrtBetretenEreignis(this);
	}
	
	/**
	 * Uebergibt dem Ereignis einen Ort, falls dieser mit dem uebergebenen uebereinstimmt, wird
	 * die Logik geprueft und gegebenenfalls werden die Aktionen ausgefuehrt.
	 * @param betreten Der Ort, den der Spieler betreten hat.
	 */
	public void eingetreten(Ort betreten) {
		if(ort.equals(betreten))
			// Falls super ein true zurueckgibt, muss das Ereignis geloescht werden.
			if(super.eingetreten())
				Ereignis.removeEreignis(this);				
	}

}