package game.entity;

import game.SpielWelt;
import game.items.Stapel;

import java.io.Serializable;
import java.util.Vector;

import util.Drop;
import util.NumerusGenus;

/**
 * Superklasse fuer den Spieler und Gegner (evtl. auch fuer NPCs)
 */
public abstract class Entity implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Der Name des Lebewesens.
	protected String name;
	// Der Numerus und der Genus.
	protected NumerusGenus numGen;	
	// Die Beschreibung des Lebewesens.
	protected String beschreibung;
	
	// Die aktuellen Ressourcen
	protected float[] ressourcen;	
	
	// Alle Attribute des Entities.
	protected EntityAttribut attribute;
	// Alle Resistenzen des Entities.
	protected EntityResistenz resistenzen;
	// Alle Schadensmultiplikatoren des Entities.
	protected EntityDamageAmplifier[] schadensMultiplikatoren;
	// Die kritische Treffer wahrscheinlichkeit des Entities.
	public KritischerTreffer kritisch;
	
	// Alle Faehigkeiten des Lebewesens.
	protected Vector<Faehigkeit> faehigkeiten;
	// Alle Faehigkeiten der Gegner.
	protected Drop<Faehigkeit>[] faehigkeitenG;

	// Fuer Gegner.class
	// Alle Drops, die der Gegner fallen lassen kann.
	protected Drop<Stapel>[] loot;
	
	/* --- Konstruktor --- */
	
	/**
	 * Erstellt ein neues Lebewesen mit Namen, Geschlecht, Beschreibung und den attributen.
	 * @param name Der Name des Lebewesens.
	 * @param numerusGenus Das Geschlecht.
	 * @param beschreibung Die Beschreibung.
	 * @param lp Die Lebenspunkte des Entities.
	 * @param attributswerte ALLE Attribute muessen in der ID Reihenfolge(wann sie erstellt wurden) eingegeben werden.
	 */
	public Entity(String name, NumerusGenus numerusGenus, String beschreibung, int... attributswerte) {
		this.name = name;
		this.numGen = numerusGenus;
		this.beschreibung = beschreibung;
		
		attribute = new EntityAttribut(attributswerte);
		
		this.ressourcen = new float[Ressource.getMaxId()];
		for(Ressource r : Ressource.getRessourcen())
			ressourcen[r.getId()] = Ressource.getMaxRessource(r, this);
		
		// Die Resistenzen sind von Haus aus leer.
		resistenzen = new EntityResistenz(new int[0]);
		// Die Schadensmultiplikatoren ebenfalls.
		schadensMultiplikatoren = new EntityDamageAmplifier[Schadensart.getMaxId()];
		for(int i = 0; i < Schadensart.getMaxId(); i++)
			schadensMultiplikatoren[i] = new EntityDamageAmplifier(Schadensart.SCHADEN[i], 0.0f, 0);
		resetTemp();
		this.kritisch = new KritischerTreffer(0, 0);		
		
		faehigkeiten = new Vector<Faehigkeit>();
	}
	
	
	/**
	 * Ein zweiter Konstruktor fuer den Spieler, alle Attribute sind 0.
	 */
	protected Entity() {
		attribute = new EntityAttribut(new int[0]);
		
		this.ressourcen = new float[Ressource.getMaxId()];
		for(Ressource r : Ressource.getRessourcen())
			ressourcen[r.getId()] = Ressource.getMaxRessource(r, this);
		
		// Die Resistenzen sind von Haus aus leer.
		resistenzen = new EntityResistenz(new int[0]);
		// Die Schadensmultiplikatoren ebenfalls.
		schadensMultiplikatoren = new EntityDamageAmplifier[Schadensart.getMaxId()];
		for(int i = 0; i < Schadensart.getMaxId(); i++)
			schadensMultiplikatoren[i] = new EntityDamageAmplifier(Schadensart.SCHADEN[i], 0.0f, 0);
		resetTemp();
		this.kritisch = new KritischerTreffer(0, 0);	
		
		faehigkeiten = new Vector<Faehigkeit>();
	}
	
	/* --- Methoden --- */
	
	/* - Alle Getter - */
	
	/**
	 * Gibt den Namen zurueck.
	 * @return Den Namen.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gibt den Numerus und Genus zurueck.
	 * @return Den Numerus und Genus.
	 */
	public NumerusGenus getNumGen() {
		return numGen;
	}
	
	/**
	 * Gibt die Beschreibung zurueck.
	 * @return Die Beschreibung.
	 */
	public String getBeschreibung() {
		return beschreibung;
	}
	
	/**
	 * Gibt die temporaeren maximalen LP zurueck.
	 * @return Die temporaeren maximalen LP.
	 */
	public float getMaxLp() {
		return Ressource.getMaxLeben(this);
	}
	
	/**
	 * Gibt die aktuellen LP zurueck als Ganzzahl.
	 * @return Die aktuellen LP als Ganzzahl.
	 */
	public int getLp() {
		return Math.round(ressourcen[0]);
	}
	
	/**
	 * Gibt den aktuellen Wert einer Ressource zurueck.
	 * @param ressource Die Ressource, die gesucht wird.
	 * @return Den aktuellen Wert einer Ressource.
	 */
	public int getRessource(Ressource ressource) {
		return Math.round(ressourcen[ressource.getId()]);
	}
	
	/**
	 * Gibt die temporaere Initiative des Lebewesens zurueck.
	 * @return Die temporaere Initiative.
	 */
	public float getInitiative() {
		return Ressource.getInitiative(this);
	}
	
	/**
	 * Gibt den Wert eines Attributs zurueck,
	 * falls es dieses Attribut nicht gibt wird -1 zurueckgegeben.
	 * @param attribut Das Attribut, dessen Wert gesucht wird.
	 * @return Den Wert des Attributs.
	 */
	public int getWert(Attribut attribut) {
		return attribute.getWert(attribut);
	}
	
	/**
	 * Gibt ein temporaeres Attributs zurueck,
	 * falls es dieses Attribut nicht gibt wird -1 zurueckgegeben.
	 * @param attribut Das Attribut, dessen Wert gesucht wird.
	 * @return Den Wert des temporaeren Attributs.
	 */
	public int getTemp(Attribut attribut) {
		return attribute.getTemp(attribut);
	}
	
	/**
	 * Gibt den Wert fuer eine Resistenz zurueck.
	 * @param resistenz Die Resistenz, deren Wert gesucht wird.
	 * @return Den Wert der Resistenz in Prozent, falls es dies Resistenz nicht gibt wird -1 zurueckgegeben.
	 */
	public float getResistenz(Resistenz resistenz) {
		return resistenzen.getWert(resistenz);
	}
	
	/**
	 * Gibt eine temporaere Resistenz zurueck,
	 * falls es diese Resistenz nicht gibt wird -1 zurueckgegeben.
	 * @param resistenz Die Resistenz, deren temporaerer Wert gesucht wird.
	 * @return Den Wert der temporaeren Resistenz.
	 */
	public float getTempResistenz(Resistenz resistenz) {
		return resistenzen.getTemp(resistenz);
	}
	
	/**
	 * Gibt den Schadensbonus fuer eine bestimmte Schadensart und den Grundschaden zurueck.
	 * @param schadensart Die Schadensart mit der angegriffen wird.
	 * @param schaden Der zugefuegte Grundschaden.
	 * @return Der Schaden mit dem Bonus dazu gerechnet.
	 */
	public int getSchadensBonus(Schadensart schadensart, int schaden) {
		for(EntityDamageAmplifier eda : schadensMultiplikatoren)
			if(eda.getSchadensart().equals(schadensart))
				return eda.getAmplifiedDamage(schaden);
		return schaden;
	}
	
	/**
	 * Gibt alle Faehigkeiten in einer Liste zurueck.
	 * @return Alle Faehigkeiten.
	 */
	public Faehigkeit[] getFaehigkeiten() {
		return faehigkeiten.toArray(new Faehigkeit[0]);
	}
	
	/**
	 * Gibt alle Faehigkeiten in einer Liste zurueck.
	 * @return Alle Faehigkeiten.
	 */
	public Drop<Faehigkeit>[] getFaehigkeitenAlsDrop() {
		return faehigkeitenG;
	}
	
	/**
	 * Gibt die Angriffsfaehigkeit des Entitys zurueck(muss ueberschrieben werden).
	 * @param kommando Das Angriffskommando.
	 * @return Die Faehigkeit, die das Entity einsetzt.
	 */
	public abstract Faehigkeit getFaehigkeit(String kommando);
	
	/* - Alle add-Methoden - */
	
	/**
	 * Fuegt den LP einen Betrag hinzu.
	 * @param wert Erhoeht die LP um diesen Wert.
	 */
	public void addLp(float wert) {
		ressourcen[0] = Math.min(ressourcen[0] + wert, getMaxLp());
	}
	
	/**
	 * Fuegt einer Ressource einen Wert hinzu.
	 * @param ressource Die Ressource, der ein Wert hinzugefuegt werden soll.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 */
	public void addRessource(Ressource ressource, float wert) {
		ressourcen[ressource.getId()] = Math.min(ressourcen[ressource.getId()] + wert, Ressource.getMaxRessource(ressource, this));
	}
	
	/**
	 * Fuegt einem Attribut einen gewissen Wert hinzu.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 * @param attribut Das Attribut, dem etwas hinzugefuegt werden soll.
	 */
	public void addAttribut(int wert, Attribut attribut) {
		attribute.addWert(attribut, wert);
	}
	
	/**
	 * Fuegt einem Attribut einen gewissen temporaeren Wert hinzu.
	 * @param wert Der temporaere Wert, der hinzugefuegt werden soll.
	 * @param attribut Das Attribut, dem ein temporaerer Wert hinzugefuegt werden soll.
	 */
	public void addTempAttribut(int tempWert, Attribut attribut) {
		attribute.addTemp(attribut, tempWert);
	}
	
	/**
	 * Fuegt der Resistenz einen gewissen Wert hinzu.
	 * @param wert Der Wert, der hinzugefuegt werden soll.
	 * @param resistenz Die Resistenz, der ein Wert hinzugefuegt werden soll.
	 */
	public void addResistenz(float wert, Resistenz resistenz) {
		resistenzen.addWert(resistenz, wert);
	}
	
	/**
	 * Fuegt der Resistenz einen gewissen temporaeren Wert hinzu.
	 * @param wert Der temporaere Wert, der hinzugefuegt werden soll.
	 * @param resistenz Die Resistenz, der ein temporaerer Wert hinzugefuegt werden soll.
	 */
	public void addTempResistenz(float tempWert, Resistenz resistenz) {
		resistenzen.addTemp(resistenz, tempWert);
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
	 * @param wahrscheinlichkeit Die Wahrscheinlichkeit fuer einen Gegner, dass er diese Faehigkeit verwendet.
	 */
	public void addFaehigkeit(Faehigkeit faehigkeit, int wahrscheinlichkeit) {
		Vector<Drop<Faehigkeit>> neu = new Vector<Drop<Faehigkeit>>();
		if(faehigkeitenG != null)
			for(Drop<Faehigkeit> d : faehigkeitenG)
				neu.add(d);
		neu.add(new Drop<Faehigkeit>(wahrscheinlichkeit, faehigkeit));
		this.faehigkeitenG = Drop.toArray(neu);
	}
	
	/* - Alle anderen Methoden - */
	
	/**
	 * Setzt die temporaeren Attribute zurueck.
	 */
	public void resetTemp() {
		attribute.resetTemp();
		resistenzen.resetTemp();
		for(Ressource r : Ressource.getRessourcen())
			ressourcen[r.getId()] = Math.min(ressourcen[r.getId()], Ressource.getMaxRessource(r, this));
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
		// Danach kommt die prozentuale Resistenz, diese kann nicht groesser als 100% sein, weil sonst der Schaden negativ wird.
		schaden *= (100.0f - Math.min(resistenzen.getTemp(Resistenz.getResistenz(schadensart)), 100.0f)) / 100.0f;
		ressourcen[0] = Math.max(ressourcen[0] - (float) schaden, 0);
		
		return (int) schaden;
	}
	
	/**
	 * Laesst das Entity sich erholen und es regeneriert seine Ressourcen.
	 */
	public void regeneriere() {
		for(Ressource r : Ressource.getRessourcen())
			ressourcen[r.getId()] = Math.min(Ressource.getMaxRessource(r, this), ressourcen[r.getId()] + r.getRessourceRegeneration(this));
	}
	
	/**
	 * Fuegt einen moeglichen Drop hinzu.
	 * @param wahrscheinlichkeit Die Wahrscheinlichkeit fuer diesen Drop.
	 * @param stapel Die Gegenstaende, die bei diesem Drop fallengelassen werden.
	 */
	public void addDrop(int wahrscheinlichkeit, Stapel... stapel) {
		Vector<Drop<Stapel>> neu = new Vector<Drop<Stapel>>();
		if(loot != null)
			for(Drop<Stapel> d : loot)
				neu.add(d);
		neu.add(new Drop<Stapel>(wahrscheinlichkeit, stapel));
		this.loot = Drop.toArray(neu);
	}
	
	/**
	 * Gibt den Loot zurueck, der von dem Gegner erhalten wurde.
	 * @return Den Loot.
	 */
	public <E> Stapel[] getLoot() {		
		Stapel[] s = Drop.drop(SpielWelt.WELT.r, loot);
		if(s == null)
			return new Stapel[0];
		else
			return s;
	}

}