package game.logic.aktion;

import game.SpielWelt;
import game.items.AusruestbarerGegenstand;
import game.items.Gegenstand;

public class LegeAbAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der der Spieler ablegen soll(dieser muss sich im Inventar des Spielers befinden!).
	private short gegenstand;
	// Die SpielWelt ist static.
	
	/**
	 * Eine LegeAbAktion legt den uebergebenen Gegenstand ab, falls dieser ausgeruestet ist, ansonsten passiert nichts.
	 * @param ausruestbarerGegenstand Der Gegenstand, der abgelegt werden soll.
	 */
	public LegeAbAktion(AusruestbarerGegenstand ausruestbarerGegenstand) {
		this.gegenstand = ausruestbarerGegenstand.getId();
	}

	/**
	 * Fuehrt die Aktion aus, legt den Gegenstand ab, falls er ausgeruestet ist.
	 */
	@Override
	public void ausfuehren() {
		SpielWelt.WELT.getSpieler().legeAb((AusruestbarerGegenstand) Gegenstand.getGegenstand(gegenstand));
	}

}