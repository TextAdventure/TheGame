package game;

import java.io.Serializable;

/**
 *  Diese Klasse repraesentiert den Ausgang auseinem Ort zum naechsten.
 */
public class Ausgang implements Serializable {
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Die Statischen int Werte fuer die Richtungen des Ausgangs.
	// Der int-Wert fuer einen Ausgang, der keine Richtung.
	public static final int UNDEFINIERT = 0;

	// Der int-Wert fuer einen Ausgang in Richtung Osten.
	public static final int OSTEN = 1;
	// Der int-Wert fuer einen Ausgang in Richtung Suedosten.
	public static final int SUEDOSTEN = 2;
	// Der int-Wert fuer einen Ausgang in Richtung Sueden.
	public static final int SUEDEN = 3;
	// Der int-Wert fuer einen Ausgang in Richtung Suedwesten.
	public static final int SUEDWESTEN = 4;
	// Der int-Wert fuer einen Ausgang in Richtung Westen.
	public static final int WESTEN = 5;
	// Der int-Wert fuer einen Ausgang in Richtung Nordwesten.
	public static final int NORDWESTEN = 6;
	// Der int-Wert fuer einen Ausgang in Richtung Norden.
	public static final int NORDEN = 7;
	// Der int-Wert fuer einen Ausgang in Richtung Nordosten.
	public static final int NORDOSTEN = 8;

	// Der int-Wert fuer einen Ausgang, der nach unten fuehrt.
	public static final int RUNTER = 9;
	// Der int-Wert fuer einen Ausgang, der nach oben fuehrt.
	public static final int HOCH = 10;
	// Der int-Wert fuer einen Ausgang, der in etwas hineinfuehrt.
	public static final int BETRETEN = 11;
	// Der int-Wert fuer einen Ausgang, der aus etwas herausfuehrt.
	public static final int VERLASSEN = 12;
	  
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
	
	// Der Ort, zu dem dieser Ausgang fuehrt.
	private Ort zielort;
	// Die Richtung, in der der Zielort liegt.
	private int richtung;
	// Die Richtung, als Wort ausgeschrieben.
	private String richtungsName;
	// Die Abkuerzung fuer den Richtungsname.
	private String abkuerzung;
	  
	/**
	 *  Dieser Konstruktor erzeugt einen Ausgang mit einem Zielort und einer Richtung.
	 *  richtung: die Richtung, in die der Ausgang fuehrt.
	 *  fuehrtZu: der Ort, zu dem der Ausgang fuehrt.
	 */
	public Ausgang(int richtung, Ort fuehrtZu){
		zielort = fuehrtZu;
	    this.richtung = richtung;
	    // Werte werden nicht ueberprueft, da sie eigentlich von den statics sein sollten.
	    richtungsName = richtungen[richtung];
	    abkuerzung = abkuerzungen[richtung];
	}
	  
	/**
	 *  Diese Methode aendert die Richtung(int) und alle Variablen.
	 *  neueRichtung: die neue Richtung des Ausgangs
	 */
	public void setRichtung(int neueRichtung){
	    richtung = neueRichtung;
	    richtungsName = richtungen[richtung];
	    abkuerzung = abkuerzungen[richtung];
	}
	  
	/**
	 *  Diese Methode gibt den int Richtungswert zurueck.
	 */
	public int getRichtung(){
	    return richtung;
	}
	  
	/**
	 *  Diese Methode gibt die Richtung zurueck, in die der Ausgang fuehrt (als Wort, normal geschrieben).
	 */
	public String getRichtungsName(){
	    return richtungsName;
	}
	  
	/**
	 *  Diese Methode aendert die Richtung des Ausgangs (nur den String).
	 *  neueRichtung: neuer Name fuer die Richtung in die der Ausgang fuehrt.
	 */
	public void setRichtungsName(String neueRichtung){
	    richtungsName = neueRichtung;
	}
	  
	/**
	 *  Diese Methode gibt die Richtung zurueck als Abkuerzung.
	 */
	public String getAbkuerzung(){
	    return abkuerzung;
	}
	  
	/**
	 *  Diese Methode aendert die Abkuerzung des Ausgangs.
	 *  neueAbkuerzung: die neue Abkuerzung fuer den Ausgang
	 */
	public void setAbkuerzung(String neueAbkuerzung){
		abkuerzung = neueAbkuerzung;
	}
	  
	/**
	 *  Diese Methode gibt den Zielort des Ausgangs zurueck.
	 */
	public Ort getZielort(){
	    return zielort;
	}
	  
	/**
	 *  Diese Methode aendert das Ziel des Ausgangs.
	 *  neuesZiel: der neue Ort, zu dem der Ausgang fuehrt.
	 */
	public void setZielort(Ort neuesZiel){
		zielort = neuesZiel;
	}
	
	/**
	 * Gibt zu einer gegeben Richtung die Rückrichtung zurück. Beispielsweise ist 
	 * getRueckrichtung(Ausgang.WESTEN) == Assgang.OSTEN
	 * @param richtung Die Richtung, deren Umkehhrung man haben möchte.
	 * @return Die Rpckrichtung zu richtung.
	 */
	public static int getRueckrichtung(int richtung) {
		if(richtung >= 1 && richtung <= 8)
			return richtung > 4 ? richtung-4 : richtung+4;				//OSTEN bis NORDOSTEN 
		if(richtung == 9 || richtung == 10) return 19-richtung;			//HOCH RUNTER
		if(richtung == 11 || richtung == 12) return 21-richtung;		//BETETEN VERLASSEN
		
		return Ausgang.UNDEFINIERT;										//default return-Wert
	}
}