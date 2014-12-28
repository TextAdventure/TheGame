package game;

import gui.GUI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Vector;

import util.StringEvent;

/**
 *  Diese Klasse testet das Spiel und fuehrt es aus.
 */
public class SpielTest {
	// Die Datei, die geladen werden soll.
	private static final String datei = "NeueWelt.dat";
	// Die SpielWelt, die geladen wird.
	private SpielWelt welt;
	// Zufallszahlengenerator
	private Random r;
	// Die Spiel-GUI
	private GUI gui;
	  
	// Die Booleans fuer die Abfrage.
	private boolean gueltigesKommando;
	  
	/**
	 *  Der Konstruktor fuer einen neuen SpielTest, der InpuStream fuer den Test, der OutpuStream fuer die SpielWelt.
	 */
	public SpielTest(GUI gui){
		this.gui = gui;
	  
	    // Es wird erst ein File-, dann ein ObjectInputStream erstellt, um die Daten zu laden.
	    try{
	    	FileInputStream fis = new FileInputStream(datei);
	    	ObjectInputStream ois = new ObjectInputStream(fis);
	    	welt = (SpielWelt) ois.readObject();
	    	welt.setGUI(gui);
	    	welt.updateGegenstandsListe();
	    	welt.getSpieler().getInventar().addListener(gui.getEingabefeld());
	    	ois.close();
	    }catch(IOException e){
	    	System.err.println("Die Datei konnte nicht richtig geladen werden.");
	    	e.printStackTrace();
	    }catch(ClassNotFoundException e){
	    	System.err.println("Die Klasse wurde nicht gefunden.");
	    }
	    
	    r = new Random();
	}
	  
