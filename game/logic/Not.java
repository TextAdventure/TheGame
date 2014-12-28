package game.logic;

/**
 * Invertiert die Aussage eines ILogic-Objekts.
 */
public class Not extends Logik {
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	// Der ILogic Ausdruck, der invertiert werden soll.
	private Logik logic;	
	
	/**
	 * Ein neues Not braucht lediglich eine 
	 * @param logic
	 */
	public Not(Logik logic) {
		this.logic = logic;
	}

	/**
	 * Prueft den Operator und gibt entsprechend einen Wert zurueck.
	 * @return true, wenn die Logik erfuellt ist, ansonsten false.
	 */
	@Override
	public boolean pruefe() {
		return !logic.pruefe();
	}

}
