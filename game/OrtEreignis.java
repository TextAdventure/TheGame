package game;

import game.logic.Ereignis;

import java.util.Vector;

/**
 * Erweitert einen Ort so, dass er Ereignisse prueft bevor er Betreten wird und danach.
 * @author Marvin
 */
public class OrtEreignis extends Ort implements IEreignis {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Alle Ereignisse, die vor einer Untersuchung geprueft werden.
	public Vector<Ereignis> vorUntersuchung;
	// Alle Ereignisse, die nach einer Untersuchung geprueft werden.
	public Vector<Ereignis> nachUntersuchung;
	
	/* --- Konstruktor --- */
	
	/**
	 * Erstellt ein Ort Ereignis, dieses wird wie ein Ort erstellt,
	 * aber es kann auch Ereignisse verwalten.
	 * @param name Der Name dieses Orts.
	 * @param beschreibung Die Beschreibung des Orts.
	 */
	public OrtEreignis(String name, String beschreibung) {
		super(name, beschreibung);
		vorUntersuchung = new Vector<Ereignis>();
		nachUntersuchung = new Vector<Ereignis>();
	}
	
	/* --- ueberschriebene Methoden --- */
	
	/**
	 * Fuegt Ereignisse hinzu, die vor dem Betreten des Orts geprueft werden.
	 * @param ereignisse Die Ereignisse, die dann eintreten koennen.
	 * @return Sich selbst.
	 */
	@Override
	public OrtEreignis addVorBedingung(Ereignis... ereignisse) {
		for(Ereignis e : ereignisse)
			vorUntersuchung.add(e);
		return this;
	}
	
	/**
	 * Fuegt Ereignisse hinzu, die nach dem Betreten des Orts geprueft werden.
	 * @param ereignisse Die Ereignisse, die dann eintreten koennen.
	 * @return Sich selbst.
	 */
	@Override
	public OrtEreignis addNachBedingung(Ereignis... ereignisse) {
		for(Ereignis e : ereignisse)
			nachUntersuchung.add(e);		
		return this;
	}
	
	/**
	 * Die Ereignisse werden geprueft, die vor dem Betreten Eintreten koennen.
	 */
	@Override
	public void vorUntersuchung() {
		for(Ereignis e : vorUntersuchung.toArray(new Ereignis[0]))
			if(e.eingetreten())
				vorUntersuchung.remove(e);
	}
	
	/**
	 * Die Ereignisse werden geprueft, die nach dem Betreten Eintreten koennen.
	 */
	@Override
	public void nachUntersuchung() {
		for(Ereignis e : nachUntersuchung.toArray(new Ereignis[0]))
			if(e.eingetreten())
				nachUntersuchung.remove(e);
	}

}