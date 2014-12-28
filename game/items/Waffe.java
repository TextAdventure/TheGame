package game.items;

import game.entity.Attribut;
import game.entity.EntityAttribut;
import util.NumerusGenus;

/**
 *  Die Klasse repraesentiert eine Waffe im Spiel.
 */
public class Waffe extends Gegenstand {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	// Statische Variablen fuer die verschiedenen Arten des Haltens.
	// Variable fuer Schwerthand
	public static final byte SCHWERTHAND = 0;
	// Variable fuer Schildhand
	public static final byte SCHILDHAND = 1;
	// Variable fuer eine der beiden Haende
	public static final byte BEIDHAENDIG = 2;
	// Variable fuer einen Zweihaender
	public static final byte ZWEIHAENDIG = 3;	
	
	// Der Namen der Waffenhaende.
	private static final String[] WAFFENHAND = new String[] {
		"Schwerthand",
		"Schildhand",
		"Beide Hände",
		"Zweihändig"
	};
	
	
	// Die Hand, in der die Waffe gefuehrt wird.
	private byte hand;
	
	// Die Art der Waffe.
	private Waffenart waffenart;
	/**
	 *  Eine neue Waffe ueberschreibt den Konstruktor von Gegenstand und erweitert ihn.
	 */
	public Waffe(String[] namenGegenstand, String plural, NumerusGenus numerusGenus, String beschreibung, byte hand, Waffenart art, int lebenspunkte, int magiepunkte,
			int... attributswerte){
	    super(namenGegenstand, plural, numerusGenus, beschreibung);
	    this.hand = hand;
	    this.waffenart = art;
	    
	    this.lp = lebenspunkte;
	    this.mp = magiepunkte;
	    for(int i = 0; i < Attribut.getMaxId(); i++)
			attribute[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], attributswerte[i]);
	}
	  
	/**
	 *  Diese Methode gibt die Hand zurueck, in der die Waffe gefuehrt wird.
	 */
	public byte getHand(){
		return hand;
	}
	  
	/**
	 *  Diese Methode gibt die Waffenart zurueck.
	 */
	public Waffenart getWaffenart(){
	    return waffenart;
	}
	
	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "Waffe"
	 */
	public String getGegenstandsart() {
		return "Waffe";
	}
	
	/* --- ueberschriebene Methoden --- */
	
	@Override
	public String getParam(String param) {
		String sup = super.getParam(param);
		if(sup != "")
			return param;
		switch(param) {
		case "hand": return Waffe.getWaffenhandNamen(this.hand);
		case "art": return this.waffenart.getName();
		}		
		return "";
	}
	 
	/* --- Die statischen Methoden --- */
	
	public static String getWaffenhandNamen(byte hand) {
		return WAFFENHAND[hand];
	}
}