package game.entity;

import java.io.Serializable;

/**
 * Ein Attribut fuer alle Entities, die auch von Gegenstaenden verwendet werden muessen.
 */
public class Attribut implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Statisches Array mit allen Attributen, die ID ist der Index des Attributs.
	public static Attribut[] ATTRIBUTE = new Attribut[Byte.MAX_VALUE];
	
	/* --- Die Variablen --- */
	
	// Der Name(fuer den Spieler sichtbar) des Attributs.
	private String name;	
	// Der Parametername des Attributs, der nur intern verwendet wird.
	private String param;	
	// Die ID des Attributs, es kann maximal 128 Attribute geben.
	private byte id;
	
	/* --- Der Konstruktor --- */
	
	/**
	 * Ein neues Attribut wird einem Namen und einem internen Parameternamen versehen, die ID wird automatisch intern festgelegt.
	 * @param name Der Name des Attributs.
	 * @param paramName Der Name in Parameterschreibweise.
	 */
	public Attribut(String name, String paramName) {
		this.name = name;
		this.param = paramName;
		for(byte i = 0; i < ATTRIBUTE.length && i < 128; i++) {
			if(ATTRIBUTE[i] == null) {
				this.id = i;
				ATTRIBUTE[id] = this;
				break;
			}
		}
		
	}
	
	/* --- Die Methoden --- */
	
	/**
	 * Gibt den Namen des Attributs zurueck(fuer den Spieler).
	 * @return Den Namen des Attributs.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gibt den Namen des Attributs zurueck, in der Parameterschreibweise.
	 * @return Den Namen in Parameterschreibweise.
	 */
	public String getParam() {
		return this.param;
	}
	
	/**
	 * Gibt die ID des Attributs zurueck.
	 * @return Die ID des Attributs.
	 */
	public byte getId() {
		return id;
	}
	
	/* --- Die statischen Methoden --- */
	
	/**
	 * Gibt ein Attribut zurueck, basierend auf seinem Parameternamen.
	 * @param param Der Parametername des Attributs.
	 * @return Das gesuchte Attribut, null falls es so ein Attribut nicht gibt.
	 */
	public static Attribut getAttribut(String param) {
		for(Attribut a : getAttribute())
			if(a.getParam().equals(param))
				return a;
		return null;
	}
	/**
	 * Gibt ein Attribut zurueck, basierend auf seiner ID.
	 * @param id Die ID des Attributs.
	 * @return Das gesuchte Attribut, null falls es so ein Attribut nicht gibt.
	 */
	public static Attribut getAttribut(byte id) {
		if(ATTRIBUTE.length < id)
			return null;
		return ATTRIBUTE[id];
	}
	/**
	 * Gibt ein Array mit allen belegten Attributen zurueck.
	 * @return Ein Array mit allen Attribute, die im Spiel verwendet werden.
	 */
	public static Attribut[] getAttribute() {
		Attribut[] a = new Attribut[getMaxId()];
		for(int i = 0; i < a.length; i++)
			a[i] = ATTRIBUTE[i];
		return a;
	}
	/**
	 * Gibt den hoechsten belegten ID Platz zurueck.
	 * @return Den hoechsten ID Wert, -1 falls alle Plaetze belegt sind.
	 */
	public static byte getMaxId() {
		for(byte i = 0; i < ATTRIBUTE.length; i++)
			if(ATTRIBUTE[i] == null)
				return i;
		return -1;
	}
	
}
