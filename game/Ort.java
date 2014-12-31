package game;

import game.battle.Kampf;
import game.items.Gegenstand;
import game.items.Inventar;
import game.items.KommandoGegenstand;
import game.items.Stapel;
import game.logic.Ereignis;

import java.io.Serializable;
import java.util.Vector;

import util.IPrintable;

import npc.NPC;

/**
 * Der Spieler bewegt sich durch die Orte und kann dann innerhalb dieser mit der Spielwelt interagieren.
 * @author Marvin
 */
public class Ort implements Serializable, IPrintable {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Gibt an, ob dieser Ort bereits besucht wurde.
	private boolean besucht;
	// Diese Variable speichert den Name des Orts.
	private String name;
	// Die Beschreibung fuer diesen Ort.
	private String beschreibung;
	
	// Alle Ausgaenge, dieaus dem Raum herausfuehren.
	private Vector<Ausgang> ausgaenge;
	
	// Dieser Vector enthaelt alle Objekte, die untersucht werden koennen.
	private Vector<UntersuchbaresObjekt> untersuchbareObjekte;
	
	// Alle Gegenstaende an diesem Ort.
	private Inventar gegenstaende;
	
	// Alle Gegenstaende, die bei einer Benutzung eine Aktion ausloesen koennen.
	private Vector<KommandoGegenstand> kommandoGegenstaende;
	
	// Alle Custom Commands fuer diesen Ort.
	private Vector<Kommando> kommandos;
	
	// Alle Gegner an diesem Ort.
	private Vector<Kampf> kaempfe;
	
	// Alle Behaelter an diesem Ort.
	private Vector<Behaelter> behaelter;
	
	// Alle NPCs an diesem Ort.
	private Vector<NPC> npcs;
	
	// Alle Tueren and diesem Ort
	private Vector<Tuer> tueren;
	
	// Alle Ressourcen Punkte an diesem Ort
	private Vector<RessourcenPunkt> ressourcen;
	
	// Die Wahrscheinlichkeitt fuer einen Kampf.
	private double pKampf;
	
	/* --- Konstruktor --- */
	
	/**
	 * Einem neuen Ort wird lediglich ein Name und eine Beschreibung uebergeben, der Rest muss spaeter hinzugefuegt werden.
	 * @param name Der Name fuer diesen Ort.
	 * @param beschreibung Die Beschreibung fuer diesen Ort.
	 */
	public Ort(String name, String beschreibung) {
	    this.name = name;
	    this.beschreibung = beschreibung;
	    ausgaenge = new Vector<Ausgang>();
	    untersuchbareObjekte = new Vector<UntersuchbaresObjekt>();
	    gegenstaende = new Inventar();
	    kommandoGegenstaende = new Vector<KommandoGegenstand>();
	    kommandos = new Vector<Kommando>();
	    kaempfe = new Vector<Kampf>();
	    behaelter = new Vector<Behaelter>();
	    npcs = new Vector<NPC>();
	    tueren = new Vector<Tuer>();
	    ressourcen = new Vector<RessourcenPunkt>();
	    
	    besucht = false;
	    pKampf = 100.0;
	}
	
	/* --- Methoden --- */
	
	/* Name und Beschreibung */
	
	/**
	 * Gibt den Namen des Orts zurueck, OHNE Modifikatoren.
	 * @return Den Namen.
	 */
	@Override
	public String getName() {
		// Es werden nur Farben ersetzt, da von mehr im Moment nicht auszugehen ist.
		String actual = name;
		while(actual.contains("<c=")) {
			actual = actual.replaceFirst("</c>", "");
			actual = actual.substring(0, actual.indexOf("<")) + actual.substring(actual.indexOf(">") + 1);
		}
		return actual;
	}
	
	/**
	 * Gibt den Namen des Orts MIT Modifikatoren zurueck.
	 * @return Den erweiterten Namen.
	 */
	@Override
	public String getNameExtended() {
		return name;
	}
	
	/**
	 * Aendert den Namen des Orts, dadurch wird er wieder unbesucht.
	 * @param neuerName Der neue Name fuer diesen Ort.
	 */
	public void setName(String neuerName) {
		name = neuerName;
		besucht = false;
	}
	
	/**
	 * Gibt die Beschreibung des Orts zurueck, dadurch wird er wieder unbesucht.
	 * @return Die Beschreibung.
	 */
	@Override
	public String getDescription() {
		return beschreibung;
	}
	
