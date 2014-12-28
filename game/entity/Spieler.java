package game.entity;

import game.SpielWelt;
import game.items.Accessoire;
import game.items.Ausruestung;
import game.items.Gegenstand;
import game.items.Inventar;
import game.items.Ruestung;
import game.items.Waffe;
import game.items.Waffenart;

import java.util.Vector;

import util.NumerusGenus;
import util.PlayerEvent;
import util.PlayerListener;
import util.StringEvent;
import util.StringListener;

/**
 *  Alle relevanten Objekte fuer den Spieler werden hier gespeichert, diese Klasse repraesentiert also den Spieler.
 */
public class Spieler extends Entity {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Das Level des Spielers und seine XP.
	private short level;
	private int xp;
	//private int nextLevel;
	
	// Die Level des Spielers
	private Level[] levels;
	  
	
	// Das Inventar des Spielers.
	private Inventar inventar;
	// Die Ausruestung des Spielers.
	private Ausruestung ausruestung;
	
	// Die Listener
	transient private Vector<Object> listeners;
	  
	/**
	 *  Ein neuer Spieler wird ohne ein Parameter initialisiert.
	 */
	public Spieler(String name, NumerusGenus geschlecht, String beschreibung) {
		super();
		
		this.name = name;
		this.numGen = geschlecht;
		this.beschreibung = beschreibung;
		
		inventar = new Inventar();
	    ausruestung = new Ausruestung();
	    
	    listeners = new Vector<Object>();
	}
	  
	/* --- Alle Methoden --- */  

	/**
	 * Anedert die Level des Spielers und erstzt sie durch neue, diese Methode sollte nur im WeltenGenerator verwendet werden.
	 * @param levels Die neuen Level des Spielers.
	 */
	public void setLevels(Level[] levels) {
		this.levels = levels;
		
		this.maxLp = levels[0].getLpBonus();
		this.lp = this.maxLp;
		this.maxMp = levels[0].getMpBonus();
		this.mp = this.maxMp;
		
		for(Attribut a : Attribut.getAttribute())
			this.addAttribut(levels[0].getAttributsBonus(a.getName()), a.getName());
		
		level = 1;
	}
	
	/**
	 * Gibt die XP zurueck, die fuer ein Level Up benoetigt werden.
	 * @return Die Xp fuer ein Level Up.
	 */
	private int getXpFuerLevelUp() {
		int levelUp = 0;
		for(int i = 0; i <= level; i++) {
			levelUp += levels[i].getErfahrung();
		}
		return levelUp - xp;
	}
	  
	// Fuegt dem Spieler xp hinzu.
	public void addXp(int menge) {
		for( ; menge > 0 ; menge--) {
			xp++;
			int levelUp = getXpFuerLevelUp();		
			if(levelUp == 0 && level + 1 < levels.length) {
				level++;
				
				SpielWelt.WELT.println("Gl�ckwunsch! Dein Level erh�ht sich auf " + level + ".");
				SpielWelt.WELT.println("Deine Attribute verbesseren sich!");
				
				SpielWelt.WELT.println("LP: " + maxLp + " +" + levels[level].getLpBonus());
				maxLp += levels[level].getLpBonus();
				
				SpielWelt.WELT.println("MP: " + maxMp + " +" + levels[level].getMpBonus());
				maxMp += levels[level].getMpBonus();
				
				for(Attribut a : Attribut.getAttribute()) {
					SpielWelt.WELT.println(a.getName() + ": " + getAttribut(a.getName()) + " +" + levels[level].getAttributsBonus(a.getName()));
					this.addAttribut(levels[level].getAttributsBonus(a.getName()), a.getName());
				}
				SpielWelt.WELT.println();
			}
		}
	}
	
	// Gibt das Inventar des Spielers zurueck.
	public Inventar getInventar(){
	    return inventar;
	}

	/**
	 * Gibt die Ausruestung des Spielers zurueck.
	 * @return Die Ausruestung des Spielers.
	 */
	public Ausruestung getAusruestung(){
	    return ausruestung;
	}
	
