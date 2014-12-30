package game;

import java.util.Vector;

import game.logic.Ereignis;

/**
 * Erweitert ein UntersuchbaresObjekt so, dass es Ereignisse prueft bevor es untersucht wird und danach.
 * @author Marvin
 */
public class UntersuchbaresObjektEreignis extends UntersuchbaresObjekt implements IEreignis {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Variablen --- */
	
	// Alle Bedingungen, die vor einer Untersuchung geprueft werden.
	public Vector<Ereignis> vorUntersuchung;
	// Alle Bedingungen, die nach einer Untersuchung geprueft werden.
	public Vector<Ereignis> nachUntersuchung;

	/* --- Konstruktor --- */
	
	/**
	 * Erstellt ein neues UntersuchbaresObjektEreignis wie ein UntersuchbaresObjekt,
	 * aber es kann Ereignisse verwalten.
	 * @param name Der Name des Objekts.
	 * @param beschreibung Die Beschreibung des Objekts.
	 */
	public UntersuchbaresObjektEreignis(String name, String beschreibung) {
		super(name, beschreibung);
		vorUntersuchung = new Vector<Ereignis>();
		nachUntersuchung = new Vector<Ereignis>();
	}
	
	/* --- ueberschriebenen Methoden --- */
	
	/**
	 * Fuegt Ereignisse hinzu, die vor dem Untersuchen des Objekts geprueft werden.
	 * @param ereignisse Die Ereignisse, die dann eintreten koennen.
	 * @return Sich selbst.
	 */
	@Override
	public UntersuchbaresObjektEreignis addVorBedingung(Ereignis... ereignisse) {
		for(Ereignis e : ereignisse)
			vorUntersuchung.add(e);
		return this;
	}

	/**
	 * Fuegt Ereignisse hinzu, die nach dem Untersuchen des Objekts geprueft werden.
	 * @param ereignisse Die Ereignisse, die dann eintreten koennen.
	 * @return Sich selbst.
	 */
	@Override
	public UntersuchbaresObjektEreignis addNachBedingung(Ereignis... ereignisse) {
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