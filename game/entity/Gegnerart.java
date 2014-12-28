package game.entity;

import java.io.Serializable;
import java.util.Vector;

/**
 * Repraesentiert eine Gegnerart im Spiel.
 */
public class Gegnerart implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Der statische Vektor mit allen Gegnerarten --- */
	
	public static Vector<Gegnerart> GEGNERARTEN = new Vector<Gegnerart>();
	
	// Der Name der Gegnerart.
	private String name;
	// Der Parameter der Gegnerart.
	private String param;
		
	/* --- Der Konstruktor --- */
		
	/**
	 * Eine neue Gegnerart wird lediglich ein Name uebergeben.
	 * @param name Der Name fuer die Gegnerart.
	 * @param param Der Parameter dieser Gegnerart.
	 */
	public Gegnerart(String name, String param) {
		this.name = name;
		this.param = param;
		GEGNERARTEN.add(this);
	}
		
	/* --- Die Methoden --- */
		
	/**
	 * Gibt den Namen der Gegnerart zurueck.
	 * @return Den Name der Gegnerart.
	 */
	public String getName() {
		return name;
	}	
	/**
	 * Gibt den Parameter der Gegnerart zurueck.
	 * @return Den Parameter der Gegnerart.
	 */
	public String getParam() {
		return param;
	}
	
	/* --- Die statischen Methoden --- */
	
	/**
	 * Gibt eine Gegnerart basierend auf ihrem Parameter zurueck.
	 * @param param Der Parameter der gesuchten Gegnerart.
	 * @return Die gesuchte Gegnerart, null falls es diese nicht gibt.
	 */
	public Gegnerart getGegnerart(String param) {
		for(Gegnerart g : GEGNERARTEN)
			if(g.getParam() == param)
				return g;
		return null;
	}

}
