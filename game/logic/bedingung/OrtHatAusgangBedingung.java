package game.logic.bedingung;

import game.Ausgang;
import game.Ort;
import game.logic.Logik;

public class OrtHatAusgangBedingung extends Logik {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort, der auf einen Ausgang ueberprueft wird.
	private Ort ort;
	// Der Ausgang, der sich an dem Ort befinden muss, sodass die Bedingung erfuellt ist.
	private Ausgang ausgang;
	
	/**
	 * Eine OrtHasAusgangBedingung ueberprueft einen Ort auf einen Ausgang.
	 * @param ort Der Ort, der einen Ausgang haben muss, sodass die Bedingung erfuellt sein kann.
	 * @param ausgang Diesen Ausgang muss der Ort haben.
	 */
	public OrtHatAusgangBedingung(Ort ort, Ausgang ausgang) {
		this.ort = ort;
		this.ausgang = ausgang;
	}
	
	/**
	 * Prueft die Bedingung und gibt entsprechend einen Wert zurueck.
	 * @return true, wenn die Bedingung erfuellt ist, ansonsten false.
	 */
	@Override
	public boolean pruefe() {
		if(ort.hasAusgang(ausgang))
			return true;
		return false;
	}
	
}