	/**
	 * Aendert die Beschreibung des Orts zu der uebergebenen Beschreibung.
	 * @param neueBeschreibung Die neue Beschreibung fuer diesen Ort.
	 */
	public void setBeschreibung(String neueBeschreibung) {
	    beschreibung = neueBeschreibung;
	    besucht = false;
	}
	
	/* Parameter */
	
	/**
	 * Gibt den String fuer einen Parameter zurueck.
	 * @param param Der Parameter, nach dem gefragt wurde.
	 * @return Der Inhalt des Parameters in diesem Ort.
	 */
	@Override
	public String getParam(String param) {
		// Alle Parameter muessen von Hand in getParams() registriert werden!
		/*
		switch(param) {
		case "name": return this.getNameExtended();
		case "anzahlAusgänge": return Integer.toString(this.getAusgaenge().size());
		case "anzahlGegenstände": return Integer.toString(this.gegenstaende.getAlleGegenstaende().length);
		case "anzahlUntersuchbaresObjekt": return Integer.toString(this.untersuchbareObjekte.size());
		case "anzahlNichtUntersucht":
			int i = 0;
			for(UntersuchbaresObjekt o : this.untersuchbareObjekte)
				if(!o.isUntersucht())
					i++;
			return Integer.toString(i);
		case "anzahlNPC": return Integer.toString(this.npcs.size());
		case "alleNPCs":
			String s = "";
			for(NPC npc : this.npcs)
				s += npc.getName() + ", ";
			if(s.equals(""))
				return "";
			return s.substring(0, s.length() - 2);
		}
		// Alle moeglichen Zielorte und Richtungen.
		for(Ausgang a : this.getAusgaenge().toArray(new Ausgang[0]))
			if(a.getRichtungsName().toLowerCase().equals(param))
				return a.getZielort().getNameExtended();
		*/
		return "test";
	}
	
	/**
	 * Gibt ein Array mit allen moeglichen Parameter eines Orts zurueck.
	 * @return Eine Liste mit allen verfuegbaren Parametern.
	 */
	@Override
	public String[] getParams() {
		/*
		Vector<String> params = new Vector<String>();
		params.add("name");
		params.add("anzahlAusgänge");
		params.add("anzahlUntersuchbaresObjekt");
		params.add("anzahlNichtUntersucht");
		params.add("anzahlGegenstände");
		params.add("anzahlNPC");
		params.add("alleNPCs");

		for(Ausgang a : ausgaenge)
			params.add(a.getRichtungsName().toLowerCase());

		return params.toArray(new String[0]);
		*/
		return new String[]{"test"};
	}

	/* Besucht */

	/**
	 * "Besucht" diesen Ort und gibt den Namen und die Beschreibung zurueck, sie werden duch ein # getrennt, dadurch ist der Ort besucht.
	 * @return Den Namen und die Beschreibung durch ein # getrennt.
	 */
	public String besuchen() {
		besucht = true;
		return name + "#" + beschreibung;
	}
	
	/**
	 * Gibt zurueck, ob der Ort bereits besucht wurde.
	 * @return True, wenn der Spieler bereits einmal hier war, ansonsten false.
	 */
	public boolean isBesucht() {
		return besucht;
	}
	
	/* Ausgang */
	
	/**
	 * Fuegt dem Ort einen Ausgang hinzu.
	 * @param richtung Die Richtung, in die er fuehrt.
	 * @param zielort Der Ort zu dem der Ausgang fuehrt.
	 */
	public void addAusgang(byte richtung, Ort zielort) {
		ausgaenge.add(new Ausgang(richtung, zielort));
	}
	
	/**
	 * Fuegt dem Ort einen Ausgang hinzu.
	 * @param ausgang Der neue Ausgang an diesem Ort.
	 */
	public void addAusgang(Ausgang ausgang) {
		ausgaenge.add(ausgang);
	}
	
	/**
	 * Entfernt einen Ausgang aus der Liste der Ausgaenge.
	 * @param ausgang Der zuentfernde Ausgang.
	 */
	public void removeAusgang(Ausgang ausgang) {
		ausgaenge.remove(ausgang);
	}
	
	/**
	 * Gibt alle Ausgaenge zurueck, die dieser Ort hat.
	 * @return Eine Liste mit allen Ausgaengen.
	 */
	public Vector<Ausgang> getAusgaenge() {
		return ausgaenge;
	}
	
