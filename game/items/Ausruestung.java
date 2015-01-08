package game.items;

import gui.Anzeige;

import java.io.Serializable;

/**
 * Hier ist die gesamte Ausruestung des Spielers gespeichert und wird auch verwaltet.
 * @author Marvin
 */
public class Ausruestung implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Alle Ruestungsteile werden in einem Array gehalten
	private Ruestung[] ruestung;
	// Alle Accessoires werden in einem Array gehalten.
	private Accessoire[] accessoires;
	// Der Haupthand-Slot
	private Waffe haupthand;
	// Der Schildhand-Slot
	private Waffe schildhand;
	  
	/* --- Konstruktor --- */
	
	/**
	 * Eine neue Ausruestung wird leer erstellt.
	 */
	public Ausruestung() {
	    ruestung = new Ruestung[5];
	    accessoires = new Accessoire[7];
	}
	
	/* --- Methoden --- */
	  
	/**
	 * Gibt die Waffe in der Haupthand zurueck.
	 * @return Die Waffe, die momentan vom Spieler in der Haupthand gehalten wird.
	 */
	public Waffe getHaupthand() {
	    return haupthand;
	}
	
	/**
	 * Gibt die Waffe in der Schildhand zurueck.
	 * @return Die Waffe, die momentan vom Spieler in der Schildhand gehalten wird.
	 */
	public Waffe getSchildhand() {
	    return schildhand;
	}
	  
	/**	
	 * Tauscht ein Ruestungsteil gegen eine neue Ruestung aus und gibt die Alte zurueck.
	 * @param neueRuestung Das neue Ruestungsteil, das der Spieler ausruesten moechte.
	 * @return Die alte Ruestung wird zurueckgegeben, sodass diese wieder dem Inventar des Spielers hinzugefuegt werden kann.
	 */
	public Ruestung tauscheRuestung(Ruestung neueRuestung) {
		Ruestung alteRuestung = ruestung[neueRuestung.getTyp()];
		ruestung[neueRuestung.getTyp()] = neueRuestung;		
		return alteRuestung;
	}

	/**
	 * Legt eine alte Ruestung ab und gibt diese dann zurueck.
	 * @param alteRuestung Die Ruestung, die abgelegt werden soll.
	 * @return Die alte Ruestung wird zurueckgegeben, sodass diese wieder dem Inventar des Spielers hinzugefuegt werden
	 * kann, wenn der Gegenstand nicht ausgeruestet ist, dann wird null zurueckgegeben.
	 */
	public Ruestung legeRuestungAb(Ruestung alteRuestung) {
		if(ruestung[alteRuestung.getTyp()].equals(alteRuestung)) {
			ruestung[alteRuestung.getTyp()] = null;
			return alteRuestung;
		}
	    return null;
	}
	  
	/**
	 * Diese Methode tauscht die Waffe des Spielers in der Schwerthand gegen eine neue Waffe und gibt die Alte zurueck.
	 * @param neueWaffe Die neue Waffe fuehr die Schwerthand des Spielers.
	 * @return Gibt die alte Waffe zurueck, sodass diese wieder dem Inventar des Spielers hinzugefuegt werden kann.
	 */
	public Waffe tauscheSchwerthand(Waffe neueWaffe) {
	    Waffe alteWaffe = haupthand;
	    if(haupthand != null) 
	    	if(haupthand.getHand() == Waffe.ZWEIHAENDIG) 
	    		schildhand = null;
	    haupthand = neueWaffe;
	    return alteWaffe;
	}	  
	/**
	 * Diese Methode tauscht die Waffe des Spielers in der Schildhand gegen eine neue Waffe und gibt die Alte zurueck.
	 * @param neueWaffe Die neue Waffe fuehr die Schildhand des Spielers.
	 * @return Gibt die alte Waffe zurueck, sodass diese wieder dem Inventar des Spielers hinzugefuegt werden kann.
	 */
	public Waffe tauscheSchildhand(Waffe neueWaffe) {
	    Waffe alteWaffe = schildhand;
	    if(schildhand != null) 
	    	if(schildhand.getHand() == Waffe.ZWEIHAENDIG) 
	    		haupthand = null;
	    schildhand = neueWaffe;
	    return alteWaffe;
	}	  
	/**
	 * Diese Methode tauscht eine zweihaendige neue Waffe gegen die Waffen des Spielers und gibt die alten Waffen zurueck.
	 * @param neueWaffe Die neue Waffe, die der Spieler zweihaendig fuehrt.
	 * @return Gibt die alte Waffe zurueck, sodass diese wieder dem Inventar des Spielers hinzugefuegt werden kann.
	 */
	public Waffe[] tauscheZweihaendig(Waffe neueWaffe) {
	    Waffe[] alteWaffen;
	    if(haupthand != schildhand) 
	    	alteWaffen = new Waffe[]{haupthand, schildhand};
	    else 
	    	alteWaffen = new Waffe[]{haupthand};
	    haupthand = neueWaffe;
	    schildhand = neueWaffe;
	    return alteWaffen;
	}
	
	/**
	 * Ruestet ein Accesoire aus und gibt gegebenenfalls das alte Accesoire zurueck.
	 * @param neuesAccesoire Das neue Accesoire, das ausgeruestet wird.
	 * @return Das alte Accesoire, falls es eins gibt.
	 */
	public Accessoire tauscheAccesoire(Accessoire neuesAccesoire) {			// 0: Amulett 1: Ruecken 2: Guertel 3+4: Ring 5+6: Armband
		Accessoire altesAccesoire = null;
		switch(neuesAccesoire.getTyp()) {
			case(Accessoire.AMULETT): altesAccesoire = accessoires[Accessoire.AMULETT]; accessoires[Accessoire.AMULETT] = neuesAccesoire; break;
			case(Accessoire.RUECKEN): altesAccesoire = accessoires[Accessoire.RUECKEN]; accessoires[Accessoire.RUECKEN] = neuesAccesoire; break;
			case(Accessoire.GUERTEL): altesAccesoire = accessoires[Accessoire.GUERTEL]; accessoires[Accessoire.GUERTEL] = neuesAccesoire; break;
			case(Accessoire.RING):
				if(accessoires[Accessoire.RING] != null && accessoires[Accessoire.RING + 1] == null) {
					accessoires[Accessoire.RING + 1] = neuesAccesoire;
				} else {
					altesAccesoire = accessoires[Accessoire.RING]; 
					accessoires[Accessoire.RING] = neuesAccesoire;					
				}
				break;
			case(Accessoire.ARMBAND):
				if(accessoires[Accessoire.ARMBAND] != null && accessoires[Accessoire.ARMBAND + 1] == null) {
					accessoires[Accessoire.ARMBAND + 1] = neuesAccesoire;
				} else {
					altesAccesoire = accessoires[Accessoire.ARMBAND]; 
					accessoires[Accessoire.ARMBAND] = neuesAccesoire;
				}					
				break;
		}
		return altesAccesoire;
	}
	
	/**
	 * Legt ein Accessoire ab und gibt es zurueck.
	 * @param altesAccessoire Das alte Accessoire, das abgelegt werden soll.
	 * @return Das alte Accessoire, sodass es dem Spieler zurueck ins Inventar gelegt werden kann.
	 */
	public Accessoire legeAccessoireAb(Accessoire altesAccessoire) {
		for(int i = 0; i < accessoires.length; i++) {
			if(accessoires[i] == altesAccessoire) {
				accessoires[i] = null;
				return altesAccessoire;
			}				
		}
		return altesAccessoire;
	}
	  
	/**
	 * Zeigt die Ausruestung des Spielers in der SpielWelt an. 
	 * @param welt Die SpielWelt, in der die Ausruestung angezeigt werden soll. 
	 */
	public void zeigeAn(Anzeige anzeige) {
		anzeige.printSize("Ausrüstung", 18);
	    if(haupthand == schildhand && haupthand != null && schildhand != null) 
	    	anzeige.println("Beidhändig - " + haupthand.getNameExtended());	    
	    else {
	    	if(haupthand != null) 
	    		anzeige.println("Haupthand - " + haupthand.getNameExtended());
	    	else 
	    		anzeige.println("Haupthand - leer");
	    	if(schildhand != null) 
	    		anzeige.println("Schildhand - " + schildhand.getNameExtended());
	    	else 
	    		anzeige.println("Schildhand - leer");
	    }
	    anzeige.println();
	    for(int i = 0; i < ruestung.length; i++) {
	    	if(ruestung[i] != null) 
	    		anzeige.println(Ruestung.getTypNamen((byte)i) + " - " + ruestung[i].getNameExtended());
	    	else
	    		anzeige.println(Ruestung.getTypNamen((byte)i) + " - leer");
	    }
	    anzeige.println();
	    for(int i = 0; i < accessoires.length; i++) {
	    	if(accessoires[i] != null)
	    		anzeige.println(Accessoire.getTypNamen((byte)i) + " - " + accessoires[i].getNameExtended());
	    	else
	    		anzeige.println(Accessoire.getTypNamen((byte)i) + " - leer");
	    }
	}
	  
	/**
	 * Teset, ob der Spieler einen Gegenstand ausgeruestet hat.
	 * @param gegenstand Der Gegenstand, den der Spieler ausgeruestet haben soll, sodass true zurueckgegeben wird.
	 * @return True, falls der Spieler den Gegenstand ausgeruestet hat und false, wenn er ihn nicht ausgeruestet hat.
	 */
	public boolean istAusgeruestet(Gegenstand gegenstand) {
		if(haupthand != null && gegenstand.equals(haupthand))
				return true; 
	    if(schildhand != null && gegenstand.equals(schildhand))
	    	return true;
	    for(Ruestung r : ruestung)
	    	if(r != null&& gegenstand.equals(r))
	    		return true;
	    for(Accessoire a : accessoires)
	    	if(a != null && gegenstand.equals(a))
	    		return true;
	    return false;
	}

}