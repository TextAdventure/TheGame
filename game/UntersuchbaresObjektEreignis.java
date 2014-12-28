package game;

import java.util.Vector;

import game.logic.Ereignis;

public class UntersuchbaresObjektEreignis extends UntersuchbaresObjekt implements IEreignis {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Die Variablen --- */
	
	// Alle Bedingungen, die vor einer Untersuchung geprueft werden.
	public Vector<Ereignis> vorUntersuchung;
	// Alle Bedingungen, die nach einer Untersuchung geprueft werden.
	public Vector<Ereignis> nachUntersuchung;

	/* --- Der Konstruktor --- */
	
	/**
	 * Erstellt ein neues UntersuchbaresObjektEreignis wie ein UntersuchbaresObjekt,
	 * aber es kann Ereignisse verwalten.
	 */
	public UntersuchbaresObjektEreignis(String name, String beschreibung) {
		super(name, beschreibung);
		vorUntersuchung = new Vector<Ereignis>();
		nachUntersuchung = new Vector<Ereignis>();
	}
	
	/* --- Die ueberschriebenen Methoden --- */
	
	@Override
	public UntersuchbaresObjektEreignis addVorBedingung(Ereignis... ereignisse) {
		for(Ereignis e : ereignisse)
			vorUntersuchung.add(e);
		return this;
	}

	@Override
	public UntersuchbaresObjektEreignis addNachBedingung(Ereignis... ereignisse) {
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
