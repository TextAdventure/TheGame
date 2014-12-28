package game.logic.aktion;

import java.io.Serializable;

import game.SpielWelt;

/**  
 * Eine Aktion, die ausgefuehrt werden kann, sobald eine Ereignis eintritt und dessen Bedingung erfuellt ist.
 */
public abstract class Aktion implements Serializable{

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 0L;
	
	// Meldung, die ausgegeben wird, wenn die Aktion ausgefuehrt wird.
	protected String meldung;
	
	/**
	 * Diese Methode MUSS von jeder Subaktion ueberschrieben werden und mit der entsprechenden Funktion versehen werden.
	 */
	public void ausfuehren() {
		// Wird bei einer Subklasse eingefuegt.
		if(!meldung.equalsIgnoreCase(""))
			SpielWelt.WELT.println(meldung + "\n");
	}
	
}