	// Fuegt den Faehigkeiten des Spielers eine neue hizu.
	@Override
	public void addFaehigkeit(Faehigkeit faehigkeit) {
		super.addFaehigkeit(faehigkeit);
		notifyListeners(faehigkeit);
	}
	
	// Gibt die entsprechende Angriffsfaehigkeit zurueck.
	@Override
	public Faehigkeit getFaehigkeit(String kommando) {
		for(Faehigkeit f : faehigkeiten)
			if(f.getName().equalsIgnoreCase(kommando))
				return f;
		return null;
	}
	
	/**
	 * Ueberprueft, ob der Spieler diese Faehigkeit bereits kennt oder nicht.
	 * @param faehigkeit Die Faehigkeit auf die geprueft werden soll.
	 * @return true, wenn der Spieler sie kennt, ansosnten false.
	 */
	public boolean kenntFaehigkeit(Faehigkeit faehigkeit) {
		return faehigkeiten.contains(faehigkeit);
	}
	
	/**
	 *  Der Spieler reustet einen Gegenstand aus, es wird ein boolean zurueckgegeben, der mitteilt, ob es funktioniert hat.
	 *  gegenstand: der Gegenstand, welcher ausgeruestet werden soll.
	 */
	public boolean ruesteAus(Gegenstand gegenstand) {
	    if(!inventar.containsGegenstand(gegenstand))
	    	return false;
	    Gegenstand[] alt2 = new Gegenstand[1];
	    inventar.removeGegenstand(gegenstand, 1);
	    if(gegenstand instanceof Waffe) {
	    	Waffe test = (Waffe)gegenstand;
	    	if(test.getHand() == Waffe.SCHWERTHAND) alt2[0] = ausruestung.tauscheSchwerthand(test);
	    	if(test.getHand() == Waffe.SCHILDHAND) alt2[0] = ausruestung.tauscheSchildhand(test);
	    	if(test.getHand() == Waffe.ZWEIHAENDIG) alt2 = ausruestung.tauscheZweihaendig(test);
	    } else if(gegenstand instanceof Ruestung) {
	    	alt2[0] = ausruestung.tauscheRuestung((Ruestung)gegenstand);
	    } else if(gegenstand instanceof Accessoire) {
	    	alt2[0] = ausruestung.tauscheAccesoire((Accessoire)gegenstand);
	    } else {
	    	return false;
	    }
	    for(Gegenstand g : alt2) {
	    	if(g != null) {
	    		inventar.addGegenstand(g, 1);
	    		maxLp -= g.getLp();
	    		lp = Math.min(lp, maxLp);
	    		maxMp =- g.getMp();
	    		mp = Math.min(mp, maxMp);
	    		for(Attribut a : Attribut.getAttribute())
	    			this.addAttribut(-g.getAttribut(a.getName()), a.getName());
	    		for(Resistenz r : Resistenz.getResistenzen())
	    			this.addResistenz(-g.getResistenz(r.getName()), r.getName());
	    	}	
	    }
	    maxLp += gegenstand.getLp();
	    maxMp += gegenstand.getMp();
	    for(Attribut a : Attribut.getAttribute())
			this.addAttribut(gegenstand.getAttribut(a.getName()), a.getName());
	    for(Resistenz r : Resistenz.getResistenzen())
    			this.addResistenz(gegenstand.getResistenz(r.getName()), r.getName());
	    resetTemp();
	    return true;
	}
	  
