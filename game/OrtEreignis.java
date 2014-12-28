package game;

import game.logic.Ereignis;

import java.util.Vector;

public class OrtEreignis extends Ort implements IEreignis{

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Die Variablen --- */
	
	// Alle Ereignisse, die vor einer Untersuchung geprueft werden.
	public Vector<Ereignis> vorUntersuchung;
	// Alle Ereignisse, die nach einer Untersuchung geprueft werden.
	public Vector<Ereignis> nachUntersuchung;
	
	/* --- Der Konstruktor --- */
	
	/**
	 * Erstellt ein Ort Ereignis, dieses wird wie ein Ort erstellt,
	 * aber es kann auch Ereignisse verwalten.
	 */
	public OrtEreignis(String name, String beschreibung) {
		super(name, beschreibung);
		vorUntersuchung = new Vector<Ereignis>();
		nachUntersuchung = new Vector<Ereignis>();
	}
	
	/* --- Die ueberschriebenen Methoden --- */
	
	@Override
	public OrtEreignis addVorBedingung(Ereignis... ereignisse) {
		for(Ereignis e : ereignisse)
			vorUntersuchung.add(e);
		return this;
	}

	@Override
	public OrtEreignis addNachBedingung(Ereignis... ereignisse) {
		for(Ereignis e : ereignisse)
			nachUntersuchung.add(e);		
		return this;
	}

	@Override
	public void vorUntersuchung() {
		for(Ereignis e : vorUntersuchung.toArray(new Ereignis[0]))
			if(e.eingetreten())
				vorUntersuchung.remove(e);
	}

	@Override
	public void nachUntersuchung() {
		for(Ereignis e : nachUntersuchung.toArray(new Ereignis[0]))
			if(e.eingetreten())
				nachUntersuchung.remove(e);
	}

}
