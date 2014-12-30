package game;

import game.battle.Kampf;
import game.battle.KampfAktion;
import game.battle.KampfEffekt;
import game.entity.Attribut;
import game.entity.Effekt;
import game.entity.Entity;
import game.entity.Faehigkeit;
import game.entity.Gegner;
import game.entity.Gegnerart;
import game.entity.Resistenz;
import game.entity.Schadensart;
import game.entity.Spieler;
import game.items.Gegenstand;
import game.items.Inventar;
import game.items.Kombination;
import game.items.Stapel;
import game.items.VerwendbarerGegenstand;
import game.items.Waehrung;
import game.logic.Ereignis;
import gui.Anzeige;
import gui.GUI;
import gui.MiniMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import npc.NPC;

import util.Farbe;
import util.NumerusGenus;
import util.StringEvent;
import util.StringListener;

public class SpielWelt implements Serializable, StringListener {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// DIE SPIELWELT ALS STATISCHE VARIABLE, DIE IMMER AUF DAS EINZIGE SPIELWELT-OBJEKT VERWEIST! //
	transient public static SpielWelt WELT;

	private static void setWelt(SpielWelt neueWelt) { WELT = neueWelt; }
	//------------------------------------------------------------------------------------------//




	// Die Position des Spielers in der Welt.
	public Ort spielerPosition;
	// Die Anzeige, die alle auszugebende Dinge ausgibt.
	transient private Anzeige ausgabe;
	// Die Map fuer den Spieler.
	transient private MiniMap map;
	// Der Boolean, der angibt, ob sich der Spieler im Kampf befindet.
	transient public boolean kaempft;
	// Der Kampf, der gerade ausgefochten wird.
	transient private Kampf kampf;
	// Der gesamte Loot fuer den Spieler.
	transient private Inventar loot;
	// Der Boolean, der angibt, ob sich der Spieler im Gespräch befindet.
	transient private boolean spricht;
	//NPC, mit dem gerade gesprochen wird.
	transient private NPC gespraechspartner;
	// Alle Statusveraenderungen, die im Kampf aktiv sind.
	transient private Vector<KampfEffekt> kampfEffekte;

	// Der Spieler
	private Spieler spieler;
	// Alle global geltenden Kommandos.
	private Vector<Kommando> globaleKommandos;
	// Alle Gegenstaende im Spiel(wird benoetigt, um die Gegenstandsliste zu speichern und laden)
	private Gegenstand[] gegenstaende;
	// Alle Kombinationen im Spiel(wird benoetigt, um die Kombinationen zu speichern und zu laden)
	private ArrayList<Vector<Kombination>> kombinationen;
	// Alle Attribute im Spiel(wird benoetigt, um die Attribute zu speichern und zu laden)
	private Attribut[] attribute;
	// Alle Schadensarten im Spiel(wird benoetigt, um die Schadensarten zu speichern und zu laden)
	private Schadensart[] schadensarten;
	// Alle Resistenzen im Spiel(wird benoetigt, um die Resistenzen zu speichern und zu laden)
	private Resistenz[] resistenzen;
	// Alle Farben im Spiel(wird benoetigt, um die Farben zu speichern und zu laden)
	private Vector<Farbe> farben;
	// Alle Gegnerarten im Spiel(wird benoetigt, um die Gegenarten zu speichern und zu laden)
	private Vector<Gegnerart> gegnerarten;

	// Das Random-Objekt, das alle Zufallszahlen ausgibt.
	public Random r;

	/**
	 * Eine neue SpielWelt braucht keine Parameter, sie initialisiert nur alle Variabeln.
	 */
	public SpielWelt() {
		this.ausgabe = null;
	    // Die Listen werden leer intialisiert, so auch die momentan unbekannte Position des Spielers.
	    spieler = new Spieler("Spieler", NumerusGenus.MASKULIN, "Der Spieler, der das Spiel spielt.");
	    spielerPosition = null;

	    r = new Random();
	    globaleKommandos = new Vector<Kommando>();
	    kampfEffekte = new Vector<KampfEffekt>();
	    farben = new Vector<Farbe>();

	    kaempft = false;

	    gegenstaende = Gegenstand.GEGENSTAENDE;
	    kombinationen = Kombination.getAlleKombinationen();
	    attribute = Attribut.ATTRIBUTE;
	    schadensarten = Schadensart.SCHADEN;
	    resistenzen = Resistenz.RESISTENZEN;
	    Farbe.setAlleFarben(farben);
	    gegnerarten = Gegnerart.GEGNERARTEN;

	    setWelt(this);
	}



	/**
	 *  Diese Methode gibt den String auf dem Standardausgabeweg aus.
	 *  text: der auszugebenede Text.
	 */
	public void print(String text){
	    ausgabe.print(text);
	}

	/**
	 *  Diese Methode gibt den String auf dem Standardausgabeweg aus mit einem Zeilenumbruch.
	 *  text: der auszugebenede Text.
	 */
	public void println(String text){
	    ausgabe.println(text);
	}

	/**
	 *  Diese Methode gibt einen Zeilenumbruch aus.
	 */
	public void println(){
	    ausgabe.println();
	}

