package game.items;

import java.util.Vector;

import util.NumerusGenus;
import game.entity.Attribut;
import game.entity.EntityAttribut;
import game.entity.EntityResistenz;
import game.entity.Resistenz;
import game.entity.Schadensart;

/**
 * Superklasse fuer alle Gegenstaende, die ausgeruestet werden koennen, sie enthaelt naemlich alle Attribute und Resistenzen, die dafuer benoetigt werden.
 * @author Marvin
 */
public abstract class AusruestbarerGegenstand extends Gegenstand {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen fuer Subklassen --- */
	
	// Alle Statuswerte und Attribute in einem Array.
	protected EntityAttribut attribute;
	// Alle Resistenzen des Gegenstands.
	protected EntityResistenz resistenzen;
	
	/* --- Konstruktor --- */
	
	/**
	 * Dieser Konstruktor muss von jeder Subklasse ueberschrieben werden und dieser Konstruktor
	 * darf auch nicht verwendet werden, da diese Klasse abstract ist.
	 */
	protected AusruestbarerGegenstand(String[] namenGegenstand, String plural, NumerusGenus numerusGenus, String beschreibung) {
		super(namenGegenstand, beschreibung, numerusGenus, beschreibung);
		
		attribute = new EntityAttribut(new int[0]);
	    resistenzen = new EntityResistenz(new int[0]);
	}
	
	/* --- Methoden fuer die Subklassen --- */
	
	// Parameter //
	
	@Override
	public String getParam(String param) {
		if(super.getParam(param) != "Ungültig")
			return super.getParam(param);
		
		for(Attribut a : Attribut.getAttribute())
			if(a.getParam() == param)
				return Integer.toString(attribute.getWert(a));
		for(Resistenz r : Resistenz.getResistenzen())
			if(r.getParam() == param)
				return Float.toString(resistenzen.getWert(r));
		
		return "Ungültig";
	}
	
	@Override
	public String[] getParams() {
		Vector<String> s = new Vector<String>();
		for(String param : super.getParams())
			s.add(param);
		
		for(Attribut a : Attribut.getAttribute())
			s.add(a.getParam());
		for(Resistenz r : Resistenz.getResistenzen())
			s.add(r.getParam());
		
		return s.toArray(new String[0]);
	}
	
	// Attribute und Resistenzen //
	
	/**
	 * Gibt den Wert eines Attributs zurueck.
	 * @param attribut Das Attribut, das gesucht wird.
	 * @return Den Wert des gesuchten Attributs und -1, wenn es dieses nicht gibt.
	 */
	public int getAttribut(Attribut attribut) {
		return attribute.getWert(attribut);
	}
	
	/**
	 * Gibt den Wert einer Resistenz basierend zurueck.
	 * @param resistenz Die Resistenz, deren Wert gesucht wird.
	 * @return Den Wert der gesuchten Resistenz und -1.0, wenn es diese nicht gibt.
	 */
	public float getResistenz(Resistenz resistenz) {
		return resistenzen.getWert(resistenz);
	}
	
	/**
	 * Fuegt dem Gegenstand eine Resistenz hinzu, die den Traeger vor der uebergebenen Schadensart schuetzt.
	 * @param schadensart Die Schadensart, vor der der Gegenstand schuetzt.
	 * @param wert Die prozentuale Reduktion des Schadens.
	 * @return Sich selbst.
	 */
	public Gegenstand addResistenz(Schadensart schadensart, float wert) {
		resistenzen.addWert(Resistenz.getResistenz(schadensart), wert);
		return this;
	}
	
	/**
	 *  Gibt zurueck, ob der Gegenstand Statuswerte hat oder nicht.
	 *  @return True, falls der Gegenstand Statuswerte hat, ansonsten false.
	 */
	public boolean hasWerte() {
		return attribute.hasWerte() || resistenzen.hasWerte();
	}

}