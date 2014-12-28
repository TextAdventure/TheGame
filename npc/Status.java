package npc;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Felix
 * Repr�sentiert den aktuellen Zustand eines Gespr�chs zwischen Spieler und einem NPC.
 */

public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String text;												//der Text, den der NPC in diesem Gespr�chsstatus sagt
	private Vector<Schluessel> schluessel = new Vector<Schluessel>();	//die Schl�ssel Worte, die der Spieler jetzt verwenden k�nnte
	
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
	 * �bermittelt dem NPC die Antwort des Spielers auf die Aussage des NPCs in diesem Gespt�chsstatus.
	 * @return Den neuen Gespr�chsstatus, in den das Gespr�ch durch die Antwort des Spielers �bergeht,
	 * oder null, wenn die Antwort des Spielers keinen Sinn ergibt, bzw. den Status nicht �ndert.
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