	/**
	 *  Diese Methode aktualisiert die Liste aller Gegenstaende im Spiel, sie wird nach dem Laden des Spielstands aufgerufen.
	 */
	public void updateGegenstandsListe() {
		setWelt(this);

	    Gegenstand.GEGENSTAENDE = gegenstaende;
	    Kombination.setAlleKombinationen(kombinationen);
	    Attribut.ATTRIBUTE = attribute;
	    Schadensart.SCHADEN = schadensarten;
	    Resistenz.RESISTENZEN = resistenzen;
	    Farbe.setAlleFarben(farben);
	    Gegnerart.GEGNERARTEN = gegnerarten;
	}

	/**
	 *  Diese Methode aktualisiert die Anzeige der SpielWelt mit einer neuen.
	 *  neueAnzeige: die neue Anzeige
	 */
	public void setGUI(GUI gui) {
	    this.ausgabe = gui.getAnzeige();
	    this.map = gui.getMap();
	    this.map.updateMiniMap(spielerPosition);

	    this.getInventar().addListener(gui.getInventoryDisplay());
	    this.getInventar().addListener(gui.getEingabefeld());
	    this.spieler.addListener(gui.getSkillDisplay());
	    this.spieler.addListener(gui.getEingabefeld());
	    this.spieler.notifyListeners(spieler.getFaehigkeiten());

	    this.kampfEffekte = new Vector<KampfEffekt>();
	}

	/**
	 *  Diese Methode gibt die aktuelle Position des Spielers zurueck.
	 */
	public Ort aktuellePosition() {
		return spielerPosition;
	}

	/**
	 *  Diese Methode setzt die aktuelle Position auf den gegebenen Ort.
	 *  zielort: der neue Ort, an dem sich der Spieler befindet.
	 */
	public void setAktuellePositon(Ort zielort) {
	    spielerPosition = zielort;
	    if(map != null) map.updateMiniMap(spielerPosition);
	    Kampf k = spielerPosition.findetKampfStatt();
	    if(k != null) this.initKampf(k);
	}

	/**
	 *  Diese Methode gibt eine Instanz des Inventars zurueck.
	 */
	public Inventar getInventar() {
	    return spieler.getInventar();
	}

	/** TODO
	 *  Diese Methode fuegt einen neuen Gegenstand dem Inventar des Spielers hinzu.
	 *
	public void addGegenstand(Gegenstand gegenstand, int anzahl) {
		spieler.getInventar().addGegenstand(gegenstand, anzahl);
	}

	/** TODO
	 *  Diese Methode entfernt einen Gegenstand aus dem Inventar des Spielers.
	 *
	public void removeGegenstand(Gegenstand gegenstand, int anzahl){
	    spieler.getInventar().removeGegenstand(gegenstand, anzahl);
	}

	/**
	 * Fuegt ein globales Kommando dem Spiel hinzu, das ein Ereignis hat.
	 * @param befehl Der einzugebende Befehl.
	 * @param ereignis Das auszuloesende Ereignis.
	 */
	public void addGlobalesKommando(Ereignis ereignis, String... befehle) {
	    Kommando k = new Kommando(ereignis, befehle);
	    if(!globaleKommandos.contains(k))
	    	globaleKommandos.add(k);
	}

	/**
	 * Gibt den Spieler zurueck.
	 * @return Den Spieler.
	 */
	public Spieler getSpieler() {
		return spieler;
	}

	/**
	 * Der StringListener erhaelt ein StringEvent fuer einen Kampf oder ein Gespraech und gibt sie an diese weiter.
	 * @param evt Das empfangene StringEvent.
	 */
	@Override
	public void actionPerformed(StringEvent evt) {
		if(spielerKaempft())
			kampf(evt);
		if(spielerSpricht())
			gespraech(evt);
	}

	/**
	 *  Diese Methode gibt zurueck, ob sich der Spieler im Kampf befindet.
	 */
	public boolean spielerKaempft() {
	    return kaempft;
	}

	/**
	 *  Diese Methode startet einen Kampf mit dem uebergebenen Gegner.
	 *  gegner: gegen den gekaempft wird
	 */
	private void initKampf(Kampf kampf) {
		this.kampf = kampf;
	    spieler.resetTemp();
	    kaempft = true;
	    loot = new Inventar();
	    ausgabe.clear();
	    ausgabe.print("Eine Gruppe von Feinden greift dich an!");
	    for(Entity e : kampf.getKampfGegner())
	    	ausgabe.println(e.getNumGen().getUnbest(0) + e.getName() + " nähert sich.");
	    ausgabe.println();
	}

