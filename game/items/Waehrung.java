package game.items;

import util.NumerusGenus;

public class Waehrung extends Gegenstand {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die groessere Waehrung.
	private Waehrung groessereWaehrung;
	// Die Menge diser Waherung, die fuer eine groessere benoetigt wird.
	private int kursNachOben;
	// Die kleinere Waehrung.
	private Waehrung kleinereWaehrung;
	// Der gemeinsame Parameter aller Waehrungen, die mit dieser zu tun haben.
	private String waehrungsName;
	
	/* --- Der Konstruktor --- */
	
	/**
	 * Eine neue Waehrung braucht einen Namen, ein NumerusGenus und einen parametrisierten Namen, der mit dem von allen anderen Waehrungen, die
	 * mit dieser zusammenhaengen uebereinstimmen muss.
	 * @param nameWaehrung Der Name dieser Waehrung.
	 * @param numerusGenus Der Numerus und Genus der Waehrung.
	 * @param waehrungsName Der fuer alle mit dieser Waehrung zusammenhaengenden Waehrungen gleicher Namen.
	 */
	public Waehrung(String nameWaehrung, String plural, NumerusGenus numerusGenus, String waehrungsName) {
		super(new String[] { nameWaehrung }, plural, numerusGenus, "");
		
		this.waehrungsName = waehrungsName;
		this.kleinereWaehrung = null;
	}
	
	/* --- Deaktivierte Methoden --- */
	/**
	 * Gibt den Parameter fuer einen Parameter String zurueck.
	 * @param param Der Name des Parameters.
	 * @return Den String fuer den Parameter.
	 */
	@Override
	public String getParam(String param) {
		switch(param) {
		case("kurs"): return Integer.toString(kursNachOben);
		case("groessere"): return this.groessereWaehrung.getNameExtended();
		case("kleinere"): return this.kleinereWaehrung.getNameExtended();
		}
		return "";
	}
	
	/**
	 * Gibt alle Parameter der Waehrung zurueck.
	 * @return Alle Parameter.
	 */
	@Override
	public String[] getParams() {
		return new String[0];
	}
	
	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "Waehrung"
	 */
	public String getGegenstandsart() {
		return "Währung";
	}
	
	/* --- eigene Methoden --- */
	
	/**
	 * Fuegt der Waehrung eine groessere hinzu mit dem spezifischen Kurs.
	 * @param waehrung Die groessere Waehrung.
	 * @param kurs Die Anzahl dieser Waherung fuer eine groessere.
	 */
	public void setGroesereWaehrung(Waehrung waehrung, int kurs) {
		this.groessereWaehrung = waehrung;
		groessereWaehrung.kleinereWaehrung = this;
		this.kursNachOben = kurs;
	}
	/**
	 * Gibt den Betrag zurueck, davor wird er aber in die kleinste Waehrungseinheit konvertiert.
	 * @param kleinsterBetrag Der kleinste Betrag fuer diese Art von Waehrung.
	 * @return Den Betrag in der kleinsten Waehrung.
	 */
	public int kleinsteWaehrungBetrag(int kleinsterBetrag) {
		Waehrung w = this;
		int betrag = kleinsterBetrag;
		if(this.getKleinereWaehrung() == null)
			return betrag;
		while(w.hatKleinereWaehrung()) {
			w = w.getKleinereWaehrung();
			betrag *= w.getKurs();
		}
		return betrag;
	}
	/**
	 * Gibt zurueck, ob die Waehrung eine kleinere Einheit hat.
	 * @return true, wenn sie eine kleinere Waehrung hat, ansonsten false.
	 */
	public boolean hatKleinereWaehrung() {
		if(this.kleinereWaehrung != null)
			return true;
		return false;
	}
	/**
	 * Gibt zurueck, ob die Waehrung eine groessere Einheit hat.
	 * @return true, wenn sie eine groessere Waehrung hat, ansosten false.
	 */
	public boolean hatGroessereWaehrung() {
		if(this.groessereWaehrung != null)
			return true;
		return false;
	}
	/**
	 * Gibt den Kurs nach oben zurueck.
	 * @return Den Kurs fuer eine hoehere Waehrung.
	 */
	public int getKurs() {
		return this.kursNachOben;
	}
	/**
	 * Gibt die kleinere Waehrung zurueck.
	 * @return Die kleinere Waherung von dieser.
	 */
	public Waehrung getKleinereWaehrung() {
		return this.kleinereWaehrung;
	}
	/**
	 * Gibt die groessere Waehrung zurueck.
	 * @return Die greossere Waehrung von dieser.
	 */
	public Waehrung getGroessereWaehrung() {
		return this.groessereWaehrung;
	}
	/**
	 * Gibt den uebergreifenden Namen der Waehrung zurueck.
	 * @return Der Name fuer alle mit dieser Waehrung zusammenhaengenden Waehrungen.
	 */
	public String getWaehrungsName() {
		return this.waehrungsName;
	}
	
	/**
	 * Gibt einen String zurueck, mit der groessen Waehrung.
	 * @param kleinsteWaehrung Der Betrag in der kleinsten Waehrung.
	 * @return Eine fertige Ausgabe.
	 */
	public String getOutput(int kleinsteWaehrung) {
		String s = "";
		Waehrung w = this;
		while(w.hatKleinereWaehrung())
			w = w.getKleinereWaehrung();
		int kurs = 1;
		while(w.hatGroessereWaehrung()) {
			kurs *= w.getKurs();
			if(kleinsteWaehrung % kurs > 0) {
				s = kleinsteWaehrung % kurs / w.getKleinereWaehrung().getKurs() != 1 ? w.getPlural() + ", " + s : w.getName() + ", " + s;
				s = Integer.toString(kleinsteWaehrung % kurs / w.getKleinereWaehrung().getKurs()) + " " + s;
			}
			w = w.getGroessereWaehrung();
		}
		System.out.println(kleinsteWaehrung % kurs + " " + w.getName());
		if(kleinsteWaehrung / kurs > 0) {
			s = kleinsteWaehrung / kurs != 1 ? w.getPlural() + ", " + s : w.getName() + ", " + s;
			s = Integer.toString(kleinsteWaehrung / kurs) + " " + s;
		}
		s = s.substring(0, s.length() - 2);
		return s;
	}
	
}
