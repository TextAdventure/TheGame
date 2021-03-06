package game;

import game.items.Gegenstand;
import gui.GUI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Vector;

/**
 * Interpretiert die Eingabe des Spielers und uebermittelt sie an die SpielWelt.
 * @author Marvin
 */
public class Interpreter {
	
	/* --- Variablen --- */
	
	// Die Datei, die geladen werden soll.
	private static final String datei = "spielstand.dat";
	// Die SpielWelt, die geladen wird.
	private transient SpielWelt welt;
	// Zufallszahlengenerator
	private Random r;
	// Die Spiel-GUI
	private GUI gui;

	// Die Booleans fuer die Abfrage.
	private boolean gueltigesKommando;

	/* --- Konstruktor --- */
	
	/**
	 * Erstellt einen neuen Interpreter fuer die Eingabe des Spielers, der sie dann weitergibt an die SpielWelt.
	 * @param gui Die GUI, die diesen Interpreter erstellt hat.
	 */
	public Interpreter(GUI gui) {
		this.gui = gui;

	    // Es wird erst ein File-, dann ein ObjectInputStream erstellt, um die Daten zu laden.
	    try {
	    	FileInputStream fis = new FileInputStream(datei);
	    	ObjectInputStream ois = new ObjectInputStream(fis);
	    	welt = (SpielWelt) ois.readObject();
	    	welt.updateListen();
	    	welt.setGUI(gui);
	    	welt.getSpieler().resetTemp();

	    	ois.close();
	    } catch(IOException e) {
	    	System.err.println("Die Datei konnte nicht richtig geladen werden.");
	    	e.printStackTrace();
	    } catch(ClassNotFoundException e) {
	    	System.err.println("Die Klasse wurde nicht gefunden.");
	    }

	    r = new Random();
	}

	/* --- Methoden --- */
	
