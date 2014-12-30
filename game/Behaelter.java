package game;

import game.items.Inventar;
import game.items.Stapel;

import java.io.Serializable;

/**
 * Ein Behaelter ist ein Objekt an einem Ort, der Gegenstaende beinhaltet. Dieser kann geleert
 * werden, sodass der Spieler die Gegenstaende darin erhaelt.
 * @author Marvin
 */
public class Behaelter implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- statischen Konstanten --- */
	
	// Der Wert fuer ein Fass.
	public static final byte FASS = 0;
	// Der Wert fuer eine Kiste.
	public static final byte KISTE = 1;
	// Der Wert fuer eine Truhe.
	public static final byte TRUHE = 2;
	
	/* --- Variablen --- */
	
	// Alle Gegenstaende in dem Behaelter.
	private Inventar gegenstaende;
	// Der Typ des Behaelters.
	private int typ;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein neuer Behaelter wird leer erstellt und es wird der Typ uebergeben.
	 * @param typ Die Art des Behaelters.
	 */
	public Behaelter(byte typ) {
	    gegenstaende = new Inventar();
	    this.typ = typ;
	}
	
	/**
	 * Ein neuer Behaelter wird mit Gegenstaenden erstellt und es wird ein Typ uebergeben.
	 * @param typ Die Art des Behaelters.
	 * @param gegenstaende Die Gegenstaende, die sich in dem Behaelter befinden.
	 */
	public Behaelter(byte typ, Stapel... gegenstaende) {
	    this.gegenstaende = new Inventar();
	    for(Stapel s: gegenstaende)
	    	this.gegenstaende.addGegenstand(s);
	    this.typ = typ;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Fuegt den Behaelter neue Stapel hinzu.
	 * @param stapel Die neuen Stapel im Behaelter.
	 */
	public void addGegenstand(Stapel... stapel) {
	    for(Stapel s : stapel)
	    	gegenstaende.addGegenstand(s);
	}
	
	/**
	 * Entfernt Stapel aus dem Behaelter.
	 * @param stapel Die Stapel, die aus dem Behaelter entfernt werden.
	 */
	public void removeGegenstand(Stapel... stapel) {
		for(Stapel s : stapel)
			gegenstaende.removeGegenstand(s);
	}
	
	/**
	 * Gibt alle Gegenstaende im Behaelter zurueck.
	 * @return Alle Gegenstaende, die in dem Behaelter sind.
	 */
	public Stapel[] pluendern() {
	    return gegenstaende.getAlleStapel();
	}
	
	/**
	 * Gibt den Typ des Behaelters zurueck.
	 * @return Den Typ des Behaelters.
	 */
	public int getTyp() {
	    return typ;
	}
	
	/**
	 * Aendert den Typ des Behaelters.
	 * @param neuerTyp Der neue Typ des Behaelters.
	 */
	public void setTyp(byte neuerTyp) {
	    this.typ = neuerTyp;
	}

}