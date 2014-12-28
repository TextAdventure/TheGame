package game.items;

import java.io.Serializable;
import java.util.Vector;

import util.NumerusGenus;
import game.logic.Ereignis;

/**
 * Verbindet einen Gegenstand und eine Bedingung zu einem KommandoGegenstand, 
 * der eingesetzt werden kann, aber nur an bestimmten Orten. 
 */
public class KommandoGegenstand extends Gegenstand implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Die Variablen --- */
	
	// Die Ereignisse, die geprueft werden, wenn der Gegenstand eingesetzt wurde.
	private Vector<Ereignis> ereignisse;
	
	/* --- Der Konstruktor --- */
	
	/**
	 * Erstellt einen neuen KommandoGegenstand, der einen Gegenstand und Ereginisse speichert.
	 * @param gegenstand Der Gegenstand, der eingesetzt werden muss.
	 * @param ereignisse Die Ereginisse, die geprueft werden sollen.
	 */
	public KommandoGegenstand(String[] namenGegenstand, String plural, NumerusGenus numerusGenus, String beschreibung, Ereignis... ereignisse) {
		super(namenGegenstand, plural, numerusGenus, beschreibung);
		this.ereignisse = new Vector<Ereignis>();
		for(Ereignis e : ereignisse)
			this.ereignisse.add(e);
	}
	
	/* --- Die Methoden --- */

	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "Kommandogegenstand"
	 */
	public String getGegenstandsart() {
		return "Kommandogegenstand";
	}
	
	/**
	 * Der Gegenstand wurde verwendet, deshalb werden jetzt die Bedingungen geprueft.
	 * @return True, wenn der KommandoGegenstand keine Bedingungen mehr hat.
	 */
	public boolean verwendet() {
		for(Ereignis e : ereignisse.toArray(new Ereignis[0]))
			if(e.eingetreten())
				ereignisse.remove(e);
		if(ereignisse.isEmpty())
			return true;
		else 
			return false;
	}
	
}
