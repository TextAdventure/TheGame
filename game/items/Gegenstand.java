package game.items;

import java.io.Serializable;
import java.util.Vector;

import util.IPrintable;
import util.NumerusGenus;

/**
 * Ein Gegenstand mit ein paar grundlegenden Eigenschaften.
 */
public class Gegenstand implements Serializable, IPrintable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- statische Liste --- */

	// Ein statisches Array mit allen Gegenstaenden.
	public static Gegenstand[] GEGENSTAENDE = new Gegenstand[0];
	
	/* --- Variablen --- */
	
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
		  
	/* --- Konstruktor --- */
	
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
	  
	/* --- Methoden --- */
	  
	/**
	 * Diese Methode gibt den Namen des Gegenstands zurueck, OHNE Modifikatoren.
	 * @return Der Name des Gegenstands.
	 */
	@Override
	public String getName() {
		// Es werden nur Farben ersetzt, da von mehr im Moment nicht auszugehen ist.
		String actual = name;
		while(actual.contains("<c=")) {
			actual = actual.replaceFirst("</c>", "");
			actual = actual.substring(0, actual.indexOf("<")) + actual.substring(actual.indexOf(">") + 1);
		}
		return actual;
	}
	/**
	 * Gibt den Plural des Gegenstands zurueck.
	 * @return Den Plural des Gegenstands.
	 */
	public String getPlural() {
		// Es werden nur Farben ersetzt, da von mehr im Moment nicht auszugehen ist.
		String actual = plural;
		while(actual.contains("<c=")) {
			actual = actual.replaceFirst("</c>", "");
			actual = actual.substring(0, actual.indexOf("<")) + actual.substring(actual.indexOf(">") + 1);
		}
		return actual;
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
	 *
	public void setBeschreibung(String neueBeschreibung) {
	    beschreibung = neueBeschreibung;
	}*/
	  
	/**
	 * Diese Methode gibt die Beschreibung des Gegenstands zurueck.
	 * @return Die Beschreibung des Gegenstands.
	 */
	@Override
	public String getBeschreibung() {
		// Subklassen koennen hier noch genauer definiert werden.
		return beschreibung;
	}
	  
	/**
	 * Ueberprueft, ob es sich um ein Synonym fuer den Name des Gegenstands handelt.
	 * @param synonym Das Synonym, welches ueberprueft werden soll.
	 * @return True, falls es sich um ein Synonym handelt, ansonsten false.
	 */
	public boolean isSynonym(String synonym) {
	    for(String s : synonyme) {
	    	if(s.equalsIgnoreCase(synonym)) 
	    		return true;
	    }
	    return false;
	}
	  
	/**
	 * Gibt den NumerusGenus des Gegenstands zurueck.
	 * @return Gibt den NumerusGenus des Gegenstands zurueck.
	 */
	public NumerusGenus getNumGen() {
	    return numGen;
	}

	/**
	 * Gibt den ausgeschriebenen Parameter fuer einen Parameter zurueck, dies koennen verschieden Eigenschaften des Gegenstandes sein.
	 * @param param Der geuschte Parameter.
	 * @return Der ausgeschriebene Wert fuer den Parameter.
	 */
	@Override
	public String getParam(String param) {
		if(param == "name") return this.getNameExtended();
		if(param == "plural") return this.getPlural();
		if(param == "numerusGenus") return this.getNumGen().toString();
		if(param == "id") return Short.toString(this.getId());
		return "Ungültig";
	}

	/**
	 * Gibt alle Parameter des Gegenstands zurueck.
	 * @return Eine Liste mit allen gueltigen Parametern.
	 */
	@Override
	public String[] getParams() {
		Vector<String> s = new Vector<String>();
		s.add("name");
		s.add("plural");
		s.add("numerusGenus");
		s.add("id");
		return s.toArray(new String[0]);
	}

	/**
	 * Gibt die ID des Gegenstands zurueck.
	 * @return Die ID des Gegenstands.
	 */
	public short getId() {
		return id;
	}
	
	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "Gegenstand"
	 */
	public String getGegenstandsart() {
		return "Gegenstand";
	}

	/* --- statische Methoden --- */
	
	/**
	 * Gibt einen Gegenstand basierend auf seinem Namen zurueck.
	 * @param name Der Name des Gegenstands, der gesucht wird.
	 * @retun Der entsprechende Gegenstand fuer den Namen.
	 */
	public static Gegenstand getGegenstand(String name) {
		for(Gegenstand g : GEGENSTAENDE)
			if(g.isSynonym(name))
				return g;
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
	
	/**
	 * Gibt die Liste mit allen Gegenstaenden zurueck, wird benoetigt, um die Gegenstaende zu speichern und zu laden,
	 * @return Die Liste mit allen Gegenstaenden.
	 */
	public static Gegenstand[] getAlleGegenstaende() {
		return GEGENSTAENDE;
	}
	
	/**
	 * Legt die Liste aller Gegenstaende fest, wird benoetigt um die Gegenstaende zu speichern und zu laden.
	 * @param gegenstaende Die neue Liste aller Gegenstaende.
	 */
	public static void setAlleGegenstaende(Gegenstand[] gegenstaende) {
		GEGENSTAENDE = gegenstaende;
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
