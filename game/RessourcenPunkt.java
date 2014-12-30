package game;

import game.items.Stapel;

import java.io.Serializable;
import util.NumerusGenus;

/**
 * Ein Ressourcenpunkt, an dem man Ressourcen abbauen kann. Zwischen dem mehrmaligen Abbauen muss eine gewisse Zeit versteichen.
 * @author Marvin
 */
public class RessourcenPunkt implements Serializable {
	
	// Die serielle versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Der Name des Ressourcen Punkts.
	private String name;
	// Der Numerus und der Genus des Ressourcen Punkts.
	private NumerusGenus numGen;
	// Die Zeit, die vergehen muss, bevor der Ressourcenpunkt wieder verwendet werden kann.
	private int wiederherstellungszeit;	
	// Der Zeitpunkt der letzten Ernte.
	private long letzteErnte;
	// Die maximale Anzahl, die man hintereinander abbauen kann.
	private int maxAnzahl;
	// Die aktuelle Anzahl an verbleibenden Abbau Moeglichkeiten.
	private int aktuelleAnzahl;
	// Die Drops, die moeglich sind.
	private Drop[] drops;
	
	/* --- Konstruktor --- */
	
	/**
	 * Erstellt einen neuen Ressourcen Punkt, an dem Ressourcen abgebaut werden koennen.
	 * @param name Der Name des Ressourcen Punkts.
	 * @param numGen Der Numerus und Genus der Ressourcen Punkts.
	 * @param wiederherstellungszeit Die Zeit, bis der Ressourcen Punkt wieder verwendet werden kann in Sekunden.
	 * @param maxAnzahl Die maximale Anzahl, die der Ressourcen Punkt hintereinander abgebaut werden kann(-1 beduetet unendlich oft).
	 * @param drops Die moeglichen Drops von diesem Ressourcen Punkt.
	 */
	public RessourcenPunkt(String name, NumerusGenus numGen, int wiederherstellungszeit, int maxAnzahl, Drop... drops) {
		this.name = name;
		this.numGen = numGen;
		this.wiederherstellungszeit = wiederherstellungszeit;
		this.letzteErnte = System.currentTimeMillis();
		this.maxAnzahl = maxAnzahl;
		this.aktuelleAnzahl = this.maxAnzahl;
		this.drops = drops;
	}
	
	/* --- Methoden --- */
	
	/**
	 * "Erntet" die Ressourcen des Punkts und gibt diese zurueck.
	 * @return Die geernteten Ressourcen oder ein leeres Array, wenn es nichts zu ernten gibt.
	 */
	public Stapel[] ernte() {
		aktuelleAnzahl = Math.min((int)(System.currentTimeMillis() - letzteErnte) / (this.wiederherstellungszeit * 1000) + aktuelleAnzahl, maxAnzahl);
		if(aktuelleAnzahl != 0) {
			letzteErnte = System.currentTimeMillis();
			if(aktuelleAnzahl > 0)
				aktuelleAnzahl--;
			
			int sigmaChance = 0;
			for(Drop d : drops)
				sigmaChance += d.getChance();
			// Exklusive obere Grenze!
			int nInt = SpielWelt.WELT.r.nextInt(sigmaChance + 1);
			for(int i = drops.length - 1; i >= 0; i--) 
				if(nInt > sigmaChance - drops[i].getChance() && nInt <= sigmaChance)
					return drops[i].getGegenstaende();
				else
					sigmaChance -= drops[i].getChance();
		}
		return null;
	}
	
	/**
	 * Gibt den Namen des Ressourcen Punkts zurueck.
	 * @return Den Namen des Ressourcen Punkts.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gibt den Numerus und Genus des Ressourcen Punkts zurueck.
	 * @return Den Numerus und Genus.
	 */
	public NumerusGenus getNumGen() {
		return numGen;
	}
	
}