package game.items;

import java.util.Vector;

import util.NumerusGenus;

/**
 * Eine Ruestung kann ausgeruestet werden und verbessert die Attribute eines Spielers.
 * @author Marvin
 */
public class Ruestung extends AusruestbarerGegenstand {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	/* --- statische Konstanten --- */
	
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
	  
	/* --- Variablen --- */  
	
	// Der Typ der Ruestung.
	private byte typ;
	  
	/* --- Konstruktor --- */
	
	/**
	 * Eine neue Ruestung wird wie ein Gegenstand erstellt, aber es werden noch die Attributsboni uebergeben.
	 * @param nameRuestung Der Name der Ruestung.
	 * @param plural Der Plural der Ruestung.
	 * @param numerusGenus Der Numerus und Genus.
	 * @param beschreibung Die Beschreibung fuer diese Ruestung.
	 * @param typ Der Typ dieser Ruestung und in welchem Ausruestungsplatz sie getragen werden kann.
	 * @param attributswerte Die Attributsboni beim Tragen dieser Ruestung.
	 */
	public Ruestung(String[] nameRuestung, String plural, NumerusGenus numerusGenus, String beschreibung, byte typ,	int... attributswerte) {		
	    super(nameRuestung, plural, numerusGenus, beschreibung);
	    this.typ = typ;
	    
	    attribute.addAlleWerte(attributswerte);
	}
	  
	/* --- Methoden --- */
	
	/**
	 * Gibt den Typ der Ruestung zurueck.
	 * @return Den Typ dieser Rustung, diese werden durch die Konstanten in dieser Klasse festgelegt: HELM, BRUSTPANZER, HANDSCHUHE, HOSE, SCHUHE.
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
	
	@Override
	public String getParam(String param) {
		if(super.getParam(param) != "Ungültig")
			return super.getParam(param);
		
		if(param == "typ") return Ruestung.getTypNamen(this.getTyp());
		
		return "Ungültig";
	}
	
	@Override
	public String[] getParams() {
		Vector<String> s = new Vector<String>();
		
		for(String param : super.getParams())
			s.add(param);
		
		s.add("typ");
		
		return s.toArray(new String[0]);
	}
	
	/* --- statische Methode --- */
	
	/**
	 *  Diese statische Methode gibt entsprechend des Typs eines Ruestungsteils, den Namen dieses Typs zurueck.
	 *  typ: der Typ der Ruestung.
	 */
	/**
	 * Gibt den ausgeschriebenen Namen des Typs einer Ruestung zurueck.
	 * @param typ Eine der statischen Konstanten in dieser Klasse: HELM, BRUSTPANZER, HANDSCHUHE, HOSE, SCHUHE.
	 * @return Deb ausgeschrieben Namen des Typs der Ruestung.
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