package util;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

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
	
	/* --- statische Methoden --- */
	
	/**
	 * Waehlt einen der Drops aus mithilfe des Zufallszahlengenerators und gibt diesen dann zurueck.
	 * @param random Ein Zufallszahlengenerator.
	 * @param drops Die Drops aus denen ausgewaehlt werden soll.
	 * @return Einen zufaelligen Drop aus den Drops.
	 */
	public static <T> T[] drop(Random random, Drop<T>[] drops) {
		int sigmaChance = 0;
		for(Drop<T> d : drops)
			sigmaChance += d.getWahrscheinlichkeit();
		// Exklusive obere Grenze!
		int nInt = random.nextInt(sigmaChance + 1);		
		for(Drop<T> d : drops)
			if(nInt <= sigmaChance && nInt >= sigmaChance - d.getWahrscheinlichkeit()) // TODO <= statt <
				return d.getObjekte();
			else
				sigmaChance -= d.getWahrscheinlichkeit();
		
		// Das sollte eigentlich nicht passieren... TODO
		System.err.println("Drop: Fehler beim auswaehlen.");
		return drops[0].getObjekte();
	}
	
	/**
	 * Gibt ein Array zurueck, basierend auf einem Vector<Drop<T>>.
	 * @param drops Das Vector-Objekt.
	 * @return Ein Array mit allen Objekten des Vector-Objekts.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Drop<T>[] toArray(Vector<Drop<T>> drops) {
		return (Drop<T>[]) drops.toArray(new Drop[0]);
	}

}