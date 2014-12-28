package game.logic.bedingung;

import game.SpielWelt;
import game.items.Gegenstand;
import game.items.Stapel;
import game.logic.Logik;

public class SpielerHatGegenstandBedingung extends Logik {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, auf den das Inventar des Spielers ueberprueft wird.
	private Gegenstand gegenstand;
	// Die Anzahl dieses Gegenstands, die der Spieler haben muss.
	private int anzahl;
	
	/**
	 * Eine SpielerHatGegenstandBedingung ueberprueft das Inventar des Spielers auf den Gegenstand.
	 * @param gegenstand Der Gegenstand, den der Spieler in seinem Inventar haben muss, damit sie erfuellt ist.
	 * @param anzahl Die Mindestanzahl an Gegenstaenden, die der Spieler haben muss.
	 */
	public SpielerHatGegenstandBedingung(Gegenstand gegenstand, int anzahl) {
		this.gegenstand = gegenstand;
		this.anzahl = anzahl;
	}

	/**
	 * Prueft die Bedingung und gibt entsprechend einen Wert zurueck.
	 * @return true, wenn die Bedingung erfuellt ist, ansonsten false.
	 */
	@Override
	public boolean pruefe() {
		if(SpielWelt.WELT.getInventar().containsGegenstand(new Stapel(gegenstand, anzahl)))
			return true;
		return false;
	}
	
}
