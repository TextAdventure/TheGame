package game.items;

import game.SpielWelt;

import java.io.Serializable;

/**
 * Die Ausruestung des Spielers wird in einem Objekt dieser Klasse verwaltet.
 */
public class Ausruestung implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Die Variablen --- */
	
	// Alle Ruestungsteile werden in einem Array gehalten
	private Ruestung[] ruestung;
	// Alle Accesoires werden in einem Array gehalten.
	private Accessoire[] accesoires;
	// Der Haupthand-Slot
	private Waffe haupthand;
	// Der Schildhand-Slot
	private Waffe schildhand;
	  
	/* --- Der Konstruktor --- */
	
	/**
	 * Eine neue Ausruestung wird leer erstellt.
	 */
	public Ausruestung() {
	    ruestung = new Ruestung[5];
	    accesoires = new Accessoire[7];
	}
	
	/* --- Die Methoden --- */
	  
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
	 * Diese Methode tauscht ein Ruestungsteil gegen eine neue Ruestung aus und gibt die Alte zurueck.
	 * @param neueRuestung Das neue Ruestungsteil, das der Spieler ausruesten moechte.
	 * @return Die alte Ruestung wird zurueckgegeben, sodass diese wieder dem Inventar des Spielers hinzugefuegt werden kann.
	 */
	public Ruestung tauscheRuestung(Ruestung neueRuestung) {
		Ruestung alteRuestung = null;
		switch(neueRuestung.getTyp()) {
			case(Ruestung.HELM): alteRuestung = ruestung[Ruestung.HELM]; ruestung[Ruestung.HELM] = neueRuestung; break;
			case(Ruestung.BRUSTPANZER): alteRuestung = ruestung[Ruestung.BRUSTPANZER]; ruestung[Ruestung.BRUSTPANZER] = neueRuestung; break;
			case(Ruestung.HANDSCHUHE): alteRuestung = ruestung[Ruestung.HANDSCHUHE]; ruestung[Ruestung.HANDSCHUHE] = neueRuestung; break;
			case(Ruestung.HOSE): alteRuestung = ruestung[Ruestung.HOSE]; ruestung[Ruestung.HOSE] = neueRuestung; break;
			case(Ruestung.SCHUHE): alteRuestung = ruestung[Ruestung.SCHUHE]; ruestung[Ruestung.SCHUHE] = neueRuestung; break;
		}
	    return alteRuestung;
	}	  
	/**
	 * Diese Methode legt eine alte Ruestung ab und gibt diese dann zurueck.
	 * @param alteRuestung Die Ruestung, die abgelegt werden soll.
	 * @return Die alte Ruestung wird zurueckgegeben, sodass diese wieder dem Inventar des Spielers hinzugefuegt werden kann.
	 */
	public Ruestung legeRuestungAb(Ruestung alteRuestung) {
		for(int i = 0; i < ruestung.length; i++) {
			if(ruestung[i] == alteRuestung) {
				ruestung[i] = null;
				return alteRuestung;
			}				
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
			case(Accessoire.AMULETT): altesAccesoire = accesoires[Accessoire.AMULETT]; accesoires[Accessoire.AMULETT] = neuesAccesoire; break;
			case(Accessoire.RUECKEN): altesAccesoire = accesoires[Accessoire.RUECKEN]; accesoires[Accessoire.RUECKEN] = neuesAccesoire; break;
			case(Accessoire.GUERTEL): altesAccesoire = accesoires[Accessoire.GUERTEL]; accesoires[Accessoire.GUERTEL] = neuesAccesoire; break;
			case(Accessoire.RING):
				if(accesoires[Accessoire.RING] != null && accesoires[Accessoire.RING + 1] == null) {
					accesoires[Accessoire.RING + 1] = neuesAccesoire;
				} else {
					altesAccesoire = accesoires[Accessoire.RING]; 
					accesoires[Accessoire.RING] = neuesAccesoire;					
				}
				break;
			case(Accessoire.ARMBAND):
				if(accesoires[Accessoire.ARMBAND] != null && accesoires[Accessoire.ARMBAND + 1] == null) {
					accesoires[Accessoire.ARMBAND + 1] = neuesAccesoire;
				} else {
					altesAccesoire = accesoires[Accessoire.ARMBAND]; 
					accesoires[Accessoire.ARMBAND] = neuesAccesoire;
				}					
				break;
		}
		return altesAccesoire;
	}
	
	/**
	 * Legt ein Accesoire ab und gibt es zurueck.
	 * @param altesAccesoire Das alte Accesoire, das abgelegt werden soll.
	 * @return Das alte Accesoire, sodass es dem Spieler zurueck ins Inventar gelegt werden kann.
	 */
	public Accessoire legeAccesoireAb(Accessoire altesAccesoire) {
		for(int i = 0; i < accesoires.length; i++) {
			if(accesoires[i] == altesAccesoire) {
				accesoires[i] = null;
				return altesAccesoire;
			}				
		}
		return altesAccesoire;
	}
	  
	/**
	 * Zeigt die Ausruestung des Spielers in der SpielWelt an. 
	 * @param welt Die SpielWelt, in der die Ausruestung angezeigt werden soll. 
	 */
	public void zeigeAn(SpielWelt welt) {
	    welt.print("Ausrüstung:");
	    if(haupthand == schildhand && haupthand != null && schildhand != null) 
	    	welt.println("Beidhändig - " + haupthand.getNameExtended());	    
	    else {
	    	if(haupthand != null) 
	    		welt.println("Haupthand - " + haupthand.getNameExtended());
	    	else 
	    		welt.println("Haupthand - leer");
	    	if(schildhand != null) 
	    		welt.println("Schildhand - " + schildhand.getNameExtended());
	    	else 
	    		welt.println("Schildhand - leer");
	    }
	    welt.println();
	    for(int i = 0; i < ruestung.length; i++) {
	    	if(ruestung[i] != null) 
	    		welt.println(Ruestung.getTypNamen((byte)i) + " - " + ruestung[i].getNameExtended());
	    	else
	    		welt.println(Ruestung.getTypNamen((byte)i) + " - leer");
	    }
	    welt.println();
	    for(int i = 0; i < accesoires.length; i++) {
	    	if(accesoires[i] != null)
	    		welt.println(Accessoire.getTypNamen((byte)i) + " - " + accesoires[i].getNameExtended());
	    	else
	    		welt.println(Accessoire.getTypNamen((byte)i) + " - leer");
	    }
	}
	  
	/**
	 * Teset, ob der Spieler einen Gegenstand ausgeruestet hat.
	 * @param gegenstand Der Gegenstand, den der Spieler ausgeruestet haben soll, sodass wahr zurueckgegeben wird.
	 * @return Gibt true zurueck, falls der Spieler den Gegenstand ausgeruestet hat und false, wenn er ihn nicht ausgeruestet hat.
	 */
	public boolean istAusgeruestet(Gegenstand gegenstand) {
		if(haupthand != null && haupthand == gegenstand) 
				return true; 
	    if(schildhand != null && schildhand == gegenstand) 
	    	return true;
	    for(Ruestung r : ruestung) {
	    	if(r != null && r == gegenstand) 
	    		return true;
	    }
	    for(Accessoire a : accesoires) {
	    	if(a != null && a == gegenstand)
	    		return true;
	    }
	    return false;
	}
	
}