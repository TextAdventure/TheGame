package game.items;

import game.KombinierungsEinrichtung;
import game.Ort;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Diese Klasse beinhaltet alle moeglichen Kombinationen von Gegenstaende.
 * @author Marvin
 */
public class Kombination implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Alle benoetigten Gegenstaende, der letzte Gegenstand ist das Produkt.
	private Stapel[] zutaten;
	// Die Zeit in ms, die benoetigt wird um den Gegenstand herzustellen.
	private long dauer;
	// Die KombinierungsEinrichtung, die zum herstellen erforderlich ist.
	private KombinierungsEinrichtung einrichtung;
	  
	// Liste aller Kombinationen.
	private static ArrayList<Vector<Kombination>> alleKombinationen = new ArrayList<Vector<Kombination>>(3);
	
	static {
		alleKombinationen.add(new Vector<Kombination>());            // 1 Edukt Kombinationen
	    alleKombinationen.add(new Vector<Kombination>());            // 2 Edukt Kombinationen
	    alleKombinationen.add(new Vector<Kombination>());            // 3 Edukt Kombinationen
	}
	  
	/* --- Konstruktor --- */
	
	/**
	 * Eine Kombination kann vom Spieler verwendet werden, um aus ein paar Gegenstaenden einen neuen zu machen.
	 * @param dauer Die Zeit, die vergeht bis der Gegenstand hergestellt wird in Millisekunden.
	 * @param gegenstaende Die Gegenstaende die kombiniert werden koennen, bis zu drei Edukte gefolgt vom Produkt.
	 */
	public Kombination(long dauer, Stapel... gegenstaende) {
		zutaten = gegenstaende;
	    this.dauer = dauer;
	    // es wird automatisch aufgrund der Laenge die Kombination dem richtigen Vector zugeordnet.
	    alleKombinationen.get(zutaten.length - 2).add(this);
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt basierend auf einem Gegenstand den entsprechenden Stapel zurueck, es kann sowohl Produkt als auch Edukt sein.
	 * @param gegenstand Der Gegenstand, dessen Stapel gesucht wird.
	 * @return Den Stapel des Gegenstand, falls vorhanden, ansonsten null.
	 */
	public Stapel getStapel(Gegenstand gegenstand) {
		if(gegenstand == null)
			return null;
	    for(Stapel s : zutaten)
	    	if(gegenstand.equals(s.getGegenstand()))
	    		return s;
	    return null;
	}
	
	/**
	 * Legt die KombinierungsEinrcihtung fest, die fuer die Kombination erforderlich ist.
	 * @param einrichtung Die neue Einrichtung, die benoetigt wird.
	 * @return Sich Selbst.
	 */
	public Kombination setKombinierungsEinrichtung(KombinierungsEinrichtung einrichtung) {
		this.einrichtung = einrichtung;
		return this;
	}
	
	/**
	 * Gibt die benoetigte KombinierungsEinrichtung zurueck, die fuer die Kombination benoetigt wird.
	 * @return Die benoetigte KombinierungsEinrichtung.
	 */
	public KombinierungsEinrichtung getEinrichtung() {
		return einrichtung;
	}
	
	/**
	 * Uberprueft, ob an dem Ort eine entsprechende Einrichtung zum Herstellen dieser Kombination vorhanden ist.
	 * @param ort Der Ort, an dem der Spieler diese Kombination versucht.
	 * @return True, falls so eine Einrichtung vorhanden ist, ansonsten false.
	 */
	public boolean istEinrichtungVorhanden(Ort ort) {
		if(this.einrichtung == null)
			return true;
		return ort.getUntersuchbaresObjekt(einrichtung.getName()) != null ? true : false;
	}
	
	/**
	 * Gibt das Produkt der Kombination zurueck.
	 * @return Das Produkt dieser Kombination.
	 */
	public Stapel getProdukt() {
		return zutaten[zutaten.length - 1];
	}
	
	/**
	 * Gibt ein Array mit allen Edukten zurueck.
	 * @return Ein Array, das alle Edukte dieser Kombination enthaelt.
	 */
	public Stapel[] getAlleEdukte() {
		Stapel[] s = new Stapel[zutaten.length - 1];
	    for(int i = zutaten.length - 2; i >= 0; i--)
	    	s[i] = zutaten[i];
	    return s;
	}
	
	/**
	 * Gibt an, ob der uebergebene Gegenstand ein gueltiges Edukt ist.
	 * @param gegenstand Der Gegenstand, auf den ueberprueft werden soll.
	 * @return wahr, wenn er ein gueltiges Edukt ist, ansonsten falsch.
	 */
	public boolean istEdukt(Gegenstand gegenstand) {
		for(Stapel s : zutaten)
			if(s.getGegenstand().equals(gegenstand))
				return true;
	    return false;
	}	
	
	/**
	 * Gibt die Dauer der Herstellung in Millisekunden zurueck.
	 * @return Die Dauer der Herstellung in Millisekunden.
	 */
	public long getDauer() {
		return dauer;
	}
	
	/* --- statische Methoden --- */
	
	/**
	 * Gibt die Liste mit allen Kombinationen, wird benoetigt, um diese zu speichern und zu laden.
	 * @return Die Liste mit allen Kombinationen.
	 */
	public static ArrayList<Vector<Kombination>> getAlleKombinationen() {
		return alleKombinationen;
	}
	
	/**
	 * Legt die aktuelle Liste der Kombinationen fest, wird benoetigt, um diese speichern und laden zu koennen.
	 * @param kombinationen Die neue Liste aller Kombinationen.
	 */
	public static void setAlleKombinationen(ArrayList<Vector<Kombination>> kombinationen) {
		alleKombinationen = kombinationen;
	}
	  
	/**
	 * Gibt eine Kombination zurueck, basierend auf dem Produkt der Herstellung.
	 * @param produkt Das Produkt, dessen Kombination gesucht wird.
	 * @return Die Kombination, falls es so eine Kombination gibt, ansonsten null.
	 */
	public static Kombination getKombination(Gegenstand produkt) {
		for(Vector<Kombination> v : alleKombinationen)
			for(Kombination k : v)
				if(k.getProdukt().getGegenstand().equals(produkt))
					return k;
	    return null;
	}
	  
	/**
	 * Gibt alle n Edukt Kombinationen zurueck.
	 * @param edukte Die Anzahl der Edukte.
	 * @return Einen Vector mit allen Kombinationen, die so viele Edukte haben.
	 */
	public static Vector<Kombination> getAlleKombinationen(int edukte) {
		return alleKombinationen.get(edukte - 1);
	}

	/**
	 * Ueberprueft, ob es sich um eine gueltige Kombination handelt und gibt diese Kombination dann zurueck.
	 * @param edukt1 Das erste Edukt.
	 * @return Die Kombination, falls es diese gibt, ansonsten null.
	 */
	public static Kombination kombiniere(Stapel edukt1) {
		for(Kombination k : getAlleKombinationen(1))
			for(Stapel s : k.getAlleEdukte())
				if(s.equals(edukt1))
					return k;
		return null;
	}
	
	/**
	 * Ueberprueft, ob es sich um eine gueltige Kombination handelt und gibt diese Kombination dann zurueck.
	 * @param edukt1 Das erste Edukt.
	 * @param edukt2 Das zweite Edukt.
	 * @return Die Kombination, falls es diese gibt, ansonsten null.
	 */
	public static Kombination kombiniere(Stapel edukt1, Stapel edukt2) {
	    boolean erstes;
	    for(Kombination k : getAlleKombinationen(2)) {
	    	erstes = false;
	    	for(Stapel s : k.getAlleEdukte()) {
	    		if((s.equals(edukt1) || s.equals(edukt2)) && erstes)
	    			return k;
	    		if(s.equals(edukt1) || s.equals(edukt2))
	    			erstes = true;
	    	}
	    }
	    return null;
	}
	
	/**
	 * Ueberprueft, ob es sich um eine gueltige Kombination handelt und gibt diese Kombination dann zurueck.
	 * @param edukt1 Das erste Edukt.
	 * @param edukt2 Das zweite Edukt.
	 * @param edukt3 Das dritte Edukt.
	 * @return Die Kombination, falls es diese gibt, ansonsten null.
	 */
	public static Kombination kombiniere(Stapel edukt1, Stapel edukt2, Stapel edukt3) {
	    boolean erstes, zweites;
	    for(Kombination k : getAlleKombinationen(3)) {
	    	erstes = false;
	    	zweites = false;
	    	for(Stapel s : k.getAlleEdukte()) {
	    		if((s.equals(edukt1) || s.equals(edukt2) || s.equals(edukt3)) && erstes && zweites)
	    			return k;
	    		if((s.equals(edukt1) || s.equals(edukt2) || s.equals(edukt3)) && erstes)
	    			zweites = true;
	    		if(s.equals(edukt1) || s.equals(edukt2) ||s.equals(edukt3))
	    			erstes = true;
	    	}
	    }
	    return null;
	}
	
	/**
	 * Gibt eine Liste mit allen Gegenstaenden zurueck, die mit diesem Gegenstand kombinierbar sind.
	 * Falls null uebergeben wird, dann werden alle Gegenstaende zurueckgegeben, die bei Kombinationen verwendet werden.
	 * @param gegenstand Der Gegenstand nach dem gesucht werden soll.
	 * @return Die Liste mit allen Gegenstaenden, die mit diesem kombinierbar sind.
	 */
	public static Vector<Gegenstand> istKombinierbarMit(Gegenstand gegenstand) {
		Vector<Gegenstand> liste = new Vector<Gegenstand>();
	    if(gegenstand == null) {
	    	for(int i = 1; i < 4; i++) {
	    		for(Kombination k : getAlleKombinationen(i))
	    			for(Stapel s : k.getAlleEdukte())
	    				if(!liste.contains(s.getGegenstand()))
	    					liste.add(s.getGegenstand());
	    	}
	    	return liste;
	    }
	    for(int i = 1; i < 4; i++) {
	    	for(Kombination k : getAlleKombinationen(i))
	    		for(Stapel s : k.getAlleEdukte())
	    			if(k.istEdukt(gegenstand) && !liste.contains(s.getGegenstand()) && !s.getGegenstand().equals(gegenstand))
		    			liste.add(s.getGegenstand());
	    }
	    return liste;
	}
	
	/**
	 * Gibt eine Liste mit allen Gegenstaenden zurueck, die mit diesen beiden Gegenstaenden kombinierbar sind.
	 * @param gegenstand1 Der erste Gegenstand.
	 * @param gegenstand2 Der zweite Gegenstand.
	 * @return Eine Liste mit allen Gegenstaenden, die mit diesen beiden Gegenstaenden kombinierbar sind.
	 */
	public static Vector<Gegenstand> istKombinierbarMit(Gegenstand gegenstand1, Gegenstand gegenstand2){
		Vector<Gegenstand> liste1 = istKombinierbarMit(gegenstand1);
	    Vector<Gegenstand> liste2 = istKombinierbarMit(gegenstand2);
	    Vector<Gegenstand> schnittmenge = new Vector<Gegenstand>();
	    for(Gegenstand g : liste1)
	    	if(liste2.contains(g) && !schnittmenge.contains(g))
	    		schnittmenge.add(g);
	    return schnittmenge;
	}

}