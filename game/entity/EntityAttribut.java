package game.entity;

import java.io.Serializable;

/**
 * Fuehrt ein Attribut und einen Wert fuer das Entity zusammen.
 * @author Marvin
 */
public class EntityAttribut implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Das Attribut.
	private byte attribut;		
	// Der Wert des Attributs.
	private int wert;
	// Der temporaere Wert des Attributs.
	private int temp;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein neues EntityAttribut wird mithilfe eines Attributes und einem Wert erstellt.
	 * @param attribut Das Attribut, welches festgelegt werden soll.
	 * @param wert Der Wert des Attributs.
	 */
	public EntityAttribut(Attribut attribut, int wert) {
		this.attribut = (byte) attribut.getId();
		this.wert = wert;
		this.temp = wert;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Fuegt dem Attribut einen Wert hinzu.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 */
	public void addWert(int wert) {
		this.wert += wert;
	}
	
	/**
	 * Gibt den Wert des Attributs zurueck.
	 * @return Den Wert des Attributs.
	 */
	public int getWert() {
		return wert;
	}
	
	/**
	 * Fuegt dem temporaeren Attribut einen Wert hinzu.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 */
	public void addTemp(int wert) {
		// Der temporaere Wert ist mindestens 1, da ansonsten merkwuerdige Ergebnisse bei Schaden etc. herauskommen wuerden
		this.temp = Math.max(wert + temp, 1);
	}
	
	/**
	 * Gibt den temporaeren Wert des Attributs zurueck.
	 * @return Den temporaeren Wert des Attributs.
	 */
	public int getTemp() {
		return temp;
	}
	
	/**
	 * Setzt den temporaeren Wert zurueck.
	 */
	public void resetTemp() {
		this.temp = this.wert;
	}
	
	/**
	 * Gibt das Attribut zurueck.	
	 * @return Das Attribut.
	 */
	public Attribut getAttribut() {
		return Attribut.getAttribut(attribut);
	}
	
}