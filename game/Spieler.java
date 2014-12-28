package game;

import java.io.Serializable;
import java.util.Vector;

import util.NumerusGenus;

/**
 *  Alle relevanten Objekte fuer den Spieler werden hier gespeichert, diese Klasse repraesentiert also den Spieler.
 */
public class Spieler implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Die aktuellen Lebenspunkte des Spielers
	private int lp;
	// Die maximalen Lebenspunkte des Spielers.
	private int maxLp;
	// Die aktuellen Magiepunkte des Spielers.
	private int mp;
	// Die maximalen Magiepunkte des Spielers.
	private int maxMp;
	// Das Level des Spielers und seine XP.
	private short level;
	private int xp;
	private int nextLevel;
	  
	// Die Statuswerte des Spielers.
	// Der Angriff
	private short ang;
	transient private short tempAng;
	// Die Verteidigung
	private short def;
	transient private short tempDef;
	// Der magische Angriff
	private short magAng;
	transient private short tempMagAng;
	// Die magische Verteidigung
	private short magDef;
	transient private short tempMagDef;
	// Die Praezision
	private short prz;
	transient private short tempPrz;
	// Die Flinkheit
	private short flk;
	transient private short tempFlk;
	
	// Das Inventar des Spielers.
	private Inventar inventar;
	// Die Ausruestung des Spielers.
	private Ausruestung ausruestung;
	  
	// Die Faehigkeiten des Spielers
	private Vector<Faehigkeit> faehigkeiten;
	  
	/**
	 *  Ein neuer Spieler wird ohne ein Parameter initialisiert.
	 */
	public Spieler(){
		inventar = new Inventar();
	    ausruestung = new Ausruestung();
	    
	    faehigkeiten = new Vector<Faehigkeit>();
	    faehigkeiten.add(new Faehigkeit("Angriff", NumerusGenus.MASKULIN, "Du schlägst zu und greifst §3 an und fügst # Schaden zu.", Faehigkeit.PHYSISCH, "0", "0", new byte[0]));
	    faehigkeiten.add(new Faehigkeit("Klingensturm", NumerusGenus.MASKULIN, "Du wirbelst mit deinem Schwert herum und fügst # Schaden zu.", Faehigkeit.PHYSISCH, "100%", "2", new byte[]{Waffe.SCHWERT}));
	    faehigkeiten.add(new Faehigkeit("Todesschlag", NumerusGenus.MASKULIN, "Mit aller Macht holst du aus und zertrümmerst §3, mit deinem gewaltigen Schlag fügst du # Schaden zu!", Faehigkeit.PHYSISCH, "666%", "0", new byte[0]));
	    
	    level = 1;
	    xp = 0;
	    nextLevel = 5;
	    maxLp = 24;
	    lp = maxLp;
	    maxMp = 5;
	    mp = maxMp;
	    
	    ang = 6;
	    def = 0;
	    magAng = 3;
	    magDef = 4;
	    prz = 5;
	    flk = 4;
	    
	    resetTemp();
	}
	  
	// Alle get-Methoden
	  
	// Gibt die maximalen LP des Spielers zurueck.
	public int getMaxLp(){return maxLp;}
	// Gibt die aktuellen LP des Spielers zurueck.
	public int getLp(){return lp;}
	// Gibt die maximalen MP des Spielers zurueck.
	public int getMaxMp(){return maxMp;}
	// Gibt die aktuellen MP des Spielers zurueck.
	public int getMp(){return mp;}
	  
	// Gibt den Angriff des Spielers zurueck.
	public int getAng(){return ang;}
	// Gibt den temporaeren Angriff des Spielers zurueck.
	public int getTempAng(){return tempAng;}
	
	// Gibt die Verteidigung des Spielers zurueck.
	public int getDef(){return def;}
	// Gibt die temporaere Verteidigung des Spielers zurueck.
	public int getTempDef(){return tempDef;}
	  
	// Gibt den magischen Angriff des Spielers zurueck.
	public int getMagAng(){return magAng;}
	// Gibt den temporaeren magischen Angriff des Spielers zurueck.
	public int getTempMagAng(){return tempMagAng;}
	  
	// Gibt die magische Verteidigung des Spielers zurueck.
	public int getMagDef(){return magDef;}
	// Gibt die temporaere magische Verteidigung des Spielers zurueck.
	public int getTempMagDef(){return tempMagDef;}
	  
	// Gibt die Praezision des Spielers zurueck.
	public int getPrz(){return prz;}
	// Gibt die temporaere Praezision des Spielers zurueck.
	public int getTempPrz(){return tempPrz;}
	
	// Gibt die Flinkheit des Spielers zurueck.
	public int getFlk(){return flk;}
	// Gibt die temporaere Flinkheit des Spielers zurueck.
	public int getTempFlk(){return tempFlk;}
	  
	// Alle Temp-Add-Methoden
	// Fuegt dem temporaeren Angriff des Spielers etwas hinzu.
	public void addTempAng(int wert){
		tempAng = (short)Math.max(tempAng + wert, 1);
	}
	// Fuegt der temporaeren Verteidigung des Spielers etwas hinzu.
	public void addTempDef(int wert){
		tempDef = (short)Math.max(tempDef + wert, 1);
	}
	// Fuegt dem temporaeren magischen Angriff des Spielers etwas hinzu.
	public void addTempMagAng(int wert){
	    tempMagAng = (short)Math.max(tempMagAng + wert, 1);
	}
	// Fuegt der temporaeren magischen Verteidigung des Spielers etwas hinzu.
	public void addTempMagDef(int wert){
	    tempMagDef = (short)Math.max(tempMagDef + wert, 1);
	}
	// Fuegt der temporaeren Praezision des Spielers etwas hinzu.
	public void addTempPrz(int wert){
	    tempPrz = (short)Math.max(tempPrz + wert, 1);
	}
	// Fuegt der temporaeren Flinkheit des Spielers etwas hinzu.
	public void addTempFlk(int wert){
	    tempFlk = (short)Math.max(tempFlk + wert, 1);
	}
	  
	  
	// Diese Methode heilt die LP des Spielers.
	// * wert: die Heilungsmenge.
	public void heileLp(int wert){
	    lp = Math.min(lp + wert, maxLp);
	}
	// Diese Methode heilt die MP des Spielers.
	// * wert: die Heilungsmenge.
	public void heileMp(int wert){
	    mp = Math.min(mp + wert, maxMp);
	}
	  
	// Fuegt dem Spieler xp hinzu.
	public void addXp(int menge, SpielWelt welt){
	    for(;menge > 0; menge--){
	    	nextLevel--;
	    	xp++;
	    	if(nextLevel < 0){
	    		level++;
	    		welt.println("Dein Level erhöht sich auf " + level + ".");
	    		// LEVEL UP
	    		maxLp += level;
	    		maxMp += level;
	    		ang   += level;
	    		def   += level;
	    		magAng+= level;
	    		magDef+= level;
	    		prz   += level;
	    		flk   += level;
	    		// PROVISORIUM
	    		nextLevel = 5 * level;
	    	}
	    }
	}
	  
	  

	// Uebergibt dem Spieler sein Inventar.
	public void setInventar(Inventar inventar){
		this.inventar = inventar;
	}
	// Gibt das Inventar des Spielers zurueck.
	public Inventar getInventar(){
	    return inventar;
	}

	// Gibt die Ausruestung des Spielers zurueck.
	public Ausruestung getAusruestung(){
	    return ausruestung;
	}
	  
	// Gibt die Faehigkeiten des Spielers zurueck.
	public Faehigkeit[] getFaehigkeiten(){
	    return faehigkeiten.toArray(new Faehigkeit[0]);
	}
	  
	/**
	 *  Der Spieler reustet einen Gegenstand aus, es wird ein boolean zurueckgegeben, der mitteilt, ob es funktioniert hat.
	 *  gegenstand: der Gegenstand, welcher ausgeruestet werden soll.
	 */
	public boolean ruesteAus(Gegenstand gegenstand){
	    if(!inventar.containsGegenstand(gegenstand)) return false;
	    Gegenstand[] alt2 = new Gegenstand[1];
	    inventar.removeGegenstand(gegenstand, 1);
	    if(gegenstand instanceof Waffe){
	    	Waffe test = (Waffe)gegenstand;
	    	if(test.getHand() == Waffe.SCHWERTHAND) alt2[0] = ausruestung.tauscheSchwerthand(test);
	    	if(test.getHand() == Waffe.SCHILDHAND) alt2[0] = ausruestung.tauscheSchildhand(test);
	    	if(test.getHand() == Waffe.ZWEIHAENDIG) alt2 = ausruestung.tauscheZweihaendig(test);
	    }else if(gegenstand instanceof Ruestung){
	    	alt2[0] = ausruestung.tauscheRuestung((Ruestung)gegenstand);
	    }else{
	    	return false;
	    }
	    for(Gegenstand g: alt2){
	    	if(g != null){
	    		inventar.addGegenstand(g, 1);
	    		maxLp -= g.getLp();
	    		if(lp > maxLp) lp = maxLp;
	    		maxMp -= g.getMp();
	    		if(mp > maxMp) mp = maxMp;
	    		ang -= g.getAng();
	    		def -= g.getDef();
	    		magAng -= g.getMagAng();
	    		magDef -= g.getMagDef();
	    		prz -= g.getPrz();
	    		flk -= g.getFlk();
	    	}	
	    }
	    maxLp += gegenstand.getLp();
	    maxMp += gegenstand.getMp();
	    ang += gegenstand.getAng();
	    def += gegenstand.getDef();
	    magAng += gegenstand.getMagAng();
	    magDef += gegenstand.getMagDef();
	    prz += gegenstand.getPrz();
	    flk += gegenstand.getFlk();
	    return true;
	}
	  
	/**
	 *  Der Spieler legt den uebergebenen Gegenstand ab, wenn er ihn ausgeruestet hat.
	 *  gegenstand: der abzulegende Gegenstand.
	 */
	public boolean legeAb(Gegenstand gegenstand){
	    if(!ausruestung.istAusgeruestet(gegenstand)) return false;
	    Gegenstand[] alt2 = new Gegenstand[1];
	    if(gegenstand instanceof Waffe){
	    	Waffe test = (Waffe)gegenstand;
	    	if(test.getHand() == Waffe.SCHWERTHAND) alt2[0] = ausruestung.tauscheSchwerthand(null);
	    	if(test.getHand() == Waffe.SCHILDHAND) alt2[0] = ausruestung.tauscheSchildhand(null);
	    	if(test.getHand() == Waffe.ZWEIHAENDIG) alt2 = ausruestung.tauscheZweihaendig(null);
	    }else if(gegenstand instanceof Ruestung){
	    	alt2[0] = ausruestung.legeRuestungAb((Ruestung)gegenstand);
	    }else{
	    	return false;
	    }
	    for(Gegenstand g: alt2){
	    	inventar.addGegenstand(g, 1);
	    	maxLp -= g.getLp();
	    	if(lp > maxLp) lp = maxLp;
	    	maxMp -= g.getMp();
	    	if(mp > maxMp) mp = maxMp;
	    	ang -= g.getAng();
	    	def -= g.getDef();
	    	magAng -= g.getMagAng();
	    	magDef -= g.getMagDef();
	    	prz -= g.getPrz();
	    	flk -= g.getFlk();
	    }
	    return true;
	}
	  
	/**
	 *  Diese Methode gibt die Waffenarten zurueck, beider Waffen des Spielers.
	 */
	public byte[] getWaffenarten(){
	    byte[] b = new byte[2];
	    if(ausruestung.getHaupthand() != null) b[0] = ausruestung.getHaupthand().getWaffenart();
	    else b[0] = -2;
	    if(ausruestung.getSchildhand() != null) b[1] = ausruestung.getSchildhand().getWaffenart();
	    else b[1] = -2;
	    return b;
	}
	  
	/**
	 *  Es werden die aktuellen Statuswerte des Spieler ausgegeben.
	 *  welt: die SpielWelt, in der ausgegeben wird.
	 */
	public void zeigeStatusAn(SpielWelt welt){
	    welt.println("Statuswerte:");
	    welt.println("Leben " + lp + "/" + maxLp);
	    welt.println("Magie " + mp + "/" + maxMp);
	    welt.println("Level: " + level);
	    welt.println("XP: " + nextLevel + " zum nächsten Level, insgesamt " + xp);
	    welt.println();
	    welt.println("Angriff: " + ang);
	    welt.println("Verteidigung: " + def);
	    welt.println("Magischer Angriff: " + magAng);
	    welt.println("Magische Verteidigung " + magDef);
	    welt.println("Präzision: " + prz);
	    welt.println("Flinkheit: " + flk);
	}
	  
	/**
	 *  Diese Methode setzt alle temporaere Werte zurueck.
	 */
	public void resetTemp(){
	    tempAng = ang;
	    tempDef = def;
	    tempMagAng = magAng;
	    tempMagDef = magDef;
	    tempPrz = prz;
	    tempFlk = flk;
	}
}