	/**
	 * Gibt einen Ausgang zurueck, basierend auf der Richtung.
	 * @param richtung Die Richtung des Ausgangs.
	 * @return Den Ausgang, falls vorhanden, ansonsten null.
	 */
	public Ausgang getAusgang(int richtung) {
		for(Ausgang a : ausgaenge)
			if(a.getRichtung() == richtung)
				return a;
		return null;
	}
	
	/**
	 * Gibt einen Ausgang zurueck, basierend auf dem Namen der Richtung oder der Abkuerzung.
	 * @param nameOderAbkuerzung Der Name der Richtung oder deren Abkuerzung.
	 * @return Den Ausgang selbst, falls vorhanden, ansonsten null.
	 */
	public Ausgang getAusgang(String nameOderAbkuerzung) {
		for(Ausgang a : ausgaenge)
			if(a.getRichtungsName().equalsIgnoreCase(nameOderAbkuerzung) || a.getAbkuerzung().equalsIgnoreCase(nameOderAbkuerzung))
				return a;
		return null;
	}
	
	/**
	 * Gibt zurueck, ob der Ort einen entsprechenden Ausgang hat oder auch nicht.
	 * @param ausgang Der Ausgang auf den der Ort ueberprueft werden soll.
	 * @return True, wenn der Ort diesen Ausgang hat.
	 */
	public boolean hasAusgang(Ausgang ausgang) {
		for(Ausgang a : ausgaenge)
			if(a == ausgang) return true;
		return false;
	}
	
	/* UntersuchbaresObjekt */
	
	/**
	 * Fuegt ein neues UntersuchbaresObjekt hinzu, das untersucht werden kann.
	 * @param objName Der Name des Objekts.
	 * @param objBeschreibung Die Beschreibung fuer das Objekt.
	 */
	public void addUntersuchbaresObjekt(String objName, String objBeschreibung) {
		untersuchbareObjekte.add(new UntersuchbaresObjekt(objName, objBeschreibung));
	}
	
	/**
	 * Fuegt ein neues UntersuchbaresObjekt hinzu, das untersucht werden kann.
	 * @param objekt Das UntersuchbareObjekt fuer den Ort.
	 */
	public void addUntersuchbaresObjekt(UntersuchbaresObjekt objekt) {
		untersuchbareObjekte.add(objekt);
	}
	
	/**
	 * Entfernt ein UntersuchbaresObjekt aufgrund des Namens.
	 * @param objektName Der Name des zuentfernden Objekts.
	 */
	public void removeUntersuchbaresObjekt(String objektName) {
		UntersuchbaresObjekt objekt = getUntersuchbaresObjekt(objektName);
	    if(objekt == null)
	    	return;
	    untersuchbareObjekte.remove(objekt);
	}
	
	/**
	 * Entfernt ein UntersuchbaresObjekt.
	 * @param objekt Das zuentfernende UntersuchbareObjekt.
	 */
	public void removeUntersuchbaresObjekt(UntersuchbaresObjekt objekt) {
	    if(objekt == null)
	    	return;
	    untersuchbareObjekte.remove(objekt);
	}
	
	/**
	 * Gibt aufgrund des Namens ein UntersuchbaresObjekt zurueck.
	 * @param objektName Der Name des UntersuchbarenObjekts.
	 * @return Das UntersuchbareObjekt, falls es gefunden wurde, ansonsten null.
	 */
	public UntersuchbaresObjekt getUntersuchbaresObjekt(String objektName) {
		for(UntersuchbaresObjekt o : untersuchbareObjekte)
			if(o.getName().equalsIgnoreCase(objektName))
				return o;
	  return null;
	}
	
	/**
	 * Gibt alle UntersuchbarenObjekte dieses Orts zurueck.
	 * @return Eine Liste mit allen UntersuchbarenObjekten.
	 */
	public UntersuchbaresObjekt[] getUnteruschbareObjekte() {
		return untersuchbareObjekte.toArray(new UntersuchbaresObjekt[0]);
	}
	
	/* Gegenstand */
	
	/**
	 * Fuegt eine bestimmte Anzahl eines Gegenstand diesem Ort hinzu.
	 * @param gegenstand Der Gegenstand, der hinzugefuegt werden soll.
	 * @param anzahl Die Anzahl des Gegenstands.
	 */
	public void addGegenstand(Gegenstand gegenstand, int anzahl) {
		gegenstaende.addGegenstand(gegenstand, anzahl);
	}
	
