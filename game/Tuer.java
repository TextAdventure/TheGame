package game;

import java.io.Serializable;

import game.items.Gegenstand;
import game.logic.Ereignis;

/**
 * Eine Tuer wird geoeffnet, wenn der richtige Schluessel verwendet wird, dabei tritt ein Ereignis ein.
 * @author Marvin
 */
public class Tuer implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Die Schluessel, die die Tuer oeffnen.
	private Gegenstand[] schluessel;
	// Der Ausgang, der hinter der Tuer liegt.
	private Ausgang ausgang;
	// Das Ereignis, das eintritt wird, wenn der Schluessel an der Tuer verwendet wird.
	private Ereignis ereignis;
	
	/* --- Konstruktor --- */
	
	/**
	 * Erstellt eine Tuer, die einen Ausgang hat und mehrere moegliche Schluessel, die verwendet werden koennen.
	 * @param ausgang Der Ausgang, der hinter der Tuer liegt.
	 * @param ereignis Das Ereignis, das dadurch ausgeloest wird.
	 * @param schluessel Alle Schluessel, mit denen man die Tuer oeffnen kann.
	 */
	public Tuer(Ausgang ausgang, Ereignis ereignis, Gegenstand...schluessel) {
		this.ausgang = ausgang;
		this.ereignis = ereignis;
		this.schluessel = schluessel;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt den Ausgang der Tuer zurueck.
	 * @return Den Ausgang der Tuer.
	 */
	public Ausgang getAusgang() {
		return ausgang;
	}
	
	/**
	 * Die Tuer wird mithilfe eines Schluessels geoffnet.
	 * @param schluessel Der Schluessel mit dem die Tuer geoffnet werden soll.
	 * @return True, wenn die Tuer geoffnet wurde, ansonsten false.
	 */
	public boolean oeffnen(Gegenstand schluessel) {
		for(Gegenstand s : this.schluessel)
			if(s == schluessel)
				return ereignis.eingetreten();
		return false;
	}

}