	/**
	 *  Diese Methode wird aufgerufen, wenn ein neues CombatEvent gefunden wurde.
	 */
	private void kampf(StringEvent evt) {
		/*Vector<Entity> enti = new Vector<Entity>();
		enti.add(spieler);
		enti.add(enemy);

		Vector<Double> flks = new Vector<Double>();
		Vector<Faehigkeit> abilities = new Vector<Faehigkeit>();
		Vector<String> ausgaben = new Vector<String>();
		Vector<Integer> schaden = new Vector<Integer>();

		for(Entity e : enti) {
			int index = enti.indexOf(e);
			flks.add(index, new Double(e.getTemp("flk") * (double)(r.nextInt(21) - 10) / 100.0 + 1.0));
			abilities.add(index, e.getFaehigkeit(evt.getCommand()));

			if(abilities.get(index) != null) {

				// Der Schaden der Entities
				double angriff = abilities.get(index).getBonus(e) * (r.nextInt(31) + 85) / 100.0;
	    		if(enti.indexOf(e) > 0)
	    			schaden.add(index, (int)(Math.pow(1.6, Math.log(angriff / Math.max(enti.get(0).getTemp("def"), 1))) * angriff / 4));
	    		else
	    			schaden.add(index, (int)(Math.pow(1.6, Math.log(angriff / Math.max(enti.get(1).getTemp("def"), 1))) * angriff / 4));

				// Alle Schaden ersetzten
				String ausgabe = abilities.get(index).getAusgabe();
				ausgabe = ausgabe.replaceAll("#", Integer.toString(schaden.get(index)));

				for(char c : ausgabe.toCharArray()) {
		    		if(c == '§') {
		    			int fall = Integer.valueOf(ausgabe.substring(ausgabe.indexOf(c) + 1, ausgabe.indexOf(c) + 2));
		    			ausgabe = ausgabe.replaceFirst(String.valueOf(fall), "");

		    			if(ausgabe.startsWith("§"))
		    				ausgabe = ausgabe.replaceFirst("§", e.getNumGen().getBest(fall) + e.getName());
		    			else
		    				ausgabe = ausgabe.replaceFirst("§", e.getNumGen().getBest(fall).toLowerCase() + e.getName());
		    		}
		    		if(c == '&') {
		    			int fall = Integer.valueOf(ausgabe.substring(ausgabe.indexOf(c) + 1, ausgabe.indexOf(c) + 2));
		    			ausgabe = ausgabe.replaceFirst(String.valueOf(fall), "");

		    			int indexAnderer = 0;
		    			if(enti.indexOf(e) == 0)
		    				indexAnderer = 1;
		    			if(ausgabe.startsWith("&"))
		    				ausgabe = ausgabe.replaceFirst("&", enti.get(indexAnderer).getNumGen().getBest(fall) + enti.get(indexAnderer).getName());
		    			else
		    				ausgabe = ausgabe.replaceFirst("&", enti.get(indexAnderer).getNumGen().getBest(fall).toLowerCase() + enti.get(indexAnderer).getName());
		    		}
		    	}
				ausgaben.add(index, ausgabe);
			} else {
				ausgaben.add(index, "");
				schaden.add(index, 0);
			}
		}*/


		//double flkGegner = enemy.getFlk() * (double)(r.nextInt(21) - 10) / 100.0 + 1.0;
	    //double flkSpieler = spieler.getTempFlk() * (double)(r.nextInt(21) - 10) / 100.0 + 1.0;

	    /*Faehigkeit fSpieler = null;
	    for(Faehigkeit f : spieler.getFaehigkeiten()){
	    	if(f.getName().equalsIgnoreCase(evt.getCommand())) fSpieler = f;
	    }
	    Faehigkeit fGegner = enemy.getFaehigkeit();
	    */
	    /*Kommando kommando = Kommando.getKommando(evt.getCommand());

	    // Bei einer ungueltigen Eingabe wird die Auswertung abgebrochen.
	    if(kommando == Kommando.INVALID && abilities.get(0) == null) return;

	    // Wenn der Gegenstand eine Faehigkeit bei der Benutzung ausloest, dann wird diese nun uebergeben.
	    VerwendbarerGegenstand g = null;
	    if(Gegenstand.getGegenstand(Kommando.getEingabe()) instanceof VerwendbarerGegenstand) {
	    	g = (VerwendbarerGegenstand)Gegenstand.getGegenstand(Kommando.getEingabe());
		    if(g != null){
		    	if(!g.hasEffekt()){
		    		abilities.set(0, g.getFaehigkeit());
		    		kommando = Kommando.INVALID;
		    	}
		    }
	    }*/

	    /*for(int i = 0; i < ausgaben.size(); i++) {
	    	if(ausgaben.get(i) != "") {
	    		if(enti.get(i) instanceof Spieler && !abilities.get(i).gueltigeWaffe(((Spieler)enti.get(i)).getWaffenarten())) {
	    			ausgabe.println("Du hast nicht die richtige Waffe für diese Fähigkeit ausgerüstet, du kannst diese Fähigkeit nur einsetzen wenn du");
		    		for(byte b : abilities.get(0).getGueltigeWaffen()) {
		    			ausgabe.print(" " + Waffe.getWaffenartNamen(b));
		    		}
		    		ausgabe.print(" ausgerüstet hast.");
		    		return;
	    		}

	    		double angriff = abilities.get(0).getBonus(enti.get(i).getTempAng()) * (r.nextInt(31) + 85) / 100.0;
	    		if(i > 0)
	    			schaden.add(i, (int)(Math.pow(1.6, Math.log(angriff / Math.max(enti.get(0).getDef(), 1))) * angriff / 4));
	    		else
	    			schaden.add(i, (int)(Math.pow(1.6, Math.log(angriff / Math.max(enti.get(1).getDef(), 1))) * angriff / 4));

		    	String ausgabe = abilities.get(i).getAusgabe().replaceAll("#", String.valueOf(schaden.get(i)));

	    		for(int j = 0; i < ausgabe.length(); i++) {
		    		char c = ausgabe.charAt(j);
		    		if(c == '§') {
		    			int fall = Integer.valueOf(ausgabe.substring(j + 1, j + 2));
		    			ausgabe = ausgabe.replaceFirst(String.valueOf(fall), "");
		    			if(ausgabe.startsWith("§"))
		    				ausgabe = ausgabe.replaceFirst("§", enti.get(i).getNumGen().getBest(fall) + enti.get(i).getName());
		    			else
		    				ausgabe = ausgabe.replaceFirst("§", enti.get(i).getNumGen().getBest(fall).toLowerCase() + enti.get(i).getName());
		    		}
		    	}
	    	}
	    }*/

	    /*
	    // Spieler
	    if(abilities.get(0) != null){
	    	// Es wird ueberprueft, ob der Spieler die richtige Waffe fuer die Faehigkeit ausgeruestet hat.
	    	if(!abilities.get(0).gueltigeWaffe(spieler.getWaffenarten())){
	    		// Der Spieler hat die falsche Waffe ausgeruestet.
	    		ausgabe.println("Du hast nicht die richtige Waffe für diese Fähigkeit ausgerüstet, du kannst diese Fähigkeit nur einsetzen wenn du");
	    		for(byte b: abilities.get(0).getGueltigeWaffen()){
	    			ausgabe.print(" " + Waffe.getWaffenartNamen(b));
	    		}
	    		ausgabe.print(" ausgerüstet hast.");
	    		return;
	    	}

	    	// Der Schaden des Spielers und des Gegners werden berechnet.
	    	double angriffS = abilities.get(0).getBonus(spieler.getTempAng()) * (r.nextInt(31) + 85) / 100.0;
	    	schadenS = (int)(Math.pow(1.6, Math.log(angriffS / Math.max(enemy.getDef(), 1))) * angriffS / 4);
	    	// Ausgaben erstellen
	    	ausgabeS = abilities.get(0).getAusgabe().replaceAll("#", String.valueOf(schadenS));
	    	for(int i = 0; i < ausgabeS.length(); i++){
	    		char c = ausgabeS.charAt(i);
	    		if(c == '§'){
	    			int fall = Integer.valueOf(ausgabeS.substring(i + 1, i + 2));
	    			ausgabeS = ausgabeS.replaceFirst(String.valueOf(fall), "");
	    			if(ausgabeS.startsWith("§")){
	    				ausgabeS = ausgabeS.replaceFirst("§", enemy.getNumGen().getBest(fall) + enemy.getName());
	    			}else{
	    				ausgabeS = ausgabeS.replaceFirst("§", enemy.getNumGen().getBest(fall).toLowerCase() + enemy.getName());
	    			}
	    		}
	    	}
	    }

	    // Gegner
	    double angriffG = abilities.get(1).getBonus(enemy.getAng()) * (r.nextInt(31) + 85) / 100.0;
	    int schadenG = (int)(Math.pow(1.6, Math.log(angriffG / Math.max(spieler.getTempDef(), 1))) * angriffG / 4);

	    String ausgabeG = abilities.get(1).getAusgabe();
	    for(int i = 0; i < ausgabeG.length(); i++){
	    	char c = ausgabeG.charAt(i);
	    	if(c == '§'){
	    		int fall = Integer.valueOf(ausgabeG.substring(i + 1, i + 2));
	    		ausgabeG = ausgabeG.replaceFirst(String.valueOf(fall), "");
	    		if(ausgabeG.startsWith("§")){
	    			ausgabeG = ausgabeG.replaceFirst("§", enemy.getNumGen().getBest(fall) + enemy.getName());
	    		}else{
	    			ausgabeG = ausgabeG.replaceFirst("§", enemy.getNumGen().getBest(fall).toLowerCase() + enemy.getName());
	    		}
	    	}
	    }
	    ausgabeG = ausgabeG.replaceAll("#", String.valueOf(schadenG));
	    */

		// TODO neue Idee
		Vector<KampfAktion> aktionen = new Vector<KampfAktion>();

		Faehigkeit spielerF = null;
		String str = "";
		for(String s : evt.getCommand().split(" ")) {
			str += s + " ";
			if(spieler.getFaehigkeit(str.trim()) != null) {
				spielerF = spieler.getFaehigkeit(str.trim());
				break;
			}
		}

		Gegner ziel = null;
		try {
			ziel = (Gegner) kampf.getGegner(evt.getCommand().substring(str.length()).trim());
		} catch(StringIndexOutOfBoundsException e) {
			ziel = null;
		}

		KampfAktion spielerAktion;
		if(Gegenstand.getGegenstand(evt.getCommand()) instanceof VerwendbarerGegenstand)
			spielerAktion = new KampfAktion(spieler, (VerwendbarerGegenstand) Gegenstand.getGegenstand(evt.getCommand()), spieler);
		else
			spielerAktion = new KampfAktion(spieler, spielerF, ziel);

		// Ueberprueft, ob der Spieler Mist bei der Eingabe gebaut hat.
		if(!spielerAktion.istGueltigeAktion()) {
			ausgabe.println("Das ist keine gueltige Aktion!\n");
			return;
		} else
			aktionen.add(spielerAktion);
		// Die Gegner adden
		for(Entity g : kampf.getKampfGegner())
			if(g.getLp() > 0)
				aktionen.add(new KampfAktion(g, g.getFaehigkeit(evt.getCommand()), spieler));

		Collections.sort(aktionen);

		for(KampfAktion aktion : aktionen.toArray(new KampfAktion[0])) {
			if(aktion.istGueltigeAktion()) aktion.fuehreAktionAus();
			if(spieler.getLp() <= 0) {
				kampfEndet(false);
				return;
			} else if(aktion.getZiel().getLp() <= 0) {
				kampf.toeteGegner(aktion.getZiel());
				for(Stapel s : aktion.getZiel().getLoot())
					loot.addGegenstand(s);
				for(KampfAktion a : aktionen.toArray(new KampfAktion[0]))
					if(a.getAngreifer() == aktion.getZiel())
						a.ungueltig();
				ausgabe.println(aktion.getZiel().getNumGen().getBest(0) + aktion.getZiel().getName() + " ist gestorben.");
				if(kampf.alleTot()) {
					kampfEndet(true);
					return;
				}
			}
		}


	    // Die eigentlichen Angriffe
	    /*if(flks.get(0) > flks.get(1)) {
	    	if(kommando == Kommando.VERWENDEN) {
	    		verwendeGegenstand(g);
	    	} else if(abilities.get(0) != null) {
	    		ausgabe.println(ausgaben.get(0));
	    		if(enemy.getLp() - schaden.get(0) <= 0){
	    			kampfEndet(true);
	    			return;
	    		}else{
	    			enemy.addLp(-schaden.get(0));
	    		}
	    	}
	    	ausgabe.println(ausgaben.get(1));
	    	if(spieler.getLp() - schaden.get(1) <= 0){
	    		kampfEndet(false);
	    		return;
	    	}else{
	    		spieler.addLp(-schaden.get(1));
	    	}

	   	}else{
	   		ausgabe.println(ausgaben.get(1));
	   		if(spieler.getLp() - schaden.get(1) <= 0){
	   			kampfEndet(false);
	   			return;
	   		}else{
	   			spieler.addLp(-schaden.get(1));
	   		}
	   		if(kommando == Kommando.VERWENDEN){
	   			verwendeGegenstand(g);
	   		}else if(abilities.get(0) != null){
	   			ausgabe.println(ausgaben.get(0));
	   			if(enemy.getLp() - schaden.get(0) <= 0){
	   				kampfEndet(true);
	   				return;
	   			}else{
	   				enemy.addLp(-schaden.get(0));
	   			}
	   		}
	   	}*/

	    // Alle KampfEffekte werden ueberprueft.
	    for(KampfEffekt ke : kampfEffekte.toArray(new KampfEffekt[0])) {
	    	switch(ke.getTyp()){
	    		case(Effekt.HEILEN): int lp = ke.getBonusLpMp(spieler.getMaxLp()); if(lp == 0) kampfEffekte.remove(ke); else ausgabe.println("Du stellst " + lp + " LP wieder her."); spieler.addLp(lp); break;
	    		case(Effekt.MPREGENERATION): int mp = ke.getBonusLpMp(spieler.getMaxMp()); if(mp == 0) kampfEffekte.remove(ke); else ausgabe.println("Du stellst " + mp + " MP wieder her."); spieler.addMp(mp); break;
	    		case(Effekt.ANGBONUS): int ang = ke.getBonus(spieler.getTempAttribut("ang")); if(ang < 0) kampfEffekte.remove(ke); spieler.addTempAttribut(ang, "ang"); break;
	    		case(Effekt.DEFBONUS): int def = ke.getBonus(spieler.getTempAttribut("def")); if(def < 0) kampfEffekte.remove(ke); spieler.addTempAttribut(def, "def"); break;
	    		case(Effekt.MAGANGBONUS): int magAng = ke.getBonus(spieler.getTempAttribut("magAng")); if(magAng < 0) kampfEffekte.remove(ke); spieler.addTempAttribut(magAng, "magAng"); break;
	    		case(Effekt.MAGDEFBONUS): int magDef = ke.getBonus(spieler.getTempAttribut("magDef")); if(magDef < 0) kampfEffekte.remove(ke); spieler.addTempAttribut(magDef, "magDef"); break;
	    		case(Effekt.PRZBONUS): int prz = ke.getBonus(spieler.getTempAttribut("prz")); if(prz < 0) kampfEffekte.remove(ke); spieler.addTempAttribut(prz, "prz"); break;
	    		case(Effekt.FLKBONUS): int flk = ke.getBonus(spieler.getTempAttribut("flk")); if(flk < 0) kampfEffekte.remove(ke); spieler.addTempAttribut(flk, "flk"); break;
	    	}
	    }
	    ausgabe.println();
	}

