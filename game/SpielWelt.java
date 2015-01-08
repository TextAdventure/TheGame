package game;

import game.battle.Kampf;
import game.battle.KampfAktion;
import game.battle.KampfEffekt;
import game.entity.Attribut;
import game.entity.Effekt;
import game.entity.Entity;
import game.entity.Faehigkeit;
import game.entity.Gegner;
import game.entity.Resistenz;
import game.entity.Schadensart;
import game.entity.Spieler;
import game.items.AusruestbarerGegenstand;
import game.items.Gegenstand;
import game.items.Inventar;
import game.items.Kombination;
import game.items.Stapel;
import game.items.VerwendbarerGegenstand;
import game.items.Waehrung;
import game.logic.ereignis.Ereignis;
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
import util.StringListener;

/**
 * In der SpielWelt findet das gesamte Spielgeschehen statt, in dieser Klasse werden ausserdem alle wichtigen Informationen gespeichert.
 * @author Marvin
 */
public class SpielWelt implements Serializable, StringListener {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	//--------------------------------------------------------------------------------------------//
	// DIE SPIELWELT ALS STATISCHE VARIABLE, DIE IMMER AUF DAS EINZIGE SPIELWELT-OBJEKT VERWEIST! //
	transient public static SpielWelt WELT;

	/**
	 * Legt das aktuelle SpielWelt Objekt fest, wird immer im Konstruktor aufgerufen.
	 * @param neueWelt Die aktuelle SpielWelt.
	 */
	private static void setWelt(SpielWelt neueWelt) {
		WELT = neueWelt;
	}
	//--------------------------------------------------------------------------------------------//

	/* --- Variablen --- */

	// Dieses Variabeln werden NICHT gespeichert
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

	// Diese Variablen werden gespeichert
	// Der Spieler
	private Spieler spieler;
	// Die Position des Spielers in der Welt.
	private Ort spielerPosition;
	// Alle global geltenden Kommandos.
	private Vector<Kommando> globaleKommandos;
	// Alle Gegenstaende im Spiel(wird benoetigt, um die Gegenstandsliste zu speichern und laden)
	public Gegenstand[] gegenstaende;
	// Alle Kombinationen im Spiel(wird benoetigt, um die Kombinationen zu speichern und zu laden)
	private ArrayList<Vector<Kombination>> kombinationen;
	// Alle Ereignisse im Spiel(wird benoetigt, um die Ereignisse zu speichern und zu laden)
	private Ereignis[] ereignisse;
	// Alle Attribute im Spiel(wird benoetigt, um die Attribute zu speichern und zu laden)
	private Attribut[] attribute;
	// Alle Schadensarten im Spiel(wird benoetigt, um die Schadensarten zu speichern und zu laden)
	private Schadensart[] schadensarten;
	// Alle Resistenzen im Spiel(wird benoetigt, um die Resistenzen zu speichern und zu laden)
	private Resistenz[] resistenzen;
	// Alle Farben im Spiel(wird benoetigt, um die Farben zu speichern und zu laden)
	private Vector<Farbe> farben;

	// Das Random-Objekt, das alle Zufallszahlen ausgibt.
	public Random r;

	/* --- Konstruktor --- */
	
	/**
	 * Eine neue SpielWelt braucht keine Parameter, sie initialisiert nur alle Variabeln und laedt alle Listen.
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

	    setWelt(this);
	}

	/* --- Methoden --- */

	/**
	 * Gibt den String auf der Anzeige aus.
	 * @param text Der auszugebenede Text.
	 */
	public void print(String text) {
	    ausgabe.print(text);
	}

	/**
	 * Gibt den String auf der Anzeige aus mit einem Zeilenumbruch.
	 * @param text Der auszugebenede Text mit einem Zeilenumbruch davor.
	 */
	public void println(String text) {
	    ausgabe.println(text);
	}

	/**
	 * Erzeugt einen Zeilenumbruch in der Ausgabe.
	 */
	public void println() {
	    ausgabe.println();
	}

