package npc;

import java.io.Serializable;

/**
 * repräsentier ein Schlüsselwort, das der Spieler im Gespräch mit einem NPC verwenden kann.
 * @author Felix
 *
 */
public class Schluessel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//die Spielerantworten, bei denen dieser Schluessel aktiviert wird
	private String[] schluessel;
	//der neue Gesprächszustand
	private Status ziel;
	
	public Schluessel(Status ziel, String... schluessel) {
		this.ziel= ziel;
		this.schluessel = schluessel;
	}
	
	/**
	 * Reaktion auf Spielerantwort
	 * @param text Die Antwort des Spielers
	 * @return Den neuen Gesprächsstatus, falls dieser Schluessel aktiviert wurde, null sonst
	 */
	protected Status antworte(String text) {
		for(String str : schluessel) {
			if(text.indexOf(str) != -1)
				return ziel;
		}
		return null;
	}
	
}