	// Dieser Methode wird uebergeben, ob der Spieler den kampf gewonnen hat oder nicht.
	private void kampfEndet(boolean sieg) {
		kaempft = false;
	    spieler.resetTemp();
	    kampfEffekte = new Vector<KampfEffekt>();
	    ausgabe.println();
	    if(sieg) {
	    	//if(enemy.getNumGen().isPlural()) ausgabe.println(enemy.getNumGen().getBest(0) + enemy.getName() + " wurden besiegt, du hast gewonnen!");
	    	//else ausgabe.println(enemy.getNumGen().getBest(0) + enemy.getName() + " wurde besiegt, du hast gewonnen!");
	    	ausgabe.println("Du hast die Feinde besiegt, du hast gewonnen!");
	    	// XP, GOLD UND LOOT WERDEN HIER VERTEILT
	    	int xp = 0;
	    	for(Entity g : kampf.getKampfGegner())
	    		xp += ((Gegner) g).getXp();

	    	ausgabe.println("Du erhälst " + xp + " Erfahrungspunkte.\n");
	    	spieler.addXp(xp);
	    	if(!loot.istLeer()) {
	    		ausgabe.println("Die Gegner haben etwas fallen gelassen, du erhälst ");
	    		for(Stapel s : loot.getAlleStapel()) {
	    			if(s.getAnzahl() < 1)
	    				continue;
	    			if(s.getGegenstand() instanceof Waehrung)
	    				ausgabe.print(((Waehrung) s.getGegenstand()).getOutput(((Waehrung) s.getGegenstand()).kleinsteWaehrungBetrag(s.getAnzahl())) + ", ");
	    			else
	    				ausgabe.print(s.getAnzahl() + " " + s.getName() + ", ");
	    			getInventar().addGegenstand(s);
	    		}
	    		ausgabe.print("du steckst die Gegenstände weg.");
	    	}
	    } else {
	    	ausgabe.println("Du wurdest besiegt!");
	    	spieler.addLp(-spieler.getLp() + spieler.getMaxLp());
	    }
	}