	/**
	 * Aktualisiert die Listen aller Dinge im Spiel, sie wird nach dem Laden des Spielstands aufgerufen.
	 */
	public void updateListen() {
		setWelt(this);

	    Gegenstand.setAlleGegenstaende(this.gegenstaende);
	    Kombination.setAlleKombinationen(this.kombinationen);
	    Ereignis.setAlleEreignisse(ereignisse);
	    Attribut.ATTRIBUTE = this.attribute;
	    Schadensart.SCHADEN = this.schadensarten;
	    Resistenz.RESISTENZEN = this.resistenzen;
	    Farbe.setAlleFarben(this.farben);
	}
	
	/**
	 * Speichert die Listen aller Dinge im Spiel, sodass sie beim naechsten Laden wieder verfuegbar sind.
	 */
	public void speichereListen() {
		this.gegenstaende = Gegenstand.getAlleGegenstaende();
	    this.kombinationen = Kombination.getAlleKombinationen();
	    this.ereignisse = Ereignis.getAlleEreignisse();
	    this.attribute = Attribut.ATTRIBUTE;
	    this.schadensarten = Schadensart.SCHADEN;
	    this.resistenzen = Resistenz.RESISTENZEN;
	    this.farben = Farbe.getAlleFarben();
	}
	
	/**
	 * Legt die GUI fest, die diese SpielWelt ausgibt und aktualisiert die Referenzen auf die Anzeige und MiniMap.
	 * @param gui Die aktuelle GUI.
	 */
	public void setGUI(GUI gui) {
	    this.ausgabe = gui.getAnzeige();
	    this.map = gui.getMap();
	    this.map.updateMiniMap(spielerPosition);

	    this.getInventar().addListener(gui.getInventarAnzeige());
	    this.getInventar().addListener(gui.getEingabefeld());
	    this.spieler.addListener(gui.getFaehigkeitenAnzeige());
	    this.spieler.addListener(gui.getEingabefeld());
	    this.spieler.addListener(gui.getAusruestungsAnzeige());
	    this.spieler.notifyListeners(spieler.getFaehigkeiten());

	    this.kampfEffekte = new Vector<KampfEffekt>();
	}

	/**
	 * Gibt die aktuelle Position des Spielers zurueck.
	 * @return Den Ort, an dem sich der Spieler befindet.
	 */
	public Ort getAktuellePosition() {
		return spielerPosition;
	}
	
