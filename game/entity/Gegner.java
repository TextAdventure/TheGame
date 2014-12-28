package game.entity;

import game.Drop;
import util.NumerusGenus;

/**
 *  Diese Klasse bietet die Moeglichkeit Gegner fuer das Spiel zu erstellen, die bekaempft werden koennen.
 */
public class Gegner extends Entity {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	  
	// Die EXP, die der Gegner gibt.
	private int xp;
	  
	/* --- Die Konstruktoren --- */
	
	/**
	 * Erstellt einen Gegner mit Namen, Geschlecht, Beschreibung, Attributen und XP.
	 * @param name Der Name des Gegners.
	 * @param numGen Das Geschlecht des Gegners.
	 * @param beschreibung Die Beschreibung des Gegners.
	 * @param xp Die XP fuer das besiegen des Gegners.
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
	  
	/* --- Die Methoden --- */
	
	// Gibt die XP zurueck.
	public int getXp() { return xp; }
	  
	// Gibt die Attributswerte als Array zurueck.
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