	/**
	 * Entfernt alle vorhandenen Gegenstaende diesen Typs von diesem Ort.
	 * @param gegenstand Der zuentfernende Gegenstand.
	 */
	public void removeGegenstand(Gegenstand gegenstand) {
		gegenstaende.removeAlleGegenstand(gegenstand);
	}
	
	/**
	 * Gibt aufgrund des Namens einen Gegenstand zurueck.
	 * @param gegenstandName Der Name des gesuchten Gegenstands.
	 * @return Der Gegenstand, falls er gefunden wurde, ansonsten null.
	 */
	public Stapel getGegenstand(String gegenstandName) {
	    Gegenstand gegenstand = Gegenstand.getGegenstand(gegenstandName);
	    if(gegenstand == null)
	    	return null;
	    return gegenstaende.getStapel(gegenstand);
	}
	
	/**
	 * Gibt aus, ob sich so ein Gegenstand an diesem Ort befindet, falls dies der Fall ist wird wahr zurueckgegeben.
	 * @param gegenstand Der Gegenstand, auf den der Ort untersucht wird.
	 * @return True, wenn der Gegenstand an diesem Ort ist, false wenn nicht.
	 */
	public boolean hasGegenstand(Gegenstand gegenstand) {
		if(gegenstaende.containsGegenstand(gegenstand))
			return true;
		else
			return false;
	}
	
	/* KommandoGegenstaende & Tuer */
	
	/**
	 * Fuegt einen Gegenstand hinzu, der, wenn er verwendet wird, eine Aktion ausloesen kann.
	 * @param gegenstand Der Gegenstand, der verwendet werden muss.
	 */
	public void addKommandoGegenstand(KommandoGegenstand gegenstand) {
		kommandoGegenstaende.add(gegenstand);
	}
	
	/**
	 * Fuegt dem Ort eine verschlossene Tuer hinzu, die mit einem Schluessel einen Ausgang oeffnet.
	 * @param ausgang Der neue Ausgang hinter der Tuer.
	 * @param ereignis Das Ereignis, das erfuellt sein muss, sodass sich die Tuer oeffnet.
	 * @param schluessel Die Schluessel fuer die Tuer.
	 */
	public void addTuer(Ausgang ausgang, Ereignis ereignis, Gegenstand... schluessel) {
		tueren.add(new Tuer(ausgang, ereignis, schluessel));
	}
	
	/**
	 * Fuehrt eine Aktion fuer einen Gegenstand aus.
	 * @param gegenstand Der Gegenstand, der verwendet wurde.
	 * @return True, wenn ein Gegenstand gefunden wurde, ansonsten false;
	 */
	public boolean fuehreAktionAus(Gegenstand gegenstand) {
		if(gegenstand == null)
			return false;

	    for(Tuer t : tueren.toArray(new Tuer[0])) {
	    	if(t.oeffnen(gegenstand)) {
	    		addAusgang(t.getAusgang());
	    		tueren.remove(t);
	    		return true;
	    	}
	    }

	    for(KommandoGegenstand kg : kommandoGegenstaende)
	    	if(kg.equals(gegenstand))
	    		if(((KommandoGegenstand) gegenstand).verwendet())
	    			return true;

	    return false;
	}
	
	/* Kommando & NPC */
	
	/**
	 * Fuegt diesem Ort ein spezielles Kommando hinzu.
	 * @param kommando Der Befehl, der vom Spieler eingegeben werden muss.
	 * @param ereignis Das Ereignis, das erfuellt sein muss, sodass die Aktion ausgefuehrt wird.
	 */
	public void addCustomCommand(Ereignis ereignis, String... kommando) {
	    kommandos.add(new Kommando(ereignis, kommando));
	}
	
	/**
	 * Fuegt dem Ort einen neuen NPC hinzu.
	 * @param npc Der NPC, der hinzugefuegt werden soll.
	 */
	public void addNPC(NPC npc) {
		if(!npcs.contains(npc))
			npcs.add(npc);
	}
	
	/**
	 * Gibt alle NPCs an diesem Ort zurueck.
	 * @return Eine Liste mit allen NPCs.
	 */
	public NPC[] getNPCs() {
		return npcs.toArray(new NPC[0]);
	}
	
	/**
	 * Entfernt einen NPC von diesem Ort.
	 * @param npc Der NPC, der entfernt werden soll.
	 */
	public void removeNPC(NPC npc) {
		npcs.remove(npc);
	}
	
