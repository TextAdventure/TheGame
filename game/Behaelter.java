package game;

import java.io.Serializable;

/**
 * Ein Behaelter ist ein Objekt an einem Ort, der Gegenstaende beinhaltet.
 */
public class Behaelter implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	// Statische Variablen fuer alle moeglichen Typen von Behaelter.
	// Der Wert fuer ein Fass.
	public static final byte FASS = 0;
	// Der Wert fuer eine Kiste.
	public static final byte KISTE = 1;
	// Der Wert fuer eine Truhe.
	public static final byte TRUHE = 2;

	// Alle Gegenstaende in dem Behaelter.
	private Inventar gegenstaende;
	// Der Typ des Behaelters.
	private int typ;
	  
	// Ein neuer Behaelter wird leer erstellt und es wird der Typ uebergeben.
	public Behaelter(byte typ){
	    gegenstaende = new Inventar();
	    this.typ = typ;
	}
	  
	// Ein neuer Behaelter wird mit Gegenstaenden erstellt und es wird ein Typ uebergeben.
	public Behaelter(byte typ, Stapel... gegenstaende){
	    this.gegenstaende = new Inventar();
	    for(Stapel s: gegenstaende){
	    	this.gegenstaende.addGegenstand(s);
	    }
	    this.typ = typ;
	}
	  
	// Fuegt den Behaelter einen neuen Gegenstand hinzu.
	public void addGegenstand(Stapel gegenstand){
	    gegenstaende.addGegenstand(gegenstand);
	}
	// Entfernt einen Gegenstand aus dem Behaelter.
	public void removeGegenstand(Stapel gegenstand){
	    gegenstaende.removeGegenstand(gegenstand);
	}
	  
	// Gibt alle Gegenstaende im Behaelter zurueck.
	public Stapel[] pluendern(){
	    return gegenstaende.getAlleStapel();
	}
	  
	// Aendert den Typ des Behaelters auf den uebergebenen Wert.
	public void setTyp(byte neuerTyp){
	    this.typ = neuerTyp;
	}
	  
	// Gibt den Typ des Behaelters zurueck.
	public int getTyp(){
	    return typ;
	}
}