package game.items;

import game.KombinierungsEinrichtung;
import game.SpielWelt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 *  Diese Klasse beinhaltet alle moeglichen Kombinationen von Gegenstaende.
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
	static{
		alleKombinationen.add(new Vector<Kombination>());            // 1 Edukt Kombinationen
	    alleKombinationen.add(new Vector<Kombination>());            // 2 Edukt Kombinationen
	    alleKombinationen.add(new Vector<Kombination>());            // 3 Edukt Kombinationen
	}
	  
	/* --- Der Konstruktor --- */	
	
	/**
	 *  Es werden zwei Gegenstaende kombiniert.
	 *  dauer: die Zeit in ms, die benoetigt werden um den Gegenstand herzustellen.
	 *  gegenstaende: alle benoetigten Gegenstaende, der letzte Gegenstand ist das Produkt.
	 */
	public Kombination(long dauer, Stapel... gegenstaende){
		zutaten = gegenstaende;
	    this.dauer = dauer;
	    // es wird automatisch aufgrund der Laenge die Kombination dem richtigen Vector zugeordnet.
	    Kombination.addKombination(this, zutaten.length - 1);
	}
	  
	/* --- Die statischen Methoden --- */
	
	// Fuegt eine neue Kombination mit 1-3 Edukten hinzu.
	private static void addKombination(Kombination kombination, int edukte){
		switch(edukte){
			case(1): alleKombinationen.get(0).add(kombination); break;
			case(2): alleKombinationen.get(1).add(kombination); break;
			case(3): alleKombinationen.get(2).add(kombination); break;
	    }
	}
	  
	// Gibt eine Kombination aufgrund des Produkts zurueck.
	public static Kombination getKombination(Gegenstand produkt){
		for(Vector<Kombination> v: alleKombinationen){
			for(Kombination k: v.toArray(new Kombination[0])){
				if(k.getProdukt().getGegenstand() == produkt) return k;
			}
	    }
	    return null;
	}
	  
	/**
	 *  Gibt alle n Edukt Kombinationen zurueck.
	 *  edukte: die Anzahl der Edukte.
	 */
	public static Vector<Kombination> getAlleKombinationen(int edukte){
		return alleKombinationen.get(edukte - 1);
	}
	  
	// Gibt die Liste aller Kombiationen zurueck.
	public static ArrayList<Vector<Kombination>> getAlleKombinationen(){return alleKombinationen;}
	// Aktualisiert die Liste aller Kombinationen.
	public static void setAlleKombinationen(ArrayList<Vector<Kombination>> kombinationen){alleKombinationen = kombinationen;}

	/**
	 *  Diese Methode ueberprueft, ob es sich um ein gueltiges Rezept handelt und gibt das Ergebnis zurueck.
	 *  edukt1+2: alle Zutaten fuer den Gegenstand.
	 */
	public static Gegenstand kombiniere(Gegenstand edukt1, Gegenstand edukt2){
	    boolean erstes;
	    for(Kombination k: getAlleKombinationen(2).toArray(new Kombination[0])){
	    	erstes = false;
	    	for(Gegenstand g: k.getAlleEdukte()){
	    		if((g == edukt1 | g == edukt2) && erstes)
	    			return k.getProdukt().getGegenstand();
	    		if(g == edukt1 | g == edukt2) erstes = true;
	    	}
	    }
	    return null;
	}
	  
	/**
	 *  Diese Methode ueberprueft, ob es sich um ein gueltiges Rezept handelt und gibt das Ergebnis zurueck.
	 *  edukt1-3: alle Zutaten fuer den Gegenstand.
	 */
	public static Gegenstand kombiniere(Gegenstand edukt1, Gegenstand edukt2, Gegenstand edukt3){
	    boolean erstes, zweites;
	    for(Kombination k: getAlleKombinationen(3).toArray(new Kombination[0])){
	    	erstes = false;
	    	zweites = false;
	    	for(Gegenstand g: k.getAlleEdukte()){
	    		if((g == edukt1 || g == edukt2 || g == edukt3) && erstes && zweites)
	    			return k.getProdukt().getGegenstand();
	    		if((g == edukt1 || g == edukt2 || g == edukt3) && erstes) zweites = true;
	    		if(g == edukt1 || g == edukt2 ||g == edukt3) erstes = true;
	    	}
	    }
	    return null;
	}
	  
	// Diese Methode gibt einen Vector zurueck, der alle Gegenstaende beinhaltet, die mit diesem Gegenstand kombinierbar sind.
	// Wenn null uebergeben wird, dann werden alle Gegenstaende zurueckgegeben.
	public static Vector<Gegenstand> istKombinierbarMit(Gegenstand gegenstand){
		Vector<Gegenstand> liste = new Vector<Gegenstand>();
	    if(gegenstand == null){
	    	for(int i = 1; i < 4; i++){
	    		for(Kombination k: getAlleKombinationen(i).toArray(new Kombination[0])){
	    			for(Gegenstand g: k.getAlleEdukte()){
	    				if(!liste.contains(g))
	    					liste.add(g);
	    			}
	    		}
	    	}
	    	return liste;
	    }
	    for(int i = 1; i < 4; i++){
	    	for(Kombination k: getAlleKombinationen(i).toArray(new Kombination[0])){
	    		for(Gegenstand g: k.getAlleEdukte()){
	    			if(k.istEdukt(gegenstand) && !liste.contains(g) && gegenstand != g)
		    				liste.add(g);
	    		}
	    	}
	    }
	    return liste;
	}
	  
	// Diese Methode ueberprueft zwei Gegenstaende auf Kompatibilitaet.
	public static Vector<Gegenstand> istKombinierbarMit(Gegenstand gegenstand1, Gegenstand gegenstand2){
		Vector<Gegenstand> liste1 = istKombinierbarMit(gegenstand1);
	    Vector<Gegenstand> liste2 = istKombinierbarMit(gegenstand2);
	    Vector<Gegenstand> schnittmenge = new Vector<Gegenstand>();
	    for(Gegenstand g : liste1)
	    	if(liste2.contains(g) && !schnittmenge.contains(g))
	    		schnittmenge.add(g);
	    return schnittmenge;
	}
	  

	// Diese Methode gibt basierend auf einem Gegenstand den entsprechenden Stapel zurueck.
	public Stapel getStapel(Gegenstand gegenstand){
		if(gegenstand == null) return null;
	    for(Stapel s: zutaten)
	    	if(gegenstand == s.getGegenstand())
	    		return s;
	    return null;
	}

	// Diese Methode gibt Edukt1 zurueck.
	public Stapel getEdukt1(){return zutaten[0];}
	// Diese Methode gibt Edukt2 zurueck.
	public Stapel getEdukt2(){
		if(zutaten.length >= 3) return zutaten[1];
		return zutaten[0];
	}
	// Diese Methode gibt Edukt3 zurueck.
	public Stapel getEdukt3(){
		if(zutaten.length == 4) return zutaten[2];
	    return zutaten[0];
	}
	/**
	 * Gibt die benoetigte KombinierungsEinrichtung zurueck, die fuer die Kombination benoetigt wird.
	 * @return Die benoetigte KombinierungsEinrichtung.
	 */
	public KombinierungsEinrichtung getEinrichtung() {
		return einrichtung;
	}
	// Diese Methode gibt ein Edukt zurueck, basierend auf einer Zahl.
	public Stapel getEdukt(int n){
		switch(n){
	      	case(1): return getEdukt1();
	      	case(2): return getEdukt2();
	      	case(3): return getEdukt3();
	      	default: System.err.println("Ein Fehler trat auf!"); return null;
		}
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
	
	public boolean istEinrichtungVorhanden() {
		if(this.einrichtung == null)
			return true;
		return SpielWelt.WELT.getAktuellePosition().getUntersuchbaresObjekt(einrichtung.getName()) != null ? true : false;
	}
	
	// Diese Methode gibt das Produkt zurueck.
	public Stapel getProdukt(){return zutaten[zutaten.length - 1];}
	  
	// Diese Methode gibt ein Array mit allen Edukten zurueck.
	private Gegenstand[] getAlleEdukte(){
		Gegenstand[] g = new Gegenstand[zutaten.length - 1];
	    for(int i = zutaten.length - 2; i > -1; i--){
	    	g[i] = zutaten[i].getGegenstand();
	    }
	    return g;
	}
	
	/**
	 * Gibt an, ob der uebergebene Gegenstand ein gueltiges Edukt ist.
	 * @param gegenstand Der Gegenstand, auf den ueberprueft werden soll.
	 * @return wahr, wenn er ein gueltiges Edukt ist, ansonsten falsch.
	 */
	public boolean istEdukt(Gegenstand gegenstand) {
		for(Stapel s : zutaten)
			if(s.getGegenstand() == gegenstand)
				return true;
	    return false;
	}	
	
	// Diese Methode gibt die Dauer der Herstellung zurueck.
	public long getDauer() { return dauer; }
}