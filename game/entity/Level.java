package game.entity;

import java.io.Serializable;

/**
 * Ein Level/Stufe fuer den Spieler mit allen moeglichen Attributsaenderungen.
 * @author Marvin
 */
public class Level implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Die Erfahrung bis zu diesem Level.
	private int erfahrung;
	
	// Aenderungen der Attribute.	
	private EntityAttribut attributsBonus;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein neues Level wird mit allen Veraenderungen der Statuswerte generiert.
	 * @param erfahrung Die benoetigte Erfahrung bis zu diesem Level.
	 * @param attributsBonus Der Bonus auf das Attribut in der entsprechenden ID-Reihenfolge.
	 */
	public Level(int erfahrung, int... attributsBonus) {
		this.erfahrung = erfahrung;
		this.attributsBonus = new EntityAttribut(attributsBonus);
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt die Erfahrung aus, die fuer diese Stufe benoetigt wird. 
	 * @return Die Erfahrung fuer dieses Level.
	 */
	public int getErfahrung() {
		return erfahrung;
	}
	
	/**
	 * Gibt den Bonus fuer ein bestimmtes Attribut zurueck.
	 * @param attribut Das Attribut, fuer das der Bonus ermittelt werden soll.
	 * @return Den Bonuswert fuer das Attribut, -1 wenn es das Attribut nicht gibt.
	 */
	public int getAttributsBonus(Attribut attribut) {
		return attributsBonus.getWert(attribut);
	}
	
	/* --- statische Methoden --- */
	
	/**
	 * Erzeugt aus Anfangswerten und Zielwerten, ein Array aller Level, die dafuer benoetigt werden, aufgrund einer linearen Verteilung.
	 * @param levelAnzahl Die Anzahl an Leveln.
	 * @param anfangsWerte Die anfaengliche Werte als Level uebergeben.
	 * @param zielWerte Die Zielwerte des Charakters auf dem hoechsten Level.
	 * @return Alle Level fuer den Spieler, das 0. Level sind die Startwerte fuer den Spieler.
	 */
	public static Level[] createLinearLevels(int levelAnzahl, Level anfangsWerte, Level zielWerte) {
		Level[] level = new Level[levelAnzahl + 2];
		// Zuerst werden die Faktoren fuer die einzelnen Werte berechnet.
		int erfFak = Math.round((float) (zielWerte.getErfahrung() - anfangsWerte.getErfahrung()) / (float) levelAnzahl);
		int[] attributFaks = new int[Attribut.getMaxId()];
		for(Attribut a : Attribut.getAttribute())
			attributFaks[a.getId()] = Math.round((float) (zielWerte.getAttributsBonus(a)	- anfangsWerte.getAttributsBonus(a)) / (float) levelAnzahl);
		
		int sigmaErf = anfangsWerte.getErfahrung();
		int[] sigmaAttribute = new int[attributFaks.length];
		
		level[0] = anfangsWerte;
		for(int i = 1; i <= levelAnzahl; i++) {
			level[i] = new Level(erfFak, attributFaks);
			sigmaErf += erfFak;
			for(int j = 0; j < sigmaAttribute.length; j++)
				sigmaAttribute[j] += attributFaks[j];
		}
		
		for(Attribut a : Attribut.getAttribute()) {
			sigmaAttribute[a.getId()] += anfangsWerte.getAttributsBonus(a);
			sigmaAttribute[a.getId()] = zielWerte.getAttributsBonus(a) - sigmaAttribute[a.getId()] + attributFaks[a.getId()];
		}			
		
		level[levelAnzahl + 1] = new Level(zielWerte.getErfahrung() - sigmaErf, sigmaAttribute);
		
		return level;
	}

}