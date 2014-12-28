package worldgen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import game.*;
import game.entity.Attribut;
import game.items.Gegenstand;
import game.items.KommandoGegenstand;
import game.items.Waehrung;
import game.logic.*;
import game.logic.aktion.*;
import game.logic.bedingung.*;
import util.Farbe;
import util.NumerusGenus;

/**
 *  Diese Klasse erstellt ein neues Spiel und speichert es in einer Datei.
 */
public final class WeltenGenerator {
	// Der Name der Datei, in der der Spielstand gespeichert wird.
	public static String dateiName = "spielstand.dat";
	  
	/**
	 *  Wenn dieses Programm ausgefuehrt wird, erzeugt es eine neue Welt in der angegebenen Datei.
	 */
	public static void main(String[] args) {		
		
		SpielWelt welt = new SpielWelt();
		
		Farbe f = new Farbe("heiﬂ", 220, 100, 50);
		
		Ort ort1 = new Ort("<c=heiﬂ>Ort 1</c>", "Das ist der erste Ort.");
		Ort ort2 = new Ort("Ort 2", "Das ist der 2. Ort.");
		Ort ort3 = new Ort("Der Ort", "Ein ganz besonderer Ort.");
		Ort ort4 = new Ort("Ein weiterer Ort", "Ein Ort");
		
		ort1.addAusgang(Ausgang.NORDEN, ort2);
		ort2.addAusgang(Ausgang.SUEDEN, ort1);
		
		ort2.addAusgang(Ausgang.NORDEN, ort3);
		ort3.addAusgang(Ausgang.SUEDEN, ort2);
		
		ort3.addAusgang(Ausgang.NORDEN, ort4);
		ort4.addAusgang(Ausgang.SUEDEN, ort3);
		
		ort4.addAusgang(Ausgang.NORDEN, ort1);
		ort1.addAusgang(Ausgang.SUEDEN, ort4);
		
		
		welt.setAktuellePositon(ort1);
	    
	    /**
	     *  Das Programm schreibt die SpielWelt in die angegebene Datei.
	     */
	    try {
	    	FileOutputStream fos = new FileOutputStream(dateiName);
	    	// Der OutputStream kann nun Objekte schreiben.
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    	// Die SpielWelt wird in die Datei gespeichert.
	    	oos.writeObject(welt);
	    	// Der OutputStream wird geschlossen.
	    	oos.close();
	      
	    	// Meldung, das die SpielWelt erstellt wurde.
	    	System.out.println("Eine neue Spielwelt wurde in " + dateiName + " erstellt.");
	    } catch(IOException e) {
	    	e.printStackTrace();
	    	System.err.println("ACHTUNG: Es konnte keine neue Spielwelt erstellt werden.");
	    }
	}	
}