package game;

import game.bedingung.Bedingung;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import npc.NPC;


/**
 *  Diese Klasse repraesentiert jeden Ort/Raum im Spiel.
 */
public class Ort implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

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
	// Alle Gegenstaende, die bei einer Benutzung eine Aktion ausloesen.
	private Vector<Gegenstand> verwendbareGegenstaende;
	// Alle Aktionen, die mit diesem Ort zusammenhaengen.
	private Vector<Bedingung> bedingungen;
	// Alle Custom Commands fuer diesen Ort.
	private Vector<Kommando> kommandos;
	// Alle Gegner an diesem Ort.
	private Vector<Gegner> enemies;
	// Alle Wahrscheinlichkeiten fuer die Gegner.
	private Vector<Double> wahrscheinlichkeiten;
	// Alle Behaelter an diesem Ort.
	private Vector<Behaelter> behaelter;
	// Alle NPCs an diesem Ort.
	private Vector<NPC> npcs;
	
	// Alle Schluessel fuer verschlossene Tueren
	private Vector<Schluessel> schluessel;
	// Alle Ausgaenge hinter den Tueren.
	private Vector<Ausgang> tueren;
	  
	// Die Wahrscheinlichkeitt fuer einen Kampf.
	private double pKampf;
	  
	/**
	 *  Die Konstruktoren fuer einen Ort. Einer ist leer, der andere voll.
	 *  Ausgaenge muessen spaeter hinzugefuegt werden.
	 */
	  
	// Der Ort hat nur einen Namen.
	public Ort(String name){
		// Nur der Name wird initialisiert.
	    this.name = name;
	    beschreibung = new String();
	    ausgaenge = new Vector<Ausgang>();
	    untersuchbareObjekte = new Vector<UntersuchbaresObjekt>();
	    gegenstaende = new Inventar();
	    verwendbareGegenstaende = new Vector<Gegenstand>();
	    bedingungen = new Vector<Bedingung>();
	    kommandos = new Vector<Kommando>();
	    enemies = new Vector<Gegner>();
	    wahrscheinlichkeiten = new Vector<Double>();
	    behaelter = new Vector<Behaelter>();
	    npcs = new Vector<NPC>();
	    
	    schluessel = new Vector<Schluessel>();
	    tueren = new Vector<Ausgang>();
	    
	    besucht = false;
	    pKampf = 100.0;
	}
	  
	// Der Ort hat sowohl Name als auch Beschreibung.
	public Ort(String name, String beschreibung){
		// Der Name und die Beschreibung wird initialisiert.
	    this.name = name;
	    this.beschreibung = beschreibung;
	    ausgaenge = new Vector<Ausgang>();
	    untersuchbareObjekte = new Vector<UntersuchbaresObjekt>();
	    gegenstaende = new Inventar();
	    verwendbareGegenstaende = new Vector<Gegenstand>();
	    bedingungen = new Vector<Bedingung>();
	    kommandos = new Vector<Kommando>();
	    enemies = new Vector<Gegner>();
	    wahrscheinlichkeiten = new Vector<Double>();
	    behaelter = new Vector<Behaelter>();
	    npcs = new Vector<NPC>();

	    schluessel = new Vector<Schluessel>();
	    tueren = new Vector<Ausgang>();
	    
	    besucht = false;
	    pKampf = 100.0;
	}
	  
	/**
	 *  Diese Methode fuegt einen Ausgang hinzu, der zuerst erstellt wird, ueberprueft aber, ob er bereits in der Liste ist.
	 *  richtung: die Richtung, in die der Ausgang fuehrt.
	 *  zielort: der Ort, zu dem der Ausgnag fuehrt.
	 */
	public void addAusgang(int richtung, Ort zielort){
		Ausgang ausgang = new Ausgang(richtung, zielort);
	    if(!ausgaenge.contains(ausgang)){
	    	// Wenn der Ausgang noch nicht in der Liste ist, wird er hinzugefuegt.
	    	ausgaenge.add(ausgang);
	    }
	}
	/**
	 * Fügt einen Ausgang zu einem gegeben Zielort hinzu und fügt dem Zielort auch gleich
	 * einen "Rückausgang" zu diesem Ort hinzu.
	 * @param richtung Die Richtung, in der der Zielort liegen soll.
	 * @param zielort Der Zielort.
	 */
	public void addAusgangMitRueckweg(int richtung, Ort zielort) {
		addAusgang(richtung, zielort);
		zielort.addAusgang(Ausgang.getRueckrichtung(richtung), this);
	}
	/**
	 *  Diese Methode fuegt einen Ausgang hinzu, ueberprueft aber, ob er bereits in der Liste ist.
	 *  ausgang: dieser Ausgang wird der Liste der Ausgaenge hinzugefuegt.
	 */
	public void addAusgang(Ausgang ausgang){
		if(!ausgaenge.contains(ausgang)){
			// Wenn der Ausgang noch nicht in der Liste ist, wird er hinzugefuegt.
			ausgaenge.add(ausgang);
	    }
	}
	/**
	 *  Diese Methode entfernt einen Ausgang aus der Liste der Ausgaenge.
	 *  ausgang: der Ausgang, der entfernt werden soll.
	 */
	public void removeAusgang(Ausgang ausgang){
		if(ausgaenge.contains(ausgang)){
			// Der Ausgang wird nur entfernt, wen der sich in der Liste befindet.
			ausgaenge.remove(ausgang);
	    }
	}
	/**
	 *  Gibt einen Ausgang zurueck basierend auf der Richtung.
	 *  richtung: die Richtung, in die der Ausgang fuhert.
	 */
	public Ausgang getAusgang(int richtung){
		for(Ausgang a: ausgaenge.toArray(new Ausgang[0])){
			if(a.getRichtung() == richtung) return a;
	    }
	    return null;
	}
	/**
	 *  Diese Methode gibt alle Ausgaenge zurueck, die dieser Ort hat.
	 */
	public Vector<Ausgang> getAusgaenge(){
		// Es wird nur eine Kopie der Liste zurueckgegeben, da so die urspruengliche
	    // Liste nicht veraendert werden kann.
	    return (Vector<Ausgang>)ausgaenge.clone();
	}
	/**
	 *  Gibt zurueck, ob der Ort Ausgaenge hat.
	 */
	public boolean hatAusgaenge(){
		if(!ausgaenge.isEmpty()){
			return true;
	    }
		return false;
	}
	/**
	 * Gibt zurueck, ob der Ort einen entsprechenden @ausgang hat oder auch nicht.
	 * @param ausgang Der Ausgang auf den der Ort ueberprueft werden soll.
	 */
	public boolean hasAusgang(Ausgang ausgang) {
		for(Ausgang a: ausgaenge.toArray(new Ausgang[0])) {
			if(a.equals(ausgang)) return true;
		}
		return false;
	}	
	  
	/**
	 *  Teilt dem Ort mit, dass der Spieler sich bereits dort befand.
	 */
	public void besucht(){
		besucht = true;
	}
	/**
	 *  Gibt zurueck, ob der Ort bereits besucht wurde.
	 */
	public boolean istBesucht(){
		return besucht;
	}
	  
	/**
	 *  Diese Methode gibt den Name des Orts zurueck.
	 */
	public String getName(){
		return name;
	}
	/**
	 *  Diese Methode aendert den Namen des Orts auf den uebergebenen Name.
	 *  neuerName: der neue Name des Orts
	 */
	public void setName(String neuerName){
		name = neuerName;
	}
	  
	/**
	 *  Diese Methode gibt die Beschreibung des Orts zurueck.
	 */
	public String getBeschreibung(){
		return beschreibung;
	}
	/**
	 *  Diese Methode aendert die Beschreibung des Orts auf die uebergebenen Beschreibung.
	 *  neueBeschreibung: die neue Beschreibung des Orts
	 */
	public void setBeschreibung(String neueBeschreibung){
	    beschreibung = neueBeschreibung;
	}
	
	// neue Methoden
	  
	/**
	 *  Diese Methode fuegt ein neues Objekt ein, dass untersucht werden kann.
	 *  objName: der Name des Objekts.
	 *  objBeschreibung: die Beschreibung, die bei einer Untersuchung angezeigt wird.
	 */
	public void addUntersuchbaresObjekt(String objName, String objBeschreibung){
		untersuchbareObjekte.add(new UntersuchbaresObjekt(objName, objBeschreibung));
	}
	/**
	 * Fuegt ein neues Objekt ein, dass untersucht werden kann.
	 * objekt: das neue UntersuchbareObjekt.
	 */
	public void addUntersuchbaresObjekt(UntersuchbaresObjekt objekt){
		untersuchbareObjekte.add(objekt);
	}
	
	/**
	 *  Diese Methode entfernt ein UntersuchbaresObjekt aufgrund des Namens.
	 *  objektName: der Name des UntersuchbarenObjekts.
	 */
	public void removeUntersuchbaresObjekt(String objektName){
		UntersuchbaresObjekt objekt = getUntersuchbaresObjekt(objektName);
	    if(objekt != null){
	    	untersuchbareObjekte.remove(objekt);
	    	objekt = null;
	    }
	}
	/**
	 *  Diese Methode entfernt ein UntersuchbaresObjekt.
	 *  objekt: das UntersuchbarenObjekts.
	 */
	public void removeUntersuchbaresObjekt(UntersuchbaresObjekt objekt){
	    if(objekt != null){
	    	untersuchbareObjekte.remove(objekt);
	    	objekt = null;
	    }
	}
	/**
	 *  Diese Methode gibt ein Array zurueck, welches alle Namen der UntersuchbarenObjekte enthaelt.
	 */
	public String[] getUntersuchbareObjekteNamen(){
		String[] namen = new String[untersuchbareObjekte.size()];
	    for(int i = 0; i > untersuchbareObjekte.size(); i++){
	    	namen[i] = untersuchbareObjekte.get(i).getName();
	    }
	    if(namen.length != 0) return namen;
	    else{
	    	String[] str = { "NICHTS" };
	    	return str;
	    }
	}
	/**
	 * Diese Methode gibt alle UntersuchbarenObjekte dieses Orts zurück.
	 */
	public UntersuchbaresObjekt[] getUnteruschbareObjekte() {
		return untersuchbareObjekte.toArray(new UntersuchbaresObjekt[0]);
	}
	/**
	 *  Diese Methode gibt aufgrund des Namens eines UntersuchbaresObjekt zurueck.
	 *  objektName: der Name des Objekts, das zurueckgegeben werden soll.
	 */
	public UntersuchbaresObjekt getUntersuchbaresObjekt(String objektName){
		for(UntersuchbaresObjekt o: untersuchbareObjekte.toArray(new UntersuchbaresObjekt[0])){
			if(o.getName().equals(objektName)){
				return o;
			}
	    }
	    return null;
	}

	/**
	 *  Diese Methode fuegt eine bestimmte Anzahl eines Gegenstand an diesem Ort hinzu.
	 *  gegenstand: der neu hinzugefuegte Gegenstand.
	 */
	public void addGegenstand(Gegenstand gegenstand, int anzahl){
		gegenstaende.addGegenstand(gegenstand, anzahl);
	}
	/**
	 *  Diese Methode entfernt den uebergebenen Gegenstand aus diesem Ort.
	 *  gegenstand: der zu entfernen ist.
	 */
	public void removeGegenstand(Gegenstand gegenstand){
		gegenstaende.removeAlleGegenstand(gegenstand);
	}
	/**
	 *  Diese Methode gibt aufgrund des Namens eines Gegenstand zurueck.
	 *  gegenstandName: der Name des Gegenstands.
	 */
	public Stapel getGegenstand(String gegenstandName){
	    Gegenstand gegenstand = Gegenstand.getGegenstand(gegenstandName);
	    if(gegenstand == null) return null;
	    for(Gegenstand g: gegenstaende.getAlleGegenstaende()){
	    	if(gegenstand.getName().equals(g.getName())){
	    		return gegenstaende.getStapel(g);
	    	}
	    }
	    return null;
	}
	
	/**
	 * Gibt aus, ob sich so ein Gegenstand an diesem Ort befindet, falls dies der Fall ist wird wahr zurueckgegeben.
	 * @param gegenstand Der Gegenstand, auf den der Ort untersucht wird.
	 */
	public boolean hasGegenstand(Gegenstand gegenstand) {
		for(Gegenstand g: gegenstaende.getAlleGegenstaende()) {
			if(gegenstand.equals(g)) return true;
		}
		return false;
	}
	  
	/**
	 *  Fuegt einen Gegenstand hinzu, der, wenn er verwendet wird, eine Aktion ausloest.
	 *  gegenstand: der Gegenstand, der verwendet werden muss.
	 *  aktion: die Aktion, die nach der Verwendung ausgefuehrt wird.
	 */
	public void addKommandoGegenstand(Gegenstand gegenstand, Bedingung bedingung){
		if(!verwendbareGegenstaende.contains(gegenstand)){
			verwendbareGegenstaende.add(gegenstand);
			bedingungen.add(bedingung);
	    }
	}
	/**
	 *  Fuehrt eine Aktion entsprechend eines verwendeten Gegenstands aus.
	 */
	public boolean fuehreAktionAus(Gegenstand gegenstand){
		if(gegenstand == null) return false;
	    boolean gefunden = false;
	    for(Gegenstand g: verwendbareGegenstaende.toArray(new Gegenstand[0])){
	    	if(gegenstand == g){
	    		Bedingung b = bedingungen.get(verwendbareGegenstaende.indexOf(g));
	    		b.pruefen();
	    		if(b.istErfuellt()){
	    			verwendbareGegenstaende.remove(g);
	    			bedingungen.remove(b);
	    		}
	    		gefunden = true;
	    	}
	    }
	    
	    for(Gegenstand g: schluessel.toArray(new Schluessel[0])) {
	    	if(gegenstand == g) {
	    		if(g instanceof Schluessel && ((Schluessel)g).verwenden()) {
	    			int index = schluessel.indexOf(g);
		    		this.addAusgang(tueren.get(index));
		    		schluessel.remove(index);
		    		tueren.remove(index);
		    		gefunden = true;
	    		}     		
	    	}
	    }
	    
	    return gefunden;
	}
	  
	/**
	 *  Diese Methode fuegt diesem Ort ein spezial Kommando hinzu.
	 *  kommando: der Befehl, der von Spieler eingegeben werden muss.
	 *  Bedingung: die Bedingung, die Erfuellt sein muss, sodass eine Aktion ausgefuehrt wird.
	 */
	public void addCustomCommand(String kommando, Bedingung bedingung){
		Kommando k = new Kommando(kommando, bedingung);
	    if(!kommandos.contains(k)){
	    	kommandos.add(k);
	    }
	}
	/**
	 *  Uberprueft, ob hier ein Kommando moeglich ist.
	 *  befehl: der eingegebene Befehl.
	 */
	public boolean kommandoEingegeben(String befehl){
	    for(Kommando k: kommandos.toArray(new Kommando[0])){
	    	if(k.getBefehl().equalsIgnoreCase(befehl)){
	    		k.getBedingung().pruefen();
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
	  
	/**
	 *  Fuegt diesem Ort einen Gegner hinzu, der mit der uebergebenen Wahrscheinlichkeit angreift.
	 *  gegner: der Gegner an diesem Ort
	 *  wahrscheinlichkeit: die Wahrscheinlichkeit fuer das erscheinen dieses Gegners in Prozent
	 */
	public void addGegner(Gegner gegner, double wahrscheinlichkeit){
	    if(!enemies.contains(gegner)){
	    	enemies.add(gegner);
	    	wahrscheinlichkeiten.add(wahrscheinlichkeit);
	    }
	}
	/**
	 *  Ueberprueft, ob ein Kampf stattfinden wird.
	 */
	public Gegner findetKampfStatt(){
		Random r = new Random();
	    for(int i = 0; i < enemies.size(); i++){
	    	if(!(r.nextDouble() * pKampf <= wahrscheinlichkeiten.get(i))) continue;
	    	return new Gegner(enemies.get(i));
	    }
	    return null;
	}
	  
	/**
	 *  Fuegt dem Ort einen Behaelter hinzu.
	 *  behaelter: der neue Behaelter.
	 */
	public void addBehaelter(Behaelter behaelter){
		if(!this.behaelter.contains(behaelter)) this.behaelter.add(behaelter);
	}
	/**
	 *  Gibt alle Gegenstaende aus einem Behaelter zurueck, der aufgrund des Typs ermittelt wird.
	 *  typ: der Typ des Behaelters.
	 */
	public Stapel[] getBehaelterInhalt(byte typ){
	    for(Behaelter b: behaelter.toArray(new Behaelter[0])){
	    	if(b.getTyp() == typ){
	    		Stapel[] loot = b.pluendern();
	    		behaelter.remove(b);
	    		if(loot.length == 0) return new Stapel[0];
	    		return loot;
	    	}
	    }
	    return null;
	}
	
	/**
	 * Fuegt dem Ort eine verschlossene "Tuer" hinzu, die mit einem @schluessel einen @ausgang oeffnet.
	 * @param schluessel Der Schluessel fuer die Tuer.
	 * @param ausgang Der neue Ausgang hinter der "Tuer".
	 */
	public void addTuer(Schluessel schluessel, Ausgang ausgang) {
		this.schluessel.add(schluessel);
		this.tueren.add(ausgang);		
	}
	
	/**
	 * Fuegt dem Ort einen neuen NPC hinzu.
	 * @param npc Der NPC, der hinzugefügt werden soll.
	 */
	public void addNPC(NPC npc) {
		if(!npcs.contains(npc))
			npcs.add(npc);
	}
	
	/**
	 * Löscht einen NPC von diesem Ort.
	 * @param npc Der NPC, der entfernt werden soll.
	 */
	public void removeNPC(NPC npc) {
		npcs.remove(npc);
	}
	
	/**
	 * Gibt alle NPCs an diesem Ort zurück.
	 */
	public NPC[] getNPCs() {
		return npcs.toArray(new NPC[0]);
	}
	
	/**
	 * Prüft, ob der gegebene NPC an diesem Ort ist.
	 * @param npc
	 * @return
	 */
	public boolean istNpcDa(NPC npc) {
		return npcs.contains(npc);
	}
	
	
}