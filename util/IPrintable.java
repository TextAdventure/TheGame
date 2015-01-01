package util;

/**
 * Jede Klasse kann dieses Interface implementieren, sodass sie eine einhetiliche Ausgabe hat
 * und es bietet die Moeglichkeit fuer formatierte Ausgaben und Farben.
 */
public interface IPrintable {
	// Gibt den Namen des Objekts zurueck(OHNE Modifikatoren).
	public String getName();
	// Gibt den Namen MIT allen Modifikatoren zurueck.
	public String getNameExtended();
	// Gibt eine naehere Beschreibung des Objekts zurueck.
	public String getBeschreibung();
	
	// Gibt den Parameter fuer das byte param zurueck(wird individuell verwaltet).
	// Parameter werden prinzipiell mit '<p=WERT>' eingefuegt.
	
	/**
	 * Gibt den String fuer den Parameter zurueck.
	 * @param param Der Parameter, der abgefragt wird.
	 * @return Das Wort fuer disen Parameter.
	 */
	public String getParam(String param);
	
	// Gibt alle Parameter fuer dieses Objekt zurueck.
	
	/**
	 * Gibt alle Parameter in einme String-Array zurueck.
	 * @return Alle Parameter fuer dieses Objekt.
	 */
	public String[] getParams();
	
	
	/**
	 * <p=WERT> Parameter einfuegen
	 * <c=Farbe.class.getName()>TEXT</c>  farbig
	 * <b>TEXT</b> fett
	 * <u>TEXT</u> unterstrichen
	 */
}
