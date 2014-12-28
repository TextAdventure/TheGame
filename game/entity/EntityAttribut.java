package game.entity;

import java.io.Serializable;

/**
 * Diese Klasse fuehrt ein Attribut und einen Wert fuer das Entity zusammen.
 */
public class EntityAttribut implements Serializable {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	// Das Attribut
	private byte attribut;		
	// Der Wert des Attributs.
	private int wert;
	// Der temporaere Wert des Attributs.
	private int temp;
	
	/**
	 * Ein neues EntityAttribut wird mithilfe eines Attributes und einem Wert erstellt.
	 * @param attribut Das Attribut, welches festgelegt werden soll.
	 * @param wert Der Wert des Attributs.
	 */
	public EntityAttribut(Attribut attribut, int wert) {
		this.attribut = (byte) attribut.getId();
		this.wert = wert;
		this.temp = wert;
	}
	
	public void addWert(int wert) {
		this.wert += wert;
	}
	public void addTemp(int wert) {
		// Der temporaere Wert ist mindestens 1, da ansonsten merkwuerdige Ergebnisse bei Schaden etc. herauskommen wuerden
		this.temp = Math.max(wert + temp, 1);
	}
	public void resetTemp() {
		this.temp = this.wert;
	}
	
	public int getWert() {
		return wert;
	}
	public int getTemp() {
		return temp;
	}
	public Attribut getAttribut() {
		return Attribut.getAttribut(attribut);
	}
	
}