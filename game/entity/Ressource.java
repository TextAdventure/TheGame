package game.entity;

import java.io.Serializable;
import java.util.Vector;

/**
 * Ressourcen werden benoetigt, um Faehigkeiten zu verwenden oder sie dient als Leben(dies ist immer die zu erst erstellte Ressource!)
 * und die zweite ist die Initiative.
 * @author Marvin
 */
public class Ressource implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- statische Konstanten --- */
	
	// Statisches Array mit allen Ressourcen, die Nullte ist das Leben, oder wie auch immer es heissen mag.
	private static Ressource[] ressourcen = new Ressource[0];
	
	/**
	 * Die Konstante fuer einen Modifikator, der die maximale Menge erhoeht.
	 */
	public static final byte MAXMENGE = 0;
	
	/**
	 * Die Konstante fuer einen Modifikator, der die Regeneration pro Ort erhoeht.
	 */
	public static final byte REGENERATION = 1;
	
	/* --- Variablen --- */
	
	// Der Name der Ressource.
	private String name;
	// Der Parameter der Ressource.
	private String param;
	// Die Attribute, die diese Ressource beeinflussen.
	private RessourcenModifikator[] mods;
	
	/* --- Konstruktor --- */
	
	/**
	 * Erstellt eine neue Ressource, die von allen Lebewesen verwendet wird, alle Attribute, 
	 * die diese Ressource beeinflussen, muessen spaeter eingebracht werden.
	 * @param name Der Name der Ressource.
	 * @param param Der Parameter, der auf diese Ressource verweisst.
	 */
	public Ressource(String name, String param) {
		this.name = name;
		this.param = param;
		this.mods = new RessourcenModifikator[0];
		
		Vector<Ressource> neu = new Vector<Ressource>();
		
		for(Ressource r : getRessourcen())
			neu.add(r);
		neu.add(this);
		
		setRessourcen(neu.toArray(new Ressource[0]));
	}
	
	/* --- Methoden --- */
		
	/**
	 * Gibt den Namen der Ressource zurueck.
	 * @return Den Namen der Ressource.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gibt den Parameter der Ressource zurueck.
	 * @return Den Parameter der Ressource.
	 */
	public String getParam() {
		return param;
	}
	
	/**
	 * Gibt die ID der Ressource zurueck.
	 * @return Die ID der Ressource.
	 */
	public byte getId() {
		for(byte b = 0; b < getMaxId(); b++)
			if(getRessource(b).equals(this))
				return b;
		return -1;
	}
	
	/**
	 * Fuegt der Ressource einen Modifikator hinzu, der bestimmt, ob die maximale Menge des Attributs
	 * erhoeht wird oder die Regeneration. Es wird auch die Menge festgelegt.
	 * @param attribut Das Attribut, das diese Ressource beeinflusst.
	 * @param modifikator Die Modifikation der Ressoure pro Attributspunkt.
	 * @param typ Die Art der Modifikation, entweder Regenration oder maximale Menge.
	 */
	public void addModifikator(Attribut attribut, float modifikator, byte typ) {
		Vector<RessourcenModifikator> neu = new Vector<RessourcenModifikator>();
		
		for(RessourcenModifikator rm : mods)
			neu.add(rm);
		neu.add(new RessourcenModifikator(attribut, modifikator, typ));
		
		mods = neu.toArray(new RessourcenModifikator[0]);
	}
	
	/**
	 * Gibt die maximale Ressource fuer dieses Entity zurueck.
	 * @param entity Das Entity fuer das die Ressource berechnet werden soll.
	 * @return Die maximale Menge der Ressource fuer dieses Entity, basierend auf den temporaeren Werten.
	 */
	public float getMaxRessource(Entity entity) {
		float maxRessource = 0;
		for(RessourcenModifikator rm : mods)
			if(rm.getTyp() == MAXMENGE)
				maxRessource += Math.max(rm.getModifikator() * entity.getTemp(rm.getAttribut()), 0.0f);
		return maxRessource;
	}
	
	/**
	 * Gibt die Regeneration der Ressource pro Ort fuer dieses Entity zurueck.
	 * @param entity Das Entity fuer das die Regeneration berechnet werden soll.
	 * @return Die Regeneration pro Ort fuer dieses Entity, basierend auf den temporaeren Werten.
	 */
	public float getRessourceRegeneration(Entity entity) {
		float regeneration = 0;
		for(RessourcenModifikator rm : mods)
			if(rm.getTyp() == REGENERATION)
				regeneration += Math.max(rm.getModifikator() * entity.getTemp(rm.getAttribut()), 0.0f);
		return regeneration;
	}
	
	/* --- private Klasse --- */
	
	private class RessourcenModifikator implements Serializable {
		
		// Die serielle Versionsnummer.
		private static final long serialVersionUID = 1L;
		
		// Der Modifikator, der die Ressource beeinflusst.
		private float modifikator;
		// Das Attribut, das diese Ressource beeinflusst.
		private byte attribut;
		// Die Art der Beeinflussung.
		private byte typ;
		
		/* --- Konstruktor --- */
		
		/**
		 * Erstellt einen neuen Ressourcenmodifikator, der die Modifikation der Ressource festhaelt und die Art und die Menge der Modifikation speichert.
		 * @param attribut Das Attribut, das die Ressource modifiziert.
		 * @param modifikator Die Menge der Modifikation.
		 * @param typ Die Art der Modifikation.
		 */
		protected RessourcenModifikator(Attribut attribut, float modifikator, byte typ) {
			this.attribut = attribut.getId();
			this.modifikator = modifikator;
			this.typ = typ;
		}
		
		/* --- Methoden --- */
		
		/**
		 * Gibt den Modifikator zurueck.
		 * @return Den Modifikator.
		 */
		protected float getModifikator() {
			return modifikator;
		}
		
		/**
		 * Gibt das Attribut zurueck, das diesen Modifikator hat.
		 * @return Das Attribut.
		 */
		protected Attribut getAttribut() {
			return Attribut.getAttribut(attribut);
		}
		
		/**
		 * Gibt den Typ des Modifikators zurueck.
		 * @return Den Typ.
		 */
		protected byte getTyp() {
			return typ;
		}
	}
	
	/* --- statische Methoden --- */
	
	/**
	 * Gibt die Ressource zurueck, basierend auf ihrer ID.
	 * @param id Die ID der Ressource.
	 * @return Die gesuchte Ressource, null falls es diese id nicht gibt.
	 */
	public static Ressource getRessource(byte id) {
		if(ressourcen.length < id)
			return null;
		return ressourcen[id];
	}
	
	/**
	 * Gibt die maximale Menge einer Ressource fuer ein Entity zurueck.
	 * @param ressource Die Ressource.
	 * @param entity Das Entity fuer das die maximalen Leben berechnet werden soll.
	 * @return Gibt die maximale Ressource fuer dieses Entity zurueck.
	 */
	public static float getMaxRessource(Ressource ressource, Entity entity) {
		return ressource.getMaxRessource(entity);
	}
	
	/**
	 * Gibt die maximalen Leben fuer ein Entity zurueck.
	 * @param entity Das Entity fuer das die maximalen Leben berechnet werden soll.
	 * @return Gibt die maximalen Leben fuer dieses Entity zurueck.
	 */
	public static float getMaxLeben(Entity entity) {
		return getMaxRessource(getRessource((byte) 0), entity);
	}
	
	/**
	 * Gibt die Initiative fuer ein Entity zurueck.
	 * @param entity Das Entity fuer das die Initiative berechnet werden soll.
	 * @return Gibt die Inititative fuer dieses Entity zurueck.
	 */
	public static float getInitiative(Entity entity) {
		return getMaxRessource(getRessource((byte) 1), entity);
	}
	
	/**
	 * Gibt ein Array mit allen Ressourcen zurueck.
	 * @return Ein Array mit allen Ressourcen, die im Spiel verwendet werden.
	 */
	public static synchronized Ressource[] getRessourcen() {
		return ressourcen;
	}
	
	/**
	 * Legt alle Ressourcen fest, die im Spiel verwendet werden und ueberschreibt vorherige.
	 * @param alleRessourcen Die neue Liste mit allen Ressourcen.
	 */
	public static synchronized void setRessourcen(Ressource[] alleRessourcen) {
		ressourcen = alleRessourcen;
	}
	
	/**
	 * Gibt die hoechste belegte ID zurueck.
	 * @return Die maximale ID.
	 */
	public static byte getMaxId() {
		return (byte) ressourcen.length;
	}

}