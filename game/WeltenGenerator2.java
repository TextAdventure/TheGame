package game;

import game.aktion.AddAusgangAktion;
import game.bedingung.SpielerHasGegenstandBedingung;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


import util.NumerusGenus;

/**
 *  Diese Klasse erstellt ein neues Spiel und speichert es in einer Datei. Geschrieben von Felix.
 */
public final class WeltenGenerator2 {
	// Der Name der Datei, in der der  Spielstand gespeichert wird.
	public static String dateiName = "spielstand.dat";
	  
	/**
	 *  Wenn dieses Programm ausgefuehrt wird, erzeugt es eine neue Welt in der angegebenen Datei.
	 */
	public static void main(String[] args){
		// Die Welt, die generiert wird.
	    SpielWelt welt = new SpielWelt();
	    
	    // alle Gegenstaende
	    VerwendbarerGegenstand schnitzel = new VerwendbarerGegenstand(new String[]{"Schnitzel", "Essen", "Futter"}, NumerusGenus.NEUTRAL, "Ein leckeres, saftiges Schnitzel.", new Effekt(Effekt.HEILEN, ""), VerwendbarerGegenstand.UEBERALL);
	    Gegenstand werkzeug = new Gegenstand(new String[]{"Schraubenschlüssel"}, NumerusGenus.MASKULIN, "Ein wahrhaft praktisches Werkzeug.");
	    
	    // alle Orte
	    Ort aussichtsplattform = new Ort("Aussichtsplattform", "Du bist auf der Aussichtsplattform an Deck des Schiffes. Über dir ziehen die Wolken dahin.");
	    aussichtsplattform.addUntersuchbaresObjekt("Wolken", "Die Wolken schweben am Himmel üder dir. Duu wünschst dir, du könntest fliegen...");
	    
	    Ort treppenhaus1 = new Ort("Treppenhaus 1. Unterdeck", "Von hier geht es in den Speißesaal oder noch weiter nach unten.");
	    Ort speissesaal = new Ort("Speißesaal", "Heute gibt es Schnitzel");
	    Ort treppenhaus2 = new Ort("Treppenhaus 2. Unterdeck", "Hier sollten normale Passagiere eigentlich nicht sein...");
	    Ort maschinenraum = new Ort("Maschinenraum", "Zwischen den riesigen Motoren ist es ohrenbetäubend laut. Du entdeckst einen Schraubenschlüssel, den wohl ein Mechaniker vergessen hat. Damit könnte man hier einiges zerlegen...");
	    maschinenraum.addUntersuchbaresObjekt("Motoren", "Die riesigen Motoren sind sehr laut. Sie treiben das ganze Schiff. Es wäre sehr dumm hier etwas kaputt zu machen");
	    
	    Ort geheimKammer = new Ort("Kleine Kammer", "Wofür dieser Raum wohl dient? Die Bodenplatte ist mit wenigen Schrauben befestigt.");
	    
	    
	    //Ausgäng und Co
	    aussichtsplattform.addAusgang(Ausgang.RUNTER, treppenhaus1);
	    
	    treppenhaus1.addAusgang(Ausgang.HOCH, aussichtsplattform);
	    treppenhaus1.addAusgang(Ausgang.BETRETEN, speissesaal);
	    treppenhaus1.addAusgang(Ausgang.RUNTER, treppenhaus2);
	    
	    speissesaal.addAusgang(Ausgang.VERLASSEN, treppenhaus1);
	    speissesaal.addGegenstand(schnitzel, 5);
	    
	    treppenhaus2.addAusgang(Ausgang.HOCH, treppenhaus1);
	    treppenhaus2.addAusgang(Ausgang.BETRETEN, maschinenraum);
	    
	    maschinenraum.addAusgang(Ausgang.VERLASSEN, treppenhaus2);
	    maschinenraum.addGegenstand(werkzeug, 1);
	    maschinenraum.addKommandoGegenstand(werkzeug, new SpielerHasGegenstandBedingung(welt, werkzeug, new AddAusgangAktion(maschinenraum, new Ausgang(Ausgang.BETRETEN, geheimKammer), "Du hast eine Abdeckung aufgeschraubt und einen kleinen Raum dahinter entdeckt.")));
	    
	    geheimKammer.addAusgang(Ausgang.VERLASSEN, maschinenraum);


	    
	    welt.setAktuellePositon(aussichtsplattform);
	     
	    /**
	     *  Das Programm schreibt die SpielWelt in die angegebene Datei.
	     */
	    try{
	    	FileOutputStream fos = new FileOutputStream(dateiName);
	    	// Der OutputStream kann nun Objekte schreiben.
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    	// Die SpielWelt wird in die Datei gespeichert.
	    	oos.writeObject(welt);
	    	// Der OutputStream wird geschlossen.
	    	oos.close();
	      
	    	// Meldung, das die SpielWelt erstellt wurde.
	    	System.out.println("Eine neue Spielwelt wurde in " + dateiName + " erstellt.");
	    }catch(IOException e){
	    	e.printStackTrace();
	    	System.err.println("ACHTUNG: Es konnte keine neue Spielwelt erstellt werden.");
	    }
	}	
}