	// - - - Gesprächs-Methoden - - -

	/**
	 * Diese Methode gibt zurueck, ob sich der Spieler im Gespräch mit einem NPC befindet.
	 * @return Wahr, wenn er sich im Gespraech befindet, ansonsten falsch.
	 */
	public boolean spielerSpricht() {
		return spricht;
	}

	/**
	 * Startet ein Gespräch.
	 * @param npc Der neue Gesprächspartner.
	 */
	public void initGespraech(NPC npc) {
		gespraechspartner = npc;
		spricht = true;
		ausgabe.println();
	}

	private void gespraech(StringEvent evt) {
		if(!gespraechspartner.ansprechen(evt.getCommand()))
			spricht = false;
	}


	// =================================================== //
	// ALLE METHODEN, DIE VON SPIELTEST AUFGERUFEN WERDEN. //
	// =================================================== //

	/**
	 *  Diese Methode gibt alle Informationen ueber die Spielerposition aus.
	 */
	public void spielerPositionAnzeigen() {
		// Zuerst wird die Anzeige gecleart.
		ausgabe.clear();

		if(spielerPosition instanceof IEreignis)
			((OrtEreignis)spielerPosition).vorUntersuchung();

		ausgabe.printPrintable(spielerPosition);
		ausgabe.println();

	    if(spielerPosition.getAusgaenge().isEmpty())
	    	return;

	    // Alle verfuegbaren Ausgaenge werden angezeigt.
	    ausgabe.println("Verfügbare Ausgänge:");

	    // Es wird eine Kopie des Vectors aller Ausgaenge angefordert.
	    Vector<Ausgang> aktuelleAusgaenge = spielerPosition.getAusgaenge();

	    // Es werden die Ausgaenge ueberprueft und ausgegeben.
	    for(Ausgang a : aktuelleAusgaenge) {
	    	// Der Ausgang wird ausgegeben.
	    	ausgabe.println(a.getRichtungsName() + "(" + a.getAbkuerzung() + ")");
	    	if(a.getZielort().istBesucht())
	    		ausgabe.print(" - " + a.getZielort().getName());
	    	else
	    		ausgabe.print(" - ???");
	    }
	    ausgabe.println("\n");

	    if(spielerPosition instanceof IEreignis)
			((OrtEreignis)spielerPosition).nachUntersuchung();
	}

