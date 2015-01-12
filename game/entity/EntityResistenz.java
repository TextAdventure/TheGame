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
	
	// Der Wert der Resistenz.
	private float[] werte;
	// Der temporaere Wert der Resistenz.
	private transient float[] temps;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein EntityResistenz verwaltet alle Resistenzen eines Lebewesens.
	 * @param werte Die Startwerte des Entities.
	 */
	public EntityResistenz(int[] werte) {
		this.werte = new float[Resistenz.getMaxId()];
		this.temps = new float[Resistenz.getMaxId()];
		addAlleWerte(werte);
		resetTemp();
	}
	
	/* --- Methoden --- */
	
	/**
	 * Fuegt der Resistenz einen Wert hinzu.	
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 */
	public void addWert(Resistenz resistenz, float wert) {
		this.werte[resistenz.getId()] += wert;
	}
	
	/**
	 * Fuegt alle Resistenzen hinzu mit einem Array, fehlende Werte werden hinten mit 0 ergaenzt.
	 * @param werte Das Array mit allen Werten.
	 */
	private void addAlleWerte(int[] werte) {		
		for(byte b = 0; b < Resistenz.getMaxId() && werte.length > b; b++)
			addWert(Resistenz.getResistenz(b), werte[b]);
		if(werte.length < Resistenz.getMaxId())
			for(byte b = (byte) werte.length; b < Resistenz.getMaxId(); b++)
				addWert(Resistenz.getResistenz(b), 0);
	}
	
	/**
	 * Gibt den Wert einer Resistenz zurueck.
	 * @param resistenz Die gesuchte Resistenz.
	 * @return Den Wert der Resistenz.
	 */
	public float getWert(Resistenz resistenz) {
		return werte[resistenz.getId()];
	}
	
	/**
	 * Fuegt einer temporaeren Resistenz einen Wert hinzu.
	 * @param resistenz Die Resistenz, der ein temporaere Wert hinzugefuegt werden soll.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 */
	public void addTemp(Resistenz resistenz, float wert) {
		if(temps == null)
			resetTemp();
		// Der temporaere Wert ist mindestens 1, da ansonsten merkwuerdige Ergebnisse bei Schaden etc. herauskommen wuerden
		this.temps[resistenz.getId()] = Math.max(wert + temps[resistenz.getId()], 1);
	}
	
	/**
	 * Gibt den Wert einer temporaere Resistenz zurueck.
	 * @param resistenz Die Resistenz, deren temporaerer Wert gesucht wird.
	 * @return Den Wert der temporaeren Resistenz.
	 */
	public float getTemp(Resistenz resistenz) {
		if(temps == null)
			resetTemp();
		return temps[resistenz.getId()];
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
		for(float wert : werte)
			if(wert != 0.0f)
				return true;
		return false;
	}

}