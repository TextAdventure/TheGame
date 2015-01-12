package game.entity;

import java.io.Serializable;

/**
 * Speichert alle Werte der Attribute fuer ein Lebewesen oder Gegenstand.
 * @author Marvin
 */
public class EntityAttribut implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Die Werte der Attribute.
	private int[] werte;
	// Die temporaeren Werte der Attribute.
	private transient int[] temps;
	
	/* --- Konstruktor --- */

	/**
	 * Ein EntityAttribut verwaltet alle Attribute und temporaere Werte fuer diese.
	 * @param werte Die Startwerte des Entities.
	 */
	public EntityAttribut(int[] werte) {
		this.werte = new int[Attribut.getMaxId()];
		this.temps = new int[Attribut.getMaxId()];
		addAlleWerte(werte);
		resetTemp();
	}
	
	/* --- Methoden --- */
	
	/**
	 * Fuegt einem Attribut einen Wert hinzu.
	 * @param attribut Das Attribut dem etwas hinzugefuegt werden soll.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 */
	public void addWert(Attribut attribut, int wert) {
		this.werte[attribut.getId()] += wert;
	}
	
	/**
	 * Fuegt alle Attribute hinzu mit einem Array, fehlende Werte werden hinten mit 0 ergaenzt.
	 * @param werte Das Array mit allen Werten.
	 */
	public void addAlleWerte(int[] werte) {		
		for(byte b = 0; b < Attribut.getMaxId() && werte.length > b; b++)
			addWert(Attribut.getAttribut(b), werte[b]);
		if(werte.length < Attribut.getMaxId())
			for(byte b = (byte) werte.length; b < Attribut.getMaxId(); b++)
				addWert(Attribut.getAttribut(b), 0);
	}
	
	/**
	 * Gibt den Wert eines Attributs zurueck.
	 * @param attribut Das Attribut fuer das der Wert zurueckgegeben werden soll.
	 * @return Den Wert des Attributs.
	 */
	public int getWert(Attribut attribut) {
		return werte[attribut.getId()];
	}
	
	/**
	 * Gibt ein Array mit allen Werten zurueck.
	 * @return Ein Array mit allen Werten.
	 */
	public int[] getAlleWerte() {
		return werte;
	}
	
	/**
	 * Fuegt einem temporaeren Attribut einen Wert hinzu.
	 * @param attribut Das temporaere Attribut dem etwas hinzugefuegt werden soll.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 */
	public void addTemp(Attribut attribut, int wert) {
		if(temps == null)
			resetTemp();
		// Der temporaere Wert ist mindestens 1, da ansonsten merkwuerdige Ergebnisse bei Schaden etc. herauskommen wuerden
		this.temps[attribut.getId()] = Math.max(wert + temps[attribut.getId()], 1);
	}
	
	/**
	 * Gibt den temporaeren Wert eines Attributs zurueck.
	 * @param attribut Das Attribut fuer das der temporaere Wert zurueckgegeben werden soll.
	 * @return Den temporaeren Wert eines Attributs.
	 */
	public int getTemp(Attribut attribut) {
		if(temps == null)
			resetTemp();
		return temps[attribut.getId()];
	}
	
	/**
	 * Setzt die temporaeren Werte zurueck.
	 */
	public void resetTemp() {
		this.temps = this.werte;
	}
	
	/**
	 * Gibt an, ob dieses Lebewesen irgendwelche Werte besitzt.
	 * @return True, wenn es Werte hat, ansonsten false.
	 */
	public boolean hasWerte() {
		for(int wert : werte)
			if(wert != 0)
				return true;
		return false;
	}
	
}