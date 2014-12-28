package npc;

import game.SpielWelt;

import java.io.Serializable;
import java.util.Vector;


import util.NumerusGenus;

/** 
 * Prototyp eines NPCs, mit dem man reden kann. 
 * Die Methode beginneGespraech muss für den jeweiligen konkret realisierten NPC implementiert werden.
 */
public abstract class NPC implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private final NumerusGenus geschlecht;
	private boolean imGespraech = false;
	private int beziehung = 0;
	/*
	 * Letzteres kann vom Prgrammierer benutzt werden, um dem NPC mehr Plastizität zu verleihen. So kann bspw.
	 * wiederholtes Beleidigen durch den Spieler durch verändern dieses Werts gespeichert werden. In Gesprächen
	 * kann dann darauf zugegriffen werden und der Gesprächsverlauf kann entsprechende geändert werden.
	 */
	private SpielWelt welt;
	
	private Vector<GespraechListener> listeners = new Vector<GespraechListener>();
	
	
	//Spezielle Gesprächsstadien
	private Status startGespraech;		//enthält alle Gesprächsstart-Signale
	private Status endeGespraech;		//enthält alle Gesprächsende-Signale
	private Status aktuell;				//der aktuelle Gesprächszustand
	private String[] keineAhnung;		//Phrasen, die benutzt werden, wenn der Spieler Unsinn eingibt
	
	 
	public NPC (SpielWelt welt, String name, NumerusGenus geschlecht){
		this.welt = welt;
	    this.name = name;
	    this.geschlecht = geschlecht;
	}
	
	
	
	/* Ein paar Getter und Setter */
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	public NumerusGenus getGeschlecht(){return geschlecht;}
	
	public boolean imGespraech() {return imGespraech;}
	public void setImGespraech(boolean b) {
		this.imGespraech = b;
		if(b)	for(GespraechListener l : listeners) l.gespraechStart();
		else 	for(GespraechListener l : listeners) l.gespraechEnde();
	}
	
	public void setStatus(Status aktuell) {this.aktuell = aktuell;}
	
	public int getBeziehung() {return beziehung;}
	public void setBeziehung(int beziehung) {this.beziehung = beziehung;}
	public void changeBeziehung(int anzahl) {beziehung+=anzahl;}
	
	public void setEndeGespraech(Status endeGespraech) {
		this.endeGespraech = endeGespraech;
	}
	public Status getEndeGespraech() {
		return endeGespraech;
	}
	
	public void setStartGespraech(Status startGespraech) {
		this.startGespraech = startGespraech;
	}
	public Status getStartGespraech() {
		return startGespraech;
	}

	public void setKeineAhnung(String[] keineAhnung) {
		this.keineAhnung = keineAhnung;
	}
	public String[] getKeineAhnung() {
		return keineAhnung;
	}
	
	
	public void addGespraechListener(GespraechListener l) {
		listeners.add(l);
	}



	/**
	 * Übergibt dem NPC einen text, der an ihn gerichtet ist. Gibt die Antwort des NPCs zurück.
	 * @return Ob der NPC angesprochen wurde.
	 */
	public boolean ansprechen(String spielerAntwort) {
		String text = spielerAntwort.toLowerCase();
		if(imGespraech) {
			Status ende = endeGespraech.antworte(text);
			if(ende != null) {
				//noch läuft ein Gespräch, das will der Spieler aber jetzt beenden
				setImGespraech(false);
				prompt(spielerAntwort, ende.getText());
				return false;
				
			} else {
				//Es läuft ein Gespräch, dass nicht beendet werden soll --> übergib dem aktuellen Status die Spieler-Antwort
				Status neu = aktuell.antworte(text);
				if(neu == null) {
					//Spieler nicht verstanden
					int index = (int)(keineAhnung.length*Math.random());
					prompt(spielerAntwort, keineAhnung[index]);
					return true;
				} else {
					aktuell = neu;
					prompt(spielerAntwort, aktuell.getText());
					return imGespraech;
				}
			}
			
		} else {
			//es läuft aktuell kein Gespräch
			aktuell = startGespraech.antworte(text);
			if(aktuell != null) {		//ein Gespräch beginnt
				aktuell = beginneGespraech(aktuell);
				setImGespraech(true);
				welt.initGespraech(this);
				prompt(spielerAntwort, aktuell.getText());
				return imGespraech;
			} else return false;
		}
		
	}
	
	/**
	 * Gibt ein Gesprächsstück bestehend aus Spieler- und NPC-Antwort auf der GUI aus.
	 * @param spieler Der Text des Spielers.
	 * @param npc Der Text des NPCs.
	 */
	private void prompt(String spieler, String npc) {
		welt.println("Ich: " + spieler);
		welt.println(name + ": " + npc);
	}
	
	/**
	 * Ermöglicht es externen Klassen (z.B. StarteGespraechAktion) die Ausgabe des aktuellen NPC-Texts zu erzwingen.
	 */
	public void promt() {
		System.out.println("prompt");
		welt.println(name + ": " + aktuell.getText());
	}
	
	
	/**
	 * Der Spieler beginnt ein Gespräch mit dem NPC
	 * @param ziel Der Status der vom Gesprächsstart als neuer Status herausgegeben wurde.
	 * @return Der Status, der jetzt angenommen werden soll. Dieser kann z.B. basierend auf der Beziehung 
	 * von ziel abweichen
	 */
	protected abstract Status beginneGespraech(Status ziel);
}
