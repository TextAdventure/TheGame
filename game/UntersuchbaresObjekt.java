package game;

import java.io.Serializable;

import util.IPrintable;

/**
 * Ein UntersuchbaresObjekt kann sich der Spieler genauer anschauen, um Informationen darueber zu erhalten.
 * @author Marvin
 */
public class UntersuchbaresObjekt implements Serializable, IPrintable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */

	// Der Name des Objekts.
	private String name;
	// Die Beschreibung, die bei einer Untersuchung angezeigt wird.
	private String beschreibung;

	// Gibt an, ob das Objekt bereits untersucht wurde.
	private boolean untersucht;

	/* --- Konstruktor --- */

	/**
	 * Erstellt ein neues UntersuchbaresObjekt mit einem Namen und einer Beschreibung.
	 * @param name Der Name des Objekts.
	 * @param beschreibung Die Beschreibung des Objekts.
	 */
	public UntersuchbaresObjekt(String name, String beschreibung) {
	    this.name = name;
	    this.beschreibung = beschreibung;
	    untersucht = false;
	}

	/* --- Methoden --- */

	/* Name */
	
	/**
	 * Gibt den Namen des Objekts zurueck, OHNE Modifikatoren.
	 * @return Den Namen des Objekts.
	 */
	 @Override
	public String getName() {
		 String actual = name;
		 while(actual.contains("<c=")) {
			 actual = actual.replaceFirst("</c>", "");
			 actual = actual.substring(0, actual.indexOf("<")) + actual.substring(actual.indexOf(">") + 1);
		 }
		 return actual;
	}
	 
	/**
	 * Gibt den Namen des Objekts zurueck, MIT Modifikatoren.
	 * @return Den Namen des Objekts.
	 */
	@Override
	public String getNameExtended() {
		return name;
	}
	
	/**
	 * Aendert den Namen des Objekts auf den uebergebenen Namen und der Untersucht-Status wird zurueckgesetzt.
	 * @param name Der neue Name des Objekts.
	 */
	public void setName(String name) {
	    this.name = name;
	    untersucht = false;
	}
	
	/* Beschreibung */
	
	/**
	 * Gibt die Beschreibung des Objekt zurueck.
	 * @return Die Beschreibung des Objekts.
	 */
	@Override
	public String getDescription() {
	    return beschreibung;
	}
	
	/**
	 * Aendert die Beschreibung des Objekts auf die uebergebene Beschreibung und der Untersucht-Status wird zurueckgesetzt.
	 * @param beschreibung Die neue Beschreibung des Objekts.
	 */
	public void setBeschreibung(String beschreibung) {
	    this.beschreibung = beschreibung;
	    untersucht = false;
	}
	
	/* Untersucht */
	
	/**
	 * Gibt zurueck, ob das Objekt bereits untersucht wurde.
	 * @return True, wenn es untersucht wurde, ansonsten false.
	 */
	public boolean isUntersucht() {
	    return untersucht;
	}
	
	/**
	 * Das Objekt wird untersucht, es gibt seinen Namen und Beschreibung zurueck, damit wurde es untersucht.
	 * @return Der Name und die Beschreibung des Objekts.
	 */
	public String untersuchen() {
		untersucht = true;
		return name + "#" + beschreibung;
	}

	/**
	 * Gibt den richtigen Inhalt fuer einen Parameter zurueck.
	 * @param param Der Parameter nach dem gefragt wird.
	 * @return Den Inhalt, fuer den der Parameter steht.
	 */
	@Override
	public String getParam(String param) {
		/* TODO
		 * switch (param) {
		case("name"): return getName();
		case("nameExt"): return getNameExtended();
		case("besucht"): return isUntersucht() ? "wahr" : "falsch";
		}
		return null;
		*/
		return "test";
	}

	/**
	 * Gibt eine Liste mit allen gueltigen Parametern zurueck.
	 * @return Eine Liste mit allen Parametern.
	 */
	@Override
	public String[] getParams() {
		// return new String[] {"name", "nameExt", "besucht"}; TODO
		return new String[]{"test"};
	}

}