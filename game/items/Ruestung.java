package game.items;

import game.entity.Attribut;
import game.entity.EntityAttribut;
import util.NumerusGenus;

/**
 *  Diese Klasse repraesentiert alle Ruestungsteile fuer den Spieler.
 */
public class Ruestung extends Gegenstand {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	/* --- Die statischen Konstanten --- */
	
	// Der Wert fuer einen Helm.
	public static final byte HELM = 0;
	// Der Wert fuer einen Brustpanzer.
	public static final byte BRUSTPANZER = 1;
	// Der Wert fuer Handschuhe.
	public static final byte HANDSCHUHE = 2;
	// Der Wert fuer eine Hose.
	public static final byte HOSE = 3;
	// Der Wert fuer Schuhe.
	public static final byte SCHUHE = 4;
	  
	/* --- Die Variable --- */  
	
	// Der Typ der Ruestung.
	private byte typ;
	  
	/* --- Der Konstruktor --- */
	
	/**
	 * Eine neue Ruestung wird wie ein Gegenstand erstellt, aber es werden noch die Attributsboni uebergeben.
	 * @param nameRuestung Der Name der Ruestung.
	 * @param numerusGenus Der Numerus und Genus.
	 * @param beschreibung Die Beschreibung fuer diese Ruestung.
	 * @param typ Der Typ dieser Ruestung.
	 */
	public Ruestung(String[] nameRuestung, String plural, NumerusGenus numerusGenus, String beschreibung, byte typ, int lebenspunkte, int magiepunkte,
			int... attributswerte) {		
	    super(nameRuestung, plural, numerusGenus, beschreibung);
	    this.typ = typ;
	    
	    this.lp = lebenspunkte;
	    this.mp = magiepunkte;
	    for(int i = 0; i < Attribut.getMaxId(); i++)
			attribute[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], attributswerte[i]);
	}
	  
	/* --- Die Methode --- */
	
	/**
	 * Gibt den Typ der Ruestung zurueck.
	 * @return Den Typ dieser Rustung.
	 */
	public byte getTyp() {
	    return typ;
	}
	
	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "Ruestung"
	 */
	public String getGegenstandsart() {
		return "Rüstung";
	}
	
	/* --- Die statische Methode --- */
	
	/**
	 *  Diese statische Methode gibt entsprechend des Typs eines Ruestungsteils, den Namen dieses Typs zurueck.
	 *  typ: der Typ der Ruestung.
	 */
	public static String getTypNamen(byte typ) {
		switch(typ) {
	      	case(HELM): return "Helm";
	      	case(BRUSTPANZER): return "Brustpanzer";
	      	case(HANDSCHUHE): return "Handschuhe";
	      	case(HOSE): return "Hose";
	      	case(SCHUHE): return "Schuhe";
	      	default: return "";
	    }
	}
	
}