	/**
	 * Testet einen Befehl und gibt dann die noetigen Befehle an die SpielWelt weiter.
	 * @param befehl Die Eingabe des Spielers.
	 */
	public void ueberpruefeBefehl(String befehl) {
		// Wenn der Spieler sich im Kampf oder im Gespraech mit einem NPC befindet, dann wird der Befehl direkt
		// an die Welt weitergegeben.
	    if(welt.spielerKaempft() || welt.spielerSpricht()) {
	    	welt.actionPerformed(befehl);
	    	return;
	    }

	    // Die Position des Spielers anzeigen.
	    welt.spielerPositionAnzeigen(welt.ereignis);
	    if(welt.ereignis)
	    	welt.ereignis = false;

	    // Die Booleans werden zurueckgesetzt.
	    gueltigesKommando = false;

	    // Die Eingabe des Spielers wird ueberprueft.
	    if(befehl.length() == 0)
	    	gueltigesKommando = true;
	    
	    // Alle Befehle werden als Grossbuchstaben verglichen, um Schreibfehler zu korrigieren.

	    // Uberprueft, ob es an diesem Ort eigene Kommandos gibt.
	    if(welt.sucheKommando(befehl))
	    	gueltigesKommando = true;
	    
	    // Globale Kommandos, die hinzugefuegt wurden.
	    if(welt.ueberpruefeKommandos(befehl))
	    	gueltigesKommando = true;

	    Kommando kommando = Kommando.getKommando(befehl);
	    String eingabe = Kommando.getEingabe().trim();

	    if(kommando != Kommando.INVALID && !gueltigesKommando) {
	    	// Der Spieler hat den Untersuchen Befehl eingegeben.
	    	if(kommando == Kommando.UNTERSUCHEN)
	    		welt.sucheUntersuchbaresObjekt(eingabe);
	    	
	    	// Der Spieler hat den Nehmen Befehl eingegeben.
	    	if(kommando == Kommando.NEHMEN)
	    		welt.sucheGegenstand(eingabe);
	    	
	    	// Der Spieler hat den Verwenden Befehl eingegeben.
	    	if(kommando == Kommando.VERWENDEN)
	    		welt.verwendeGegenstand(Gegenstand.getGegenstand(eingabe));
	    	
	    	// Der Spieler hat den Kombinieren Befehl eingegeben.
	    	if(kommando == Kommando.KOMBINIEREN)
	    		gui.kombinationsGUIStarten();
	    	
	    	// Der Spieler hat den Ausruesten Befehl eingegeben.
	    	if(kommando == Kommando.AUSRUESTEN)
	    		welt.ausruesten(eingabe);
	    	
	    	// Der Spieler hat den Ablegen Befehl eingegeben.
	    	if(kommando == Kommando.ABLEGEN)
	    		welt.ablegen(eingabe);
	    	
	    	// Der Spieler hat den Ausruestung Befehl eingegeben.
	    	if(kommando == Kommando.AUSRUESTUNG)
	    		welt.zeigeAusruestungAn();
	    	
	    	// Der Spieler hat den Status Befehl eingegeben.
	    	if(kommando == Kommando.STATUS)
	    		welt.zeigeStatusAn();
	    	
	    	// Der Spieler hat den Info Befehl eingegeben.
	    	if(kommando == Kommando.INFO) {
	    		Gegenstand g = Gegenstand.getGegenstand(eingabe);
	    		welt.zeigeGegenstandInfosAn(g);
	    	}
	    	
	    	// Der Spieler hat den Oeffnen Befehl eingegeben.
	    	if(kommando == Kommando.OEFFNEN)
	    		welt.sucheBehaelter(eingabe);
	    	
	    	// Der Spieler hat den Wegwerfen Befehl eingegeben.
	    	if(kommando == Kommando.WEGWERFEN)
	    		welt.werfeWeg(eingabe);
	    	
	    	if(kommando == Kommando.OPTIONEN)
	    		gui.zeigeOptionenAn();
	    	
	    	welt.println();
	    	gueltigesKommando = true;
	    }

	    // Die laden/speichern Kommandos
	    if(befehl.startsWith("speichere")) {
	    	String pfad = befehl.substring(befehl.indexOf(' ')).trim();
	    	if(pfad.isEmpty())
	    		pfad = "auto";
	    	pfad += ".sav";

	    	try {
	    		FileOutputStream fos = new FileOutputStream(pfad);
	    		ObjectOutputStream oos = new ObjectOutputStream(fos);
	    		oos.writeObject(welt);
	    		oos.close();
	    		welt.print("Der Spielstand wurde in " + pfad + " gespeichert.");
	    		gueltigesKommando = true;
	    	} catch(IOException e) {
	    	  System.err.println("Es trat ein Fehler beim Speichern eines Spielstands auf.");
	    	  e.printStackTrace();
	    	}
	    }

	    if(befehl.startsWith("lade")) {
	    	String pfad = befehl.substring(befehl.indexOf(' ')).trim() + ".sav";
	    	try {
	    		FileInputStream fis = new FileInputStream(pfad);
	    		ObjectInputStream ois = new ObjectInputStream(fis);
	    		welt = (SpielWelt)ois.readObject();
	    		ois.close();
	    		welt.updateListen();
	    		welt.setGUI(gui);
	    		welt.print("Es wurde das Spiel aus " + pfad + " geladen.");
		    	welt.getSpieler().resetTemp();

	    		gueltigesKommando = true;
	    	} catch(IOException e) {
	    		System.err.println("Es trat ein Fehler beim Laden eines Spielstands auf.");
	    		e.printStackTrace();
	    	} catch(ClassNotFoundException e) {
	    		System.err.println("Die Klasse wurde nicht gefunden.");
	    	}
	    }

	    // Es wird eine Kopie des Ausgangslisten Vectors angefordert.
	    Vector<Ausgang> ausgaenge = welt.getAktuellePosition().getAusgaenge();

	    // Alle Ausgaenge werden ueberprueft, ob sie uebereinstimmen.
	    for(Ausgang a : ausgaenge.toArray(new Ausgang[0])) {
	    	if(a.getRichtungsName().equalsIgnoreCase(befehl) || a.getAbkuerzung().equalsIgnoreCase(befehl)) {
	    		// Der Spieler "bewegt" sich durch den Ausgang zu dem Zielort.
	    		
	    		welt.setAktuellePositon(a.getZielort());
	    		
	    		// Die Position des Spielers anzeigen, da sie sich veraendert hat, ausser der Spieler befindet sich im Kampf.
	    		if(!welt.spielerKaempft())
		    		welt.spielerPositionAnzeigen(true);

	    		// Ein gueltiges Kommando wurde eingelesen.
	    		gueltigesKommando = true;

	    		// Die Suche kann gestoppt werden, da ein Ergebnis gefunden wurde.
	    		break;
	    	}
	    }

	    // Uebreprueft, ob der Spieler das Spiel beenden will.
	    if(befehl.equalsIgnoreCase("BEENDEN") | befehl.equalsIgnoreCase("BEENDE"))
	    	System.exit(0);

	    // Der Spieler wird gewarnt, dass das Kommando ungueltig ist.
	    if(!gueltigesKommando) {
	    	switch(r.nextInt(5)) {
	    		case(0): welt.print("ung�ltiges Kommando"); break;
	    		case(1): welt.print("kein g�ltiges Kommando"); break;
	    		case(2): welt.print("Dieses Kommando ist ung�ltig."); break;
	    		case(3): welt.print("Nur g�ltige Kommandos k�nnen ausgef�hrt werden."); break;
	    		case(4): welt.print("Dieses Kommando kann nicht ausgef�hrt werden."); break;
	    	}
	    	welt.println("\n");
	    }
	}
	
}