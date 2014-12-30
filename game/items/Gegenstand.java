package game.items;

import game.entity.Attribut;
import game.entity.EntityAttribut;
import game.entity.EntityResistenz;
import game.entity.Resistenz;
import game.entity.Schadensart;

import java.io.Serializable;
import java.util.Vector;

import util.IPrintable;
import util.NumerusGenus;

/**
 * Ein Gegenstand mit ein paar grundlegenden Eigenschaften.
 */
public class Gegenstand implements Serializable, IPrintable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	/* --- Das statische Array --- */

	// Ein statisches Array mit allen Gegenstaenden.
	public static Gegenstand[] GEGENSTAENDE = new Gegenstand[0];
	
	/* --- Die Variablen --- */
	
	// Der Name des Gegenstands.
	private String name;
	// Der Plural des Gegenstands.
	private String plural;
	// Der Genus und Numerus addiert.
	private NumerusGenus numGen;
	// Alle Synonyme fuer den Gegenstand.
	protected String[] synonyme;
	// Die Beschreibung des Gegenstands.
	private String beschreibung;
	// Die ID des Gegenstands.
	private short id;
	
	/* --- Die Variablen fuer alle Subklassen --- */	
	
	// Der Lebenspunktebonus des Gegenstands.
	protected int lp;	
	// Der Magiepunktebonus des Gegenstands.
	protected int mp;
	
	// Alle Statuswerte und Attribute in einem Array.
	protected EntityAttribut[] attribute;
	// Alle Resistenzen des Gegenstands.
	protected EntityResistenz[] resistenzen;
	  
	/* --- Der Konstruktor --- */
	
	/**
	 * Ein neuer Gegenstand wird erstellt und ihm wird ein Name und Synonyme gegeben, ein NumerusGenus und eine Beschreibung.
	 * @param namenGegenstand Der erste Eintrag ist der Name, alle weiteren sins Synonyme.
	 * @param numerusGenus Der NumerusGenus diese Gegenstands.
	 * @param beschreibung Die Beschreibung fuer diesen Gegenstand.
	 */
	public Gegenstand(String[] namenGegenstand, String plural, NumerusGenus numerusGenus, String beschreibung) {
		this.name = namenGegenstand[0];
		this.plural = plural;
	    this.synonyme = namenGegenstand;
	    synonyme[0] = this.getName();
	    this.numGen = numerusGenus;
	    this.beschreibung = beschreibung;
	    
	    attribute = new EntityAttribut[Attribut.getMaxId()];
	    for(int i = 0; i < Attribut.getMaxId(); i++)
	    	attribute[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], 0);
	    resistenzen = new EntityResistenz[Resistenz.getMaxId()];
	    for(int i = 0; i < Resistenz.getMaxId(); i++)
	    	resistenzen[i] = new EntityResistenz(Resistenz.RESISTENZEN[i], 0.0f);
	    
	    Gegenstand[] g = GEGENSTAENDE;
	    GEGENSTAENDE = new Gegenstand[g.length + 1];
	    
	    for(short i = 0; i < GEGENSTAENDE.length && i < Short.MAX_VALUE; i++) {
	    	if(g.length > i) 
	    		GEGENSTAENDE[i] = g[i];
			if(GEGENSTAENDE[i] == null) {
				this.id = i;
				GEGENSTAENDE[id] = this;
				break;
			}
		}
	}
	  
	/* --- Die Methoden --- */
	
	/**
	 * Diese Methode aendert den Namen des Gegenstands.
	 * @param neuerName Der neue Name fuer den Gegenstand.
	 */
	public void setName(String neuerName) {
	    name = neuerName;
	}
	  
	/**
	 * Diese Methode gibt den Namen des Gegenstands zurueck, OHNE Modifikatoren.
	 * @return Der Name des Gegenstands.
	 */
	@Override
	public String getName() {
		// Subklassen koennen hier noch genauer bestimmt werden.
		if(name.contains("<c=") || name.contains("<p=")) {
			String actual = name.replaceAll("</c>", "");
			while(actual.contains("<") && actual.contains(">"))
				actual = actual.substring(0, actual.indexOf("<")) + actual.substring(actual.indexOf(">") + 1);
			return actual;
		}
	    return name;
	}
	/**
	 * Gibt den Plural des Gegenstands zurueck.
	 * @return Den Plural des Gegenstands.
	 */
	public String getPlural() {
		if(plural.contains("<c=") || plural.contains("<p=")) {
			String actual = plural.replaceAll("</c>", "");
			while(actual.contains("<") && actual.contains(">"))
				actual = actual.substring(0, actual.indexOf("<")) + actual.substring(actual.indexOf(">") + 1);
			return actual;
		}
	    return plural;
	}
	/**
	 * Gibt den Namen des Gegenstands MIT den Modifikatoren zurueck.
	 * @return Der erweiterte Name des Gegenstands.
	 */
	@Override
	public String getNameExtended() {
		return name;
	}
	/**
	 * Gibt den Plural des Gegenstandes MIT den Modifikatoren zurueck.
	 * @return Den erweiterten Plural des Gegenstandes.
	 */
	public String getPluralExtended() {
		return plural;
	}
	/**
	 * Diese Methode aendert die Beschreibung des Gegenstands.
	 * @param neueBeschreibung Die neue Beschreibung fuer den Gegenstand.
	 */
	public void setBeschreibung(String neueBeschreibung) {
	    beschreibung = neueBeschreibung;
	}
	  
	/**
	 * Diese Methode gibt die Beschreibung des Gegenstands zurueck.
	 * @return Die Beschreibung des Gegenstands.
	 */
	@Override
	public String getDescription() {
		// Subklassen koennen hier noch genauer bestimmt werden.
		return beschreibung;
	}
	  
	/**
	 * Diese Methode ueberprueft, ob es sich um ein Synonym fuer den Name des Gegenstands handelt.
	 * @param synonym Das Synonym, welches ueberprueft werden soll.
	 * @return Gibt zurueck, ob es sich um ein Synonyme handelt.
	 */
	public boolean isSynonym(String synonym) {
	    for(String s : synonyme) {
	    	if(s.equalsIgnoreCase(synonym)) 
	    		return true;
	    }
	    return false;
	}
	  
	/**
	 * Diese Methode gibt den NumerusGenus des Gegenstands zurueck.
	 * @return Gibt den NumerusGenus des Gegenstands zurueck.
	 */
	public NumerusGenus getNumGen() {
	    return numGen;
	}

	/**
	 * Gibt den Parameter fuer einen Byte-Wert zurueck.
	 * @param param Der Wert des Parameters.
	 * @return Der String fuer den Parameter.
	 */
	@Override
	public String getParam(String param) {
		// Alle Parameter muessen von Hand int getParams() registriert werden!
		switch(param) {
		case "lp": return Integer.toString(lp);
		case "mp": return Integer.toString(mp);
		}
		for(EntityAttribut ea : attribute)
			if(ea.getAttribut().getParam().equals(param))
				return Integer.toString(ea.getWert());
		for(EntityResistenz er : resistenzen)
			if(er.getResistenz().getParam().equals(param))
				return Float.toString(er.getWert());
		return "";
	}

	/**
	 * Gibt alle Parameter des Gegenstands zurueck.
	 * @return Alle Parameter.
	 */
	@Override
	public String[] getParams() {
		Vector<String> s = new Vector<String>();
		s.add("lp");
		s.add("mp");
		for(EntityAttribut ea : attribute)
			s.add(ea.getAttribut().getParam());
		for(EntityResistenz er : resistenzen)
			s.add(er.getResistenz().getParam());
		return s.toArray(new String[0]);
	}

	/**
	 * Gibt die ID des Gegenstands zurueck.
	 * @return Die ID des Gegenstands.
	 */
	public short getId() {
		return id;
	}

	/* --- Die statischen Methoden --- */
	
	/**
	 * Gibt einen Gegenstand basierend auf seinem Namen zurueck.
	 * @param name Der Name des Gegenstands, der gesucht wird.
	 * @retun Der entsprechende Gegenstand fuer den Namen.
	 */
	public static Gegenstand getGegenstand(String name) {
		for(Gegenstand g : GEGENSTAENDE) {
			if(g.isSynonym(name))
				return g;
		}
	    return null;
	}
	/**
	 * Gibt einen Gegenstand basierend auf seiner ID zurueck.
	 * @param id Die ID des Gegenstands.
	 * @return Der gesuchte Gegenstand, null falls es keinen solchen Gegenstand gibt.
	 */
	public static Gegenstand getGegenstand(short id) {
		if(GEGENSTAENDE.length < id)
			return null;
		return GEGENSTAENDE[id];
	}
	  
	/* --- Die Methoden fuer die Subklassen --- */
	
	/**
	 * Gibt den Wert eines Attributs basierend auf dem Namen oder des Parameters zurueck.
	 * @param nameOderParam Der Name oder die Parameterschreibweise des Attributs.
	 * @return Den Wert des gesuchten Attributs und -1, wenn es dieses nicht gibt.
	 */
	public int getAttribut(String nameOderParam) {
		for(EntityAttribut ea : attribute)
			if(ea.getAttribut().getName().equals(nameOderParam) || ea.getAttribut().getParam().equals(nameOderParam))
				return ea.getWert();
		return -1;
	}
	/**
	 * Gibt den Wert einer Resistenz basierend auf dem Namen oder des Parameters zurueck.
	 * @param nameOderParam Der Name oder die Parameterschreibweise der Resistenz.
	 * @return Den Wert der gesuchten Resistenz und -1.0, wenn es diese nicht gibt.
	 */
	public float getResistenz(String nameOderParam) {
		for(int i = 0; i < Resistenz.getMaxId(); i++)
			if(Resistenz.RESISTENZEN[i].getName().equals(nameOderParam) || Resistenz.RESISTENZEN[i].getParam().equals(nameOderParam))
				return resistenzen[i].getWert();
		return -1.0f;
	}
	/**
	 * Fuegt dem Gegenstand eine Resistenz hinzu, die den Traeger vor der uebergebenen Schadensart schuetzt.
	 * @param schadensart Die Schadensart, vor der der Gegenstand schuetzt.
	 * @param wert Die prozentuale Reduktion des Schadens.
	 * @return Sich selbst.
	 */
	public Gegenstand addResistenz(Schadensart schadensart, float wert) {
		for(EntityResistenz er : resistenzen)
			if(er != null && er.getResistenz().getSchadensart().equals(schadensart)) {
				er.addWert(wert);
				return this;
			}		
		return this;
	}
	
	/**
	 * Gibt die zusaetzlichen Lebenspunkte des Gegenstands zurueck.
	 * @return Die zusaetzlichen Lebenspunkte fuer den Traeger.
	 */
	public int getLp() { return lp; }
	/**
	 * Gibt die zusaetzlichen Magiepunkte des Gegenstands zurueck.
	 * @return Die zusaetzlichen Magiepunkte fuer den Traeger.
	 */
	public int getMp() { return mp; }
	
	/**
	 * Gibt das gesamte Attributsarray zurueck.
	 * @return Ein Array mit allen Attributen.
	 */
	public EntityAttribut[] getAttribute() { return attribute; }
	
	/**
	 * Gibt das gesmate Resistenzenarray zurueck.
	 * @return Ein Array mit allen Resistenzen.
	 */
	public EntityResistenz[] getResistenzen() { return resistenzen; }
	
	/**
	 *  Gibt zurueck, ob der Gegenstand Statuswerte hat oder nicht.
	 *  @return gibt wahr zurueck, falls der Gegenstand Statuswerte hat.
	 */
	public boolean hasWerte() {
		if(lp != 0 || mp != 0)
			return true;
		for(EntityAttribut ea : attribute)
			if(ea.getWert() != 0)
				return true;
		for(EntityResistenz er : resistenzen)
			if(er.getWert() != 0)
				return true;
		return false;
	}
	
	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "Gegenstand"
	 */
	public String getGegenstandsart() {
		return "Gegenstand";
	}
	
	/**
	 * Die toString()-Methode eines Gegenstands liefert dessen Namen als String.
	 * @return Der Name dieses Gegenstands.
	 */
	@Override
	public String toString() {
		return getName();
	}
	
}