	/**
	 * Gibt das Inventar des Spielers zurueck.
	 * @return Das Spielerinventar.
	 */
	public Inventar getInventar() {
	    return spieler.getInventar();
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
	 * Der StringListener erhaelt einen String fuer einen Kampf oder ein Gespraech und gibt sie an diese Methode weiter.
	 * @param evt Der empfangene String.
	 */
	@Override
	public void actionPerformed(String evt) {
		if(spielerKaempft())
			kampf(evt);
		if(spielerSpricht())
			gespraech(evt);
	}

	/**
	 * Gibt zurueck, ob der Spieler kaempft.
	 * @return True, falls er kaempft, ansonsten false.
	 */
	public boolean spielerKaempft() {
	    return kaempft;
	}

	/**
	 * Startet den uebergebenen Kampf.
	 * @param kampf Der Kampf, der gestartet werden soll.
	 */
	private void initKampf(Kampf kampf) {
		this.kampf = kampf;
	    spieler.resetTemp();
	    kaempft = true;
	    loot = new Inventar();
	    
	    ausgabe.clear();
	    ausgabe.print("Eine Gruppe von Feinden greift dich an!");
	    for(Entity e : kampf.getKampfGegner())
	    	ausgabe.println(e.getNumGen().getUnbest(NumerusGenus.NOMINATIV) + e.getName() + " nähert sich.");
	    ausgabe.println();
	}
	
	/**
	 * Wenn der Spieler sich im Kampf befindet wird anstelle der normalen Abfrage die Eingabe
	 * direkt an diese Methode uebergeben, die sich nur auf den Kampf konzentriert.
	 * @param evt Die Eingabe des Spielers als String.
	 */
	private void kampf(String evt) {
		// TODO neue Idee
		Vector<KampfAktion> aktionen = new Vector<KampfAktion>();

		Faehigkeit spielerF = null;
		String str = "";
		for(String s : evt.split(" ")) {
			str += s + " ";
			if(spieler.getFaehigkeit(str.trim()) != null) {
				spielerF = spieler.getFaehigkeit(str.trim());
				break;
			}
		}

		Gegner ziel = null;
		try {
			ziel = (Gegner) kampf.getGegner(evt.substring(str.length()).trim());
		} catch(StringIndexOutOfBoundsException e) {
			ziel = null;
		}

		KampfAktion spielerAktion;
		if(Gegenstand.getGegenstand(evt) instanceof VerwendbarerGegenstand)
			spielerAktion = new KampfAktion(spieler, (VerwendbarerGegenstand) Gegenstand.getGegenstand(evt), spieler);
		else
			spielerAktion = new KampfAktion(spieler, spielerF, ziel);

		// Ueberprueft, ob der Spieler Mist bei der Eingabe gebaut hat.
		if(!spielerAktion.isGueltigeAktion()) {
			ausgabe.println("Das ist keine gültige Aktion!\n");
			return;
		} else
			aktionen.add(spielerAktion);
		// Die Gegner adden
		for(Entity g : kampf.getKampfGegner())
			if(g.getLp() > 0)
				aktionen.add(new KampfAktion(g, g.getFaehigkeit(evt), spieler));

		Collections.sort(aktionen);

		for(KampfAktion aktion : aktionen.toArray(new KampfAktion[0])) {
			if(aktion.isGueltigeAktion()) aktion.fuehreAktionAus();
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
				ausgabe.println(aktion.getZiel().getNumGen().getBest(NumerusGenus.NOMINATIV) + aktion.getZiel().getName() + " ist gestorben.");
				if(kampf.alleTot()) {
					kampfEndet(true);
					return;
				}
			}
		}
		
	    // Alle KampfEffekte werden ueberprueft.
	    for(KampfEffekt ke : kampfEffekte.toArray(new KampfEffekt[0])) {
	    	switch(ke.getTyp()) {
	    		case(Effekt.HEILEN):
	    			int lp = ke.getBonusLpMp(spieler.getMaxLp());
	    			if(lp == 0)
	    				kampfEffekte.remove(ke);
	    			else
	    				ausgabe.println("Du stellst " + lp + " LP wieder her.");
	    			spieler.addLp(lp);
	    		break;
	    			
	    		case(Effekt.MPREGENERATION):
	    			int mp = ke.getBonusLpMp(spieler.getMaxMp());
	    			if(mp == 0)
	    				kampfEffekte.remove(ke);
	    			else
	    				ausgabe.println("Du stellst " + mp + " MP wieder her.");
	    			spieler.addMp(mp);
	    		break;
	    	}
	    }
	    ausgabe.println();
	}
	
