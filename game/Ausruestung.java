package game;

import java.io.Serializable;

public class Ausruestung implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Alle Ruestungsteile werden in einem Array gehalten
	private Ruestung[] ruestung;
	// Der Haupthand-Slot
	private Waffe haupthand;
	// Der Schildhand-Slot
	private Waffe schildhand;
	  
	/**
	 *  Eine neue Ausruestung wird leer erstellt.
	 */
	public Ausruestung(){
	    ruestung = new Ruestung[5];
	}
	  
	// Gibt die Waffe in der Haupthand zurueck.
	public Waffe getHaupthand(){
	    return haupthand;
	}
	// Gibt die Waffe in der Schildhand zurueck.
	public Waffe getSchildhand(){
	    return schildhand;
	}
	  
	/**
	 *  Diese Methode tauscht ein Ruestungsteil gegen ein Neues und gibt das Alte zurueck.
	 *  neueRuestung: die neue Ruestung.
	 */
	public Ruestung tauscheRuestung(Ruestung neueRuestung){
		Ruestung alteRuestung = null;
		switch(neueRuestung.getTyp()){
			case(Ruestung.HELM): alteRuestung = ruestung[Ruestung.HELM]; ruestung[Ruestung.HELM] = neueRuestung; break;
			case(Ruestung.BRUSTPANZER): alteRuestung = ruestung[Ruestung.BRUSTPANZER]; ruestung[Ruestung.BRUSTPANZER] = neueRuestung; break;
			case(Ruestung.HANDSCHUHE): alteRuestung = ruestung[Ruestung.HANDSCHUHE]; ruestung[Ruestung.HANDSCHUHE] = neueRuestung; break;
			case(Ruestung.HOSE): alteRuestung = ruestung[Ruestung.HOSE]; ruestung[Ruestung.HOSE] = neueRuestung; break;
			case(Ruestung.SCHUHE): alteRuestung = ruestung[Ruestung.SCHUHE]; ruestung[Ruestung.SCHUHE] = neueRuestung; break;
		}
	    return alteRuestung;
	}
	  
	/**
	 *  Diese Methode legt ein Ruestungsteil ab.
	 *  alteRuestung: die Ruestung, die abgelegt werden soll(diese ist der uebergebene Parameter).
	 */
	public Ruestung legeRuestungAb(Ruestung alteRuestung){
		switch(alteRuestung.getTyp()){
			case(Ruestung.HELM): ruestung[Ruestung.HELM] = null;  break;
			case(Ruestung.BRUSTPANZER): ruestung[Ruestung.BRUSTPANZER] = null; break;
			case(Ruestung.HANDSCHUHE): ruestung[Ruestung.HANDSCHUHE] = null; break;
			case(Ruestung.HOSE): ruestung[Ruestung.HOSE] = null; break;
			case(Ruestung.SCHUHE): ruestung[Ruestung.SCHUHE] = null; break;
	    }
	    return alteRuestung;
	}
	  
	/**
	 *  Diese Methode tauscht die Waffe des Spielers in der Haupthand gegen eine Neue und gibt die Alte zurueck.
	 *  neueWaffe: die neue Waffe.
	 */
	public Waffe tauscheSchwerthand(Waffe neueWaffe){
	    Waffe alteWaffe = haupthand;
	    if(haupthand != null)if(haupthand.getHand() == Waffe.ZWEIHAENDIG) schildhand = null;
	    haupthand = neueWaffe;
	    return alteWaffe;
	}
	  
	/**
	 *  Diese Methode tauscht die Waffe des Spielers in der Schildhand gegen eine Neue und gibt die Alte zurueck.
	 *  neueWaffe: die neue Waffe.
	 */
	public Waffe tauscheSchildhand(Waffe neueWaffe){
	    Waffe alteWaffe = schildhand;
	    if(schildhand != null) if(schildhand.getHand() == Waffe.ZWEIHAENDIG) haupthand = null;
	    schildhand = neueWaffe;
	    return alteWaffe;
	}
	  
	/**
	 *  Diese Methode tauscht eine Zweihaendige Waffe aus.
	 *  neueWaffe: die neu ausgeruestete Waffe.
	 */
	public Waffe[] tauscheZweihaendig(Waffe neueWaffe){
	    Waffe[] alteWaffen;
	    if(haupthand != schildhand) alteWaffen = new Waffe[]{haupthand, schildhand};
	    else alteWaffen = new Waffe[]{haupthand};
	    haupthand = neueWaffe;
	    schildhand = neueWaffe;
	    return alteWaffen;
	}
	  
	/**
	 *  Zeigt die aktueller Ausruestung an, braucht das Welt-Objekt dafuer.
	 *  welt: die Welt, in der angezeigt werden soll.
	 */
	public void zeigeAn(SpielWelt welt){
	    welt.println("Ausrüstung:");
	    if(haupthand == schildhand && haupthand != null && schildhand != null) welt.println("Beidhändig - " + haupthand.getName());
	    else{
	    	if(haupthand != null) welt.println("Haupthand - " + haupthand.getName());
	    	else welt.println("Haupthand - leer");
	    	if(schildhand != null) welt.println("Schildhand - " + schildhand.getName());
	    	else welt.println("Schildhand - leer");
	    }
	    welt.println();
	    for(int i = 0; i < ruestung.length; i++){
	    	if(ruestung[i] != null) welt.println(Ruestung.getTypName(ruestung[i]) + " - " + ruestung[i].getName());
	    	else welt.println(Ruestung.getTypName((byte)i) + " - leer");
	    }
	}
	  
	/**
	 *  Testet, ob der Gegenstand ausgeruestet ist.
	 *  gegenstand: der Gegenstand, der ueberprueft werden soll.
	 */
	public boolean istAusgeruestet(Gegenstand gegenstand){
		if(haupthand != null) if(haupthand.getName().equalsIgnoreCase(gegenstand.getName())) return true;
	    if(schildhand != null) if(schildhand.getName().equalsIgnoreCase(gegenstand.getName())) return true;
	    for(Ruestung r: ruestung){
	    	if(r != null && r.getName().equalsIgnoreCase(gegenstand.getName())) return true;
	    }
	    return false;
	}
}