	/**
	 * Ueberprueft, ob es ein entsprechendes UntersuchbaresObjekt am aktuellen Ort gibt und gibt die Beschreibung aus.
	 * @param objektName Der Name des Objekts.
	 */
	public void sucheUntersuchbaresObjekt(String objektName) {
		UntersuchbaresObjekt o = spielerPosition.getUntersuchbaresObjekt(objektName);
	    if(o != null) {
	    	if(o instanceof IEreignis)
				((UntersuchbaresObjektEreignis)o).vorUntersuchung();

	    	ausgabe.println();
	    	ausgabe.printPrintable(o);

	    	if(o instanceof IEreignis)
				((UntersuchbaresObjektEreignis)o).nachUntersuchung();
	    } else {
	    	switch(r.nextInt(8)) {
	    		case(0): ausgabe.println("An " + objektName + " ist nichts auffällig."); break;
	    		case(1): ausgabe.println(objektName + " ist nicht interessant."); break;
	    		case(2): ausgabe.println("Es gibt interssantere Dinge als " + objektName); break;
	    		case(3): ausgabe.println(objektName + " ist nicht wichtig."); break;
	    		case(4): ausgabe.println("Es gibt hier auffäligere Dinge als " + objektName); break;
	    		case(5): ausgabe.println("Was soll an " + objektName + " interessant sein?"); break;
	    		case(6): ausgabe.println("Es bringt nichts " + objektName + " zu untersuchen."); break;
	    		case(7): ausgabe.println(objektName + " ist nicht von Bedeutung."); break;
	    	}
	    }
	}

