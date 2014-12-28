package game.entity;

import java.io.Serializable;

/**
 * Diese Klasse fuehrt eine Resistenz und einen Wert fuer diese zusammen.
 */
public class EntityResistenz implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die Resistenz.
	private byte resistenz;
	// Der Wert der Resistenz.
	private float wert;
	// Der temporaere Wert der Resistenz.
	private float temp;
	
	/**
	 * Eine neue EntityResistenz wird mithilfe einer Resistenz und eines Startwerts erstellt.
	 * @param resistenz Die Resistenz, dieser Resistenz.
	 * @param wert Der Startwert der Resistenz.
	 */
	public EntityResistenz(Resistenz resistenz, float wert) {
		this.resistenz = resistenz.getId();
		this.wert = wert;
		this.temp = wert;
	}
	
	public void addWert(float wert) {
		this.wert += wert;
	}
	public void addTemp(float wert) {
		// Der temporaere Wert ist mindestens 1, da ansonsten merkwuerdige Ergebnisse bei Schaden etc. herauskommen wuerden
		this.temp = Math.max(wert + temp, 1);
	}
	public void resetTemp() {
		this.temp = this.wert;
	}
	
	public float getWert() {
		return wert;
	}
	public float getTemp() {
		return temp;
	}
	public Resistenz getResistenz() {
		return Resistenz.getResistenz(resistenz);
	}

}
