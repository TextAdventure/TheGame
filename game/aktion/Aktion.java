package game.aktion;

import game.SpielWelt;

import java.io.Serializable;


/**  
 * Eine Aktion, die ausgefuehrt werden kann, sobald eine Ereignis eintritt und dessen Bedingung erfuellt ist.
 */
public abstract class Aktion implements Serializable{

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 0L;
	
	// Meldung, die ausgegeben wird, wenn die Aktion ausgefuehrt wird.
	protected String meldung;
	// Die SpielWelt in der sich diese Aktion befindet.
	protected SpielWelt spielwelt;
	  
	/**
	 *  Diese Methode setzt die SpielWelt Variable dieser Aktion auf den uebergebenen Wert.
	 *  @param spielwelt Die neue SpielWelt fuer diese Aktion.
	 */
	public void setWelt(SpielWelt spielwelt){
	    this.spielwelt = spielwelt;
	}	  
	
	/**
	 * Diese Methode MUSS von jeder Subaktion ueberschrieben werden und mit der entsprechenden Funktion versehen werden.
	 */
	public abstract void ausfuehren();
	
}