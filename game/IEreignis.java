package game;

import game.logic.Ereignis;

/**
 * Eine Klasse, die dieses Interface implementiert, hat die Moeglichkeit, das es vor
 * etwas und nach etwas Ereignisse pruefen kann und gegebenenfalls Aktionen ausfuehrt.
 * @author Marvin
 */
public interface IEreignis {
	
	/**
	 * Fuegt dem Ereignis Ereignisse hinzu, die VOR dem untersuchen geprueft werden. 
	 * @param ereignisse Die Ereginisse, die VOR dem untersuchen geprueft werden sollen.
	 * @return Sich selbst.
	 */
	public abstract IEreignis addVorBedingung(Ereignis... ereignisse);
	
	/**
	 * Fuegt dem Ereignis Ereignisse hinzu, die NACH dem untersuchen geprueft werden. 
	 * @param bedingungen Die Ereginisse, die NACH dem untersuchen geprueft werden soll.
	 * @return Sich selbst.
	 */
	public abstract IEreignis addNachBedingung(Ereignis... ereignisse);
	
	/**
	 * Prueft alle Ereignisse, die VOR einer Untersuchung geprueft werden sollen.
	 */
	public void vorUntersuchung();
	
	/**
	 * Prueft alle Ereignisse, die NACH einer Untersuchung geprueft werden sollen.
	 */
	public void nachUntersuchung();

}