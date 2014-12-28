package game;

import java.io.Serializable;

/**
 * Diese Klasse repraesentiert den Ausgang von einem Ort zu einem anderen.
 */
public class Ausgang implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Die statischen Konstanten --- */
	
	// Der byte-Wert fuer einen Ausgang, der keine Richtung hat, er wird auf der Karte nicht angezeigt.
	public static final byte EIGENE = 0;

	// Der byte-Wert fuer einen Ausgang in Richtung Osten.
	public static final byte OSTEN = 1;
	// Der byte-Wert fuer einen Ausgang in Richtung Suedosten.
	public static final byte SUEDOSTEN = 2;
	// Der byte-Wert fuer einen Ausgang in Richtung Sueden.
	public static final byte SUEDEN = 3;
	// Der byte-Wert fuer einen Ausgang in Richtung Suedwesten.
	public static final byte SUEDWESTEN = 4;
	// Der byte-Wert fuer einen Ausgang in Richtung Westen.
	public static final byte WESTEN = 5;
	// Der byte-Wert fuer einen Ausgang in Richtung Nordwesten.
	public static final byte NORDWESTEN = 6;
	// Der byte-Wert fuer einen Ausgang in Richtung Norden.
	public static final byte NORDEN = 7;
	// Der byte-Wert fuer einen Ausgang in Richtung Nordosten.
	public static final byte NORDOSTEN = 8;

	// Der byte-Wert fuer einen Ausgang, der nach unten fuehrt.
	public static final byte RUNTER = 9;
	// Der byte-Wert fuer einen Ausgang, der nach oben fuehrt.
	public static final byte HOCH = 10;
	// Der byte-Wert fuer einen Ausgang, der in etwas hineinfuehrt.
	public static final byte BETRETEN = 11;
	// Der byte-Wert fuer einen Ausgang, der aus etwas herausfuehrt.
	public static final byte VERLASSEN = 12;
	  
	// Die Woerter der Richtungen als String, in einem Array (normal geschrieben).
	public static final String[] richtungen = {
		"Undefiniert",

	    "Osten",
	    "Südosten",
	    "Süden",
	    "Südwesten",
	    "Westen",
	    "Nordwesten",
	    "Norden",
	    "Nordosten",
	    
	    "Runter",
	    "Hoch",
	    "Betreten",
	    "Verlassen"
	};
	  
	// Die Abkuerzungen der Richtungen, in einem Array (in Grossbuchstaben).
	public static final String[] abkuerzungen = {
		"UNDEF",

	    "O",
	    "SO",
	    "S",
	    "SW",
	    "W",
	    "NW",
	    "N",
	    "NO",
	    
	    "R",
	    "H",
	    "B",
	    "V"
	};
	
	/* --- Die Variablen --- */
	
	// Der Ort, zu dem dieser Ausgang fuehrt.
	private Ort zielort;
	// Die Richtung, in der der Zielort liegt.
	private byte richtung;
	// Die Richtung, als Wort ausgeschrieben.
	private String richtungsName;
	// Die Abkuerzung fuer den Richtungsname.
	private String abkuerzung;
	  
	/* --- Der Konstruktor --- */
	
	/**
	 * Dieser Konstruktor erzeugt einen Ausgang mit einem Zielort und einer Richtung.
	 * @param richtung Die Richtung, in die der Ausgang fuehrt.
	 * @param zielort Der Ort, zu dem der Ausgang fuehrt.
	 */
	public Ausgang(byte richtung, Ort zielort) {
		this.zielort = zielort;
	    this.richtung = richtung;
	    // Werte werden nicht ueberprueft, da sie eigentlich von den statics sein sollten.
	    richtungsName = richtungen[richtung];
	    abkuerzung = abkuerzungen[richtung];
	}
	  
	/* --- Die Methoden --- */
	
	/**
	 * Gibt dem Ausgang eine neue Richtung und aendert auch den Namen der Richtung und die Abkuerzung.
	 * @param neueRichtung Die neue Richtung des Ausgangs
	 */
	public void setRichtung(byte neueRichtung) {
	    richtung = neueRichtung;
	    richtungsName = richtungen[richtung];
	    abkuerzung = abkuerzungen[richtung];
	}
	
	/**
	 * Aendert die Richtung unabhaengig von den Standard Parameter, wird verwendet, um eigene Ausgaenge zu kreieren.
	 * @param neueRichtung Die neue Richtung, dadurch werden die anderen Parameter nicht veraendert.
	 * @return Sich selbst.
	 */
	public Ausgang setRichtungUnabhaengig(byte neueRichtung) {
		richtung = neueRichtung;
		return this;
	}
	  
	/**
	 * Gibt den Richtungswert als byte zurueck.
	 * @return Den byte-Wert der Richtung des Ausgangs.
	 */
	public byte getRichtung() {
	    return richtung;
	}
	  
	/**
	 * Gibt die Richtung zurueck, in die der Ausgang fuehrt, als Wort(normal geschrieben).
	 * @return Den Name der Richtung, in die der Ausgang fuehrt.
	 */
	public String getRichtungsName() {
	    return richtungsName;
	}
	  
	/**
	 * Aendert die Richtung des Ausgangs (nur den String).
	 * @param neueRichtung Der neue Name fuer die Richtung in die der Ausgang fuehrt.
	 * @return Sich selbst.
	 */
	public Ausgang setRichtungsName(String neueRichtung) {
	    richtungsName = neueRichtung;
	    return this;
	}
	  
	/**
	 * Gibt die Richtung zurueck als Abkuerzung.
	 * @return Die Abkuerzung fuer den Richtungsnamen.
	 */
	public String getAbkuerzung() {
	    return abkuerzung;
	}
	  
	/**	
	 * Aendert die Abkuerzung des Ausgangs.
	 * @param neueAbkuerzung Der neue Name der Abkuerzung fuer den Ausgang.
	 * @return Sich selbst.
	 */
	public Ausgang setAbkuerzung(String neueAbkuerzung) {
		abkuerzung = neueAbkuerzung;
		return this;
	}
	  
	/**
	 * Gibt den Zielort des Ausgangs zurueck.
	 * @return Der Zielort des Ausgangs.
	 */
	public Ort getZielort() {
	    return zielort;
	}
	  
	/**
	 *  Aendert das Ziel des Ausgangs und gibt ihm einen neuen Zielort.
	 *  @param zielort Der neue Ort, zu dem der Ausgang fuehrt.
	 */
	public void setZielort(Ort zielort) {
		this.zielort = zielort;
	}
	
}