package game;

import java.io.Serializable;
import java.util.Vector;

import util.NumerusGenus;

public class Gegenstand implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Der Name des Gegenstands.
	private String name;
	// Der Genus und Numerus addiert.
	private NumerusGenus numGen;
	// Alle Synonyme fuer den Gegenstand.
	private String[] synonyme;
	// Die Beschreibung des Gegenstands.
	private String beschreibung;
	  
	// Liste aller Gegenstaende im Spiel.
	private static Vector<Gegenstand> gegenstaende = new Vector<Gegenstand>();
	  
	// Diese Variablen werden fuer Subklassen(Ausruestung) benoetigt.
	// Die zusaetzlichen Lebenspunkte
	protected int lp;
	// Die zusaetzlichen Magiepunkte
	protected int mp;
	  
	// Der Angriff des Gegenstands
	protected int ang;
	// Die Verteidigung des Gegenstands
	protected int def;
	// Der magische Angirff des Gegenstands
	protected int magAng;
	// Die magische Verteidigung des Gegenstands
	protected int magDef;
	// Die Praezision des Gegenstands
	protected int prz;
	// Die Flinkheit des Gegenstands
	protected int flk;
	  
	/**
	 *  Ein neuer Gegenstand wird mithilfe eines Namens initialisiert.
	 */
	public Gegenstand(String[] namenGegenstand, NumerusGenus numerusGenus, String beschreibung){
		name = namenGegenstand[0];
	    synonyme = namenGegenstand;
	    numGen = numerusGenus;
	    this.beschreibung = beschreibung;
	    Gegenstand.addGegenstand(this);
	}
	  
	/**
	 *  Diese Methode aendert den Namen des Gegenstands.
	 *  neuerName: der neue Name fuer den Gegenstand.
	 */
	public void setName(String neuerName){
	    name = neuerName;
	}
	  
	/**
	 *  Diese Methode gibt den Namen des Gegenstands zurueck.
	 */
	public String getName(){
	    return name;
	}
	  
	/**
	 *  Diese Methode aendert die Beschreibung des Gegenstands.
	 *  neueBeschreibung: die neue Beschreibung fuer den Gegenstand.
	 */
	public void setBeschreibung(String neueBeschreibung){
	    beschreibung = neueBeschreibung;
	}
	  
	/**
	 *  Diese Methode gibt die Beschreibung des Gegenstands zurueck.
	 */
	public String getBeschreibung(){
	    return beschreibung;
	}
	  
	/**
	 *  Diese Methode ueberprueft, ob es sich um ein Synonym fuer den Gegenstand handelt.
	 *  synonym: das Synonym, welches ueberprueft werden soll.
	 */
	public boolean isSynonym(String synonym){
	    for(int i = 0; i < synonyme.length; i++){
	    	if(synonyme[i].equalsIgnoreCase(synonym)) return true;
	    }
	    return false;
	}
	  
	/**
	 *  Diese Methode gibt den Numerus/Genus des Gegenstands zurueck.
	 */
	public NumerusGenus getNumGen(){
	    return numGen;
	}

	  
	// STATISCHE METHODEN
	/**
	 *  Gibt einen Gegenstand basierend auf seinem Namen zurueck.
	 *  name: der Name des Gegenstands.
	 */
	public static Gegenstand getGegenstand(String name){
		for(Gegenstand g: gegenstaende.toArray(new Gegenstand[0])){
			if(g.isSynonym(name)) return g;
		}
	    return null;
	}
	  
	/**
	 *  Diese Methode fuegt einen neu erstellten Gegenstand der Liste aller Gegenstaende hinzu.
	 *  gegenstand: der neue Gegenstand.
	 */
	private static void addGegenstand(Gegenstand gegenstand){
		gegenstaende.add(gegenstand);
	}
	  
	/**
	 *  Diese Methode uebergibt eine Liste mit allen Gegenstaeden im Spiel.
	 *  liste: die Liste der Gegenstaende.
	 */
	public static void setListe(Vector<Gegenstand> liste){
	    gegenstaende = liste;
	}
	  
	// METHODEN FUER ALLE SUBKLASSEN.
	  
	// Gibt die zusaetzlichen Lebenspunkte des Gegenstands zurueck.
	public int getLp(){return lp;}
	// Gibt die zusaetzlichen Magiepunkte des Gegenstands zurueck.
	public int getMp(){return mp;}
	// Gibt den Angirffswert des Gegenstands zurueck.
	public int getAng(){return ang;}
	// Gibt den Verteidigungswert des Gegenstands zurueck.
	public int getDef(){return def;}
	// Gibt den magischen Angirffswert des Gegenstands zurueck.
	public int getMagAng(){return magAng;}
	// Gibt den magischen Verteidigungswert des Gegenstands zurueck.
	public int getMagDef(){return magDef;}
	// Gibt den Praezisionswert des Gegenstands zurueck.
	public int getPrz(){return prz;}
	// Gibt den Flinkheitswert des Gegenstands zurueck.
	public int getFlk(){return flk;}
	
	/**
	 *  Gibt zurueck, ob der Gegenstand Statuswerte hat oder nicht.
	 */
	public boolean hasWerte(){
		if(lp != 0 || mp != 0 || ang != 0 || def != 0 || magAng != 0 || magDef != 0 || prz != 0 || flk != 0) return true;
	    else return false;
	}
}