	/**
	 * Sucht einen Gegenstand oder einen Ressourcen Punkt an diesem Ort.
	 * @param gegenstandName Der Name des Gegenstands oder des Ressourcen Punkts.
	 */
	public void sucheGegenstand(String gegenstandName) {
		Stapel s = spielerPosition.getGegenstand(gegenstandName);
	    RessourcenPunkt rp = spielerPosition.getRessourcenPunkt(gegenstandName);
	    if(s != null) {
	    	Gegenstand g = s.getGegenstand();
	    	switch(r.nextInt(6)) {
	    		case(0): ausgabe.println("Du hast " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " eingesteckt."); break;
	    		case(1): ausgabe.println("Du steckst " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " ein."); break;
	    		case(2): ausgabe.println("Du nimmst " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " mit."); break;
	    		case(3): ausgabe.println(g.getNumGen().getBest(0) + g.getName() + " könnte sich als nützlich erweisen, deshalb steckst du " + g.getNumGen().getPers(3).toLowerCase() + "ein."); break;
	    		case(4): ausgabe.println("Du nimmst " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " an dich, da " + g.getNumGen().getPers(0).toLowerCase() + "nützlich aussieht."); break;
	    		case(5): ausgabe.println("Du steckst " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " in deine Tasche."); break;
	    	}
	    	spieler.getInventar().addGegenstand(s);
	    	spielerPosition.removeGegenstand(g);
	    	return;
	    } else if (rp != null) {
	    	Stapel[] loot = rp.ernte();
	    	if(loot == null) {
	    		ausgabe.println("Du kannst hier im Moment nichts mehr ernten, komm später vorbei.");
	    		return;
	    	}
	    	if(loot.length == 0) {
	    		ausgabe.println("Du konntest nichts ernten.");
	    		return;
	    	}
	    	ausgabe.println("Du findest " + rp.getNumGen().getBest(3).toLowerCase() + rp.getName() + " und du erntest ");
	    	for(Stapel stapel : loot) {
	    		spieler.getInventar().addGegenstand(stapel);
	    		ausgabe.print(stapel.getAnzahl() + " x " + stapel.getName() + ", ");
	    	}
	    	ausgabe.print("du nimmst die Gegenstände an dich.");

	    } else {
	    	switch(r.nextInt(5)) {
	    		case(0): ausgabe.println(gegenstandName + " kann nicht mitgenommen werden."); break;
	    		case(1): ausgabe.println("Du kannst " + gegenstandName + " nicht mitnehmen."); break;
	    		case(2): ausgabe.println(gegenstandName + " ist nicht von nutzen."); break;
	    		case(3): ausgabe.println("Was willst du mit " + gegenstandName + " anfangen?"); break;
	    		case(4): ausgabe.println("Du kannst " + gegenstandName + " nicht aufheben."); break;
	    	}
	    	return;
	    }
	}

	/**
	 * Zeigt alle ausgeruesteten Gegenstaende an.
	 */
	public void zeigeAusruestungAn() {
		// Das Ausruestungs-Objekt gibt alles selber aus, braucht aber das Welt-Objekt dafuer.
	    spieler.getAusruestung().zeigeAn(this);
	}

	/**
	 * Gibt den aktuellen Status des Spielers aus.
	 */
	public void zeigeStatusAn(){
		spieler.zeigeStatusAn(this);
	}

	/**
	 * Zeigt die Informationen zu einem Gegenstand an.
	 * @param gegenstand Der anzuzeigende Gegenstand.
	 */
	public void zeigeGegenstandInfosAn(Gegenstand gegenstand) {
	    if(gegenstand == null) {
	    	switch(r.nextInt(5)) {
	    		case(0): ausgabe.println("Das ist kein Gegenstand, den du hast."); break;
	    		case(1): ausgabe.println("Dir liegen keinerlei Informationen über diesen Gegenstand vor."); break;
	    		case(2): ausgabe.println("Darüber hast du keine Informationen."); break;
	    		case(3): ausgabe.println("Über diesen Gegenstand lässt sich nichts besonderes sagen."); break;
	    		case(4): ausgabe.println("Über diesen Gegenstand liegen dir keine Informationen vor."); break;
	    	}
	    	return;
	    }
	    ausgabe.printPrintable(gegenstand);
	}

	/**
	 * Am aktuellen Ort wird der uebergebene Gegenstand verwendet.
	 * @param gegenstand Der verwendete Gegenstand.
	 */
	public void verwendeGegenstand(Gegenstand gegenstand) {
		boolean eingesetzt = false;
	    if(gegenstand instanceof VerwendbarerGegenstand) {
	    	VerwendbarerGegenstand g = (VerwendbarerGegenstand)gegenstand;
	    	if(kaempft && g.istEinsetzbarKampf() && spieler.getInventar().containsGegenstand(g)) {
	    		// Es wird alles in der Kampf Methode geregelt.
	    		kampfEffekte.add(new KampfEffekt(g.getEffekt()));
	    		spieler.getInventar().removeGegenstand(g, 1);
	    		eingesetzt = true;
	    	} else if(!kaempft && g.istEinsetzbarAusserhalb() && spieler.getInventar().containsGegenstand(g)) {
	    		switch(g.getEffekt().getTyp()) {
	    		case(Effekt.HEILEN):
	    			int lp = g.getEffekt().getBonus(spieler.getMaxLp());
	    			ausgabe.println("Du verwendest " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " und stellst " + Math.min(spieler.getMaxLp()-spieler.getLp(), lp) + " LP wieder her.");
	    			spieler.addLp(lp);
	    			break;
	    		case(Effekt.MPREGENERATION):
	    			int mp = g.getEffekt().getBonus(spieler.getMaxMp());
	    			ausgabe.println("Du verwendest " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " und stellst " + Math.min(spieler.getMaxMp()-spieler.getMp(), mp) + " MP wieder her.");
	    			spieler.addMp(mp);
	    			break;
	    		}
	    		spieler.getInventar().removeGegenstand(g, 1);
	    		eingesetzt = true;
	    	}
	    }

	    if(spielerPosition.fuehreAktionAus(gegenstand))
	    	eingesetzt = true;

	    if(!eingesetzt)
	    	ausgabe.println("Du kannst diesen Gegenstand hier nicht verwenden.");
	}

