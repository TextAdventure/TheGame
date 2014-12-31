package game.items;

import game.entity.Attribut;
import game.entity.EntityAttribut;
import util.NumerusGenus;

/**
 * Ein ausruestbares Accessoire, das Attributsboni und Resistenzen fuer den Traegergibt.
 * @author Marvin
 */
public class Accessoire extends Gegenstand {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- statische Konstanten --- */
	
	// Der Wert fuer ein Amulett.
	public static final byte AMULETT = 0;
	// Der Wert fuer einen Rueckengegenstand.
	public static final byte RUECKEN = 1;
	// Der Wert fuer einen Guertel.
	public static final byte GUERTEL = 2;
	// Der Wert fuer einen Ring.
	public static final byte RING = 3;		// Hat auch noch Platz 4
	// Der Wert fuer ein Armband.
	public static final byte ARMBAND = 5;	// Hat auch noch Platz 6
	
	/* --- Variable --- */
	
	// Der Typ des Accesoires.
	private byte typ;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein neues Accessoire wir mit Namen, NumerusGenus, Beschreibung, einem Typ und den Attributsboni erstellt.
	 * @param namenAccessoire Der Name fuer dieses Accessoire.
	 * @param numerusGenus Der Numerus und Genus.
	 * @param beschreibung Die Beschreibung fuer dieses Accessoire.
	 * @param typ Der Typ dieses Accessoires.
	 */
	public Accessoire(String[] namenAccessoire, String plural, NumerusGenus numerusGenus, String beschreibung, byte typ, int lebenspunkte, int magiepunkte,
			int... attributswerte) {		
		super(namenAccessoire, plural, numerusGenus, beschreibung);
	    this.typ = typ;
	    
	    this.lp = lebenspunkte;
	    this.mp = magiepunkte;
	    for(int i = 0; i < Attribut.getMaxId(); i++)
			attribute[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], attributswerte[i]);
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt den Typ des Accessoires zurueck.
	 * @return Den Typ des Accessoires.
	 */
	public byte getTyp() {
		return typ;
	}

	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "Accessoire"
	 */
	public String getGegenstandsart() {
		return "Accessoire";
	}
	
	/* --- statische Methode --- */
	
	/**
	 * Gibt den Namen eines Typs basierend auf dem byte-Wert zurueck.
	 * @param typ Der Typ eines Accessoires als byte-Wert.
	 * @return Der Name dieses Typs als Wort.
	 */
	public static String getTypNamen(byte typ) {
		switch(typ) {
			case(AMULETT): return "Amulett";
			case(RUECKEN): return "Rücken";
			case(GUERTEL): return "Gürtel";
			case(RING): return "Ring";
			case(RING + 1): return "Ring";
			case(ARMBAND): return "Armband";
			case(ARMBAND + 1): return "Armband";
			default: return "";
		}
	}	
	
}
