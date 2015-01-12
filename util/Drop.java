package util;

import java.io.Serializable;
import java.util.Random;

/**
 * Ein Drop wird verwendet um zufaellig zwischen mehreren Dingen zu unterscheiden.
 * @author Marvin
 * 
 * @param <E> Die Objekte, die der Drop beinhaltet.
 */
public class Drop<E> implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Die Obejkte, die fallengelassen werden.
	private E[] objekte;
	// Die Wahrscheinlichkeit fuer diesen Droppe.
	private int wahrscheinlichkeit;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein Drop hat eine Wahrscheinlichkeit und Objekte, die damit verknuepft sind.
	 * @param wahrscheinlichkeit Die Wahrscheinlichkeit fuer diesen Drop.
	 * @param objekte Die Objekte, die damit verbunden sind.
	 */
	@SafeVarargs // TODO
	public Drop(int wahrscheinlichkeit, E... objekte) {
		this.wahrscheinlichkeit = wahrscheinlichkeit;
		this.objekte = objekte;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt die Wahrscheinlichkeit fuer diesen Drop zurueck.
	 * @return Die Wahrscheinlichkeit fuer diesen Drop.
	 */
	public int getWahrscheinlichkeit() {
		return this.wahrscheinlichkeit;
	}

	/**
	 * Gibt das erste Objekt des Drops zurueck.
	 * @return Das erste Objekt des Drops.
	 */
	public E getObjekt() {
		return objekte[0];
	}
	
	/**
	 * Gibt die Objekte des Drops zurueck.
	 * @return Die Objekte des Drops.
	 */
	public E[] getObjekte() {
		return objekte;
	}
	
	/**
	 * Waehlt einen zufaelligen Drop aus und gibt dessen Inhalt zurueck.
	 * @param random Ein RNG.
	 * @param drops Die Drops aus denen ausgewaehlt werden kann.
	 * @return Den zufaelligen Drop.
	 */
	@SuppressWarnings("unchecked") // TODO Spaeter ersetzen durch etwas anderes!
	public E[] drop(Random random, Drop<E>... drops) {
		int sigmaChance = 0;
		for(Drop<E> d : drops)
			sigmaChance += d.getWahrscheinlichkeit();
		// Exklusive obere Grenze!
		int nInt = random.nextInt(sigmaChance + 1);
		for(int i = drops.length - 1; i >= 0; i--) 
			if(nInt > sigmaChance - drops[i].getWahrscheinlichkeit() && nInt <= sigmaChance)
				return drops[i].getObjekte();
			else
				sigmaChance -= drops[i].getWahrscheinlichkeit();
		// Das sollte eigentlich nicht passieren... TODO
		System.err.println("Drop: Fehler beim auswaehlen.");
		return drops[0].getObjekte();
	}

}