	/**
	 * Ruestet dem Spieler einen Gegenstand aus.
	 * @param nameAusruestung Der Name des Gegenstands.
	 */
	public void ausruesten(String nameAusruestung) {
		Gegenstand g = Gegenstand.getGegenstand(nameAusruestung);
	    if(g == null) return;
	    if(spieler.ruesteAus(g)) {
	    	switch(r.nextInt(2)) {
	    		case(0): ausgabe.println("Du hast " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " ausgerüstet."); break;
	    		case(1): ausgabe.println("Du hast den Gegenstand ausgerüstet."); break;
	    	}
	    } else {
	    	switch(r.nextInt(3)){
	    		case(0): ausgabe.println("Du besitzt diesen Gegenstand nicht."); break;
	    		case(1): ausgabe.println(nameAusruestung + " befindet sich nicht in deinem Inventar."); break;
	    		case(2): ausgabe.println(nameAusruestung + " kann nicht ausgerüstet werden."); break;
	    	}
	    }
	}

	/**
	 * Legt einen Gegenstand, den der Spieler ausgeruestet hat ab.
	 * @param nameAusruestung Der abzulegende Gegenstand.
	 */
	public void ablegen(String nameAusruestung) {
		Gegenstand g = Gegenstand.getGegenstand(nameAusruestung);
	    if(g == null) return;
	    if(spieler.legeAb(g)) {
	    	switch(r.nextInt(3)) {
	    		case(0): ausgabe.println("Du legst den Gegenstand ab."); break;
	    		case(1): ausgabe.println("Du hast " + g.getName() + " abgelegt."); break;
	    		case(2): ausgabe.println("Du hast " + g.getName() + " zurück in dein Inventar gelegt."); break;
	    	}

	    } else {
	    	switch(r.nextInt(3)) {
	        	case(0): ausgabe.println("Du hast diesen Gegenstand nicht ausgerüstet"); break;
	        	case(1): ausgabe.println("Du kannst einen Gegenstand, den du nicht ausgerüstet hast, nicht ablegen."); break;
	        	case(2): ausgabe.println(g.getName() + " kann nicht abgelegt werden, da du " + g.getNumGen().getPers(3) + "nicht ausgerüstet hast."); break;
	    	}
	    }
	}

	/**
	 *  Sucht an der aktuellen Spielerposition nach Custom Commands.
	 *  @param befehl Der Befehl, nach dem gesucht wird.
	 */
	public boolean sucheKommando(String befehl){
	    return spielerPosition.kommandoEingegeben(befehl);
	}
	/**
	 * Ueberprueft, ob es sich bei diesem Befehl um ein globales Kommando handelt.
	 * @param befehl Der Befehl, der ueberprueft werden soll.
	 */
	public boolean ueberpruefeKommandos(String befehl) {
	    for(Kommando k : globaleKommandos)
	    	if(k.istBefehl(befehl)) {
	    		k.getEreignis().eingetreten();
	    		// Das true sagt nur aus, dass es so ein Kommando gibt.
	    		return true;
	    	}
	    return false;
	}

	/**
	 * Sucht an einem Ort einen Behaelter und der Spieler pluendert diesen gegebenenfalls.
	 * @param eingabe Die Eingabe des Spielers, nach der gesucht wird.
	 */
	public void sucheBehaelter(String eingabe) {
		int typ = -1;
	    if(eingabe.equalsIgnoreCase("Fass")) typ = Behaelter.FASS;
	    if(eingabe.equalsIgnoreCase("Kiste")) typ = Behaelter.KISTE;
	    if(eingabe.equalsIgnoreCase("Truhe")) typ = Behaelter.TRUHE;
	    if(typ > -1) {
	    	Stapel[] gegenstaende = spielerPosition.getBehaelterInhalt((byte)typ);
	    	if(gegenstaende != null) {
	    		if(gegenstaende.length > 0) {
	    			switch(typ) {
	    				case(Behaelter.FASS): ausgabe.println("Du öffnest das Fass und suchst darin nach Gegenständen. Du findest "); break;
	    				case(Behaelter.KISTE): ausgabe.println("Du öffnest die Kiste und suchst darin nach Gegenständen. Du findest "); break;
	    				case(Behaelter.TRUHE): ausgabe.println("Du öffnest den Deckel der Truhe und suchst darin nach Gegenständen, du findest in der Truhe "); break;
	    			}

	    			for(Stapel s: gegenstaende) {
	    				ausgabe.print(s.getAnzahl() + " x " + s.getName() + ", ");
	    				spieler.getInventar().addGegenstand(s);
	    			}
	    			ausgabe.print("du nimmst die Gegenstände an dich.");

	    		} else {
	    			switch(typ) {
	    				case(Behaelter.FASS): ausgabe.println("Du öffnest das Fass und suchst darin nach Gegenstäden, aber das innere des Fass ist leer."); break;
	    				case(Behaelter.KISTE): ausgabe.println("Du öffnest die Kiste und suchst darin nach Gegenständen, aber in der Kiste befindet sich nichts."); break;
	    				case(Behaelter.TRUHE): ausgabe.println("Du öffnest den Deckel der Truhe und suchst darin nach Gegenständen, aber die Truhe scheint schon seit langem leer zu sein."); break;
	    			}
	    		}
	    		return;
	    	}

	    	switch(r.nextInt(4)) {
	        	case(0): ausgabe.println("Es gibt an diesem Ort nicht so etwas."); break;
	        	case(1): ausgabe.println("Hier befindet sich so etwas nicht."); break;
	        	case(2): ausgabe.println("Es lohnt sich nicht an diesem Ort irgendetwas zu suchen."); break;
	        	case(3): ausgabe.println("An diesem Ort befindet sich nichts von Interesse."); break;
	    	}
	    }
	}
}
