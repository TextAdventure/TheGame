package game;

import java.io.Serializable;

import util.NumerusGenus;

/**
 * Eine Faehigkeit, die im Kampf verwendet werden kann.
 */
public class Faehigkeit implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Die Konstante fuer eine physische Faehigkeit.
	public static final byte PHYSISCH = 0;
	// Die Konstante fuer eine magische Faehigkeit;
	public static final byte MAGISCH = 1;
	  
	// Der Name der Faehigkeit.
	private String name;
	// Der Numerus/Genus der Faehigkeit
	private NumerusGenus numGen;
	// Die Ausgabe, die angezeigt wird, wenn die FAehigkeit ausgefuehrt wird.
	private String ausgabe;
	// Die Schadensart der Faehigkeit.
	private byte schadensart;
	// Der Attributsbonus fuer die Faehigkeit. (MAGISCH => mag. Ang; PHYSISCH => Ang) mit "100%" wird das Attribut verdoppelt
	private String bonus;
	// Die Kosten fuer die Faehigkeit.
	private String kosten;
	// Alle Waffenarten mit denen man diese Faehigkeit ausfuehren kann.
	private byte[] waffenarten;

	/**
	 *  Eine neue Faehigkeit wird mit Namen, NumerusGenus, der Ausgabe, der Schadensart, einem Bonus, den Kosten und der benoetigten Waffenart.
	 */
	public Faehigkeit(String name, NumerusGenus numerusGenus, String ausgabe, byte schadensart, String bonus, String kosten, byte[] waffenarten){
		this.name = name;
	    this.numGen = numerusGenus;
	    this.ausgabe = ausgabe;
	    this.schadensart = schadensart;
	    this.bonus = bonus;
	    this.kosten = kosten;
	    this.waffenarten = waffenarten;
	}
	  
	// Diese Methode gibt den Namen der Faehigkeit zurueck.
	public String getName(){return name;}
	// Diese Methode gibt den Numerus/Genus zurueck.
	public NumerusGenus getNumGen(){return numGen;}
	// Diese Methode gibt die Ausgabe der Faehigkeit zurueck.
	public String getAusgabe(){return ausgabe;}
	// Diese Methode gibt zurueck, ob es sich um physischen Schaden handelt.
	public boolean isPhysisch(){
		if(schadensart == PHYSISCH) return true;
	    else return false;
	}
	/*
	 *  Diese Methode gibt das Attribut zurueck inklusive dem Bonus.
	 *  attribut: das Attribut, von dem diese Faehigkeit abheangig ist, des Wirkers
	 */
	public int getBonus(int attribut){
		// Hier wird ermittelt, ob es sich um einen prozentualen Bonus handelt
		if(bonus.endsWith("%")){
			double faktor = Double.valueOf(bonus.substring(0, bonus.length() - 1)) / 100.0 + 1.0;
			return (int)(faktor * attribut);
	    }
	    return attribut + Integer.valueOf(bonus);
	}
	/*
	 *  Diese Methode gibt die Kosten der Faehigkeit zurueck, dazu muss die maximale MP Anzahl uebergeben werden.
	 *  maMp: die maximalen MP des Wirkers
	 */
	public int getKosten(int maxMp){
		// Hier wird ermittelt, ob es sich um prozentuale Kosten handelt
		if(kosten.endsWith("%")){
			double faktor = Double.valueOf(kosten.substring(0, kosten.length() - 1)) / 100.0;
			return (int)(faktor * maxMp);
	    }
	    return Integer.valueOf(kosten);
	}
	  
	/*
	 *  Diese Methode gibt alle gueltigen Waffenarten zurueck.
	 */
	public byte[] getGueltigeWaffen(){
	    return waffenarten;
	}
	  
	/*
	 *  Diese Methode gibt true zurueck, wenn die uebergebene Waffenart fuer diese Faehigkeit zulaessig ist.
	 *  waffen: die Waffen, die der Spieler ausgerüstet hat
	 */
	public boolean gueltigeWaffe(byte[] waffen){
	    if(waffenarten.length < 1) return true;
	    for(byte b: waffen){
	    	for(byte b1: waffenarten){
	    		if(b == b1) return true;
	    	}
	    }
	    return false;
	}
}