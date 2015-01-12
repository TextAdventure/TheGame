package game.entity;

import java.io.Serializable;
import java.util.Vector;

/**
 * Die Resistenz vor einer bestimmten Schadensart, speichert das Attribut mir dem abgewehrt wird und den Namen der Resistenz.
 * @author Marvin
 */
public class Resistenz implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- statische Konstanten --- */
	
	// Ein statiches Array mit allen Resistenzen, die ID ist der Index.
	private static Resistenz[] resistenzen = new Resistenz[0];
	
	/* --- Variablen --- */
	
	// Der Name der Resistenz.
	private String name;
	// Die Parameterschreibweise der Resistenz.
	private String param;
	// Die ID der Schadensart, vor der die Resistenz schuetzt.
	private byte schadensart;
	// Die ID des Attributes, das diesen Schaden reduziert.
	private byte attribut;
	
	/* --- Konstruktor --- */
	
	/**
	 * Erstellt eine neue Resistenz fuer jede Schadensart im Spiel.
	 * @param name Der Name der Resistenz.
	 * @param paramName Die Parameterschreibweise der Resistenz.
	 * @param schadensart Die Schadensart, vor der die Resistenz schuetzt.
	 * @param attributs Das Attribut, das diesen Schaden reduziert.
	 */
	public Resistenz(String name, String paramName, Schadensart schadensart, Attribut attribut) {
		this.name = name;
		this.param = paramName;
		this.schadensart = (byte) schadensart.getId();
		this.attribut = (byte) attribut.getId();
		
		Vector<Resistenz> neu = new Vector<Resistenz>();
		
		for(Resistenz r : resistenzen)
			neu.add(r);
		neu.add(this);
		
		setResistenzen(neu.toArray(new Resistenz[0]));
	}

	/* --- Methoden --- */
	
	/**
	 * Gibt den Namen der Resistenz zurueck.
	 * @return Den Namen der Resistenz.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gibt die Parameterschreibweise der Resistenz zurueck.
	 * @return Den Parameter.
	 */
	public String getParam() {
		return param;
	}
	
	/**
	 * Gibt die Schadensart zurueck, vor der die Resistenz schuetzt.
	 * @return Die Schadensart vor der die Resistenz schuetzt.
	 */
	public Schadensart getSchadensart() {
		return Schadensart.getSchadensart(schadensart);
	}
	
	/**
	 * Gibt den Attributswert zurueck mit dem diese Art von Schaden abgewehrt werden kann.
	 * @param entity Das Entity fuer das der Wert berechnet werden soll.
	 * @return Den temporaeren Wert der Abwehr fuer diese Art von Schaden.
	 */
	public int getAttribut(Entity entity) {
		return entity.getTemp(Attribut.getAttribut(attribut));
	}
	
	/**
	 * Gibt die ID der Resistenz zurueck.
	 * @return Die ID der Resistenz.
	 */
	public byte getId() {
		for(byte b = 0; b < getMaxId(); b++)
			if(getResistenz(b).equals(this))
				return b;
		// Das sollte eigentlich nicht passieren...
		return -1;
	}
	
	/* --- statische Methoden --- */
	
	/**
	 * Gibt eine Resistenz basierend auf ihrer ID zurueck.
	 * @param id Die ID der gesuchten Resistenz.
	 * @return Die gesuchte Resistenz, null falls es so eine Resistenz nicht gibt.
	 */
	public static Resistenz getResistenz(byte id) {
		if(getMaxId() < id)
			return null;
		return resistenzen[id];
	}
	
	/**
	 * Gibt eine Resistenz basieren auf der Schadensart, die sie abwehrt zurueck.
	 * @param schadensart Die Schadensart, die abgewehrt werden soll.
	 * @return Die gesuchte Resistenz, null falls es so eine Resistenz nicht gibt.
	 */
	public static Resistenz getResistenz(Schadensart schadensart) {
		for(Resistenz r : getResistenzen())
			if(r.getSchadensart().equals(schadensart))
				return r;
		return null;
	}
	
	/**
	 * Gibt den hoechsten belegten ID Platz zurueck.
	 * @return Den hoechsten ID Platz, oder -1 wenn alle belegt sind.
	 */
	public static byte getMaxId() {
		return (byte) resistenzen.length;
	}
	
	/**
	 * Gibt ein Array mit allen belegten Resistenzen zurueck.
	 * @return Ein Array mit allen Resistenzen, die im Spiel verwendet werden.
	 */
	public static synchronized Resistenz[] getResistenzen() {
		return resistenzen;
	}

	/**
	 * Legt alle Resistenzen fest und ueberschreibt die vorherigen.
	 * @param neueResistenzen Die neue Liste mit allen Attributen.
	 */
	public static synchronized void setResistenzen(Resistenz[] neueResistenzen) {
		resistenzen = neueResistenzen;		
	}
	
}