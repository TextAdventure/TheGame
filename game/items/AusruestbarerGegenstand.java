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
	
	// Der Lebenspunktebonus des Gegenstands.
	protected int lp;	
	// Der Magiepunktebonus des Gegenstands.
	protected int mp;
	
	// Alle Statuswerte und Attribute in einem Array.
	protected EntityAttribut[] attribute;
	// Alle Resistenzen des Gegenstands.
	protected EntityResistenz[] resistenzen;
	
	/* --- Konstruktor --- */
	
	/**
	 * Dieser Konstruktor muss von jeder Subklasse ueberschrieben werden und dieser Konstruktor
	 * darf auch nicht verwendet werden, da diese Klasse abstract ist.
	 */
	protected AusruestbarerGegenstand(String[] namenGegenstand, String plural, NumerusGenus numerusGenus, String beschreibung) {
		super(namenGegenstand, beschreibung, numerusGenus, beschreibung);
		
		lp = 0;
		mp = 0;
		
		attribute = new EntityAttribut[Attribut.getMaxId()];
	    for(int i = 0; i < Attribut.getMaxId(); i++)
	    	attribute[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], 0);
	    resistenzen = new EntityResistenz[Resistenz.getMaxId()];
	    for(int i = 0; i < Resistenz.getMaxId(); i++)
	    	resistenzen[i] = new EntityResistenz(Resistenz.RESISTENZEN[i], 0.0f);
	}
	
	/* --- Methoden fuer die Subklassen --- */
	
	// Parameter //
	
	@Override
	public String getParam(String param) {
		if(super.getParam(param) != "Ungültig")
			return super.getParam(param);
		
		if(param == "lp") return Integer.toString(lp);
		if(param == "mp") return Integer.toString(mp);
		
		for(EntityAttribut ea : attribute)
			if(ea.getAttribut().getParam() == param)
				return Integer.toString(ea.getWert());
		for(EntityResistenz er : resistenzen)
			if(er.getResistenz().getParam() == param)
				return Float.toString(er.getWert());
		
		return "Ungültig";
	}
	
	@Override
	public String[] getParams() {
		Vector<String> s = new Vector<String>();
		for(String param : super.getParams())
			s.add(param);
		
		s.add("lp");
		s.add("mp");
		for(EntityAttribut ea : attribute)
			s.add(ea.getAttribut().getParam());
		for(EntityResistenz er : resistenzen)
			s.add(er.getResistenz().getParam());
		
		return s.toArray(new String[0]);
	}
	
	// Attribute und Resistenzen //
	
	/**
	 * Gibt den Wert eines Attributs basierend auf dem Namen oder des Parameters zurueck.
	 * @param nameOderParam Der Name oder die Parameterschreibweise des Attributs.
	 * @return Den Wert des gesuchten Attributs und -1, wenn es dieses nicht gibt.
	 */
	public int getAttribut(String nameOderParam) {
		for(EntityAttribut ea : attribute)
			if(ea.getAttribut().getName().equals(nameOderParam) || ea.getAttribut().getParam().equals(nameOderParam))
				return ea.getWert();
		return -1;
	}
	
	/**
	 * Gibt den Wert einer Resistenz basierend auf dem Namen oder des Parameters zurueck.
	 * @param nameOderParam Der Name oder die Parameterschreibweise der Resistenz.
	 * @return Den Wert der gesuchten Resistenz und -1.0, wenn es diese nicht gibt.
	 */
	public float getResistenz(String nameOderParam) {
		for(int i = 0; i < Resistenz.getMaxId(); i++)
			if(Resistenz.RESISTENZEN[i].getName().equals(nameOderParam) || Resistenz.RESISTENZEN[i].getParam().equals(nameOderParam))
				return resistenzen[i].getWert();
		return -1.0f;
	}
	
	/**
	 * Fuegt dem Gegenstand eine Resistenz hinzu, die den Traeger vor der uebergebenen Schadensart schuetzt.
	 * @param schadensart Die Schadensart, vor der der Gegenstand schuetzt.
	 * @param wert Die prozentuale Reduktion des Schadens.
	 * @return Sich selbst.
	 */
	public Gegenstand addResistenz(Schadensart schadensart, float wert) {
		for(EntityResistenz er : resistenzen)
			if(er != null && er.getResistenz().getSchadensart().equals(schadensart)) {
				er.addWert(wert);
				return this;
			}		
		return this;
	}
	
	/**
	 * Gibt die zusaetzlichen Lebenspunkte des Gegenstands zurueck.
	 * @return Die zusaetzlichen Lebenspunkte fuer den Traeger.
	 */
	public int getLp() {
		return lp;
	}
	
	/**
	 * Gibt die zusaetzlichen Magiepunkte des Gegenstands zurueck.
	 * @return Die zusaetzlichen Magiepunkte fuer den Traeger.
	 */
	public int getMp() {
		return mp;
	}
	
	/**
	 * Gibt das gesamte Attributsarray zurueck.
	 * @return Ein Array mit allen Attributen.
	 */
	public EntityAttribut[] getAttribute() {
		return attribute;
	}
	
	/**
	 * Gibt das gesmate Resistenzenarray zurueck.
	 * @return Ein Array mit allen Resistenzen.
	 */
	public EntityResistenz[] getResistenzen() {
		return resistenzen;
	}
	
	/**
	 *  Gibt zurueck, ob der Gegenstand Statuswerte hat oder nicht.
	 *  @return True, falls der Gegenstand Statuswerte hat, ansonsten false.
	 */
	public boolean hasWerte() {
		if(lp != 0 || mp != 0)
			return true;
		for(EntityAttribut a : attribute)
			if(a.getWert() != 0)
				return true;
		for(EntityResistenz r : resistenzen)
			if(r.getWert() != 0)
				return true;
		return false;
	}

}