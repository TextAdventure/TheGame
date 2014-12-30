package game;

/**
 * An einer KombinierungsEinrichtung koennen spezielle Kombinationen
 * gemacht werden, wenn sie vorher als solche markiert werden.
 * @author Marvin
 */
public class KombinierungsEinrichtung extends UntersuchbaresObjekt {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Konstruktor --- */
	
	/**
	 * Eine KombinierungsEinrichtung wird fuer bestimmte Kombinationen
	 * gebraucht und kann nur an Orten mit solch einer kombiniert werden.
	 * @param name Der Name der Einrichtung.
	 * @param beschreibung Die Beschreibung der Einrichtung.
	 */
	public KombinierungsEinrichtung(String name, String beschreibung) {
		super(name, beschreibung);
	}

}