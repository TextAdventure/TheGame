package game.entity;

import java.io.Serializable;

/**
 * Fuehrt eine Resistenz und einen Wert fuer diese zusammen.
 * @author Marvin
 */
public class EntityResistenz implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Die Resistenz.
	private byte resistenz;
	// Der Wert der Resistenz.
	private float wert;
	// Der temporaere Wert der Resistenz.
	private float temp;
	
	/* --- Konstruktor --- */
	
	/**
	 * Eine neue EntityResistenz wird mithilfe einer Resistenz und einem Wert erstellt.
	 * @param resistenz Die Resistenz, dieser Resistenz.
	 * @param wert Der Wert der Resistenz.
	 */
	public EntityResistenz(Resistenz resistenz, float wert) {
		this.resistenz = resistenz.getId();
		this.wert = wert;
		this.temp = wert;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Fuegt der Resistenz einen Wert hinzu.	
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 */
	public void addWert(float wert) {
		this.wert += wert;
	}
	
	/**
	 * Gibt den Wert der Resistenz zurueck.
	 * @return Den Wert der Resistenz.
	 */
	public float getWert() {
		return wert;
	}
	
	/**
	 * Fuegt der temporaeren Resistenz einen Wert hinzu.
	 * @param wert Der Weert, der hinzugefuegt werden soll.
	 */
	public void addTemp(float wert) {
		// Der temporaere Wert ist mindestens 1, da ansonsten merkwuerdige Ergebnisse bei Schaden etc. herauskommen wuerden
		this.temp = Math.max(wert + temp, 1);
	}
	
	/**
	 * Gibt den Wert der temporaere Resistenz zurueck.
	 * @return Den Wert der temporaeren Resistenz.
	 */
	public float getTemp() {
		return temp;
	}
	
	/**
	 * Setzt den temporaeren Wert zurueck.
	 */
	public void resetTemp() {
		this.temp = this.wert;
	}
	
	/**
	 * Gibt die Resistenz zurueck.	
	 * @return Die Resistenz.
	 */
	public Resistenz getResistenz() {
		return Resistenz.getResistenz(resistenz);
	}

}