package game.entity;

import java.io.Serializable;

/**
 * Repraesentiert eine Schadensart im Spiel z.B. Physich oder Magisch, koennen direkt im 
 * Weltengenerator erstellt werden und verwendet werden.
 * @author Marvin
 */
public class Schadensart implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- statische Konstanten --- */
	
	// Ein statisches Array mit allen Schadensarten.
	public static Schadensart[] SCHADEN = new Schadensart[Byte.MAX_VALUE];
	
	/* --- Variablen --- */
	
	// Der Name des Schadensart.
	private String name;	
	// Das Attribut, fuer das der Bonus berechnet werden soll, als id.
	private byte attribut;
	// Die id der Schadensart.
	private byte id;
	
	/* --- Konstruktor --- */
	
	/**
	 * Einer neuen Schadensart wird lediglich ein Name uebergeben.
	 * @param name Der Name fuer die Schadensart.
	 * @param attributsBonus Das Attribut, fuer welches der Bonus berechnet werden soll.
	 */
	public Schadensart(String name, Attribut attributsBonus) {
		this.name = name;
		this.attribut = (byte) attributsBonus.getId();
		
		Schadensart[] s = SCHADEN;
		SCHADEN = new Schadensart[s.length + 1];
		
		for(byte i = 0; i < SCHADEN.length && i < Byte.MAX_VALUE; i++) {
			if(s.length > i)
				SCHADEN[i] = s[i];
			if(SCHADEN[i] == null) {
				this.id = i;
				SCHADEN[id] = this;
				break;
			}
		}
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt den Namen der Schadensart zurueck.
	 * @return Der Name der Schadensart.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gibt die ID der Schadensart zurueck,
	 * @return Die ID der Schadensart.
	 */
	public byte getId() {
		return id;
	}
	
	/**
	 * Gibt den Attributswert zurueck, fuer welchen ein Bonus berechnet werden soll.
	 * @param entity Das Entity fuer welches das Attribut bestimmt werden soll.
	 * @return Der Attributswert fuer das Bonus Attribut.
	 */
	public int getAttribut(Entity entity) {
		return entity.getTempAttribut(Attribut.getAttribut(attribut).getName());
	}
	
	/**
	 * Gibt den Attributs Bonus zurueck als String.
	 * @return Der Attributs Bonus in Parameterschreibweise.
	 */
	public String getAttributsNamen() {
		return Attribut.getAttribut(attribut).getParam();
	}
	
	/* --- statische Methoden --- */
	
	/**
	 * Gibt eine Schadensart basierend auf ihrem Namen zurueck.
	 * @param name Der Name der Schadensart.
	 * @return Die gesuchte Schadensart, null wenn es diese nicht gibt.
	 */
	public static Schadensart getSchadensart(String name) {
		for(Schadensart s : SCHADEN)
			if(s.getName().equals(name))
				return s;
		return null;
	}
	
	/**
	 * Gibt eine Schadensart zurueck, basierend auf ihrer ID.
	 * @param id Die ID der Schadensart.
	 * @return Die gesuchte Schadensart, null falls es so eine Schadensart nicht gibt.
	 */
	public static Schadensart getSchadensart(byte id) {
		if(getMaxId() < id)
			return null;
		return SCHADEN[id];
	}
	
	/**
	 * Gibt den hoechsten belegten ID Platz zurueck.
	 * @return Den hoechsten ID Wert, -1 falls alle Plaetze belegt sind.
	 */
	public static byte getMaxId() {
		for(byte i = 0; i < SCHADEN.length; i++)
			if(SCHADEN[i] == null)
				return i;
		return -1;
	}
	
	/**
	 * Gibt ein Array mit allen belegten Schadensarten zurueck.
	 * @return Ein Array mit allen Schadensarten, die im Spiel verwendet werden.
	 */
	public static Schadensart[] getSchadensarten() {
		Schadensart[] r = new Schadensart[getMaxId()];
		for(int i = 0; i < r.length; i++)
			r[i] = SCHADEN[i];
		return r;
	}

}