package game;

import game.items.Inventar;
import game.items.Stapel;

import java.io.Serializable;

/**
 * Ein Behaelter ist ein Objekt an einem Ort, der Gegenstaende beinhaltet.
 */
public class Behaelter implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Die statischen Konstanten --- */
	
	// Der Wert fuer ein Fass.
	public static final byte FASS = 0;
	// Der Wert fuer eine Kiste.
	public static final byte KISTE = 1;
	// Der Wert fuer eine Truhe.
	public static final byte TRUHE = 2;

	/* --- Die Variablen --- */
	
	// Alle Gegenstaende in dem Behaelter.
	private Inventar gegenstaende;
	// Der Typ des Behaelters.
	private int typ;	  
	
	/* --- Die Konstruktoren --- */
	
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
	    for(Stapel s: gegenstaende) {
	    	this.gegenstaende.addGegenstand(s);
	    }
	    this.typ = typ;
	}
	
	/* --- Die Methoden --- */
	  
	/**
	 * Fuegt den Behaelter einen neuen Stapel eines Gegenstands hinzu.
	 * @param stapel Der neue Gegenstandsstapel fuer den Behaelter
	 */
	public void addGegenstand(Stapel... stapel) {
	    for(Stapel s : stapel) {
	    	gegenstaende.addGegenstand(s);
	    }
	}
	
	/**
	 * Entfernt einen Stapel eines Gegenstands aus dem Behaelter.
	 * @param stapel Der Gegenstandsstapel, der aus dem Behaelter entfernt werden soll.
	 */
	public void removeGegenstand(Stapel... stapel) {
		for(Stapel s : stapel) {
			gegenstaende.removeGegenstand(s);
		}	    
	}
	  
	/**
	 * Gibt alle Gegenstaende im Behaelter zurueck.
	 * @return Alle Gegenstaende, die in dem Behaelter sind.
	 */
	public Stapel[] pluendern() {
	    return gegenstaende.getAlleStapel();
	}
	  
	/**
	 * Aendert den Typ des Behaelters zu einem neuen Typ.
	 * @param neuerTyp Der neue Typ des Behaelters.
	 */
	public void setTyp(byte neuerTyp) {
	    this.typ = neuerTyp;
	}
	  
	/**
	 * Gibt den Typ des Behaelters zurueck.
	 * @return Der Typ des Behaelters.
	 */
	public int getTyp() {
	    return typ;
	}
	
}