package game.logic.ereignis;

import java.io.Serializable;
import java.util.Vector;

import game.Ort;
import game.logic.Logik;
import game.logic.aktion.Aktion;

/**
 * Ein Ereignis hat einerseits einen Logik Teil, der entahelt alle Bedingunugen, die erfuellt sein muessen oder auch nicht und der
 * zweite Teil ist der Aktionen Teil, der entaehlt alle Aktionen, die ausgefuehrt werden, wenn der Logik Teil erfuellt ist.
 * @author Marvin
 */
public abstract class Ereignis implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Die Logik des Ereignisses.
	private Logik logik;
	// Die Aktionen, die ausgefuehrt werden sollen.
	private Aktion[] aktionen;
	
	// Der Zaheler, der die Ausfuehrungen des Ereignisses zaehlt.
	private int zaehler;
	// Gibt an, ob das Ereginis mindestens einmal eingetreten ist.
	private boolean eingetreten;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein Ereignis besteht aus dem Logik Teil, der erfuellt sein muss, sodass alle Aktionen ausgefuehrt werden.
	 * @param zaehler Der Zaehler gibt an, wie oft das Ereignis eintreten darf, bevor es entfernt wird(-1 bedeutet unendlich oft).
	 * @param logik Die Logik, die erfuellt sein muss, sodass die Aktionen ausgefuehrt werden.
	 * @param aktionen Die Aktionen, die ausgefuehrt werden sollen.
	 */
	protected Ereignis(int zaehler, Logik logik, Aktion... aktionen) {
		this.logik = logik;
		this.aktionen = aktionen;
		
		this.zaehler = zaehler;
		this.eingetreten = false;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt zurueck, ob das Ereignis schon mindestens ein mal eingetreten ist.
	 * @return True, wenn es bereits mindestens ein mal eingetreten ist, ansonsten false.
	 */
	public boolean istEingetreten() {
		return eingetreten;
	}
	
	/**
	 * Das Ereignis ist eingetreten und wird nun behandelt und fuehrt gegebenfalls seine Aktionen aus.
	 * @return True, wenn der Zaehler 0 ist und somit das Objekt geloescht werden kann, ansonsten false.
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
	
	/* --- statische Konstanten und Methoden --- */
	
	// Eine Liste mit allen OrtBetretenEreignissen.
	private static Vector<OrtBetretenEreignis> ortBetreten = new Vector<OrtBetretenEreignis>();
	
	/**
	 * Fuegt der Liste der OrtBetretenEreignisse ein neues hinzu.
	 * @param ereignis Das neue OrtBetretenEreignis.
	 */
	protected static void addOrtBetretenEreignis(OrtBetretenEreignis ereignis) {
		ortBetreten.add(ereignis);
	}
	
	/**
	 * Der Spieler betritt einen Ort, dieser wird hier uebergeben, danach werden alle OrtBetretenEreignisse geprueft mit diesem Ort.
	 * @param ort Der Ort, der betreten wurde.
	 */
	public static synchronized void ortBetreten(Ort ort) {
		for(OrtBetretenEreignis obe : ortBetreten.toArray(new OrtBetretenEreignis[0]))
			obe.eingetreten(ort);
	}
	
	/**
	 * Entfernt ein Ereignis aus der Liste aller Ereignisse, nachdem sein Zaehler 0
	 * erreicht hat und es somit nicht mehr ausgeloest werden sollte.
	 * @param ereignis Das Ereignis, das entfernt werden soll.
	 */
	protected static synchronized void removeEreignis(Ereignis ereignis) {
		if(ereignis instanceof OrtBetretenEreignis)
			for(OrtBetretenEreignis obe : ortBetreten.toArray(new OrtBetretenEreignis[0]))
				if(obe.equals(ereignis))
					ortBetreten.remove(ereignis);
	}
	
	/**
	 * Gibt eine Liste mit allen Ereignissen zurueck, diese Methode wird verwendet, um die Liste zu speichern.
	 * @return Die Liste mit allen Ereignissen, die gespeichert werden soll.
	 */
	public static Ereignis[] getAlleEreignisse() {
		Vector<Ereignis> e = new Vector<Ereignis>();
		for(OrtBetretenEreignis obe : ortBetreten)
			e.add(obe);
		return e.toArray(new Ereignis[0]);
	}
	
	/**
	 * Legt die Liste aller Ereignisse fest, diese Methode wird verwendet, um die Liste zu laden.
	 * @param ereignisse Die Liste mit allen Ereignissen, die geladen werden soll.
	 */
	public static void setAlleEreignisse(Ereignis[] ereignisse) {
		for(Ereignis e : ereignisse)
			if(e instanceof OrtBetretenEreignis)
				addOrtBetretenEreignis((OrtBetretenEreignis) e); 
	}

}