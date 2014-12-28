package game.logic.bedingung;

import game.SpielWelt;
import game.items.Gegenstand;
import game.logic.Logik;

public class SpielerHatAusgeruestetBedingung extends Logik {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, den der Spieler ausgeruestet haben muss, sodass die Bedingung erfuellt ist.
	private Gegenstand gegenstand;
	
	/**
	 * Eine SpielerHatAusgeruestetBedingung prueft, ob der Spieler einen Gegenstand ausgeruestet hat.
	 * @param gegenstand Der Gegenstand, den der Spieler ausgeruestet haben muss.
	 */
	public SpielerHatAusgeruestetBedingung(Gegenstand gegenstand) {
		this.gegenstand = gegenstand;
	}

	/**
	 * Prueft die Bedingung und gibt entsprechend einen Wert zurueck.
	 * @return true, wenn die Bedingung erfuellt ist, ansonsten false.
	 */
	@Override
	public boolean pruefe() {
		if(SpielWelt.WELT.getSpieler().getAusruestung().istAusgeruestet(gegenstand))
			return true;
		return false;
	}
	
}
