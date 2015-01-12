package game.entity;

import java.io.Serializable;

/**
 * Speichert eine Schadenserhoehung fuer eine Lebewesen und eine bestimmte Schadensart.
 * @author Maravin
 */
public class EntityDamageAmplifier implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Die Schadensart, ide verstaerkt werden soll.
	private byte schadensart;
	// Der prozentuale Wert, um den der Wert erhoeht werden soll.
	private float prozent;
	// Der absolute Wert, um den der Wert erhoeht werden soll.
	private int absolut;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein EntityDamageAmpliefier erhoeht den Schaden fuer eine bestimmte Schadensart.
	 * @param schadensart Die Schadensart, deren Schaden erhoeht werden soll.
	 * @param prozent Die prozentuale Steigerung des Schadens.
	 * @param absolut Die absolute Steigerung des Schadens zusaetzlich zur prozentualen.
	 */
	public EntityDamageAmplifier(Schadensart schadensart, float prozent, int absolut) {
		this.schadensart = schadensart.getId();
		this.prozent = prozent;
		this.absolut = absolut;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt den Schaden inklusive der Verstaerkung zurueck.
	 * @param schaden Der Schaden vor der Verstaerkung.
	 * @return Den Schaden inklusive der Verstaerkung, dabei wird zu erst der prozentuale, dann der absolute Bonus verrechnet.
	 */
	public int getAmplifiedDamage(int schaden) {
		int rueckgabe = (int) (schaden * (prozent / 100.0f + 1.0f));
		rueckgabe += absolut;
		return rueckgabe;
	}
	
	/**
	 * Gibt die Schadensart zurueck.
	 * @return Die Schadensart, die verstaerkt wird.
	 */
	public Schadensart getSchadensart() {
		return Schadensart.getSchadensart(schadensart);
	}
	
	/**
	 * Fuegt dem prozentualen Schaden eine Wert hinzu.
	 * @param prozent Der zusaetzliche prozentuale Schaden.
	 */
	public void addProzentualenSchaden(float prozent) {
		this.prozent += prozent;
	}
	/**
	 * Fuegt dem absoluten Schaden einen Wert hinzu.
	 * @param absolut Der zusaetzliche absolute Schaden.
	 */
	public void addAbsolutenSchaden(short absolut) {
		this.absolut += absolut;
	}
	
	/**
	 * Gibt die prozentuale Schadenserhoehung zurueck.
	 * @return Die prozentuale Schadenserhoehung.
	 */
	public float getProzetual() {
		return prozent;
	}
	
	/**
	 * Gibt die absolute Schadenserhoehung zurueck.
	 * @return Die absolute Schadenserhoehung.
	 */
	public int getAbsolut() {
		return absolut;
	}

}
