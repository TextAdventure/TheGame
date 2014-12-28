package util;

import java.io.Serializable;

/**
 *  Diese Klasse bietet die Moeglichkeiten fuer das erkennen und richtige
 *  auslesen von Artikeln und Pronomen entsprechend des Genus und Numerus.
 */
public class NumerusGenus implements Serializable {
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Wert fuer Numerus/Genus
	private byte numGen;

	// Die Werte: 0 maskulin, 1 feminin, 2 neutral, 3 plural
	private NumerusGenus(byte numGen) {
		this.numGen = numGen;
	}

	/**
	 *  Diese Methode gibt den bestimmten Artikel zurueck.
	 *  Nominativ: 0; Genitiv: 1; Dativ: 2; Akkusativ: 3
	 */
	public String getBest(int fall) {
	    return best[numGen][fall];
	}

	/**
	 *  Diese Methode gibt den unbestimmten Artikel zurueck.
	 *  Nominativ: 0; Genitiv: 1; Dativ: 2; Akkusativ: 3
	 */
	public String getUnbest(int fall) {
		return unbest[numGen][fall];
	}

	/**
	 *  Diese Methode gibt das Personalpronomen zurueck.
	 *  Nominativ: 0; Genitiv: 1; Dativ: 2; Akkusativ: 3
	 */
	public String getPers(int fall) {
	    return pers[numGen][fall];
	}

	/**
	 *  Diese Methode gibt true aus, wenn das Wort im Plural steht.
	 */
	public boolean isPlural() {
	    if(numGen == 3) return true;
	    else return false;
	}

	/**
	 * Gibt das passende Adjektiv zu dem Geschlecht zurueck.
	 * @return Das Adjektiv des Geschlechts.
	 */
	@Override
	public String toString() {
		if(MASKULIN.equals(this))
			return "maskulin";
		if(FEMININ.equals(this))
			return "feminin";
		if(NEUTRUM.equals(this))
			return "neutrum";
		if(PLURAL.equals(this))
			return "plural";
		return "invalid"; //  Dieser Fall darf eigentlich nicht eintreten, wenn man die Konstanten benutzt.
	}

	// Dieses statische 2D-Array enthaelt alle Personalpronomen fuer alle Faelle.
	private static String[][] pers = new String[][] {                                 // Zeile fuer Zeile
		{"Er ", "Seiner ", "Ihm ", "Ihn "},
		{"Sie ", "Ihrer ", "Ihr ", "Sie "},
		{"Es ", "Seiner ", "Ihm ", "Es "},
		{"Sie ", "Ihrer ", "Ihnen ", "Sie "}
	};

	// Dieses statische 2D-Array enthaelt alle bestimmtne Artikel fuer alle Faelle.
	private static String[][] best = new String[][] {                                 // Zeile fuer Zeile
		{"Der ", "Des ", "Dem ", "Den "},
		{"Die ", "Der ", "Der ", "Die "},
		{"Das ", "Des ", "Dem ", "Das "},
		{"Die ", "Der ", "Den ", "Die "}
	};

	// Dieses statische 2D-Array enthaelt alle unbestimmtne Artikel fuer alle Faelle.
	private static String[][] unbest = new String[][] {                                 // Zeile fuer Zeile
		{"Ein ", "Eines ", "Einem ", "Einen "},
		{"Eine ", "Einer ", "Einer ", "Eine "},
		{"Ein ", "Eines ", "Einem ", "Ein "},
		{"", "", "", ""}
	};


	// statisches Feld fuer Numerus und Genus
	// maskulin
	public static final NumerusGenus MASKULIN = new NumerusGenus((byte)0);
	// feminin
	public static final NumerusGenus FEMININ = new NumerusGenus((byte)1);
	// neutrum
	public static final NumerusGenus NEUTRUM = new NumerusGenus((byte)2);
	// plural
	public static final NumerusGenus PLURAL = new NumerusGenus((byte)3);
}