	/**
	 *  Der Spieler legt den uebergebenen Gegenstand ab, wenn er ihn ausgeruestet hat.
	 *  gegenstand: der abzulegende Gegenstand.
	 */
	public boolean legeAb(Gegenstand gegenstand) {
	    if(!ausruestung.istAusgeruestet(gegenstand)) return false;
	    Gegenstand[] alt2 = new Gegenstand[1];
	    if(gegenstand instanceof Waffe) {
	    	Waffe test = (Waffe)gegenstand;
	    	if(test.getHand() == Waffe.SCHWERTHAND) alt2[0] = ausruestung.tauscheSchwerthand(null);
	    	if(test.getHand() == Waffe.SCHILDHAND) alt2[0] = ausruestung.tauscheSchildhand(null);
	    	if(test.getHand() == Waffe.ZWEIHAENDIG) alt2 = ausruestung.tauscheZweihaendig(null);
	    } else if(gegenstand instanceof Ruestung) {
	    	alt2[0] = ausruestung.legeRuestungAb((Ruestung)gegenstand);
	    } else if(gegenstand instanceof Accessoire) {
	    	alt2[0] = ausruestung.legeAccesoireAb((Accessoire)gegenstand);
	    } else {
	    	return false;
	    }
	    for(Gegenstand g: alt2) {
	    	inventar.addGegenstand(g, 1);
	    	maxLp -= g.getLp();
	    	if(lp > maxLp) lp = maxLp;
	    	maxMp -= g.getMp();
	    	if(mp > maxMp) mp = maxMp;
	    	for(Attribut a : Attribut.getAttribute())
    			this.addAttribut(-g.getAttribut(a.getName()), a.getName());
	    	for(Resistenz r : Resistenz.getResistenzen())
	    		this.addResistenz(-g.getResistenz(r.getName()), r.getName());
	    }
	    resetTemp();
	    return true;
	}
	  
	/**
	 * Gibt die Arten beider Waffen des Spielers.
	 * @return Die Arten der Waffen des Spielers.
	 */
	public Waffenart[] getWaffenarten(){
	    Waffenart[] wa = new Waffenart[2];
	    if(ausruestung.getHaupthand() != null) wa[0] = ausruestung.getHaupthand().getWaffenart();
	    else wa[0] = null;
	    if(ausruestung.getSchildhand() != null) wa[1] = ausruestung.getSchildhand().getWaffenart();
	    else wa[1] = null;
	    return wa;
	}
	  
	/**
	 * Es werden die aktuellen Statuswerte des Spieler ausgegeben.
	 * @param welt Die SpielWelt, in der es ausgegeben werden soll.
	 */
	public void zeigeStatusAn(SpielWelt welt) {
	    welt.println("Statuswerte:");
	    welt.println("Leben " + lp + "/" + maxLp);
	    welt.println("Magie " + mp + "/" + maxMp);
	    welt.println("Level: " + level);
	    if(levels != null && levels.length > level + 1)
	    	welt.println("XP: " + getXpFuerLevelUp() + " zum n�chsten Level, insgesamt " + xp);
	    else
	    	welt.println("XP: insgesamt " + xp);
	    welt.println();
	    for(int i = 0; i < Attribut.getMaxId(); i++)
	    	welt.println(Attribut.ATTRIBUTE[i].getName() + ": " + this.getAttribut(Attribut.ATTRIBUTE[i].getName()));
	    welt.println();
	    for(int i = 0; i < Resistenz.getMaxId(); i++)
	    	welt.println(Resistenz.RESISTENZEN[i].getName() + ": " + this.getResistenz(Resistenz.RESISTENZEN[i].getName()) + "%");
	    welt.println();
	    for(EntityDamageAmplifier eda : schadensMultiplikatoren)
	    	welt.println(eda.getSchadensart().getName() + " wird um " + eda.getProzetual() + "% erh�ht und um " + eda.getAbsolut());
	}
	
	
	/**
	 * Fuegt dem Spieler einen neuen Listener hinzu.
	 * @param listener Der neue Listener fuer den Spieler.
	 */
	public void addListener(Object listener) {
		// Das Spiel wurde gerade geladen, deshalb ist kein Listener mehr da.
		if(listeners == null)
			listeners = new Vector<Object>();			
		
		if(!listeners.contains(listener))
			listeners.add(listener);
	}
	
	/**
	 * Updated alle StringListener und PlayerListener des Spielers.
	 * @param faehigkeit Die neue Faehigkeit des Spielers.
	 */
	public void notifyListeners(Faehigkeit... faehigkeit) {
		for(Object listener : listeners) {			
			if(listener instanceof PlayerListener) {
				((PlayerListener)listener).playerChanged(new PlayerEvent(this));
			}
			
			if(listener instanceof StringListener) {
				for(Faehigkeit f : faehigkeit) {
					((StringListener)listener).actionPerformed(new StringEvent(f.getName()));
				}				
			}
	    }
	}
	
}