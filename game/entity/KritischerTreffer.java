package game.entity;

import java.io.Serializable;
import java.util.Random;

/**
 * Ein kritischer Treffer fuegt im Kampf einem Gegner mehr Schaden zu.
 * @author Marvin
 */
public class KritischerTreffer implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Die Chance auf einen kritischen Treffer(in Prozent).
	private int chanceAbsolut;
	// Die Chancen-Erhoehung durch einen Faktor.
	private int chanceProzent;
	
	// Die temporaere Chance.
	private transient int chanceTemp;
	
	// Der Multiplikator mit dem der Schaden multipliziert werden soll(in Prozent).
	private int multiplikatorAbsolut;
	// Die Multiplikator-Erhoehung durch einen Faktor.
	private int multiplikatorProzent;
	
	// Der temporaere Multiplikator.
	private transient int multiplikatorTemp;
	
	/* --- Konstruktor --- */
	
	/**
	 * Es wird die Chance auf einen kritischen Treffer und der Multiplikator, mit dem der Schaden multipliziert wird, festgelegt.
	 * @param chance Die Chance auf einen kritischen Treffer in Prozent.
	 * @param multiplikator Der Multiplikator mit dem der Schaden multipliziert wird.
	 */
	public KritischerTreffer(int chance, int multiplikator) {
		// Zu Beginn wird nur die absolute Chance festgelegt.
		this.chanceAbsolut = chance;
		this.chanceProzent = 0;
		// Zu Beginn wird nur der absolute Multiplikator festgelegt.
		this.multiplikatorAbsolut = multiplikator;
		this.multiplikatorProzent = 0;
		
		// Aktualisiert die temporaeren Werte.
		aktualisieren();
	}
	
	/* --- Methoden --- */

	/**
	 * Fuegt der Chance auf einen kritischen Treffer einen Wert hinzu.
	 * @param prozentPunkte Fuegt der Chance eine gewisse Anzahl an Prozentpunkten hinzu.
	 * @param prozent Fuegt der Chance einen gewissen Prozentsatz hinzu.
	 */
	public void addKritChance(int prozentPunkte, int prozent) {
		this.chanceAbsolut += prozentPunkte;
		this.chanceProzent += prozent;
		aktualisieren();
	}
	
	/**
	 * Fuegt dem Schadensmultiplikator einen Wert hinzu.
	 * @param prozentPunkte Fuegt dem Multiplikator eine gewisse Anzahl an Prozentpunkten hinzu.
	 * @param prozent Fuegt dem Multiplikator einen gewissen Prozentsatz hinzu.
	 */
	public void addKritMultiplikator(int prozentPunkte, int prozent) {
		this.multiplikatorAbsolut += prozentPunkte;
		this.multiplikatorProzent += prozent;
		aktualisieren();
	}
	
	/**
	 * Ueberprueft, ob der Angriff kritisch ist und wenn ja, dann wird der verstaerkte Angriff zurueckgegeben.
	 * @param angriff Der Angriff des Entities.
	 * @param r Ein Zufallszahlen Generator.
	 * @return Den Angriff, der gegebenenfalls kritisch ist.
	 */
	public int kritischerTreffer(int angriff, Random r) {
		aktualisieren();
		if(r.nextInt(100) + 1 < chanceTemp)
			return Math.round(angriff * (((float) chanceTemp + 100.0f) / 100.0f));
		else
			return angriff;
	}
	
	/**
	 * Aktualisiert die temporaeren Werte fuer einen kritischen Treffer.
	 */
	private void aktualisieren() {
		this.chanceTemp = chanceAbsolut;
		this.chanceTemp *= Math.round(((float) chanceProzent + 100.0f) / 100.0f);
		// Die minimale Chance ist 0 Prozent.
		this.chanceTemp = Math.max(chanceTemp, 0);
		
		this.multiplikatorTemp = multiplikatorAbsolut;
		this.multiplikatorTemp *= Math.round(((float) multiplikatorProzent + 100.0f) / 100.f);
		// Der minimale Multiplikator ist 0 Prozent.
		this.multiplikatorTemp = Math.max(multiplikatorTemp, 0);
	}

}