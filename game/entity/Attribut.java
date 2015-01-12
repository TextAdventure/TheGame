package game.entity;

import java.io.Serializable;
import java.util.Vector;

/**
 * Ein Attribut fuer alle Entities, die auch von Gegenstaenden verwendet werden muessen.
 * @author Marvin
 */
public class Attribut implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- statische Konstanten --- */
	
	// Statisches Array mit allen Attributen, die ID ist der Index des Attributs.
	private static Attribut[] attribute = new Attribut[0];
	
	/* --- Variablen --- */
	
	// Der Name(fuer den Spieler sichtbar) des Attributs.
	private String name;
	// Der Parametername des Attributs, der nur intern verwendet wird.
	private String param;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein neues Attribut wird mit einem Namen und einem internen Parameternamen versehen, die ID wird automatisch intern festgelegt.
	 * @param name Der Name des Attributs.
	 * @param paramName Der Name in Parameterschreibweise.
	 */
	public Attribut(String name, String paramName) {
		this.name = name;
		this.param = paramName;
		
		Vector<Attribut> neu = new Vector<Attribut>();
		
		for(Attribut a : attribute)
			neu.add(a);
		neu.add(this);
		
		setAttribute(neu.toArray(new Attribut[0]));
	}
	
	/* --- Methoden --- */
	
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
		for(byte b = 0; b < getMaxId(); b++)
			if(getAttribut(b).equals(this))
				return b;
		// Das sollte eigentlich nicht passieren...
		return -1;
	}
	
	/* --- statische Methoden --- */
	
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
		if(getMaxId() <= id)
			return null;
		return attribute[id];
	}
	
	/**
	 * Gibt ein Array mit allen belegten Attributen zurueck.
	 * @return Ein Array mit allen Attribute, die im Spiel verwendet werden.
	 */
	public static synchronized Attribut[] getAttribute() {
		return attribute;
	}
	
	/**
	 * Legt Alle Attribute fest und ueberschreibt die vorherigen,
	 * @param attribute Die neue Liste mit allen Attributen.
	 */
	public static synchronized void setAttribute(Attribut[] alleAttribute) {
		attribute = alleAttribute;
	}
	
	/**
	 * Gibt den hoechsten belegten ID Platz zurueck.
	 * @return Den hoechsten ID Wert.
	 */
	public static byte getMaxId() {
		return (byte) attribute.length;
	}
	
}
