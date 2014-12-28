package game;

import game.bedingung.Bedingung;
import gui.Anzeige;
import gui.GUI;
import gui.MiniMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import npc.NPC;

import util.StringEvent;
import util.StringListener;

public class SpielWelt implements Serializable, StringListener {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Die Position des Spielers in der Welt.
	private Ort spielerPosition;
	// Die Anzeige, die alle auszugebende Dinge ausgibt.
	transient private Anzeige ausgabe;
	// Die Map fuer den Spieler.
	transient private MiniMap map;
	// Der Boolean, der angibt, ob sich der Spieler im Kampf befindet.
	transient private boolean kaempft;
	// Gegner gegen den gerade gekaepft wird.
	transient private Gegner enemy;
	// Der Boolean, der angibt, ob sich der Spieler im Gespräch befindet.
	transient private boolean spricht;	
	//NPC, mit dem gerade gesprochen wird.
	transient private NPC gespraechspartner;
	// Alle Statusveraenderungen, die im Kampf aktiv sind.
	transient private Vector<KampfEffekt> kampfEffekte;

	// Der Spieler
	private Spieler spieler;
	// Die Ereignisse, die bei einem Raumwechsel eintreten.
	private Vector<Ereignis> ereignisse;
	// Alle global geltenden Kommandos.
	private Vector<Kommando> globaleKommandos;
	// Alle Gegenstaende im Spiel(wird benoetigt, um die Gegenstandsliste zu speichern und laden)
	private Vector<Gegenstand> gegenstaende;
	// Alle Kombinationen im Spiel(wird benoetigt, um die Kombinationen zu speichern und zu laden)
	private ArrayList<Vector<Kombination>> kombinationen;
	  
	// Das Random-Objekt, das alle Zufallszahlen ausgibt.
	private Random r;

