package game.items;

import java.io.Serializable;
import java.util.Vector;

/**
 * Repraesentiert eine Waffenart im Spiel z.B. Schwert oder Schild.
 */
public class Waffenart implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Der statische Vektor mit allen Waffenarten --- */
	
	public static Vector<Waffenart> WAFFENARTEN = new Vector<Waffenart>();
	
	// Der Name der Waffenart.
	private String name;
	// Der Parameter der Waffenart-
	private String param;
	
	
	/* --- Der Konstruktor --- */
	
	/**
	 * Eine neue Waffenart wird lediglich ein Name uebergeben.
	 * @param name Der Name fuer die Waffenart.
	 * @param param Der Parameter der Waffenart.
	 */
	public Waffenart(String name, String param) {
		this.name = name;
		this.param = param;
	}
	
	/* --- Die Methoden --- */
	
	/**
	 * Gibt den Namen der Waffenart zurueck.
	 * @return Den Name der Waffenart.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Gibt den Parameter der Gegnerart zurueck. TODO
	 * @return Den Parameter der Gegnerart.
	 */
	public String getParam() {
		return param;
	}
	
/* --- Die statischen Methoden --- */
	
	/**
	 * Gibt eine Waffenart basierend auf ihrem Parameter zurueck.
	 * @param param Der Parameter der gesuchten Waffenart.
	 * @return Die gesuchte Waffenart, null falls es diese nicht gibt.
	 */
	public Waffenart getWaffenart(String param) {
		for(Waffenart w : WAFFENARTEN)
			if(w.getParam() == param)
				return w;
		return null;
	}

	
}
