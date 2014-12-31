package game.entity;

import game.Drop;
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
	 * @param leben Die Lebenspunkte des Gegners.
	 * @param magie Die Magiepunkte des Gegners.
	 * @param attributswert Die Attribute des Gegners.
	 */
	public Gegner(String name, NumerusGenus numGen, String beschreibung, int xp, int leben, int magie, int... attributswerte) {
		super(name, numGen, beschreibung, leben, magie, attributswerte);	    
		
	    this.xp = xp;
	}
	
	/**
	 * Ein Gegner wird aus einem anderen erstellt, sprich kopiert, aber als neues Objekt.
	 * @param gegner Der Gegener, der dupliziert werden soll.
	 */
	public Gegner(Gegner gegner) {
		this(gegner.getName(), gegner.getNumGen(), gegner.getBeschreibung(), gegner.getXp(), gegner.getMaxLp(), gegner.getMaxMp(), gegner.getAttributswerte());
		
	    for(Faehigkeit f : gegner.getFaehigkeiten())
	    	this.addFaehigkeit(f);
	    for(Drop d : gegner.loot)
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
	 * Gibt die Attributswerte als Array zurueck.
	 * @return Eine Liste mit allen Attributswerten.
	 */
	private int[] getAttributswerte() {
		int[] attributswerte = new int[attribute.length];
		for(int i = 0; i < attributswerte.length; i++)
			attributswerte[i] = attribute[i].getWert();
		return attributswerte;
	}
	
	// PROVISORIUM: Gibt den Agriff des Gegners zurueck. TODO
	@Override
	public Faehigkeit getFaehigkeit(String kommando) {
		return faehigkeiten.firstElement();
	}
	
	// PROVISORIUM: Aendert den Namen des Gegners. TODO
	public Gegner addBuchstabe(char buchstabe) {
		this.name += " " + buchstabe;
		return this;
	}
	
}		