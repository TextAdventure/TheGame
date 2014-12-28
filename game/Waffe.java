package game;

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
	  
	// Statische Variablen fuer alle Waffentypen.
	// Die Schild-Variable
	public static final byte SCHILD = -1;
	// Die Schwert-Variable
	public static final byte SCHWERT = 0;
	  
	// Die Namen der Waffentypen.
	private static final String[] WAFFENARTEN = new String[]{
		"Schild",
	    "Schwert"
	};
	  
	// Die Hand, in der die Waffe gefuehrt wird.
	private byte hand;
	// Die Art der Waffe.
	private byte waffenart;
	/**
	 *  Eine neue Waffe ueberschreibt den Konstruktor von Gegenstand und erweitert ihn.
	 */
	public Waffe(String[] namenGegenstand, NumerusGenus numerusGenus, String beschreibung, int lebenspunkte, int magiepunkte, int angriff, int verteidigung, int magAngriff, int magVerteidigung, int praezision, int flinkheit, byte hand, byte art){
	    super(namenGegenstand, numerusGenus, beschreibung);
	    this.lp = lebenspunkte;
	    this.mp = magiepunkte;
	    this.ang = angriff;
	    this.def = verteidigung;
	    this.magAng = magAngriff;
	    this.magDef = magVerteidigung;
	    this.prz = praezision;
	    this.flk = flinkheit;
	    
	    this.hand = hand;
	    this.waffenart = art;
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
	public byte getWaffenart(){
	    return waffenart;
	}
	  
	/**
	 *  Diese statische Methode gibt den Namen einer Waffenart zurueck, basierend auf der Waffenart.
	 */
	public static String getWaffenartNamen(byte waffenart){
	    return WAFFENARTEN[waffenart + 1];
	}
}