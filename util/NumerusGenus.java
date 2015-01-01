package util;

import java.io.Serializable;

/**
 * Mit dieser Klasse koennen die entsprechenden Artikel fuer einen bestimmten
 * Fall automatisch ergaenzt werden und das entsprechend des Numerus und Genus.
 * @author Marvin
 */
public final class NumerusGenus implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Der Wert fuer Numerus/Genus
	private byte numGen;

	/* --- Konstruktor --- */
	
	/**
	 * Privater Konstruktor, der nur innehalb dieser Klasse verwendet werden darf, um die Konstanten zu erstellen.
	 * @param numGen 0 = maskulin, 1 = feminin, 2 = neutrum, 3 = plural
	 */
	private NumerusGenus(byte numGen) {
		this.numGen = numGen;
	}

	/* --- Methoden --- */
	
	/**
	 * Gibt den bestimmten Artikel fuer einen Fall zurueck.
	 * @param fall Der Fall, fuer den der Artikel bestimmt werden soll.
	 * @return Gibt den bestimmten Artikel fuer diesen Fall zurueck.
	 */
	public String getBest(byte fall) {
	    return best[numGen][fall];
	}

	/**
	 * Gibt den unbestimmten Artikel fuer einen Fall zurueck.
	 * @param fall Der Fall, fuer den der Artikel bestimmt werden soll.
	 * @return Gibt den unbestimmten Artikel fuer diesen Fall zurueck.
	 */
	public String getUnbest(byte fall) {
		return unbest[numGen][fall];
	}

	/**
	 * Gibt das Personalpronomen fuer einen Fall zurueck.
	 * @param fall Der Fall, fuer den das Pronomen bestimmt werden soll.
	 * @return Gibt den unbestimmten Artikel fuer diesen Fall zurueck.
	 */
	public String getPers(byte fall) {
	    return pers[numGen][fall];
	}

	/**
	 * Testet, ob das Wort im Plural ist oder nicht.
	 * @return True, wenn es im Plural ist, ansonsten false.
	 */
	public boolean isPlural() {
	    if(numGen == 3)
	    	return true;
	    else
	    	return false;
	}
	
	/**
	 * Überprüft dieses Objekt auf Gleichheit mit obj. Falls obj auch vom Typ NumerusGenus, so ist Gleichheit dadurch defineirt,
	 * dass das repräsentierte grammatikalsiche Geschlecht der beiden übereinstimmt.
	 * @param obj Das Objekt, mit dem verglichen werden soll.
	 * @return true genau dann wenn obj von Typ NumerusGenus ist und das gleiche grammatikalische Geschlecht wie dieses Objekt 
	 * repräsentiert, oder Object.equals(Object) dieses Ergebnis geliefert hat.
	 */
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) return true;
		if(obj instanceof NumerusGenus) {
			return (((NumerusGenus)obj).numGen == this.numGen);
		}
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

	/* --- Konstanten --- */

	/**
	 * Diese Array enthaelt alle bestimmten Artikel fuer [Genus][Fall].
	 */
	private static String[][] best = new String[][] {                                 // Zeile fuer Zeile
		{"Der ", "Des ", "Dem ", "Den "},
		{"Die ", "Der ", "Der ", "Die "},
		{"Das ", "Des ", "Dem ", "Das "},
		{"Die ", "Der ", "Den ", "Die "}
	};

	/**
	 * Dieses Array enthaelt alle unbestimmten Artikel fuer [Genus][Fall].
	 */
	private static String[][] unbest = new String[][] {                                 // Zeile fuer Zeile
		{"Ein ", "Eines ", "Einem ", "Einen "},
		{"Eine ", "Einer ", "Einer ", "Eine "},
		{"Ein ", "Eines ", "Einem ", "Ein "},
		{"", "", "", ""}
	};

	/**
	 * Dieses Array enthaelt alle Personalpronomen fuer [Genus][Fall].
	 */
	private static String[][] pers = new String[][] {                                 // Zeile fuer Zeile
		{"Er ", "Seiner ", "Ihm ", "Ihn "},
		{"Sie ", "Ihrer ", "Ihr ", "Sie "},
		{"Es ", "Seiner ", "Ihm ", "Es "},
		{"Sie ", "Ihrer ", "Ihnen ", "Sie "}
	};
	

	// statisches Feld fuer Numerus und Genus
	/**
	 * Die statische Konstante fuer ein maskulines Wort.
	 */
	public static final NumerusGenus MASKULIN = new NumerusGenus((byte)0);
	/**
	 * Die statische Konstante fuer ein feminines Wort.
	 */
	public static final NumerusGenus FEMININ = new NumerusGenus((byte)1);
	/**
	 * Die statusche Konstante fuer ein Neutrum.
	 */
	public static final NumerusGenus NEUTRUM = new NumerusGenus((byte)2);
	/**
	 * Die statische Konstante fuer den Plural.
	 */
	public static final NumerusGenus PLURAL = new NumerusGenus((byte)3);
	
	//statisches Feld mit allen Faellen als Konstanten.
	/**
	 * Die statische Konstante fuer den Nominativ.
	 */
	public static final byte NOMINATIV = 0;
	/**
	 * Die statische Konstante fuer den Genitiv.
	 */
	public static final byte GENITIV = 1;
	/**
	 * Die statische Konstante fuer den Dativ.
	 */
	public static final byte DATIV = 2;
	/**
	 * Die statische Konstante fuer den Akkusativ.
	 */
	public static final byte AKKUSATIV = 3;
	
}
