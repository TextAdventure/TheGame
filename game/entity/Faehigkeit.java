package game.entity;

import game.items.Waffenart;

import java.io.Serializable;

import util.NumerusGenus;

/**
 * Eine Faehigkeit, die im Kampf verwendet werden kann vom Spieler oder von einem Gegener.
 * @author Marvin
 */
public class Faehigkeit implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	  
	/* --- Variablen --- */
	
	// Der Name der Faehigkeit.
	private String name;
	// Der Numerus und Genus der Faehigkeit
	private NumerusGenus numGen;
	// Die Ausgabe, die angezeigt wird, wenn die Faehigkeit ausgefuehrt wird.
	private String ausgabe;
	// Der Attributsbonus fuer die Faehigkeit, mit "100%" wird das Attribut verdoppelt
	private String bonus;
	// Die Kosten fuer die Faehigkeit.
	private String kosten;
	// Alle Waffenarten mit denen man diese Faehigkeit ausfuehren kann.
	private Waffenart[] waffenarten;
	// Die Schadensart der Faehigkeit.
	private byte schadensart;

	/* --- Konstruktor --- */
	
	/**
	 * Eine neue Faehigkeit wird mit Namen, NumerusGenus, der Ausgabe, der Schadensart, einem Bonus,
	 * den Kosten und den moeglichen Waffenarten erstellt.
	 * @param name Der Name der Faehigkeit.
	 * @param numerusGenus Der Numerus und Genus der Faehigkeit.
	 * @param ausgabe Die Ausgabe bei der Benutzung der Faehigkeit.
	 * @param schadensart Die Schadensart der Faehigkeit.
	 * @param bonus Der Attributsbonus der Faehigkeit.
	 * @param kosten Die Kosten fuer die Benutzung der Faehigkeit.
	 * @param waffenarten Die Waffentypen mit denen man diese Faehigkeit ausfuehren kann.
	 */
	public Faehigkeit(String name, NumerusGenus numerusGenus, String ausgabe, Schadensart schadensart,
			String bonus, String kosten, Waffenart[] waffenarten) {
		this.name = name;
	    this.numGen = numerusGenus;
	    this.ausgabe = ausgabe;
	    this.schadensart = schadensart.getId();
	    this.bonus = bonus;
	    this.kosten = kosten;
	    this.waffenarten = waffenarten;
	}
	
	/* --- Methoden --- */
	  
	/**
	 * Gibt den Namen der Faehigkeit zurueck.
	 * @return Der Name der Faehigkeit.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gibt den NumerusGenus zurueck.
	 * @return Der NumerusGenus der Faehigkeit.
	 */
	public NumerusGenus getNumGen() {
		return numGen;
	}
	
	/**
	 * Gibt die Ausgabe der Faehigkeit zurueck.
	 * @return Die Ausgabe der Faehigkeit, wenn sie eingesetzt wird.
	 */
	public String getAusgabe() {
		return ausgabe;
	}
	
	/**
	 * Gibt zurueck, ob es sich um die uebergebene Schadensart handelt.
	 * @param schadensart Die Schadensart, auf die ueberprueft wird.
	 * @return Gibt true zurueck, wenn es die gleiche Schadensart ist, ansonsten false.
	 */
	public boolean isSchadensart(Schadensart schadensart) {
		return Schadensart.getSchadensart(this.schadensart) == schadensart ? true : false;
	}
	
	/**
	 * Gibt die Schadensart der Faehigkeit zurueck.
	 * @return Die Schadensart der Faehigkeit.
	 */
	public Schadensart getSchadensart() {
		return Schadensart.getSchadensart(schadensart);
	}
	
	/**
	 * Gibt das Attribut zurueck inklusive des Bonuses.
	 * @param entity Das Entity, fuer das der Bonus berechnet werden soll.
	 * @return Das Attribut und der Bonus der Faehigkeit.
	 */
	public int getBonus(Entity entity) {
		// Hier wird ermittelt, ob es sich um einen prozentualen Bonus handelt
		if(bonus.endsWith("%")) {
			double faktor = Double.valueOf(bonus.substring(0, bonus.length() - 1)) / 100.0 + 1.0;
			return (int)(faktor * Schadensart.getSchadensart(schadensart).getAttribut(entity));
	    }
	    return Schadensart.getSchadensart(schadensart).getAttribut(entity) + Integer.valueOf(bonus);
	}
	
	/**
	 * Gibt einen String mit der kompletten Berechnung des Schadesn zurueck.
	 * @param entity Das Entity fuer das der Schaden berechnet werden soll.
	 * @return Ein String mit allen Schritten der Berechnung.
	 */
	public String getBonusExtended(Entity entity) {
		String ausgabe = Integer.toString(Schadensart.getSchadensart(schadensart).getAttribut(entity))
				+ "(" + Attribut.getAttribut(Schadensart.getSchadensart(schadensart).getAttributsNamen()).getName() + ") ";
		if(bonus.endsWith("%"))
			ausgabe += "+ " + bonus;
		else
			ausgabe += (Integer.valueOf(bonus) < 0) ? bonus : "+ " + bonus;
		ausgabe += " = " + Integer.toString(this.getBonus(entity));
		
		return ausgabe;		
	}
	
	/**
	 * Gibt die Kosten der Faehigkeit zurueck, dazu muss die maximale MP Anzahl uebergeben werden.
	 * @param maxMp Die maximalen MP des Wirkers.
	 * @return Die Kosten fuer das benutzen dieser Faehigkeit.
	 */
	public int getKosten(int maxMp) {
		// Hier wird ermittelt, ob es sich um prozentuale Kosten handelt
		if(kosten.endsWith("%")) {
			double faktor = Double.valueOf(kosten.substring(0, kosten.length() - 1)) / 100.0;
			return (int)(faktor * maxMp);
	    }
	    return Integer.valueOf(kosten);
	}
	  
	/**	
	 * Gibt alle gueltigen Waffenarten zurueck.
	 * @return Alle Waffenarten mit denen man diese Faehigkeit verwenden kann.
	 */
	public Waffenart[] getGueltigeWaffen() {
	    return waffenarten;
	}
	  
	/**
	 * Gibt true zurueck, wenn die uebergebene Waffenarten fuer diese Faehigkeit zulaessig ist.
	 * @param waffen Die Waffen, die der Spieler gerade ausgeruestet hat.
	 * @return Gibt zurueck, ob der Spieler gueltige Waffen verwendet.
	 */
	public boolean gueltigeWaffe(Waffenart[] waffen) {
	    if(waffenarten.length < 1) 
	    	return true;
	    for(Waffenart wa : waffen) {
	    	for(Waffenart wa1 : waffenarten) {
	    		if(wa == wa1) 
	    			return true;
	    	}
	    }
	    return false;
	}
	
}