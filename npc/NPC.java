package npc;

import game.SpielWelt;

import java.io.Serializable;
import java.util.Vector;


import util.NumerusGenus;

/** 
 * Prototyp eines NPCs, mit dem man reden kann. 
 * Die Methode beginneGespraech muss f�r den jeweiligen konkret realisierten NPC implementiert werden.
 */
public abstract class NPC implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private final NumerusGenus geschlecht;
	private boolean imGespraech = false;
	private int beziehung = 0;
	/*
	 * Letzteres kann vom Prgrammierer benutzt werden, um dem NPC mehr Plastizit�t zu verleihen. So kann bspw.
	 * wiederholtes Beleidigen durch den Spieler durch ver�ndern dieses Werts gespeichert werden. In Gespr�chen
	 * kann dann darauf zugegriffen werden und der Gespr�chsverlauf kann entsprechende ge�ndert werden.
	 */
	private SpielWelt welt;
	
	private Vector<GespraechListener> listeners = new Vector<GespraechListener>();
	
	
	//Spezielle Gespr�chsstadien
	private Status startGespraech;		//enth�lt alle Gespr�chsstart-Signale
	private Status endeGespraech;		//enth�lt alle Gespr�chsende-Signale
	private Status aktuell;				//der aktuelle Gespr�chszustand
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
	 * �bergibt dem NPC einen text, der an ihn gerichtet ist. Gibt die Antwort des NPCs zur�ck.
	 * @return Ob der NPC angesprochen wurde.
	 */
	public boolean ansprechen(String spielerAntwort) {
		String text = spielerAntwort.toLowerCase();
		if(imGespraech) {
			Status ende = endeGespraech.antworte(text);
			if(ende != null) {
				//noch l�uft ein Gespr�ch, das will der Spieler aber jetzt beenden
				setImGespraech(false);
				prompt(spielerAntwort, ende.getText());
				return false;
				
			} else {
				//Es l�uft ein Gespr�ch, dass nicht beendet werden soll --> �bergib dem aktuellen Status die Spieler-Antwort
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
			//es l�uft aktuell kein Gespr�ch
			aktuell = startGespraech.antworte(text);
			if(aktuell != null) {		//ein Gespr�ch beginnt
				aktuell = beginneGespraech(aktuell);
				setImGespraech(true);
				welt.initGespraech(this);
				prompt(spielerAntwort, aktuell.getText());
				return imGespraech;
			} else return false;
		}
		
	}
	
	/**
	 * Gibt ein Gespr�chsst�ck bestehend aus Spieler- und NPC-Antwort auf der GUI aus.
	 * @param spieler Der Text des Spielers.
	 * @param npc Der Text des NPCs.
	 */
	private void prompt(String spieler, String npc) {
		welt.println("Ich: " + spieler);
		welt.println(name + ": " + npc);
	}
	
	/**
	 * Erm�glicht es externen Klassen (z.B. StarteGespraechAktion) die Ausgabe des aktuellen NPC-Texts zu erzwingen.
	 */
	public void promt() {
		System.out.println("prompt");
		welt.println(name + ": " + aktuell.getText());
	}
	
	
	/**
	 * Der Spieler beginnt ein Gespr�ch mit dem NPC
	 * @param ziel Der Status der vom Gespr�chsstart als neuer Status herausgegeben wurde.
	 * @return Der Status, der jetzt angenommen werden soll. Dieser kann z.B. basierend auf der Beziehung 
	 * von ziel abweichen
	 */
	protected abstract Status beginneGespraech(Status ziel);
}
