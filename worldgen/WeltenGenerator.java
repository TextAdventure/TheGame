package worldgen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import game.*;
import game.battle.Kampf;
import game.entity.*;
import game.items.*;
import game.logic.aktion.*;
import game.logic.bedingung.*;
import game.logic.ereignis.*;
import util.Farbe;
import util.NumerusGenus;

/**
 *  Diese Klasse erstellt ein neues Spiel und speichert es in einer Datei.
 */
public final class WeltenGenerator {
	// Der Name der Datei, in der der Spielstand gespeichert wird.
	public static String dateiName = "spielstand.dat";
	  
	/**
	 *  Wenn dieses Programm ausgefuehrt wird, erzeugt es eine neue Welt in der angegebenen Datei.
	 */
	public static void main(String[] args) {
		
		Attribut staerke = new Attribut("Stärke", "stärke");
		Attribut gewand = new Attribut("Gewandheit", "gewandheit");
		new Attribut("Intelligenz", "intelligenz");
		Attribut resistenz = new Attribut("Resistenz", "resistenz");
		Attribut lp = new Attribut("Leben", "leben");
		
		Ressource leben = new Ressource("Lebensbalken", "lebensbalken");
		leben.addModifikator(lp, 2.0f, Ressource.MAXMENGE);
		leben.addModifikator(lp, 0.05f, Ressource.REGENERATION);
		leben.addModifikator(staerke, 0.1f, Ressource.MAXMENGE);
		leben.addModifikator(staerke, 0.01f, Ressource.REGENERATION);
		Ressource init = new Ressource("Initiative", "initiative");
		init.addModifikator(gewand, 0.5f, Ressource.MAXMENGE);
		
		Waffenart flammen = new Waffenart("Flammenwerfer", "flammenwerfer");
		Waffenart napalm = new Waffenart("Napalmwerfer",  "napalmwerfer");
		
		Schadensart physisch = new Schadensart("Physischer Schaden", staerke);
		Schadensart feuerS = new Schadensart("Feuerschaden", staerke);
		
		new Resistenz("Physische Resistenz", "physicheResistenz", physisch, gewand);
		new Resistenz("Feuerresistenz", "feuerResistenz", feuerS, resistenz);
		
		Faehigkeit schlagen = new Faehigkeit("Schlagen", NumerusGenus.NEUTRUM, "&0 wird von dir geschlagen und erleidet # Schaden.",
				physisch, "0", "0", new Waffenart[0]);
		Faehigkeit feuerstoss = new Faehigkeit("Feuerstoß", NumerusGenus.MASKULIN,
				"Du hüllst &3 in Flammen ein und verursachst # Schaden!", feuerS, "10%", "0", flammen);
		
		/* --- SpielWelt --- */
		SpielWelt welt = new SpielWelt();
		/* --- SpielWelt --- */
		
		Level start = new Level(0, 14, 9, 5, 1, 8);
		Level ziel = new Level(250, 100, 82, 49, 31, 63);
		
		welt.getSpieler().setLevels(Level.createLinearLevels(10, start, ziel));
		
		welt.getSpieler().addFaehigkeit(schlagen);
		welt.getSpieler().addFaehigkeit(feuerstoss);
		
		new Farbe("heiß", 220, 100, 50);
		new Farbe("brennend", 200, 30, 25);
		
		Gegenstand hallo = new Gegenstand(new String[] { "Hallo" }, "Hallos", NumerusGenus.NEUTRUM, "Ein freundliches Hallo.");
		Waffe flammenwerfer = new Waffe(new String[] { "<c=heiß>Flammenwerfer</c>" }, "<c=heiß>Flammenwerfer</c>", NumerusGenus.MASKULIN,
				"Er ist ziemlich <c=heiß>heiß</c>.\n+<p=stärke> Stärke", Waffe.ZWEIHAENDIG, flammen, 17, 0, 0, 0, 2);
		Waffe napalmwerfer = new Waffe(new String[] { "<c=brennend>Napalmwerfer</c>" }, "<c=brennend>Napalmwerfer</c>", NumerusGenus.MASKULIN,
				"Er ist mehr als nur <c=heiß>heiß</c>, er ist schon <c=brennend>brennend heiß</c>!", Waffe.SCHWERTHAND, napalm, 38, 6, 2);
		Accessoire schleimRing = new Accessoire(new String[] { "Schleimring" }, "Schleimringe", NumerusGenus.MASKULIN, "Er sieht aus wie ein kleiner "
				+ "Schleim und er lacht auch genauso!", Accessoire.RING, 8, 2, 0, 4, 1);
		schleimRing.addResistenz(feuerS, 5);
		Ruestung drachenHelm = new Ruestung(new String[] { "<c=brennend>Drachenhelm</c>" }, "<c=brennend>Drachenhelme</c>", NumerusGenus.MASKULIN,
				"Dieser Helm wurde aus Drachenschuppen gefertigt und reduziert physischen Schaden um <p=physischeResistenz>%!", Ruestung.HELM, 0, 12, 0, 18, 3);
		
		new Kombination(1000, new Stapel(hallo, 1), new Stapel(hallo, 1), new Stapel(drachenHelm, 1));
		
		
		Gegner schleim = new Gegner("Schleim", NumerusGenus.MASKULIN, "Ein munterer kleiner Schleim.", 5, 12, 11, 2, 7, 4);
		Faehigkeit huepfen = new Faehigkeit("Hüpfen", NumerusGenus.NEUTRUM, "§0 hüpft auf dich und verursacht # Schaden.", physisch, "0", "0", new Waffenart[0]);
		schleim.addFaehigkeit(huepfen, 95);
		Faehigkeit sprungangriff = new Faehigkeit("Sprungangriff", NumerusGenus.MASKULIN, "§0 springt hoch und stürzt sich auf dich! Du erleidest # Schaden.",
				physisch, "100%", "0", new Waffenart[0]);
		schleim.addFaehigkeit(sprungangriff, 5);
		schleim.addDrop(80, (Stapel) null);
		schleim.addDrop(20, new Stapel(schleimRing, 1));
		
		Gegner drache = new Gegner("Drache", NumerusGenus.MASKULIN, "Ein feuerspuckendes Ungetüm!", 25, 27, 31, 11, 72, 14);
		Faehigkeit klaue = new Faehigkeit("Klaue", NumerusGenus.FEMININ, "Die Klaue §1 trifft dich und verursacht # Schaden!", physisch, "15%", "0", new Waffenart[0]);
		drache.addFaehigkeit(klaue, 7);
		drache.addFaehigkeit(sprungangriff, 1);
		drache.addDrop(10, (Stapel) null);
		drache.addDrop(3, new Stapel(drachenHelm, 1));
		
		Ort ort1 = new Ort("<c=heiß>Ort 1</c>", "Das ist ein <c=heiß>heißer</c> Ort.");
		ort1.addGegenstand(hallo, 2);
		ort1.addGegenstand(flammenwerfer, 1);
		Ort ort2 = new Ort("Ort 2", "Das ist der 2. Ort.");
		Ort ort3 = new Ort("Der Ort", "Ein ganz besonderer Ort. Hier ist eine <c=brennend>Hitze</c>").setWahrscheinlichkeitFuerKampf(90.0);
		UntersuchbaresObjekt obj = new UntersuchbaresObjekt("<c=heiß>Hitze</c>", "Sie ist <c=heiß>heiß</c>.");
		ort3.addUntersuchbaresObjekt(obj);
		ort3.addKampf(new Kampf(1, drache));
		Ort ort4 = new Ort("Ein weiterer Ort", "Ein Ort").setWahrscheinlichkeitFuerKampf(70.0);
		ort4.addKampf(new Kampf(1, schleim, schleim, schleim));
		ort4.addKampf(new Kampf(4, schleim, schleim));
		ort4.addKampf(new Kampf(2, schleim));
		
		ort1.addAusgang(Ausgang.NORDEN, ort2);
		ort2.addAusgang(Ausgang.SUEDEN, ort1);
		
		ort2.addAusgang(Ausgang.NORDEN, ort3);
		ort3.addAusgang(Ausgang.SUEDEN, ort2);
		
		ort3.addAusgang(Ausgang.NORDEN, ort4);
		ort4.addAusgang(Ausgang.SUEDEN, ort3);
		
		ort4.addAusgang(Ausgang.NORDEN, ort1);
		ort1.addAusgang(Ausgang.SUEDEN, ort4);
		
		new OrtBetretenEreignis(ort4, -1, new SpielerHatGegenstandBedingung(hallo, 1), new SpielerAddGegenstandAktion(hallo, 1)
								, new AusgabeAktion("Dein Hallo hat ein weiteres angezogen und es wandert direkt in dein Inventar!"));
		
		new UntersuchungsEreignis(obj, 1, new SpielerHatAusgeruestetBedingung(flammenwerfer), new LegeAbAktion(flammenwerfer)
								, new SpielerAddGegenstandAktion(napalmwerfer, 1), new RuesteAusAktion(napalmwerfer)
								, new AusgabeAktion("\nAn diesem besonderen Ort liegt ein <c=brennend>Napalmwerfer</c> auf dem Boden! "
								+ "Du nimmst ihn besser mal mit."));
		
		welt.setAktuellePositon(ort1);
		
		welt.speichereListen();
	    
	    /**
	     *  Das Programm schreibt die SpielWelt in die angegebene Datei.
	     */
	    try {
	    	FileOutputStream fos = new FileOutputStream(dateiName);
	    	// Der OutputStream kann nun Objekte schreiben.
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    	// Die SpielWelt wird in die Datei gespeichert.
	    	oos.writeObject(welt);
	    	// Der OutputStream wird geschlossen.
	    	oos.close();
	      
	    	// Meldung, das die SpielWelt erstellt wurde.
	    	System.out.println("Eine neue Spielwelt wurde in " + dateiName + " erstellt.");
	    } catch(IOException e) {
	    	e.printStackTrace();
	    	System.err.println("ACHTUNG: Es konnte keine neue Spielwelt erstellt werden.");
	    }
	}	
}