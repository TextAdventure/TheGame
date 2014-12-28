package game.logic;

import java.io.Serializable;

import game.logic.aktion.Aktion;

/**
 * Ein Ereignis hat einerseits einen Logik Teil, der entahelt alle Bedingunugen, die erfuellt sein muessen oder auch nicht und der
 * zweite Teil ist der Aktionen Teil, der entaehlt alle Aktionen, die ausgefuehrt werden, wenn der Logik Teil erfuellt ist.
 */
public class Ereignis implements Serializable {
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die Logik des Ereignisses.
	private Logik logik;
	// Die Aktionen, die ausgefuehrt werden sollen.
	private Aktion[] aktionen;
	
	// Der Zaheler, der die Ausfuehrungen des Ereignisses zaehlt.
	private int zaehler;
	// Gibt an, ob das Ereginis mindestens einmal eingetreten ist.
	private boolean eingetreten;
	
	/**
	 * Ein Ereignis besteht aus dem Logik Teil, der erfuellt sein muss, sodass alle Aktionen ausgefuehrt werden.
	 * @param logik Die Logik, die erfuellt sein muss, sodass die Aktionen ausgefueht werden.
	 * @param aktionen Die Aktionen, die ausgefuehrt werden sollen.
	 */
	public Ereignis(Logik logik, Aktion... aktionen) {
		this.logik = logik;
		this.aktionen = aktionen;
		
		this.zaehler = 1;
		this.eingetreten = false;
	}
	
	/**
	 * Aendert den Wert des Zaehlers auf den uebergebenen Wert.
	 * @param neuerZaehler Der neue Wert des Zaehlers.
	 * @return Sich selbst.
	 */
	public Ereignis setZaehler(int neuerZaehler) {
		this.zaehler = neuerZaehler;
		return this;
	}
	
	/**
	 * Gibt zurueck, ob das Ereignis schon mindestens ein mal eingetreten ist.
	 * @return wahr, wenn es bereits mindestens ein mal eingetreten ist, ansonsten falsch.
	 */
	public boolean istEingetreten() {
		return eingetreten;
	}
	
	/**
	 * Das Ereignis ist eingetreten und wird nun behandelt und fuehrt gegebenfalls seine Aktionen aus.
	 * @return wahr, wenn der Zaehler 0 ist und somit das Objekt geloescht werden kann.
	 */
	public boolean eingetreten() {
		if(logik.pruefe()) {
			eingetreten = true;
			
			if(zaehler > 0)
				zaehler--;
			
			for(Aktion a : aktionen)
				a.ausfuehren();
			
			if(zaehler == 0)
				return true;
		}
		return false;
	}

}