	public void ueberpruefeBefehl(String befehl){
		// Wenn der Spieler sich im Kampf oder im Gespräch mit einem NPC befindet, dann wird der Befehl direkt 
		//an die Welt weitergegeben.
	    if(welt.spielerKaempft() || welt.spielerSpricht()){
	    	welt.actionPerformed(new StringEvent(befehl));
	    	return;
	    }
	    
	    // Die Position des Spielers anzeigen.
	    welt.spielerPositionAnzeigen();
	    
	    // Die Booleans werden zurueckgesetzt.
	    gueltigesKommando = false;
	    
	    // Die Eingabe des Spielers wird ueberprueft.
	    if(befehl.length() == 0){
	    	gueltigesKommando = true;
	    }
	    
	    // Alle Befehle werden als Grossbuchstaben verglichen, um Schreibfehler zu korrigieren.
	    
	    // Uberprueft, ob es an diesem Ort Custom Commands gibt.
	    if(welt.sucheKommando(befehl)){
	    	gueltigesKommando = true;
	    }
	    // Globale Kommandos, die hinzugefuegt wurden.
	    if(welt.ueberpruefeKommandos(befehl)){
	    	gueltigesKommando = true;
	    }
	    
	    Kommando kommando = Kommando.getKommando(befehl);
	    String eingabe = Kommando.getEingabe();
	    
	    if(kommando != Kommando.INVALID && !gueltigesKommando){
	    	// Der Spieler hat den Untersuchen Befehl eingegeben.
	    	if(kommando == Kommando.UNTERSUCHEN){
	    		welt.sucheUntersuchbaresObjekt(eingabe);
	    	}
	    	// Der Spieler hat den Nehmen Befehl eingegeben.
	    	if(kommando == Kommando.NEHMEN){
	    		welt.sucheGegenstand(eingabe);
	    	}
	    	// Der Spieler hat den Verwenden Befehl eingegeben.
	    	if(kommando == Kommando.VERWENDEN){
	    		welt.verwendeGegenstand(Gegenstand.getGegenstand(eingabe));
	    	}
	    	// Der Spieler hat den Kombinieren Befehl eingegeben.
	    	if(kommando == Kommando.KOMBINIEREN){
	    		//welt.kombiniere(eingabe.split(", "));
	    		gui.kombinationsGUIStarten();
	    	}
	    	// Der Spieler hat den Ausruesten Befehl eingegeben.
	    	if(kommando == Kommando.AUSRUESTEN){
	    		welt.ausruesten(eingabe);
	    	}
	    	// Der Spieler hat den Ablegen Befehl eingegeben.
	    	if(kommando == Kommando.ABLEGEN){
	    		welt.ablegen(eingabe);
	    	}
	    	// Der Spieler hat den Ausruestung Befehl eingegeben.
	    	if(kommando == Kommando.AUSRUESTUNG){
	    		welt.zeigeAusruestungAn();
	    	}
	    	// Der Spieler hat den Status Befehl eingegeben.
	    	if(kommando == Kommando.STATUS){
	    		welt.zeigeStatusAn();
	    	}
	    	// Der Spieler hat den Info Befehl eingegeben.
	    	if(kommando == Kommando.INFO){
	    		Gegenstand g = Gegenstand.getGegenstand(eingabe);
	    		welt.zeigeGegenstandInfosAn(g);
	    	}
	    	// Der Spieler hat den Oeffnen Befehl eingegeben.
	    	if(kommando == Kommando.OEFFNEN){
	    		welt.sucheBehaelter(eingabe);
	    	}
	    	welt.println();
	    	gueltigesKommando = true;
	    }
	    
	    // Die laden/speichern Kommandos
	    if(befehl.equalsIgnoreCase("speichere")){
	    	String pfad = befehl.substring(befehl.indexOf(' ')) + ".sav";
	    	try{
	    		FileOutputStream fos = new FileOutputStream(pfad);
	    		ObjectOutputStream oos = new ObjectOutputStream(fos);
	    		oos.writeObject(welt);
	    		oos.close();
	    		welt.println();
	    		welt.println("Der Spielstand wurde in " + pfad + " gespeichert.");
	    		welt.println();
	    	}catch(IOException e){
	    	  System.err.println("Es trat ein Fehler beim Laden eines Spielstands auf.");
	    	}
	    }
	    
	    if(befehl.equalsIgnoreCase("lade")){
	    	String pfad = befehl.substring(befehl.indexOf(' ')) + ".sav";
	    	try{
	    		FileInputStream fis = new FileInputStream(pfad);
	    		ObjectInputStream ois = new ObjectInputStream(fis);
	    		welt = (SpielWelt)ois.readObject();
	    		welt.setGUI(gui);
	    		welt.println("\nEs wurde das Spiel aus " + pfad + " geladen.\n");
	    		welt.updateGegenstandsListe();
	    		welt.getSpieler().getInventar().addListener(gui.getEingabefeld());
	    		ois.close();
	    	}catch(IOException e){
	    		System.err.println("Es trat ein Fehler beim Laden eines Spielstands auf.");
	    	}catch(ClassNotFoundException e){
	    		System.err.println("Die Klasse wurde nicht gefunden.");
	    	}
	    }

	    // Es wird eine Kopie des Ausgangslisten Vectors angefordert.
	    Vector<Ausgang> ausgaenge = welt.aktuellePosition().getAusgaenge();

	    // Alle Ausgaenge werden ueberprueft, ob sie uebereinstimmen.
	    for(Ausgang a: ausgaenge.toArray(new Ausgang[0])){
	    	if(a.getRichtungsName().equalsIgnoreCase(befehl) || a.getAbkuerzung().equalsIgnoreCase(befehl)){
	    		// Der Spieler "bewegt" sich durch den Ausgang zu dem Zielort.
	    		welt.setAktuellePositon(a.getZielort());
	        
	    		// Die Position des Spielers anzeigen, da sie sich veraendert hat.
	    		if(!welt.spielerKaempft()) welt.spielerPositionAnzeigen();

	    		// Ein gueltiges Kommando wurde eingelesen.
	    		gueltigesKommando = true;

	    		// Die Suche kann gestoppt werden, da ein Ergebnis gefunden wurde.
	    		break;
	    	}
	    }

	    // Uebreprueft, ob der Spieler das Spiel beenden will.
	    if(befehl.equalsIgnoreCase("BEENDEN") | befehl.equalsIgnoreCase("BEENDE")){
	    	System.exit(0);
	    }
	    
	    // Der Spieler wird gewarnt, dass das Kommando ungueltig ist.
	    if(!gueltigesKommando){
	    	switch(r.nextInt(5)){
	    		case(0): welt.println("ungültiges Kommando"); break;
	    		case(1): welt.println("kein gültiges Kommando"); break;
	    		case(2): welt.println("Dieses Kommando ist ungültig."); break;
	    		case(3): welt.println("Nur gültige Kommandos können ausgeführt werden."); break;
	    		case(4): welt.println("Dieses Kommando kann nicht ausgeführt werden."); break;
	    	}	
	    	welt.println("\n");
	    }
	}
	  
	// Gibt die Welt zurueck.
	public SpielWelt getWelt(){
		return welt;
	}
}