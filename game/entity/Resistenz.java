package game.entity;

import java.io.Serializable;

/**
 * Die Resistenz vor einer bestimmten Schadensart, speichert das Attribut mir dem abgewehrt wird und den Namen der Resistenz.
 */
public class Resistenz implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Die statischen Konstanten --- */
	
	// Ein statiches Array mit allen Resistenzen, die ID ist der Index.
	public static Resistenz[] RESISTENZEN = new Resistenz[Byte.MAX_VALUE];
	
	/* --- Die Variablen --- */
	
	// Der Name der Resistenz.
	private String name;
	// Die Parameterschreibweise der Resistenz.
	private String param;
	// Die ID der Schadensart, vor der die Resistenz schuetzt.
	private byte schadensart;
	// Die ID des Attributes, das diesen Schaden reduziert.
	private byte attribut;
	// Die ID der Resistenz.
	private byte id;
	
	/* --- Der Konstruktor --- */
	
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
		for(byte i = 0; i < RESISTENZEN.length && i < Byte.MAX_VALUE; i++) {
			if(RESISTENZEN[i] == null) {
				this.id = i;
				RESISTENZEN[id] = this;
				break;
			}
		}
	}

	/* --- Die Methoden --- */
	
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
	 * Gibt die ID der Resistenz zurueck.
	 * @return Die ID der Resistenz.
	 */
	public byte getId() {
		return id;
	}
	/**
	 * Gibt den Attributswert zurueck mit dem diese Art von Schaden abgewehrt werden kann.
	 * @param entity Das Entity fuer das der Wert berechnet werden soll.
	 * @return Der Wert der Abwehr fuer dies Art von Schaden.
	 */
	public int getAttribut(Entity entity) {
		return entity.getTempAttribut(Attribut.getAttribut(attribut).getName());
	}
	
	/* --- Die statischen Methoden --- */
	
	/**
	 * Gibt eine Resistenz basierend auf ihrer ID zurueck.
	 * @param id Die ID der gesuchten Resistenz.
	 * @return Die gesuchte Resistenz, null falls es so eine Resistenz nicht gibt.
	 */
	public static Resistenz getResistenz(byte id) {
		if(getMaxId() < id)
			return null;
		return RESISTENZEN[id];
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
		for(byte i = 0; i < RESISTENZEN.length; i++)
			if(RESISTENZEN[i] == null)
				return i;
		return -1;
	}
	/**
	 * Gibt ein Array mit allen belegten Resistenzen zurueck.
	 * @return Ein Array mit allen Resistenzen, die im Spiel verwendet werden.
	 */
	public static Resistenz[] getResistenzen() {
		Resistenz[] r = new Resistenz[getMaxId()];
		for(int i = 0; i < r.length; i++)
			r[i] = RESISTENZEN[i];
		return r;
	}
	
	/**
	 * Setzt die Resistenzen auf den uebergebenen Wert(wird benoetigt, um das statische Array zu laden).
	 * @param resistenzen Das neue Resistenz Array.
	 *
	public static void setResistenzen(Resistenz[] resistenzen) {
		RESISTENZEN = resistenzen;
	}*/
	
}
