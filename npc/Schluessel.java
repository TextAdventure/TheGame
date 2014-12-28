package npc;

import java.io.Serializable;

/**
 * repr�sentier ein Schl�sselwort, das der Spieler im Gespr�ch mit einem NPC verwenden kann.
 * @author Felix
 *
 */
public class Schluessel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//die Spielerantworten, bei denen dieser Schluessel aktiviert wird
	private String[] schluessel;
	//der neue Gespr�chszustand
	private Status ziel;
	
	public Schluessel(Status ziel, String... schluessel) {
		this.ziel= ziel;
		this.schluessel = schluessel;
	}
	
	/**
	 * Reaktion auf Spielerantwort
	 * @param text Die Antwort des Spielers
	 * @return Den neuen Gespr�chsstatus, falls dieser Schluessel aktiviert wurde, null sonst
	 */
	protected Status antworte(String text) {
		for(String str : schluessel) {
			if(text.indexOf(str) != -1)
				return ziel;
		}
		return null;
	}
	
}
