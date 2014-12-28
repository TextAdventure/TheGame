package game;

import java.io.Serializable;
import java.util.Vector;

import util.NumerusGenus;

/**
 *  Diese Klasse bietet die Moeglichkeit Gegner fuer das Spiel zu erstellen, die bekaempft werden koennen.
 */
public class Gegner implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	// Der Name des Gegners
	private String name;
	private NumerusGenus numGen;
	
	// Die Faehigkeiten des Gegners
	private Vector<Faehigkeit> faehigkeiten;
	  
	// Die Grundwerte des Gegners
	private int lp;
	private int maxLp;
	private int mp;
	private int maxMp;
	  
	// Die Stautswerte des Gegners
	private short angriff;
	private short verteidigung;
	private short magischerAngriff;
	private short magischeVerteidigung;
	private short praezision;
	private short flinkheit;
	  
	// Die EXP, die der Gegner gibt.
	private int xp;

	// Die Elemenatr-Resistenzen des Gegners
	/*private byte resPhysisch;
	private byte resFeuer;
	private byte resWasser;
	private byte resLuft;
	private byte resErde;
	private byte resDunkel;
	private byte resLicht; */
	  
	/**
	 *  Ein neuer Gegner wird initialisiert.
	 */
	public Gegner(String name, NumerusGenus numGen, int leben, int magie, int angriff, int verteidigung, int magischerAngriff, int magischeVerteidigung, int praezision, int flinkheit, int xp){
	    this.name = name;
	    this.numGen = numGen;
	    
	    this.maxLp = leben;
	    lp = maxLp;
	    this.maxMp = magie;
	    mp = maxMp;
	    
	    this.angriff = (short)angriff;
	    this.verteidigung = (short)verteidigung;
	    this.magischerAngriff = (short)magischerAngriff;
	    this.magischeVerteidigung = (short)magischeVerteidigung;
	    this.praezision = (short)praezision;
	    this.flinkheit = (short)flinkheit;
	    
	    this.xp = xp;
	    
	    /*this.resPhysisch = (byte)resPhysisch;
	    this.resFeuer = (byte)resFeuer;
	    this.resWasser = (byte)resWasser;
	    this.resLuft = (byte)resLuft;
	    this.resErde = (byte)resErde;
	    this.resDunkel = (byte)resDunkel;
	    this.resLicht = (byte)resLicht;*/
	    
	    faehigkeiten = new Vector<Faehigkeit>();
	}
	  
	/**
	 * Ein Gegner wird aus einem anderen erstellt, sprich kopiert, aber als neues Objekt.
	 * gegner: Der Gegener, der dupliziert werden soll.
	 */
	public Gegner(Gegner gegner){
		this.name = gegner.getName();
	    this.numGen = gegner.getNumGen();

	    this.maxLp = gegner.getMaxLp();
	    lp = maxLp;
	    this.maxMp = gegner.getMaxMp();
	    mp = maxMp;

	    this.angriff = (short)gegner.getAng();
	    this.verteidigung = (short)gegner.getDef();
	    this.magischerAngriff = (short)gegner.getMagAng();
	    this.magischeVerteidigung = (short)gegner.getMagDef();
	    this.praezision = (short)gegner.getPrz();
	    this.flinkheit = (short)gegner.getFlk();

	    this.xp = gegner.getXp();

	    /*this.resPhysisch = 0;
	    this.resFeuer = 0;
	    this.resWasser = 0;
	    this.resLuft = 0;
	    this.resErde = 0;
	    this.resDunkel = 0;
	    this.resLicht = 0;*/

	    faehigkeiten = new Vector<Faehigkeit>();
	    faehigkeiten.add(gegner.getFaehigkeit());
	}
	  
	// Alle get-Methoden
	// Gibt den Namen zurueck.
	public String getName(){return name;}
	// Gibt den NumerusGenus zurueck.
	public NumerusGenus getNumGen(){return numGen;}
	  
	// Gibt die maximalen LP zurueck.
	public int getMaxLp(){return maxLp;}
	// Gibt die aktuellen LP zurueck.
	public int getLp(){return lp;}
	// Gibt die maximalen MP zurueck.
	public int getMaxMp(){return maxMp;}
	// Gibt die aktuellen MP zurueck.
	public int getMp(){return mp;}
	  
	// Gibt den Angriff zurueck.
	public int getAng(){return angriff;}
	// Gibt die Verteidigung zurueck.
	public int getDef(){return verteidigung;}
	// Gibt den mgischen Angriff zurueck.
	public int getMagAng(){return magischerAngriff;}
	// Gibt die magische Verteidigung zurueck.
	public int getMagDef(){return magischeVerteidigung;}
	// Gibt die Praezision zurueck.
	public int getPrz(){return praezision;}
	// Gibt die Flinkheit zurueck.
	public int getFlk(){return flinkheit;}
	  
	// Gibt die XP zurueck.
	public int getXp(){return xp;}
	  
	// PROVISORIUM: Gibt den Agriff des Gegners zurueck.
	public Faehigkeit getFaehigkeit(){
	    return faehigkeiten.firstElement();
	}
	  
	// Fuegt dem Gegner eine neue Faehigkeit hinzu.
	public void addFaehigkeit(Faehigkeit faehigkeit){
	    if(!faehigkeiten.contains(faehigkeit)){
	    	faehigkeiten.add(faehigkeit);
	    }
	}
	  
	// Alle decrease-Methoden
	// Erniedrigt die maximalen LP.
	public void decreaseMaxLp(int amount){maxLp -= amount;}
	// Erniedrigt die aktuellen LP.
	public void decreaseLp(int amount){lp -= amount;}
	// Erniedrigt die maximalen MP.
	public void decreaseMaxMp(int amount){maxMp -= amount;}
	// Erniedrigt die aktuellen MP.
	public void desreaseMp(int amount){mp -= amount;}
}		