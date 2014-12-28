package game.logic.bedingung;

import game.SpielWelt;
import game.entity.Faehigkeit;
import game.logic.Logik;

public class SpielerHatFaehigkeitBedingung extends Logik {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	// Die Faehigkeit, auf die geprueft wird.
	private Faehigkeit faehigkeit;

	/**
	 * Ueberprueft, ob der Spieler eine Faehigkeit bereits besitzt.
	 * @param faehigkeit Die Faehigkeit, die der Spieler kennen muss, sodass die Bedingung erfuellt ist.
	 */
	public SpielerHatFaehigkeitBedingung(Faehigkeit faehigkeit) {
		this.faehigkeit = faehigkeit;
	}

	/**
	 * Prueft die Bedingung und gibt entsprechend einen Wert zurueck.
	 * @return true, wenn die Bedingung erfuellt ist, ansonsten false.
	 */
	@Override
	public boolean pruefe() {
		if(SpielWelt.WELT.getSpieler().kenntFaehigkeit(faehigkeit))
			return true;
		return false;
	}

}
