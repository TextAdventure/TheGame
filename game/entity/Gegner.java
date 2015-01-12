package game.entity;

import java.util.Vector;

import game.SpielWelt;
import game.items.Stapel;
import util.Drop;
import util.NumerusGenus;

/**
 * Bietet die Moeglichkeit Gegner fuer das Spiel zu erstellen, die bekaempft werden koennen.
 * @author Marvin
 */
public class Gegner extends Entity {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	  
	/* --- Variablen --- */
	
	// Die EXP, die der Gegner gibt.
	private int xp;
	  
	/* --- Konstruktoren --- */
	
	/**
	 * Erstellt einen Gegner mit Namen, Geschlecht, Beschreibung, Attributen und XP.
	 * @param name Der Name des Gegners.
	 * @param numGen Das Geschlecht des Gegners.
	 * @param beschreibung Die Beschreibung des Gegners.
	 * @param xp Die XP fuer das besiegen des Gegners.
	 * @param attributswert Die Attribute des Gegners.
	 */
	public Gegner(String name, NumerusGenus numGen, String beschreibung, int xp, int... attributswerte) {
		super(name, numGen, beschreibung, attributswerte);
		
		faehigkeitenG = new Vector<Drop<Faehigkeit>>();
		
	    this.xp = xp;
	}
	
	/**
	 * Ein Gegner wird aus einem anderen erstellt, sprich kopiert, aber als neues Objekt.
	 * @param gegner Der Gegener, der dupliziert werden soll.
	 */
	public Gegner(Gegner gegner) {
		this(gegner.getName(), gegner.getNumGen(), gegner.getBeschreibung(), gegner.getXp(), gegner.attribute.getAlleWerte());
		
	    for(Drop<Faehigkeit> f : gegner.getFaehigkeitenAlsDrop())
	    	this.addFaehigkeit(f.getObjekt(), f.getWahrscheinlichkeit());
	    for(Drop<Stapel> d : gegner.loot)
	    	this.loot.add(d);
	}
	  
	/* --- Methoden --- */
	
	/**
	 * Gibt die XP zurueck, die man fuer das Toeten erhaelt.
	 * @return Die Menge an XP.
	 */
	public int getXp() {
		return xp;
	}
	
	/**
	 * Gibt eine Faehigkeit des Gegners zurueck, basierend auf den uebergebenen Faehigkeiten und deren Wahrscheinlichkeit.
	 * @param kommando Das Kommando, das der Spieler eingegeben hat(fuer den Gegner irrelevant).
	 * @return Eine zufaellige Faehigkeit des Gegners.
	 */
	@SuppressWarnings("unchecked") // TODO
	@Override
	public Faehigkeit getFaehigkeit(String kommando) {
		Faehigkeit[] f = faehigkeitenG.firstElement().drop(SpielWelt.WELT.r, faehigkeitenG.toArray(new Drop[0]));
		return f[0];
	}
	
	// PROVISORIUM: Aendert den Namen des Gegners. TODO
	public Gegner addBuchstabe(char buchstabe) {
		this.name += " " + buchstabe;
		return this;
	}
	
}		