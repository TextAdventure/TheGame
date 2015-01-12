package game.battle;

import java.io.Serializable;

import game.entity.Entity;
import game.entity.Gegner;

/**
 * Ein Kampf besteht aus der Wahrscheinlichkeit fuer ihn und allen Gegnern die darin auftauchen.
 * @author Marvin
 */
public class Kampf implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Die Wahrscheinlichkeit fuer diesen Kampf.
	private int wahrscheinlichkeit;
	// Alle Gegner, die in diesem Kampf erscheinen.
	private Entity[] gegner;
	transient private Entity[] kampf; // Mit den Buchstaben.

	/* --- Konstruktor --- */

	/**
	 * Ein Kampf hat eine wahrscheinlichkeit fuer sein auftreten und eine gewisse Anzahl an Gegner.
	 * @param wahrscheinlichkeit Die Wahrscheinlichkeit dafuer, ob dieser Kampf stattfindet.
	 * @param gegner Die Gegner in diesem Kampf.
	 */
	public Kampf(int wahrscheinlichkeit, Entity... gegner) {
		this.wahrscheinlichkeit = wahrscheinlichkeit;
		this.gegner = gegner;
		reset();
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt die Wahrscheinlichkeit fuer diesen Kampf zurueck.
	 * @return Die Wahrscheinlichkeit fuer diesen Kampf.
	 */
	public int getChance() {
		return this.wahrscheinlichkeit;
	}

	/**
	 * Gibt einen Gegner basierend auf seinem Namen(mit Buchstaben!) zurueck.
	 * @param name Der Name des gesuchten Gegners.
	 * @return Der gesuchte Gegner, wenn es diesen nicht gibt, dann null.
	 */
	public Entity getGegner(String name) {
		for(Entity g : kampf)
			if(g != null && g.getName().equalsIgnoreCase(name))
				return g;
		return null;
	}
	
	/**
	 * Gibt alle Gegner, die am Kampf beteiligt sind, in einem Array zurueck. Ihren Namen wurde bereits Buchstaben hinzugefuegt.
	 * @return Ein Array mit allen Gegnern fuer diesen Kampf.
	 */
	public Entity[] getKampfGegner() {
		return kampf;
	}
	
	/**
	 * Setzt die Gegner des Kampfes zurueck.
	 * @return Sich selbst.
	 */
	public Kampf reset() {
		kampf = new Gegner[gegner.length];
		for(Entity g : gegner) {
			for(int i = 0; i < gegner.length; i++) {
				if(gegner[i].equals(g))
					kampf[i] = new Gegner((Gegner) g).addBuchstabe((char)(i + 65));
			}
		}
		return this;
	}
	
	/**
	 * "Toetet" einen Gegner, sodass er aus den Kampf entfernt wird.
	 * @param gegner Der tote Gegner.
	 */
	public void toeteGegner(Entity gegner) {
		for(Entity g : kampf)
			if(g.getName().equals(gegner.getName())) {
				g = null;
				return;
			}
	}
	
	/**
	 * Gibt zurueck, ob alle Gegner tot sind oder nicht.
	 * @return True, wenn alle Gegner tot sind, ansonsten false.
	 */
	public boolean alleTot() {
		for(Entity g : kampf)
			if(g.getLp() > 0)
				return false;
		return true;
	}
	
}