	/**
	 *  Der standard Konstruktor fuer eine neue SpielWelt.
	 */
	public SpielWelt(){
		this.ausgabe = null;
	    // Die Listen werden leer intialisiert, so auch die momentan unbekannte Position des Spielers.
	    spieler = new Spieler();
	    ereignisse = new Vector<Ereignis>();
	    globaleKommandos = new Vector<Kommando>();
	    kampfEffekte = new Vector<KampfEffekt>();
	    r = new Random();
	    spielerPosition = null;
	    
	    gegenstaende = new Vector<Gegenstand>();
	    Gegenstand.setListe(gegenstaende);
	    kombinationen = Kombination.getAlleKombinationen();
	    kaempft = false;
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
	public void updateGegenstandsListe(){
	    Gegenstand.setListe(gegenstaende);
	    Kombination.setAlleKombinationen(kombinationen);
	}
	  
	/**
	 *  Diese Methode aktualisiert die Anzeige der SpielWelt mit einer neuen.
	 *  neueAnzeige: die neue Anzeige
	 */
	public void setGUI(GUI gui){
	    this.ausgabe = gui.getAnzeige();
	    this.map = gui.getMap();
	    this.map.updateMiniMap(spielerPosition);
	    this.spieler.getInventar().addListener(gui.getInventoryDisplay());
	    this.kampfEffekte = new Vector<KampfEffekt>();
	}

	/**
	 *  Diese Methode gibt die aktuelle Position des Spielers zurueck.
	 */
	public Ort aktuellePosition(){
		return spielerPosition;
	}

	/**
	 *  Diese Methode setzt die aktuelle Position auf den gegebenen Ort.
	 *  zielort: der neue Ort, an dem sich der Spieler befindet.
	 */
	public void setAktuellePositon(Ort zielort){
	    for(Ereignis e: ereignisse){
	    	if(e.getTyp() == Ereignis.SPIELERVERLAESST) e.eingetreten();
	    }
	    spielerPosition = zielort;
	    spielerPosition.besucht();
	    if(map != null) map.updateMiniMap(spielerPosition);
	    Gegner gegner = spielerPosition.findetKampfStatt();
	    if(gegner != null) initKampf(gegner);
	    for(Ereignis e: ereignisse){
	    	if(e.getTyp() == Ereignis.SPIELERBETRITT) e.eingetreten();
	    }
	}

	/**
	 *  Diese Methode gibt eine Instanz des Inventars zurueck.
	 */
	public Inventar getInventar(){
	    return spieler.getInventar();
	}
	  
	/**
	 *  Diese Methode fuegt einen neuen Gegenstand dem Inventar des Spielers hinzu.
	 */
	public void addGegenstand(Gegenstand gegenstand, int anzahl){
	    if(!spieler.getInventar().containsGegenstand(gegenstand)){
	    	spieler.getInventar().addGegenstand(gegenstand, anzahl);
	    }
	}
	  
	/**
	 *  Diese Methode entfernt einen Gegenstand aus dem Inventar des Spielers.
	 */
	public void removeGegenstand(Gegenstand gegenstand, int anzahl){
	    if(spieler.getInventar().containsGegenstand(gegenstand)){
	    	spieler.getInventar().removeGegenstand(gegenstand, anzahl);
	    }
	}

	/**
	 *  Fuegt ein neues Ereignis hinzu.
	 *  ereignis: das neue Ereignis.
	 */
	public void addEreignis(Ereignis ereignis){
	    if(!ereignisse.contains(ereignis)) ereignisse.add(ereignis);
	}

	/**
	 *  Loescht ein Ereignis, da es ausgefuhert wurde.
	 *  ereignis: das Ereignis, welches geloescht werden soll.
	 */
	public void loescheEreignis(Bedingung bedingung){
		for(Ereignis e: ereignisse.toArray(new Ereignis[0])){
			if(e.getBedingung() == bedingung){
				ereignisse.remove(e);
				e = null;
			}
	    }
	}
	  
	/**
	 *  Diese Methode fuegt ein globales Kommando dem Spiel hinzu, das eine Bedingung hat.
	 *  befehl: der einzugebende Befehl.
	 *  bedingung: die zu erfuellende Bedingung.
	 */
	public void addGlobalesKommando(String befehl, Bedingung bedingung){
	    Kommando k = new Kommando(befehl, bedingung);
	    if(!globaleKommandos.contains(k)){
	    	globaleKommandos.add(k);
	    }
	}
	  
	/**
	 *  Gibt das Spieler-Objekt zurueck.
	 */
	public Spieler getSpieler(){
		return spieler;
	}
	
	public void actionPerformed(StringEvent evt) {
		if(spielerKaempft()) 
			kampf(evt);
		if(spielerSpricht()) 
			gespraech(evt);
		
	}
	  
	/**
	 *  Diese Methode gibt zurueck, ob sich der Spieler im Kampf befindet.
	 */
	public boolean spielerKaempft(){
	    return kaempft;
	}
	
	/**
	 *  Diese Methode startet einen Kampf mit dem uebergebenen Gegner.
	 *  gegner: gegen den gekaempft wird
	 */
	private void initKampf(Gegner gegner){
		enemy = gegner;
	    spieler.resetTemp();
	    kaempft = true;
	    ausgabe.clear();
	    ausgabe.print(enemy.getNumGen().getUnbest(0) + enemy.getName() + " greift dich an!\n");
	}
	  
	/** 
	 *  Diese Methode wird aufgerufen, wenn ein neues CombatEvent gefunden wurde.
	 */
	private void kampf(StringEvent evt){
		double flkGegner = enemy.getFlk() * (double)(r.nextInt(21) - 10) / 100.0 + 1.0;
	    double flkSpieler = spieler.getTempFlk() * (double)(r.nextInt(21) - 10) / 100.0 + 1.0;
	    Faehigkeit fSpieler = null;
	    for(Faehigkeit f: spieler.getFaehigkeiten()){
	    	if(f.getName().equalsIgnoreCase(evt.getCommand())) fSpieler = f;
	    }
	    Faehigkeit fGegner = enemy.getFaehigkeit();
	    
	    Kommando kommando = Kommando.getKommando(evt.getCommand());
	    
	    // Bei einer ungueltigen Eingabe wird die Auswertung abgebrochen.
	    if(kommando == Kommando.INVALID && fSpieler == null) return;
	    
	    // Wenn der Gegenstand eine Faehigkeit bei der Benutzung ausloest, dann wird diese nun uebergeben.
	    VerwendbarerGegenstand g = (VerwendbarerGegenstand)Gegenstand.getGegenstand(Kommando.getEingabe());
	    if(g != null){
	    	if(!g.hasEffekt()){
	    		fSpieler = g.getFaehigkeit();
	    		kommando = Kommando.INVALID;
	    	}
	    }
	    
	    int schadenS = 0;
	    String ausgabeS = "";
	    
	    // Spieler
	    if(fSpieler != null){
	    	// Es wird ueberprueft, ob der Spieler die richtige Waffe fuer die Faehigkeit ausgeruestet hat.
	    	if(!fSpieler.gueltigeWaffe(spieler.getWaffenarten())){
	    		// Der Spieler hat die falsche Waffe ausgeruestet.
	    		ausgabe.println("Du hast nicht die richtige Waffe für diese Fähigkeit ausgerüstet, du kannst diese Fähigkeit nur einsetzen wenn du");
	    		for(byte b: fSpieler.getGueltigeWaffen()){
	    			ausgabe.print(" ein " + Waffe.getWaffenartNamen(b));
	    		}	
	    		ausgabe.print(" ausgerüstet hast.");
	    		return;
	    	}
	    
	    	// Der Schaden des Spielers und des Gegners werden berechnet.
	    	double angriffS = fSpieler.getBonus(spieler.getTempAng()) * (r.nextInt(31) + 85) / 100.0;
	    	schadenS = (int)(Math.pow(1.6, Math.log(angriffS / Math.max(enemy.getDef(), 1))) * angriffS / 4);
	    	// Ausgaben erstellen
	    	ausgabeS = fSpieler.getAusgabe().replaceAll("#", String.valueOf(schadenS));
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
	    double angriffG = fGegner.getBonus(enemy.getAng()) * (r.nextInt(31) + 85) / 100.0;
	    int schadenG = (int)(Math.pow(1.6, Math.log(angriffG / Math.max(spieler.getTempDef(), 1))) * angriffG / 4);
	    
	    String ausgabeG = fGegner.getAusgabe();
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
	    
	    // Die eigentlichen Angriffe
	    if(flkSpieler > flkGegner){
	    	if(kommando == Kommando.VERWENDEN){
	    		verwendeGegenstand(g);
	    	}else if(fSpieler != null){
	    		ausgabe.println(ausgabeS);
	    		if(enemy.getLp() - schadenS <= 0){
	    			kampfEndet(true);
	    			return;
	    		}else{
	    			enemy.decreaseLp(schadenS);
	    		}
	    	}
	    	ausgabe.println(ausgabeG);
	    	if(spieler.getLp() - schadenG <= 0){
	    		kampfEndet(false);
	    		return;
	    	}else{
	    		spieler.heileLp(-schadenG);
	    	}
	    	
	   	}else{
	   		ausgabe.println(ausgabeG);
	   		if(spieler.getLp() - schadenG <= 0){
	   			kampfEndet(false);
	   			return;
	   		}else{
	   			spieler.heileLp(-schadenG);
	   		}
	   		if(kommando == Kommando.VERWENDEN){
	   			verwendeGegenstand(g);
	   		}else if(fSpieler != null){
	   			ausgabe.println(ausgabeS);
	   			if(enemy.getLp() - schadenS <= 0){
	   				kampfEndet(true);
	   				return;
	   			}else{
	   				enemy.decreaseLp(schadenS);
	   			}
	   		}
	   	}

	    // Alle KampfEffekte werden ueberprueft.
	    for(KampfEffekt ke: kampfEffekte.toArray(new KampfEffekt[0])){
	    	switch(ke.getTyp()){
	    		case(Effekt.HEILEN): int lp = ke.getBonusLpMp(spieler.getMaxLp()); if(lp == 0) kampfEffekte.remove(ke); else ausgabe.println("Du stellst " + lp + " LP wieder her."); spieler.heileLp(lp); break;
	    		case(Effekt.MPREGENERATION): int mp = ke.getBonusLpMp(spieler.getMaxMp()); if(mp == 0) kampfEffekte.remove(ke); else ausgabe.println("Du stellst " + mp + " MP wieder her."); spieler.heileMp(mp); break;
	    		case(Effekt.ANGBONUS): int ang = ke.getBonus(spieler.getTempAng()); if(ang < 0) kampfEffekte.remove(ke); spieler.addTempAng(ang); break;
	    		case(Effekt.DEFBONUS): int def = ke.getBonus(spieler.getTempDef()); if(def < 0) kampfEffekte.remove(ke); spieler.addTempDef(def); break;
	    		case(Effekt.MAGANGBONUS): int magAng = ke.getBonus(spieler.getTempMagAng()); if(magAng < 0) kampfEffekte.remove(ke); spieler.addTempMagAng(magAng); break;
	    		case(Effekt.MAGDEFBONUS): int magDef = ke.getBonus(spieler.getTempMagDef()); if(magDef < 0) kampfEffekte.remove(ke); spieler.addTempMagDef(magDef); break;
	    		case(Effekt.PRZBONUS): int prz = ke.getBonus(spieler.getTempPrz()); if(prz < 0) kampfEffekte.remove(ke); spieler.addTempPrz(prz); break;
	    		case(Effekt.FLKBONUS): int flk = ke.getBonus(spieler.getTempFlk()); if(flk < 0) kampfEffekte.remove(ke); spieler.addTempFlk(flk); break;
	    	}
	    }
	    ausgabe.println();
	}
	  
	// Dieser Methode wird uebergeben, ob der Spieler den kampf gewonnen hat oder nicht.
	private void kampfEndet(boolean sieg){
		kaempft = false;
	    spieler.resetTemp();
	    kampfEffekte = new Vector<KampfEffekt>();
	    if(sieg){
	    	if(enemy.getNumGen().isPlural()) ausgabe.println(enemy.getNumGen().getBest(0) + enemy.getName() + " wurden besiegt, du hast gewonnen!");
	    	else ausgabe.println(enemy.getNumGen().getBest(0) + enemy.getName() + " wurde besiegt, du hast gewonnen!\n");
	    	// XP, GOLD UND LOOT WERDEN HIER VERTEILT
	    	spieler.addXp(enemy.getXp(), this);
	    	ausgabe.println("Du erhälst " + enemy.getXp() + " Erfahrungspunkte.");
	    }else{
	    	ausgabe.println("Du wurdest besiegt!");
	    	spieler.heileLp(-spieler.getLp() + spieler.getMaxLp());
	    }
	}
	
	// - - - Gesprächs-Methoden - - - 
	
	/**
	 * Diese Methode gibt zurueck, ob sich der Spieler im Gespräch mit einem NPC befindet.
	 * @return
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
		if(!gespraechspartner.ansprechen(evt.getCommand())) {
			spricht = false;
		}
	}
	  
	  
	// ===================================================
	// ALLE METHODEN, DIE VON SPIELTEST AUFGERUFEN WERDEN.
	// ===================================================

	/**
	 *  Diese Methode gibt alle Informationen ueber die Spielerposition aus.
	 */
	public void spielerPositionAnzeigen(){
		// Zuerst wird die Anzeige gecleart.
		ausgabe.clear();
	    
	    // Name des Orts ausgeben.
	    ausgabe.printBold(spielerPosition.getName());

	    // Beschreibung des Orts ausgeben.
	    ausgabe.println(spielerPosition.getBeschreibung());

	    ausgabe.println();

	    if(!spielerPosition.hatAusgaenge()) return;
	    
	    // Alle verfuegbaren Ausgaenge werden angezeigt.
	    ausgabe.println("Verfügbare Ausgänge:");

	    // Es wird eine Kopie des Vectors aller Ausgaenge angefordert.
	    Vector<Ausgang> aktuelleAusgaenge = spielerPosition.getAusgaenge();

	    // Es werden die Ausgaenge ueberprueft und ausgegeben.
	    for(Ausgang a: aktuelleAusgaenge.toArray(new Ausgang[aktuelleAusgaenge.size()])){
	    	// Der Ausgang wird ausgegeben.
	    	ausgabe.println(a.getRichtungsName());
	    	if(a.getZielort().istBesucht()) ausgabe.print(" - " + a.getZielort().getName());
	    	else ausgabe.print(" - ???");
	    }
	    ausgabe.println();
	}

	/**
	 *  Diese Methode ueberprueft, ob es ein entsprechendes UntersuchbaresObjekt am aktuellen Ort gibt und gibt die Beschreibung aus.
	 *  objektName: der Name des Objekts.
	 */
	public void sucheUntersuchbaresObjekt(String objektName){
		UntersuchbaresObjekt o = spielerPosition.getUntersuchbaresObjekt(objektName);
	    if(o != null){
	    	ausgabe.println(o.getName());
	    	ausgabe.println(o.getBeschreibung());
	    	// Hier wird gepreuft ob die Bedingung erfuellt ist
	    	o.pruefen();
	    }else{
	    	switch(r.nextInt(8)){
	    		case(0): ausgabe.println("An " + objektName + " ist nichts auffälig."); break;
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
	 *  Diese Methode ueberprueft, ob es ein entsprechenden Gegenstand am aktuellen Ort gibt und fuegt diesen  dem Inventar zu.
	 *  gegenstandName: der Name des Gegenstands.
	 */
	public void sucheGegenstand(String gegenstandName){
		Stapel s = spielerPosition.getGegenstand(gegenstandName);
	    if(s != null){
	    	Gegenstand g = s.getGegenstand();
	    	switch(r.nextInt(6)){
	    		case(0): ausgabe.println("Du hast " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " eingesteckt."); break;
	    		case(1): ausgabe.println("Du steckst " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " ein."); break;
	    		case(2): ausgabe.println("Du nimmst " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " mit."); break;
	    		case(3): ausgabe.println(g.getNumGen().getBest(0) + g.getName() + " könnte sich als nützlich erweisen, deshalb steckst du " + g.getNumGen().getPers(3).toLowerCase() + "ein."); break;
	    		case(4): ausgabe.println("Du nimmst " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " an dich, da " + g.getNumGen().getPers(0).toLowerCase() + "nützlich aussieht."); break;
	    		case(5): ausgabe.println("Du steckst " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " in deine Tasche."); break;
	    	}
	    	spieler.getInventar().addGegenstand(s);
	    	spielerPosition.removeGegenstand(g);
	    }else{
	    	switch(r.nextInt(5)){
	    		case(0): ausgabe.println(gegenstandName + " kann nicht mitgenommen werden."); break;
	    		case(1): ausgabe.println("Du kannst " + gegenstandName + " nicht mitnehmen."); break;
	    		case(2): ausgabe.println(gegenstandName + " ist nicht von nutzen."); break;
	    		case(3): ausgabe.println("Was willst du mit " + gegenstandName + " anfangen."); break;
	    		case(4): ausgabe.println("Du kannst " + gegenstandName + " nicht aufheben."); break;
	    	}	
	    }
	}

	/**
	 *  Diese Methode zeigt alle ausgeruesteten Gegenstaende an.
	 */
	public void zeigeAusruestungAn(){
		// Das Ausruestungs-Objekt gibt alles selber aus, braucht aber das Welt-Obhekt dafuer.
	    spieler.getAusruestung().zeigeAn(this);
	}
	  
	/**
	 *  Diese Methode gibt den aktuellen Status des Spielers aus.
	 */
	public void zeigeStatusAn(){
		spieler.zeigeStatusAn(this);
	}
	  
	/**
	 *  Diese Methode zeigt die Informationen zu einem Gegenstand an.
	 *  gegenstand: der anzuzeigende Gegenstand.
	 */
	public void zeigeGegenstandInfosAn(Gegenstand gegenstand){
	    if(gegenstand == null){
	    	switch(r.nextInt(5)){
	    		case(0): ausgabe.println("Das ist kein Gegenstand, den du hast."); break;
	    		case(1): ausgabe.println("Dir liegen keinerlei Informationen über diesen Gegenstand vor."); break;
	    		case(2): ausgabe.println("Darüber hast du keine Informationen."); break;
	    		case(3): ausgabe.println("Über diesen Gegenstand lässt sich nichts besonderes sagen."); break;
	    		case(4): ausgabe.println("Über diesen Gegenstand liegen dir keine Informationen vor."); break;
	    	}
	    	return;
	    }
	    ausgabe.println(gegenstand.getName());
	    if(gegenstand.hasWerte()){
	    	if(gegenstand.getLp() != 0) ausgabe.println("+" + gegenstand.getLp() + " Lebenspunkte");
	    	if(gegenstand.getMp() != 0) ausgabe.println("+" + gegenstand.getMp() + " Magiepunkte");
	    	if(gegenstand.getAng() != 0) ausgabe.println("+" + gegenstand.getAng() + " Angriff");
	    	if(gegenstand.getDef() != 0) ausgabe.println("+" + gegenstand.getDef() + " Verteidigung");
	    	if(gegenstand.getMagAng() != 0) ausgabe.println("+" + gegenstand.getMagAng() + " magischer Angriff");
	    	if(gegenstand.getMagDef() != 0) ausgabe.println("+" + gegenstand.getMagDef() + " magische Verteidigung");
	    	if(gegenstand.getPrz() != 0) ausgabe.println("+" + gegenstand.getPrz() + " Präzision");
	    	if(gegenstand.getFlk() != 0) ausgabe.println("+" + gegenstand.getFlk() + " Flinkheit");
	    }
	    ausgabe.println(gegenstand.getBeschreibung());
	}

	/**
	 *  Am aktuellen Ort wird der uebergebene Gegenstand verwendet.
	 */
	public void verwendeGegenstand(Gegenstand gegenstand){
		boolean eingesetzt = false;
	    if(gegenstand instanceof VerwendbarerGegenstand){
	    	VerwendbarerGegenstand g = (VerwendbarerGegenstand)gegenstand;
	    	if(kaempft && g.istEinsetzbarKampf() && spieler.getInventar().containsGegenstand(g)){
	    		// Es wird alles in der Kampf Methode geregelt.
	    		kampfEffekte.add(new KampfEffekt(g.getEffekt()));
	    		spieler.getInventar().removeGegenstand(g, 1);
	    		eingesetzt = true;
	    	}else if(g.istEinsetzbarAusserhalb() && spieler.getInventar().containsGegenstand(g)){
	    		switch(g.getEffekt().getTyp()){
	    			case(Effekt.HEILEN): int lp = g.getEffekt().getBonus(spieler.getMaxLp()); spieler.heileLp(lp); ausgabe.println("Du verwendest " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " und stellst " + lp + " LP wieder her."); break;
	    			case(Effekt.MPREGENERATION): int mp = g.getEffekt().getBonus(spieler.getMaxMp()); spieler.heileMp(mp); ausgabe.println("Du verwendest " + g.getNumGen().getBest(3).toLowerCase() + g.getName() + " und stellst " + mp + " MP wieder her."); break;
	    		}
	    		spieler.getInventar().removeGegenstand(g, 1);
	    		eingesetzt = true;
	    	}
	    }
	    if(spielerPosition.fuehreAktionAus(gegenstand)) return;
	    if(!eingesetzt){
	    	ausgabe.println("Du kannst diesen Gegenstand hier nicht verwenden.");
	    }
	}
	  
	/**
	 *  Diese Methode ruestet dem Spieler einen Gegenstand aus.
	 *  nameAusruestung: der Name des Gegenstands.
	 */
	public void ausruesten(String nameAusruestung){
		Gegenstand g = Gegenstand.getGegenstand(nameAusruestung);
	    if(g == null) return;
	    if(spieler.ruesteAus(g)){
	    	switch(r.nextInt(2)){
	    		case(0): ausgabe.println("Du hast " + g.getNumGen().getBest(0) + g.getName() + " ausgerüstet."); break;
	    		case(1): ausgabe.println("Du hast den Gegenstand ausgerüstet."); break;
	    	}
	    }else{
	    	switch(r.nextInt(3)){
	    		case(0): ausgabe.println("Du besitzt diesen Gegenstand nicht."); break;
	    		case(1): ausgabe.println(nameAusruestung + " befindet sich nicht in deinem Inventar."); break;
	    		case(2): ausgabe.println(nameAusruestung + " kann nicht ausgerüstet werden."); break;
	    	}
	    }
	}

	/**
	 *  Legt einen Gegenstand, den der Spieler ausgeruestet hat ab.
	 *  befehl: der abzulegende Gegenstand.
	 */
	public void ablegen(String nameAusruestung){
		Gegenstand g = Gegenstand.getGegenstand(nameAusruestung);
	    if(g == null) return;
	    if(spieler.legeAb(g)){
	    	switch(r.nextInt(3)){
	    		case(0): ausgabe.println("Du legst den Gegenstand ab."); break;
	    		case(1): ausgabe.println("Du hast " + g.getName() + " abgelegt."); break;
	    		case(2): ausgabe.println("Du hast " + g.getName() + " zurück in dein Inventar gelegt."); break;
	    	}
	    }else{
	    	switch(r.nextInt(3)){
	        	case(0): ausgabe.println("Du hast diesen Gegenstand nicht ausgerüstet"); break;
	        	case(1): ausgabe.println("Du kannst einen Gegenstand, den du nicht ausgerüstet hast, nicht ablegen."); break;
	        	case(2): ausgabe.println(g.getName() + " kann nicht abgelegt werden, da du " + g.getNumGen().getPers(3) + "nicht ausgerüstet hast."); break;
	    	}
	    }
	}
	  
	/**
	 *  Diese Methode sucht an der aktuellen Spielerposition nach Custom Commands.
	 *  befehl: der Befehl, nach dem gesucht wird.
	 */
	public boolean sucheKommando(String befehl){
	    return spielerPosition.kommandoEingegeben(befehl);
	}
	/**
	 *  Ueberprueft, ob es sich bei diesem Befehl um ein globales Kommando handelt.
	 *  befehl: der zuueberpruefende Befehl.
	 */
	public boolean ueberpruefeKommandos(String befehl){
	    for(Kommando k: globaleKommandos.toArray(new Kommando[0])){
	    	if(k.getBefehl().equalsIgnoreCase(befehl)){
	    		k.getBedingung().pruefen();
	    		return true;
	    	}
	    }
	    return false;
	}
	  
	/**
	 *  Ueberprueft den Ort nach einem Behaelter.
	 *  eingabe: die Eingabe des Spielers.
	 */
	public void sucheBehaelter(String eingabe){
		int typ = -1;
	    if(eingabe.equalsIgnoreCase("Fass")) typ = Behaelter.FASS;
	    if(eingabe.equalsIgnoreCase("Kiste")) typ = Behaelter.KISTE;
	    if(eingabe.equalsIgnoreCase("Truhe")) typ = Behaelter.TRUHE;
	    if(typ > -1){
	    	Stapel[] gegenstaende = spielerPosition.getBehaelterInhalt((byte)typ);
	    	if(gegenstaende != null){
	    		if(gegenstaende.length > 0){
	    			switch(typ){
	    				case(Behaelter.FASS): ausgabe.println("Du öffnest das Fass und suchst darin nach Gegenständen. Du findest "); break;
	    				case(Behaelter.KISTE): ausgabe.println("Du öffnest die Kiste und suchst darin nach Gegenständen. Du findest "); break;
	    				case(Behaelter.TRUHE): ausgabe.println("Du öffnest den Deckel der Truhe und suchst darin nach Gegenständen, du findest in der Truhe "); break;
	    			}
	    			for(Stapel s: gegenstaende){
	    				ausgabe.print(s.getAnzahl() + " x " + s.getName() + ", ");
	    				spieler.getInventar().addGegenstand(s);
	    			}
	    			ausgabe.print("du nimmst die Gegenstände an dich.");
	    		}else{
	    			switch(typ){
	    				case(Behaelter.FASS): ausgabe.println("Du öffnest das Fass und suchst darin nach Gegenständen, aber das innere des Fass ist leer."); break;
	    				case(Behaelter.KISTE): ausgabe.println("Du öffnest die Kiste und suchst darin nach Gegenständen, aber in der Kiste befindet sich nichts."); break;
	    				case(Behaelter.TRUHE): ausgabe.println("Du öffnest den Deckel der Truhe und suchst darin nach Gegenständen, aber die Truhe scheint schon seit langem leer zu sein."); break;
	    			}
	    		}
	    		return;
	    	}
	    	switch(r.nextInt(4)){
	        	case(0): ausgabe.println("Es gibt an diesem Ort nicht so etwas."); break;
	        	case(1): ausgabe.println("Hier befindet sich so etwas nicht."); break;
	        	case(2): ausgabe.println("Es lohnt sich nicht an diesem Ort irgendetwas zu suchen."); break;
	        	case(3): ausgabe.println("An diesem Ort befindet sich nichts von Interesse."); break;
	    	}
	    }
	}
}