package game.logic.bedingung;

import game.logic.Logik;

public class KeineBedingung extends Logik {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Keine eigenen Variablen noetig.
	
	/**
	 * Eine KeineBedingung fuehrt nur die Aktionen aus, ohne eine Bedingung zu ueberpruefen.
	 */
	public KeineBedingung() {
		
	}

	/**
	 * Prueft die Bedingung und gibt entsprechend einen Wert zurueck.
	 * @return true, wenn die Bedingung erfuellt ist, ansonsten false.
	 */
	@Override
	public boolean pruefe() {
		return true;
	}
	
}
