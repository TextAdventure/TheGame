package game.entity;

import java.io.Serializable;
import java.util.Random;

/**
 * Ein Effekt, der im Kampf auf Spieler oder Gegner angewendet wird.
 */
public class Effekt implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	/* --- Die statischen Konstanten --- */
	
	// Heilen: param = Menge der Heilung(%, -)[pro Runde;Anzahl Runden] bsp.: [-]10[r4][%][;4[r2]]
	public static final byte HEILEN = 0;
	// MP-Wiederherstellung: param = Menge der Wiederherstellung(%, -)[pro Runde;Anzahl Runden] bsp.: [-]16[%][;3]
	public static final byte MPREGENERATION = 1;
	// Angriff+: param = Bonus-Angriff(%, -)[Effekt;Dauer](Ohne Dauer, fuer Rest von Kampf) bsp.: [-]29[%][;2]
	public static final byte ANGBONUS = 2;
	// Verteidigung+: param = Bonus-Verteidigung(%, -)[Effekt;Dauer](Ohne Dauer, fuer Rest von Kampf) bsp.: [-]82[%][;10]
	public static final byte DEFBONUS = 3;
	// MagAngriff+: param = Bonus-MagAngriff(%, -)[Effekt;Dauer](Ohne Dauer, fuer Rest von Kampf) bsp.: [-]4[%][;1]
	public static final byte MAGANGBONUS = 4;
	// MagVerteidigung+: param = Bonus-MagVerteidigung(%, -)[Effekt;Dauer](Ohne Dauer, fuer Rest von Kampf) bsp.: [-]99[%][;99]
	public static final byte MAGDEFBONUS = 5;
	// Praezision+: param = Bonus-Praezision(%, -)[Effekt;Dauer](Ohne Dauer, fuer Rest von Kampf) bsp.: [-]34[%][;6]
	public static final byte PRZBONUS = 6;
	// Flinkheit+: param = Bonus-Flinkheit(%, -)[Effekt;Dauer](Ohne Dauer, fuer Rest von Kampf) bsp.: [-]75[%][;5]
	public static final byte FLKBONUS = 7;
	  
	/* --- Die Variablen --- */
	
	// Der Typ des Effekts.
	private byte typ;
	// Der Parameter des Effekts.
	private String parameter;
	  
	/* --- Der Konstruktor --- */
	
	/**
	 *  Ein neuer Effekt wird erstellt mit einem Typ und einem Parameter.
	 *  @param typ Der Typ des Effekts.
	 *  @param parameter Der Parameter fuer den Effekt, er veraendert die Auswirkungen.
	 */
	public Effekt(byte typ, String parameter) {
	    this.typ = typ;
	    this.parameter = parameter;
	}
	
	/* --- Die Methoden --- */
	  
	/**
	 * Gibt den Typ als byte-Wert zurueck.
	 * @return Der Typ des Effekts.
	 */
	public byte getTyp(){
	    return typ;
	}
	  
	/**
	 * Gibt die Dauer in Runden an, die der Effekt anhaelt.
	 * @return Die anfaengliche Dauer des Effekts.
	 */
	public int getDauer() {
	    String wert = parameter;
	    // Ueberprueft, ob es eine Rundenangabe gibt.
	    if(wert.indexOf(";") < 1) 
	    	return 0;
	    // Es wird nur die Rundenangabe behalten.
	    wert = wert.split(";")[1];
	    
	    int random = 0;
	    // Ueberprueft, ob es eine Zufallszahl fuer die Rundenanzahl gibt.
	    if(wert.indexOf("r") > 0) {
	    	random = Integer.valueOf(wert.substring(wert.indexOf("r") + 1, wert.length()));
	    	wert = wert.substring(0, wert.indexOf("r"));
	    }
	    
	    // Die Rundenanzahl, ohne Zufallszahl.
	    int zahl = Integer.valueOf(wert);
	    Random r = new Random();
	    // Falls es eine Zufallszahl gibt, wird dies hinzu addiert.
	    if(random > 0) 
	    	zahl += r.nextInt(random + 1);
	    // Die fertige Rundenanzahl, die der Effekt anhaelt.
	    return zahl;
	  
	    // TODO
	    /*if(parameter.split(";").length < 2 || parameter.isEmpty()) return 0;
	    String zahl = parameter.split(";")[1];
	    int dauer = 0;
	    if(zahl.split("r").length > 1){
	      int random = Integer.valueOf(zahl.split("r")[1]);						Hier musss etwas überarbeitet werden!
	      zahl = zahl.substring(0, zahl.indexOf("r"));
	      Random r = new Random();
	      dauer += r.nextInt(random + 1);
	    }
	    dauer += Integer.valueOf(zahl);
	    return Math.max(dauer, 0);*/
	}
	  
	/**
	 * Aufgrund des Grundwerts des Spielers wird der Bonus ermittelt und der reine Bonus, ohne den Grundwert, wird zurueckgegeben.
	 * @param grundwert Der Grundwert des Spielers fuer diesen Effekt.
	 * @return Der reine Bonus wird zurueckgegeben.
	 */
	public int getBonus(int grundwert) {
	    String wert = parameter;
	    // Der Attributsbonus wird extrahiert.
	    if(wert.indexOf(";") > 0) 
	    	wert = wert.split(";")[0];
	    // Ueberprueft, ob es sich um eine Prozentangabe handelt.
	    boolean prozent = false;
	    if(wert.indexOf("%") > 0) {
	    	wert = wert.replaceAll("%", "");
	    	prozent = true;
	    }
	    // Ueberprueft, ob es eine Zufallszahl fuer den Bonus gibt.
	    int random = 0;
	    if(wert.indexOf("r") > 0) {
	    	random = Integer.valueOf(wert.substring(wert.indexOf("r") + 1, wert.length()));
	    	wert = wert.substring(0, wert.indexOf("r"));
	    }
	    
	    Random r = new Random();
	    int zahl = Integer.valueOf(wert);
	    // Falls es eine Zufallszahl gibt, wird diese nizu addiert. 
	    if(random > 0) 
	    	zahl += r.nextInt(random + 1);
	    // Gibt den Bonus zurueck.
	    if(prozent) {
	    	return (int)(grundwert * zahl / 100.0);
	    } else {
	    	return zahl;
	    }
	}
	
}