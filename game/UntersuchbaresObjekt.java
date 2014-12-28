package game;

import game.bedingung.Bedingung;

import java.io.Serializable;


public class UntersuchbaresObjekt implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Der Name des Objekts.
	private String name;
	// Die Beschreibung, die bei einer Untersuchung angezeigt wird.
	private String beschreibung;
	// Gibt an, ob das Objekt bereits untersucht wurde.
	private boolean untersucht;
	// Diese Bedingung wird ueberprueft, wenn das Objekt untersucht wird.
	private Bedingung bedingung;
	  
	/**
	 *  Mit diesem Konstruktor wird ein neues UntersuchbaresObjekt erstellt.
	 *  objektName: der Name des Objekts.
	 *  objektBeschreibung: die Beschreibung des Objekts.
	 */
	public UntersuchbaresObjekt(String objektName, String objektBeschreibung){
	    name = objektName;
	    beschreibung = objektBeschreibung;
	    untersucht = false;
	}
	  
	/**
	 *  Gibt den Namen des Objekts zurueck.
	 */
	public String getName(){
	    untersucht = true;
	    return name;
	}
	  
	/**
	 *  Aendert den Namen des Objekts auf den uebergebenen Namen.
	 *  neuerName: der neue Name fuer das Objekt.
	 */
	public void setName(String neuerName){
	    name = neuerName;
	}
	  
	/**
	 *  Gibt die Beschreibung des Objekt zurueck und prueft die Bedingung, falls vorhanden.
	 */
	public String getBeschreibung(){
	    untersucht = true;	 
	    return beschreibung;
	}
	  
	/**
	 *  Aendert die Beschreibung des Objekts auf die uebergebene Beschreibung.
	 *  neueBeschreibung: die neue Beschreibung fuer das Objekt.
	 */
	public void setBeschreibung(String neueBeschreibung){
	    beschreibung = neueBeschreibung;
	}
	  
	/**
	 *  Gibt zurueck, ob das Objekt bereits untersucht wurde;
	 */
	public boolean wurdeUntersucht(){
	    return untersucht;
	}
	
	/**
	 * Fuegt eine Bedingung hinzu, die jedesmal ueberprueft wird, wenn das Objekt untersucht wird.
	 * bedingung: die neue Bedingung, ein UntersachbareObjekt kann aber immer nur eine Bedingung haben.
	 */
	public void addBedingung(Bedingung bedingung){
		this.bedingung = bedingung;
	}
	
	/**
	 * Ueberprueft die Bedingung.
	 */
	public void pruefen(){
		if(bedingung != null) bedingung.pruefen();
	}
}