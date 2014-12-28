package util;

import java.awt.Color;
import java.io.Serializable;
import java.util.Vector;

/**
 * Eine Farbe, die man fuer die Faerbung von Text verwenden kann, sie enthaelt alle wichtigen Informationen.
 */
public class Farbe implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Statischer Vektor mit allen Farben
	private static Vector<Farbe> farben = new Vector<Farbe>();
	
	
	// Der Name der Farbe.
	private String name;
	
	// Die Farbe der Farbe.
	private Color farbe;
	
	/* --- Die Konstruktoren --- */
	
	/**
	 * Eine neue Farbe, basierend auf der Hexadezimalschreibweise.
	 * @param name Der Name der Farbe.
	 * @param hexa Eine Hexadezimalzahl, die eine Farbe beschreibt.
	 */
	public Farbe(String name, String hexa) {
		this.name = name;
		farbe = new Color(Integer.decode("0x" + hexa));
		farben.add(this);
	}
	
	/**
	 * Eine neue Farbe, basierend auf den RGB Werten einer Farbe.
	 * @param name Der Name der Farbe.
	 * @param rot Der Rotwert der Farbe.
	 * @param gruen Der Gruenwert der Farbe.
	 * @param blau Der Blauwert der Farbe.
	 */
	public Farbe(String name, int rot, int gruen, int blau) {
		this.name = name;
		farbe = new Color(rot, gruen, blau);
		farben.add(this);
	}
	
	/* --- Die Methoden --- */
	
	/**
	 * Gibt den Namen der Farbe zurueck.
	 * @return Den Namen der Farbe.
	 */
	public String getName() {
		return name;
	}	
	/**
	 * Gibt die Farbe zurueck, die gespeichert ist.
	 * @return Die Farbe der Farbe.
	 */
	public Color getColor() {
		return farbe;
	}
	
	/* --- Die statischen Methoden --- */
	
	/**
	 * Gibt eine Farbe aufgrund des Namens zurueck.
	 * @param name Der Name der gesuchten Farbe.
	 * @return Die gesuchte Farbe, falls es diese gibt, ansonsten null.
	 */
	public static Farbe getFarbe(String name) {
		for(Farbe f : farben)
			if(f.getName().equalsIgnoreCase(name))
				return f;
		return null;
	}
	/* TODO
	 * Giibt alle Farben als ein Vector auf einmal zurueck.
	 * @return Alle Farben.
	 *
	public static Vector<Farbe> getAlleFarben() {
		return farben;
	}*/
	/**
	 * Ersetzt die Liste aller Farben durch eine neue(wird benoetigt, um die Farben zu laden und zu speichern).
	 * @param neueFarben Die neuen Farben.
	 */
	public static void setAlleFarben(Vector<Farbe> neueFarben) {
		farben = neueFarben;
	}
}
