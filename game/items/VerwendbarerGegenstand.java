package game.items;

import game.entity.Effekt;
import game.entity.Faehigkeit;
import util.NumerusGenus;

public class VerwendbarerGegenstand extends Gegenstand {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	// Statisches Feld fuer das Einsetzten
	// Der Gegenstand kann nur ausserhalb des Kampfes eingesetzt werden.
	public static final byte NURAUSSERHALB = 2;
	// Der Gegenstand kann sowohl im Kampf als auch ausserhalb verwendet werden.
	public static final byte UEBERALL = 6;
	// Der Gegenstand kann nur im Kampf verwendet werden.
	public static final byte NURKAMPF = 3;
	
	// Der Effekt des Gegenstands.
	private Effekt effekt;
	// Die Faehigkeit des Gegenstands.
	private Faehigkeit faehigkeit;
	// Wo ist der Gegenstand ueberall einsetzbar.
	private byte einsetzbar;

	/**
	 *  Ein neuer verwendbarer Gegenstand wird wie einen Gegenstand erstellt, nur wird noch ein Effekt uebergeben und, ob er im Kampf einsetzbar ist.
	 *  effekt: der Effekt des Gegenstands.
	 *  einsetzbar: gibt an, wo der Gegenstand einsetzbar ist.
	 */
	public VerwendbarerGegenstand(String[] namenGegenstand, String plural, NumerusGenus numerusGenus, String beschreibung, Effekt effekt, byte einsetzbar) {
	    super(namenGegenstand, plural, numerusGenus, beschreibung);
	    this.effekt = effekt;
	    this.faehigkeit = null;
	    this.einsetzbar = einsetzbar;
	}
	  
	/**
	 *  Ein neuer verwendbarer Gegenstand wird wie einen Gegenstand erstellt, nur wird noch eine Faehigkeit uebergeben und, ob er im Kampf einsetzbar ist.
	 *  faehigkeit: die faehigkeit des Gegenstands.
	 *  einsetzbar: gibt an, wo der Gegenstand einsetzbar ist.
	 */
	public VerwendbarerGegenstand(String[] namenGegenstand, String plural, NumerusGenus numerusGenus, String beschreibung, Faehigkeit faehigkeit, byte einsetzbar) {
	    super(namenGegenstand, plural, numerusGenus, beschreibung);
	    this.faehigkeit = faehigkeit;
	    this.effekt = null;
	    this.einsetzbar = einsetzbar;
	}
	  
	// Gibt den Effekt des Gegenstands zurueck.
	public Effekt getEffekt() {
		return effekt;
	}
	// Gibt die Faehhigkeit des Gegenstands zurueck.
	public Faehigkeit getFaehigkeit() {
	    return faehigkeit;
	}
	// Gibt true zurueck, wenn der Gegenstand einen Effekt hat.
	public boolean hasEffekt() {
	    if(effekt != null) return true;
	    else return false;
	}
	// Gibt true zurueck, wenn der Gegenstand nicht nur ausserhalb einsetzbar ist.
	public boolean istEinsetzbarKampf() {
		if(einsetzbar % NURKAMPF == 0) return true;
		else return false;
	}
	// Gibt true zurueck, wenn der Gegenstand nicht nur im Kampf einsetzbar ist.
	public boolean istEinsetzbarAusserhalb() {		
		if(einsetzbar % NURAUSSERHALB == 0) return true;
		else return false;
	}
	
	
	
	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "verwendbarer Gegenstand"
	 */
	public String getGegenstandsart() {
		return "verwendbarer Gegenstand";
	}
	
}