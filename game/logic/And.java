package game.logic;

/**
 * Verkettet mehrere ILogics zu einem And.
 */
public class And extends Logik {
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	// Alle ILogics, die true sein muessen, sodass der gesamte Ausdruck wahr ist.
	private Logik[] logics;
	
	/**
	 * Ein neues And wird mit mehreren ILogics erstellt und gibt nur true zurueck, wenn alle wahr sind.
	 * @param logics Alle Ilogics, die wahr sein muessen.
	 */
	public And(Logik... logics) {
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
			if(!logic.pruefe())
				ergebnis = true;
		return ergebnis;
	}

}
