package npc;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Felix
 * Repräsentiert den aktuellen Zustand eines Gesprächs zwischen Spieler und einem NPC.
 */

public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String text;												//der Text, den der NPC in diesem Gesprächsstatus sagt
	private Vector<Schluessel> schluessel = new Vector<Schluessel>();	//die Schlüssel Worte, die der Spieler jetzt verwenden könnte
	
	public Status() {
		text = "";
	}
	public Status(String text) {
		this.text = text;
	}
	public Status(String text, Schluessel... schluessel) {
		this(text);
		this.setSchluessel(schluessel);
	}
	
	
	//Ein paar Getter und Setter
	protected String getText() {
		return text;
	}
	
	public void setSchluessel(Schluessel[] schluessel) {
		this.schluessel = new Vector<Schluessel>();
		for(Schluessel s : schluessel) this.schluessel.add(s);
	}
	public Schluessel[] getSchluessel() {
		return schluessel.toArray(new Schluessel[0]);
	}
	public void addSchluessel(Schluessel s) {
		schluessel.add(s);
	}
	
	
	/**
	 * Übermittelt dem NPC die Antwort des Spielers auf die Aussage des NPCs in diesem Gesptächsstatus.
	 * @return Den neuen Gesprächsstatus, in den das Gespräch durch die Antwort des Spielers übergeht,
	 * oder null, wenn die Antwort des Spielers keinen Sinn ergibt, bzw. den Status nicht ändert.
	 *  
	 */
	protected Status antworte(String antwort) {
		for(Schluessel key : getSchluessel()) {
			Status stat = key.antworte(antwort);
			if(stat != null)
				return stat;
		}
		return null;
	}
}
