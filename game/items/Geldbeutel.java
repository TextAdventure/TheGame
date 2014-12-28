package game.items;

import java.io.Serializable;
import java.util.Vector;

public class Geldbeutel implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Alle Waehrungen in diesem Geldbeutel.
	private Vector<Stapel> geld;	

	/**
	 * Ein Geldbeutel verwaltet alle Waehrungen im Spiel.
	 */
	public Geldbeutel() {
		geld = new Vector<Stapel>();
		
		for(Gegenstand g : Gegenstand.GEGENSTAENDE) {
			if(g instanceof Waehrung) {
				for(Stapel s : geld)
					if(((Waehrung) s.getGegenstand()).getWaehrungsName().equals(((Waehrung) g).getWaehrungsName())) {
						break;
					}
				geld.add(new Stapel((Waehrung) g, 0));
			}
		}
	}

	/**
	 * Gibt die Menge einer bestimmten Waehrung zurueck.
	 * @param waehrung Die Waehrung, dessen Menge bestimmt werden soll.
	 * @return Die Menge der gesuchten Waehrung, -1 falls es sie nicht gibt.
	 */
	public int getMenge(Waehrung waehrung) {
		for(Stapel s : geld) {
			if(((Waehrung) s.getGegenstand()).getWaehrungsName().equals(waehrung.getWaehrungsName()) && s.getGegenstand() == waehrung) {
				Waehrung w = (Waehrung) s.getGegenstand();
				
				int resultat = getKleinsteWaehrung(w).getAnzahl();
				
				while(w.hatKleinereWaehrung()) {
					w = w.getKleinereWaehrung();
					if(w.getKurs() > resultat)
						resultat = 0;
					else
						resultat = (int) Math.floor(resultat / w.getKurs());
				}
				if(w == waehrung)
					resultat = resultat % w.getKurs();
				return resultat;
			}				
		}
		return -1;
	}
	/**
	 * Fuegt einer bestimmten Waehrung einen Betrag hinzu.
	 * @param waehrung Die Waehrung, die erhoeht werden soll.
	 * @param betrag Der Betrag, um welchen sie erhoet werden soll.
	 */
	public void addMenge(Waehrung waehrung, int betrag) {
		for(Stapel s : geld) {
			if(((Waehrung) s.getGegenstand()).getWaehrungsName().equals(waehrung.getWaehrungsName())) {
				getKleinsteWaehrung(waehrung).addAnzahl(waehrung.kleinsteWaehrungBetrag(betrag));
				return;
			}
		}
	}
	/**
	 * Gibt die kleinste Waehrung fuer eine Waehrung zurueck.
	 * @param waehrung Die Waehrung fuer die die kleinste ermittelt werden soll.
	 * @return Einen Stapel mit dieser Waehrung.
	 */
	private Stapel getKleinsteWaehrung(Waehrung waehrung) {
		Waehrung w = waehrung;
		
		while(w.hatKleinereWaehrung())
			w = w.getKleinereWaehrung();
		
		for(Stapel s : geld) {
			if(s.getGegenstand() == w)
					return s;
		}
		return null;
	}
	/**
	 * Gibt den Namen einer Waehrung zurueck.
	 * @param waehrung Die Waehrung fuer die der Name gesucht wird.
	 * @return Den Namen der Waehrung, dabei wird der Plural und Sigular beachtet.
	 */
	public String getName(Waehrung waehrung) {
		for(Stapel s : geld)
			if(s.getGegenstand() == waehrung)
				return this.getMenge(waehrung) != 1 ? waehrung.getPlural() : waehrung.getName();
		return "";
	}
	/**
	 * Gibt alle Waehrungen in diesem Geldbeutel zurueck.
	 * @return Alle Waehrungen in diesem Geldbeutel.
	 */
	public Waehrung[] getWaehrungen() {
		Vector<Waehrung> w = new Vector<Waehrung>();
		for(Stapel s : geld)
			w.add((Waehrung) s.getGegenstand());
		return w.toArray(new Waehrung[0]);
	}
	/**
	 * Gibt ein Array mit allen Stapeln in diesem Geldbeutel zurueck.
	 * @return Dein Array mit allen Stapeln.
	 */
	public Stapel[] getAlleStapel() {
		return geld.toArray(new Stapel[0]);
	}
	/**
	 * Gibt zurueck, ob der Geldbeutel leer ist oder nicht.
	 * @return true, wenn er leer ist, ansonsten false.
	 */
	public boolean istLeer() {
		for(Stapel s : geld)
			if(s.getAnzahl() > 0)
				return false;
		return true;
	}
	
}