	/**
	 * Prueft, ob der gegebene NPC an diesem Ort ist.
	 * @param npc Der NPC, nach dem gesucht wird.
	 * @return True, wenn der NPC da ist, ansonsten false.
	 */
	public boolean isNPCda(NPC npc) {
		return npcs.contains(npc);
	}
	
	/**
	 * Ueberprueft, ob das eingegebene Kommando an diesem Ort verwendet werden kann.
	 * @param befehl Das Kommando vom Spieler.
	 * @return True, wenn es einsetzbar ist, ansonsten false.
	 */
	public boolean kommandoEingegeben(String befehl) {
	    for(Kommando k : kommandos.toArray(new Kommando[0])) {
	    	if(k.istBefehl(befehl)) {
	    		if(k.getEreignis().eingetreten())
	    			kommandos.remove(k);
	    		return true;
	    	}
	    }

	    //prüfe auf NPCs
	    for(NPC npc : npcs) {
	    	if(npc.ansprechen(befehl))
	    		return true;
	    }

	    return false;
	}
	
	/* Gegener */
	
	/**
	 * Fuegt diesem Ort einen Kampf hinzu, der an diesem Ort eintreten kann.
	 * @param kampf Der neue Kampf, der an diesem Ort stattfinden kann.
	 */
	public void addKampf(Kampf kampf) {
	    kaempfe.add(kampf);
	}
	
	/**
	 * Ueberprueft, ob ein Kampf stattfinden wird.
	 * @return Den Kampf, der stattfindet oder null, wenn keiner stattfindet.
	 */
	public Kampf findetKampfStatt() {
		// testet, ob ueberhaupt ein Kampf stattfinden kann.
		if(SpielWelt.WELT.r.nextDouble() * 100 > pKampf)
			return null;

		int sigmaChance = 0;
		for(Kampf k : kaempfe)
			sigmaChance += k.getChance();

		// Kein Kampf an diesem Ort.
		if(sigmaChance == 0)
			return null;

		// Exklusive obere Grenze!
		int nInt = SpielWelt.WELT.r.nextInt(sigmaChance + 1);
		for(Kampf k : kaempfe)
			if(nInt >= sigmaChance - k.getChance() && nInt <= sigmaChance)
				return k.reset();
			else
				sigmaChance -= k.getChance();
		// Es findet kein Kampf statt.
		return null;
	}
	
	/**
	 * Aendert die Wahrscheinlichkeit fuer einen Kampf an diesem Ort.
	 * @param wahrscheinlichkeit Die neue Wahrscheinlichkeit fuer einen Kampf.
	 * @return Sich selbst.
	 */
	public Ort setWahrscheinlichkeitFuerKampf(double wahrscheinlichkeit) {
		this.pKampf = wahrscheinlichkeit;
		return this;
	}
	
	/* Behaelter */
	
	/**
	 * Fuegt dem Ort einen Behaelter hinzu.
	 * @param behaelter Der neue Behaelter an diesem Ort.
	 */
	public void addBehaelter(Behaelter behaelter) {
		this.behaelter.add(behaelter);
	}
	
	/**
	 * Gibt alle Gegenstaende aus einem Behaelter zurueck, der aufgrund des Typs ermittelt wird.
	 * @param typ Der Typ des Behaelters, der gesucht wird.
	 * @return Den Inhalt des Behaelters.
	 */
	public Stapel[] getBehaelterInhalt(byte typ) {
	    for(Behaelter b : behaelter.toArray(new Behaelter[0])) {
	    	if(b.getTyp() == typ) {
	    		Stapel[] loot = b.pluendern();
	    		behaelter.remove(b);
	    		return loot;
	    	}
	    }
	    return null;
	}
	
	/* Ressourcen Punkte */
	
	/**
	 * Fuegt dem Ort einen Ressourcen Punkt hinzu.
	 * @param ressourcenPunkt Der neue Ressourcen Punkt an diesem Ort.
	 */
	public void addRessourcenPunkt(RessourcenPunkt ressourcenPunkt) {
		ressourcen.add(ressourcenPunkt);
	}
	
	/**
	 * Gibt den Ressourcen Punkt zurueck.
	 * @param name Der Name des Ressourcen Punkts.
	 * @return Den Ressourcen Punkt oder null, falls es so einen Ressourcen Punkt nicht an diesem Ort gibt.
	 */
	public RessourcenPunkt getRessourcenPunkt(String name) {
		for(RessourcenPunkt rp : ressourcen)
			if(rp.getName().equalsIgnoreCase(name))
				return rp;
		return null;
	}

}