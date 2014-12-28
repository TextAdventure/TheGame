package game;

/**
 *	Diese Klasse fuehrt einen Effekt im Kampf aus.
 */
public class KampfEffekt {
	// Der Effekt.
	private Effekt effekt;
	// Die Dauer des Effekts.
	private int anfangsDauer;
	private int aktuelleDauer;
	// Der Bonus des Effekts.
	private int bonus;
	  
	/**
	 *  Eine neue Statusveraenderung wird mit einem Effekt und einer Dauer erstellt.
	 *  effekt: der Effekt der Statusveraenderung.
	 *  dauer: die Dauer der Veraenderung in Runden.
	 */
	public KampfEffekt(Effekt effekt){
	    this.effekt = effekt;
	    anfangsDauer = effekt.getDauer() + 1;// <-- Nach der ersten Auswertung wird der Wert dekrementiert.
	    aktuelleDauer = anfangsDauer;
	}
	  
	/**
	 *  Diese Methode gibt den reinen Bonus des Effekts  zurueck.
	 *  wert: der aktuelle Wert des Attributs.
	 */
	public int getBonus(int wert){
		if(aktuelleDauer == anfangsDauer){
			bonus = effekt.getBonus(wert);
			aktuelleDauer--;
			return bonus;
	    }else{
	    	aktuelleDauer--;
	    	if(aktuelleDauer < 1) return -bonus;
	    	else return 0;
	    }
	}
	  
	/**
	 *  Aus dem maximalen LP/MP-Wert wird der Bonus berechnet und der Bonus, der noch addiert werden muss, wird zurueckgegeben.
	 *  maxWert: der maximale Wert der LP/MP.
	 */
	public int getBonusLpMp(int maxWert){
	    bonus = effekt.getBonus(maxWert);
	    aktuelleDauer--;
	    if(aktuelleDauer < 1) return 0;
	    else return bonus;
	}
	  
	// Gibt den Typ des Effekts zurueck.
	public byte getTyp(){
	    return effekt.getTyp();
	}
	  
	// PROVISORIUM: Gibt die verbleibende Dauer zurueck.
	public int getDauer(){
		return aktuelleDauer;
	}
}