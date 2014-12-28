package game.logic;

/**
 * Verkettet mehrere ILogics zu einem Or.
 */
public class Or extends Logik {
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	// Alle ILogics, die ueberprueft werden sollen.
	private Logik[] logics;

	/**
	 * Ein neues Or wird mit mehreren ILogics erstellt und gibt wahr zurueck, wenn eines der ILogics wahr ist.
	 * @param logics Alle ILogics, die ueberprueft werden sollen.
	 */
	public Or(Logik... logics) {
		this.logics = logics;
	}
	
	/**
	 * Prueft den Operator und gibt entsprechend einen Wert zurueck.
	 * @return true, wenn die Logik erfuellt ist, ansonsten false.
	 */
	@Override
	public boolean pruefe() {
		boolean ergebnis = false;
		for(Logik logic : logics)
			if(logic.pruefe())
				ergebnis = true;
		return ergebnis;
	}

}
