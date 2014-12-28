package game.bedingung;

import game.SpielWelt;
import game.aktion.Aktion;

import java.io.Serializable;


/**
 * Eine Bedingun, die ueberprueft werden kann und falls sie wahr ist, werden Aktionen ausgefuehrt.
 */
public class Bedingung implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Der Zaehler, der die Ausführungen zaehlt.
	protected int zaehler;
	// Dieser boolean gibt absolut an, ob die Bedingung bereits einmal erfuellt wurde.
	protected boolean bedingungErfuellt;
	// Die SpielWelt, in der sich die Bedingung befindet.
	protected SpielWelt spielwelt;
	 
	// Die Aktionen, die ausgefuehrt werden, wenn die Bedingung erfuellt ist.
	protected Aktion[] aktionen;
	  
	
	/**
	 *  Das Ereignis wird sooft ausgefuehrt, wie es der Zaehler erlaubt. Die Methode gibt danach die Bedingung zurueck, sodass diese
	 *  Methode auch auf einen Konstruktor angewendet werden kann.
	 *  @parma neuerZaehler Der anfaengliche Wert des Zaehlers, wenn er -1 ist, dann wird das Ereignis unendlich oft ausgefuehrt, der Standardwert ist 1.
	 */
	public Bedingung setZaehler(int neuerZaehler){
	    zaehler = neuerZaehler;
	    // Gibt sich selbst zurueck, dann kann es noch uebergeben werden.
	    return this;
	}
	  
	/**
	 *  Gibt zurueck, ob die Bedingung bereits ein mal erfuellt wurde.
	 */
	public boolean istErfuellt(){
	    return bedingungErfuellt;
	}
	  

	/**
	 * Diese Methode MUSS von jeder Subbedingung ueberschrieben werden und mit der entsprechenden Funktion versehen werden.
	 */
	public boolean pruefen() {
		// Wird bei einer Subklasse eingefuegt.
		return false;
	}
	
}