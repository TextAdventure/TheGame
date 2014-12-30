package game.entity;

import game.Drop;
import game.SpielWelt;
import game.items.Stapel;

import java.io.Serializable;
import java.util.Vector;

import util.NumerusGenus;

/**
 * Superklasse fuer den Spieler und Gegner (evtl. auch fuer NPCs)
 */
public abstract class Entity implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Die Variablen --- */
	
	// Der Name des Lebewesens.
	protected String name;
	// Der Numerus und der Genus.
	protected NumerusGenus numGen;	
	// Die Beschreibung des Lebewesens.
	protected String beschreibung;
	
	// Die Lebenspunkte.
	protected int lp;
	protected int maxLp;
	
	// Die Magiepunkte.
	protected int mp;
	protected int maxMp;
	
	// Alle Attribute des Entities.
	protected EntityAttribut[] attribute;
	// Alle Resistenzen des Entities.
	protected EntityResistenz[] resistenzen;
	// Alle Schadensmultiplikatoren des Entities.
	protected EntityDamageAmplifier[] schadensMultiplikatoren;
	
	// Alle Faehigkeiten des Lebewesens.
	protected Vector<Faehigkeit> faehigkeiten;

	// Fuer Gegner.class
	// Alle Drops, die der Gegner fallen lassen kann.
	protected Vector<Drop> loot;
	
	/* --- Die Konstruktoren --- */
	
	/**
	 * Erstellt ein neues Lebewesen mit Namen, Geschlecht, Beschreibung und den attributen.
	 * @param name Der Name des Lebewesens.
	 * @param numerusGenus Das Geschlecht.
	 * @param beschreibung Die Beschreibung.
	 * @param lp Die Lebenspunkte des Entities.
	 * @param mp Die Mahiepunkte des Entities.
	 * @param attributswerte ALLE Attribute muessen in der ID Reihenfolge(wann sie erstellt wurden) eingegeben werden.
	 */
	public Entity(String name, NumerusGenus numerusGenus, String beschreibung, int lp, int mp, int... attributswerte) {
		this.name = name;
		this.numGen = numerusGenus;
		this.beschreibung = beschreibung;
		
		this.maxLp = lp;
		this.lp = maxLp;
		this.maxMp = mp;
		this.mp = maxMp;
		
		attribute = new EntityAttribut[Attribut.getMaxId()];
		for(int i = 0; i < Attribut.getMaxId(); i++)
			attribute[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], attributswerte[i]);
		// Die Resistenzen sind von Haus aus leer.
		resistenzen = new EntityResistenz[Resistenz.getMaxId()];
		for(int i = 0; i < Resistenz.getMaxId(); i++)
			resistenzen[i] = new EntityResistenz(Resistenz.RESISTENZEN[i], 0.0f);
		// Die Schadensmultiplikatoren ebenfalls.
		schadensMultiplikatoren = new EntityDamageAmplifier[Schadensart.getMaxId()];
		for(int i = 0; i < Schadensart.getMaxId(); i++)
			schadensMultiplikatoren[i] = new EntityDamageAmplifier(Schadensart.SCHADEN[i], 0.0f, 0);
		
		resetTemp();
		
		faehigkeiten = new Vector<Faehigkeit>();
		
		loot = new Vector<Drop>();
	}
	
	
	/**
	 * Ein zweiter Konstruktor fuer den Spieler, alle Attribute sind 0.
	 */
	protected Entity() {
		attribute = new EntityAttribut[Attribut.ATTRIBUTE.length];
		for(int i = 0; i < Attribut.getMaxId(); i++)
			attribute[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], 0);
		// Die Resistenzen sind von Haus aus leer.
		resistenzen = new EntityResistenz[Resistenz.getMaxId()];
		for(int i = 0; i < Resistenz.getMaxId(); i++)
			resistenzen[i] = new EntityResistenz(Resistenz.RESISTENZEN[i], 0.0f);
		// Die Schadensmultiplikatoren ebenfalls.
		schadensMultiplikatoren = new EntityDamageAmplifier[Schadensart.getMaxId()];
		for(int i = 0; i < Schadensart.getMaxId(); i++)
			schadensMultiplikatoren[i] = new EntityDamageAmplifier(Schadensart.SCHADEN[i], 0.0f, 0);
		
		faehigkeiten = new Vector<Faehigkeit>();
		
		loot = new Vector<Drop>();
	}
	
	/* --- Die Methoden --- */
	
	/* - Alle Getter - */
	
	// Gibt den Namen zurueck.
	public String getName() { return name; }
	// Gibt den Numerus und Genus zurueck.
	public NumerusGenus getNumGen() { return numGen; }
	// Gibt die Beschreibung zurueck.
	public String getBeschreibung() { return beschreibung; }
	
	// Gibt die maximalen LP zurueck.
	public int getMaxLp() { return maxLp; }
	// Gibt die aktuellen LP zurueck.
	public int getLp() { return lp; }
	// Gibt die maximalen MP zurueck.
	public int getMaxMp() { return maxMp; }
	// Gibt die aktuellen MP zurueck.
	public int getMp() { return mp; }
	
	/**
	 * Gibt ein Attribut aufgrund des Namens oder Parameternamens zurueck,
	 * falls es dieses Attribut nicht gibt wird -1 zurueckgegeben.
	 * @param nameOderParam Der Name oder die Parameterschreibweise des Namens.
	 * @return Den Wert des Attributs.
	 */
	public int getAttribut(String nameOderParam) {
		for(Attribut a : Attribut.getAttribute())
			if(a.getName().equals(nameOderParam) || a.getParam().equals(nameOderParam))
				return attribute[a.getId()].getWert();
		return -1;
	}
	/**
	 * Gibt ein temporaeres Attribut aufgrund des Namens oder Parameternamens zurueck,
	 * falls es dieses Attribut nicht gibt wird -1 zurueckgegeben.
	 * @param nameOderParam Der Name oder die Parameterschreibweise des Namens.
	 * @return Den Wert des temporaeren Attributs.
	 */
	public int getTempAttribut(String nameOderParam) {
		for(Attribut a : Attribut.getAttribute())
			if(a.getName().equals(nameOderParam) || a.getParam().equals(nameOderParam))
				return attribute[a.getId()].getTemp();
		return -1;
	}
	/**
	 * Gibt den Wert fuer eine Resistenz zurueck, basierend auf dem Namen oder dem Parameter.
	 * @param nameOderParm Der Name oder die Parameterschreibweise.
	 * @return Den Wert der Resistenz in Prozent, falls es dies Resistenz nicht gibt wird -1 zurueckgegeben.
	 */
	public float getResistenz(String nameOderParam) {
		for(Resistenz r : Resistenz.getResistenzen())
			if(r.getName().equals(nameOderParam) || r.getParam().equals(nameOderParam))
				return resistenzen[r.getId()].getWert();
		return -1.0f;
	}
	/**
	 * Gibt eine temporaere Resistenz aufgrund des Namens oder Parameternamens zurueck,
	 * falls es diese Resistenz nicht gibt wird -1 zurueckgegeben.
	 * @param nameOderParam Der Name oder die Parameterschreibweise des Namens.
	 * @return Den Wert der temporaeren Resistenz.
	 */
	public float getTempResistenz(String nameOderParam) {
		for(Resistenz r : Resistenz.getResistenzen())
			if(r.getName().equals(nameOderParam) || r.getParam().equals(nameOderParam))
				return resistenzen[r.getId()].getTemp();
		return -1.0f;
	}
	/**
	 * 	
	 * @param schadensart
	 * @param schaden
	 * @return
	 */
	public int getSchadensBonus(Schadensart schadensart, int schaden) {
		for(EntityDamageAmplifier eda : schadensMultiplikatoren)
			if(eda.getSchadensart().equals(schadensart))
				return eda.getAmplifiedDamage(schaden);
		return schaden;
	}
	
	// Gibt alle Faehigkeiten zurueck.
	public Faehigkeit[] getFaehigkeiten() { return faehigkeiten.toArray(new Faehigkeit[0]); }
	
	// Gibt die Angriffsfaehigkeit des Entitys zurueck(muss ueberschrieben werden).
	public abstract Faehigkeit getFaehigkeit(String kommando);
	
	/* - Alle add-Methoden - */
	
	// Fuegt den Leben ein wenig hinzu.
	public void addLp(int wert) { lp = Math.min(lp + wert, maxLp); }
	// Fuegt der Magie ein wenig hizu.
	public void addMp(int wert) { mp = Math.min(mp + wert, maxMp); }
	
	/**
	 * Fuegt einem Attribut einen gewissen Wert hinzu, dazu wird der Name oder die Parameterschreibweise des Attributs benoetigt.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 * @param nameOderParam Der Name oder die Parameterschreibweise des Attributs.
	 */
	public void addAttribut(int wert, String nameOderParam) {
		for(Attribut a : Attribut.getAttribute())
			if(a.getName().equals(nameOderParam) || a.getParam().equals(nameOderParam))
				attribute[a.getId()].addWert(wert);
	}
	/**
	 * Fuegt einem Attribut einen gewissen temporaeren Wert hinzu, dazu wird der Name oder die Parameterschreibweise des Attributs benoetigt.
	 * @param wert Der temporaere Wert, der hinzugefuegt werden soll.
	 * @param nameOderParam Der Name oder die Parameterschreibweise des Attributs.
	 */
	public void addTempAttribut(int tempWert, String nameOderParam) {
		for(Attribut a : Attribut.getAttribute())
			if(a.getName().equals(nameOderParam) || a.getParam().equals(nameOderParam))
				attribute[a.getId()].addTemp(tempWert);
	}
	/**
	 * Fuegt der Resistenz einen gewissen Wert hinzu, dazu wird der Name oder die Parameterschreibweise der Resistenz benoetigt.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 * @param nameOderParam Der Name oder die Parameterschreibweise der Resistenz.
	 */
	public void addResistenz(float wert, String nameOderParam) {
		for(Resistenz r : Resistenz.getResistenzen())
			if(r.getName().equals(nameOderParam) || r.getParam().equals(nameOderParam))
				resistenzen[r.getId()].addWert(wert);
	}
	/**
	 * Fuegt der Resistenz einen gewissen temporaeren Wert hinzu, dazu wird der Name oder die Parameterschreibweise der Resistenz benoetigt.
	 * @param wert Der temporaere Wert, der hinzugefuegt werden soll.
	 * @param nameOderParam Der Name oder die Parameterschreibweise der Resistenz.
	 */
	public void addTempResistenz(float tempWert, String nameOderParam) {
		for(Resistenz r : Resistenz.getResistenzen())
			if(r.getName().equals(nameOderParam) || r.getParam().equals(nameOderParam))
				resistenzen[r.getId()].addTemp(tempWert);
	}
	
	/**
	 * Fuegt den Schadensmultiplikatoren eien gewissen prozentualen und absoluten Wert hinzu.
	 * @param schadensart Die Schadensart fuer die der Bonus gilt.
	 * @param prozentual Der zusaetzliche prozentuale Bonus.
 	 * @param absolut Der zusaetzliche absolute Schaden.
	 */
	public void addSchadensMultiplikator(Schadensart schadensart, float prozentual, short absolut) {
		for(Schadensart s : Schadensart.getSchadensarten())
			if(s.equals(schadensart)) {
				this.schadensMultiplikatoren[s.getId()].addProzentualenSchaden(prozentual);
				this.schadensMultiplikatoren[s.getId()].addAbsolutenSchaden(absolut);
			}
	}
	
	/**
	 * Fuegt eine neue Faehigkeit dem Lebewesen hinzu.
	 * @param faehigkeit Die neue Faehigkeit.
	 */
	public void addFaehigkeit(Faehigkeit faehigkeit) {
		if(!faehigkeiten.contains(faehigkeit))
			faehigkeiten.add(faehigkeit);
	}
	
	
	/* - Alle anderen Methoden - */
	
	/**
	 * Setzt die temporaeren Attribute zurueck.
	 */
	public void resetTemp() {
		for(int i = 0; i < Attribut.getMaxId(); i++)
			attribute[i].resetTemp();
		for(int i = 0; i < Resistenz.getMaxId(); i++)
			resistenzen[i].resetTemp();
	}

	/**
	 * Dem Entity wird Schaden zugefuegt, dabei wird die Schadensart beruecksichtigt und eine entsprechende Resistenz gewaehlt.
	 * @param angriff Der Angriffswert des Angreifers.
	 * @param schadensart Die Art des Schadens.
	 * @return Den letztlichen Schaden, der zugefuegt wurde.
	 */
	public int fuegeSchadenZu(int angriff, Schadensart schadensart) {
		// Zuerst wird der Schaden nur mit dem Resistenzen Attribut berechnet.
		double schaden = Math.pow(1.6, Math.log(angriff / Math.max(Resistenz.getResistenz(schadensart).getAttribut(this), 1))) * angriff / 4;
		// Danach kommt die prozentuale Resistenz.
		schaden *= (100.0f - resistenzen[Resistenz.getResistenz(schadensart).getId()].getTemp()) / 100.0f;
		lp = Math.max(lp - (int) schaden, 0);
		
		return (int) schaden;
	}
	
	/**
	 * Fuegt moegliche Drops hinzu.
	 * @param drops Die moeglichen Drops, die der Gegner fallen laesst.
	 */
	public void addDrop(Drop... drops) {
		for(Drop d : drops)
			loot.add(d);
	}
	/**
	 * Gibt den Loot zurueck, der von dem Gegner erhalten wurde.
	 * @return Den Loot.
	 */
	public Stapel[] getLoot() {
		int sigmaChance = 0;
		for(Drop d : loot)
			sigmaChance += d.getChance();
		if(sigmaChance == 0)
			return new Stapel[0];
		
		// Exklusive obere Grenze!
		int nInt = SpielWelt.WELT.r.nextInt(sigmaChance + 1);
		for(Drop d : loot) 
			if(nInt >= sigmaChance - d.getChance() && nInt <= sigmaChance)
				return d.getGegenstaende();
			else
				sigmaChance -= d.getChance();
		return new Stapel[0];
	}
}
