package game.logic.aktion;

import java.io.Serializable;

/**  
 * Eine Aktion, die ausgefuehrt werden kann, sobald eine Ereignis eintritt und dessen Bedingung erfuellt ist.
 */
public abstract class Aktion implements Serializable{

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 0L;
	
	/**
	 * Diese Methode MUSS von jeder Subaktion ueberschrieben werden und mit der entsprechenden Funktion versehen werden.
	 */
	public abstract void ausfuehren();
	
}