	/**
	 * Mit dieser Methode wird dem Spiel gesagt, dass der Kampf endet.
	 * @param sieg True bedeutet Sieg, false Niederlage.
	 */
	private void kampfEndet(boolean sieg) {
		kaempft = false;
	    spieler.resetTemp();
	    kampfEffekte = new Vector<KampfEffekt>();
	    ausgabe.println();
	    if(sieg) {
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

	/* --- Gesprächs-Methoden --- */

	/**
	 * Gibt zurueck, ob sich der Spieler im Gespraech mit einem NPC befindet.
	 * @return True, wenn er sich im Gespraech befindet, ansonsten false.
	 */
	public boolean spielerSpricht() {
		return spricht;
	}

	/**
	 * Startet ein Gespraech.
	 * @param npc Der neue Gespraechspartner.
	 */
	public void initGespraech(NPC npc) {
		gespraechspartner = npc;
		spricht = true;
		ausgabe.println();
	}

	/**
	 * Wenn der Spieler sich im Gespraech befindet wird anstelle der normalen Abfrage die Eingabe
	 * direkt an diese Methode uebergeben, die sich nur auf das Gespraech konzentriert.
	 * @param evt Die Eingabe des Spielers als String.
	 */
	private void gespraech(String evt) {
		if(!gespraechspartner.ansprechen(evt))
			spricht = false;
	}


	// ===================================================== //
	// ALLE METHODEN, DIE VON INTERPRETER AUFGERUFEN WERDEN. //
	// ===================================================== //

	/**
	 * Aendert die aktuelle Position des Spielers, dabei wird geprueft, ob ein Kampf stattfindet und aktualisiert die MiniMap.
	 * @param zielort Der Ort an den sich der Spieler bewegt.
	 */
	public void setAktuellePositon(Ort zielort) {		
		spielerPosition = zielort;
		if(map != null)
			map.updateMiniMap(spielerPosition);
		Kampf k = spielerPosition.findetKampfStatt();
		if(k != null)
			this.initKampf(k);
	}

	/**
	 * Gibt alle Informationen ueber die Spielerposition auf der Anzeige aus.
	 * @param ereignisse Mit true werden alle Ereignisse beruecksichtigt, mit
	 * false werden sie ignoriert und es wird nur der Text ausgegeben.
	 */
	public void spielerPositionAnzeigen(boolean ereignisse) {
		// Zuerst wird die Anzeige gereinigt.
		ausgabe.clear();

		ausgabe.printPrintable(spielerPosition);
		ausgabe.println();
		
		if(!spielerPosition.getAusgaenge().isEmpty()) {
			// Alle verfuegbaren Ausgaenge werden angezeigt.
			ausgabe.println("Verfügbare Ausgänge:");
			
			// Es wird eine Kopie des Vectors aller Ausgaenge angefordert.
			Vector<Ausgang> aktuelleAusgaenge = spielerPosition.getAusgaenge();
			
			// Es werden die Ausgaenge ueberprueft und ausgegeben.
			for(Ausgang a : aktuelleAusgaenge) {
				// Der Ausgang wird ausgegeben.
				ausgabe.println(a.getRichtungsName() + "(" + a.getAbkuerzung() + ")");
				
				if(a.getZielort().isBesucht())
					ausgabe.print(" - " + a.getZielort().getNameExtended());
				else
					ausgabe.print(" - ???");
			}
			
			ausgabe.println("\n");
			
			// Die OrtBetretenEreignisse werden geprueft.
			if(ereignisse)
				Ereignis.ortBetreten(spielerPosition);
		}
	}

	/**
	 * Ueberprueft, ob es ein entsprechendes UntersuchbaresObjekt am aktuellen Ort gibt und gibt das Objekt aus.
	 * @param objektName Der Name des Objekts.
	 */
	public void sucheUntersuchbaresObjekt(String objektName) {
		UntersuchbaresObjekt o = spielerPosition.getUntersuchbaresObjekt(objektName);
	    if(o != null) {

	    	ausgabe.printPrintable(o);
	    	
	    	Ereignis.untersuchung(o);
	    	
	    } else {
	    	switch(r.nextInt(8)) {
	    		case(0): ausgabe.print("An " + objektName + " ist nichts auffällig."); break;
	    		case(1): ausgabe.print(objektName + " ist nicht interessant."); break;
	    		case(2): ausgabe.print("Es gibt interssantere Dinge als " + objektName); break;
	    		case(3): ausgabe.print(objektName + " ist nicht wichtig."); break;
	    		case(4): ausgabe.print("Es gibt hier auffäligere Dinge als " + objektName); break;
	    		case(5): ausgabe.print("Was soll an " + objektName + " interessant sein?"); break;
	    		case(6): ausgabe.print("Es bringt nichts " + objektName + " zu untersuchen."); break;
	    		case(7): ausgabe.print(objektName + " ist nicht von Bedeutung."); break;
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
	    	Gegenstand g = s.getGegenstand(); // getNameExtended(), damit Farben im Namen angezeigt werden!
	    	switch(r.nextInt(6)) {
	    		case(0): ausgabe.print("Du hast " + g.getNumGen().getBest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " eingesteckt."); break;
	    		case(1): ausgabe.print("Du steckst " + g.getNumGen().getBest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " ein."); break;
	    		case(2): ausgabe.print("Du nimmst " + g.getNumGen().getBest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " mit."); break;
	    		case(3): ausgabe.print(g.getNumGen().getBest(NumerusGenus.NOMINATIV) + g.getNameExtended() + " könnte sich als nützlich erweisen, deshalb steckst du " + g.getNumGen().getPers(NumerusGenus.AKKUSATIV).toLowerCase() + "ein."); break;
	    		case(4): ausgabe.print("Du nimmst " + g.getNumGen().getBest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " an dich, da " + g.getNumGen().getPers(NumerusGenus.NOMINATIV).toLowerCase() + "nützlich aussieht."); break;
	    		case(5): ausgabe.print("Du steckst " + g.getNumGen().getBest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " in deine Tasche."); break;
	    	}
	    	spieler.getInventar().addGegenstand(s);
	    	spielerPosition.removeGegenstand(g);
	    	return;
	    } else if (rp != null) {
	    	Stapel[] loot = rp.ernte();
	    	if(loot == null) {
	    		ausgabe.print("Du kannst hier im Moment nichts mehr ernten, komm später vorbei.");
	    		return;
	    	}
	    	if(loot.length == 0) {
	    		ausgabe.print("Du konntest nichts ernten.");
	    		return;
	    	}
	    	ausgabe.print("Du findest " + rp.getNumGen().getUnbest(NumerusGenus.AKKUSATIV).toLowerCase() + rp.getName() + " und du erntest ");
	    	for(Stapel stapel : loot) {
	    		spieler.getInventar().addGegenstand(stapel);
	    		if(stapel.getAnzahl() > 1)
	    			ausgabe.print(stapel.getAnzahl() + " " + stapel.getGegenstand().getPluralExtended() + ", ");
	    		else
	    			ausgabe.print(stapel.getGegenstand().getNameExtended() + ", ");
	    	}
	    	ausgabe.print("du nimmst die Gegenstände an dich.");

	    } else {
	    	switch(r.nextInt(5)) {
	    		case(0): ausgabe.print(gegenstandName + " kann nicht mitgenommen werden."); break;
	    		case(1): ausgabe.print("Du kannst " + gegenstandName + " nicht mitnehmen."); break;
	    		case(2): ausgabe.print(gegenstandName + " ist nicht von nutzen."); break;
	    		case(3): ausgabe.print("Was willst du mit " + gegenstandName + " anfangen?"); break;
	    		case(4): ausgabe.print("Du kannst " + gegenstandName + " nicht aufheben."); break;
	    	}
	    	return;
	    }
	}

	/**
	 * Zeigt alle ausgeruesteten Gegenstaende an.
	 */
	public void zeigeAusruestungAn() {
		// Das Ausruestungs-Objekt gibt alles selber aus, braucht aber das Anzeige-Objekt dafuer.
	    spieler.getAusruestung().zeigeAn(this.ausgabe);
	}

	/**
	 * Gibt den aktuellen Status des Spielers aus.
	 */
	public void zeigeStatusAn() {
		spieler.zeigeStatusAn(this);
	}

	/**
	 * Zeigt die Informationen zu einem Gegenstand an.
	 * @param gegenstand Der anzuzeigende Gegenstand.
	 */
	public void zeigeGegenstandInfosAn(Gegenstand gegenstand) {
	    if(gegenstand == null) {
	    	switch(r.nextInt(5)) {
	    		case(0): ausgabe.print("Das ist kein Gegenstand, den du hast."); break;
	    		case(1): ausgabe.print("Dir liegen keinerlei Informationen über diesen Gegenstand vor."); break;
	    		case(2): ausgabe.print("Darüber hast du keine Informationen."); break;
	    		case(3): ausgabe.print("Über diesen Gegenstand lässt sich nichts besonderes sagen."); break;
	    		case(4): ausgabe.print("Über diesen Gegenstand liegen dir keine Informationen vor."); break;
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
	    			ausgabe.println("Du verwendest " + g.getNumGen().getBest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " und stellst " + Math.min(spieler.getMaxLp()-spieler.getLp(), lp) + " LP wieder her.");
	    			spieler.addLp(lp);
	    			break;
	    		case(Effekt.MPREGENERATION):
	    			int mp = g.getEffekt().getBonus(spieler.getMaxMp());
	    			ausgabe.println("Du verwendest " + g.getNumGen().getBest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " und stellst " + Math.min(spieler.getMaxMp()-spieler.getMp(), mp) + " MP wieder her.");
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
	    	ausgabe.print("Du kannst diesen Gegenstand hier nicht verwenden.");
	}

	/**
	 * Ruestet dem Spieler einen Gegenstand aus.
	 * @param nameAusruestung Der Name des Gegenstands.
	 */
	public void ausruesten(String nameAusruestung) {
		Gegenstand g = Gegenstand.getGegenstand(nameAusruestung);
		if(g == null)
			return;
		
		if(g instanceof AusruestbarerGegenstand && spieler.ruesteAus((AusruestbarerGegenstand) g)) {
			switch(r.nextInt(2)) {
			case(0): ausgabe.print("Du hast " + g.getNumGen().getBest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " ausgerüstet."); break;
			case(1): ausgabe.print("Du hast den Gegenstand ausgerüstet."); break;
			}
		} else {
			switch(r.nextInt(3)){
			case(0): ausgabe.print("Du besitzt diesen Gegenstand nicht."); break;
			case(1): ausgabe.print(nameAusruestung + " befindet sich nicht in deinem Inventar."); break;
			case(2): ausgabe.print(nameAusruestung + " kann nicht ausgerüstet werden."); break;
			}
		}
	}

	/**
	 * Legt einen Gegenstand, den der Spieler ausgeruestet hat ab.
	 * @param nameAusruestung Der abzulegende Gegenstand.
	 */
	public void ablegen(String nameAusruestung) {
		Gegenstand g = Gegenstand.getGegenstand(nameAusruestung);
		if(g == null)
			return;
	    
		if(g instanceof AusruestbarerGegenstand && spieler.legeAb((AusruestbarerGegenstand) g)) {
			switch(r.nextInt(3)) {
			case(0): ausgabe.print("Du legst den Gegenstand ab."); break;
			case(1): ausgabe.print("Du hast " + g.getNameExtended() + " abgelegt."); break;
			case(2): ausgabe.print("Du hast " + g.getNameExtended() + " zurück in dein Inventar gelegt."); break;
			}
			
		} else {
			switch(r.nextInt(3)) {
			case(0): ausgabe.print("Du hast diesen Gegenstand nicht ausgerüstet"); break;
			case(1): ausgabe.print("Du kannst einen Gegenstand, den du nicht ausgerüstet hast, nicht ablegen."); break;
			case(2): ausgabe.print(g.getNameExtended() + " kann nicht abgelegt werden, da du " + g.getNumGen().getPers(NumerusGenus.AKKUSATIV).toLowerCase() + "nicht ausgerüstet hast."); break;
			}
		}
	}

	/**
	 *  Sucht an der aktuellen Spielerposition nach Custom Commands.
	 *  @param befehl Der Befehl, nach dem gesucht wird.
	 *  @return True, wenn ein Befehl gefunden wird, ansonsten false.
	 */
	public boolean sucheKommando(String befehl) {
	    return spielerPosition.kommandoEingegeben(befehl);
	}
	
	/**
	 * Ueberprueft, ob es sich bei diesem Befehl um ein globales Kommando handelt.
	 * @param befehl Der Befehl, der ueberprueft werden soll.
	 * @return True, wenn ein Befehl gefunden wird, ansonsten false.
	 */
	public boolean ueberpruefeKommandos(String befehl) {
	    for(Kommando k : globaleKommandos)
	    	if(k.istBefehl(befehl)) {
	    		k.eingetreten();
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
	    				case(Behaelter.FASS): ausgabe.print("Du öffnest das Fass und suchst darin nach Gegenständen. Du findest "); break;
	    				case(Behaelter.KISTE): ausgabe.print("Du öffnest die Kiste und suchst darin nach Gegenständen. Du findest "); break;
	    				case(Behaelter.TRUHE): ausgabe.print("Du öffnest den Deckel der Truhe und suchst darin nach Gegenständen, du findest in der Truhe "); break;
	    			}

	    			for(Stapel s: gegenstaende) {
	    				if(s.getAnzahl() > 1)
	    					ausgabe.print(s.getAnzahl() + " " + s.getGegenstand().getPluralExtended() + ", ");
	    				else
	    					ausgabe.print(s.getGegenstand().getNameExtended() + ", ");
	    				spieler.getInventar().addGegenstand(s);
	    			}
	    			ausgabe.print("du nimmst die Gegenstände an dich.");

	    		} else {
	    			switch(typ) {
	    				case(Behaelter.FASS): ausgabe.print("Du öffnest das Fass und suchst darin nach Gegenstäden, aber das innere des Fass ist leer."); break;
	    				case(Behaelter.KISTE): ausgabe.print("Du öffnest die Kiste und suchst darin nach Gegenständen, aber in der Kiste befindet sich nichts."); break;
	    				case(Behaelter.TRUHE): ausgabe.print("Du öffnest den Deckel der Truhe und suchst darin nach Gegenständen, aber die Truhe scheint schon seit langem leer zu sein."); break;
	    			}
	    		}
	    		return;
	    	}

	    	switch(r.nextInt(4)) {
	        	case(0): ausgabe.print("Es gibt an diesem Ort nicht so etwas."); break;
	        	case(1): ausgabe.print("Hier befindet sich so etwas nicht."); break;
	        	case(2): ausgabe.print("Es lohnt sich nicht an diesem Ort irgendetwas zu suchen."); break;
	        	case(3): ausgabe.print("An diesem Ort befindet sich nichts von Interesse."); break;
	    	}
	    }
	}
	
	/**
	 * Wirft einen Gegenstand weg, falls er sich im Inventar des Spielers befindet.
	 * @param eingabe Die Eingabe des Spielers, die den Gegenstand bennent, der weggeworfen werden soll.
	 */
	public void werfeWeg(String eingabe) {
		int anzahl = eingabe.contains(" ") ? Integer.valueOf(eingabe.trim().substring(0, eingabe.indexOf(" "))) : -1;
		Gegenstand g;
		if(anzahl > 1)
			g = Gegenstand.getGegenstand(eingabe.substring(eingabe.indexOf(" ")).trim());
		else
			g = Gegenstand.getGegenstand(eingabe);
		
		if(g == null) {
			ausgabe.print("Dieser Gegenstand existiert nicht.");
			return;
		}

		if(anzahl > 1 && getInventar().containsGegenstand(new Stapel(g, anzahl))) {
			spielerPosition.addGegenstand(g, anzahl);
			getInventar().removeGegenstand(g, anzahl);
			ausgabe.print("Du legst " + Integer.toString(anzahl) + " " + g.getPluralExtended() + " hier ab.");
		} else if(anzahl < 2 && getInventar().containsGegenstand(new Stapel(g, 1))){
			spielerPosition.addGegenstand(g, 1);
			getInventar().removeGegenstand(g, 1);
			ausgabe.print("Du legst " + g.getNumGen().getUnbest(NumerusGenus.AKKUSATIV).toLowerCase() + g.getNameExtended() + " hier ab.");
		} else {
			ausgabe.print("Es befindet sich dieser Gegenstand nicht in deinem Inventar.");
		}
	}

}