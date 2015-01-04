package game.logic.aktion;

import game.SpielWelt;
import game.items.AusruestbarerGegenstand;
import game.items.Gegenstand;

public class RuesteAusAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der dem Spieler ausgeruestet wird(dieser muss sich im Inventar des Spielers befinden!).
	private short gegenstand;
	// Die SpielWelt ist static.
	
	/**
	 * Eine RuesteAusAktion ruestet dem Spieler einen Gegenstand aus (dieser muss sich im Inventar des Spielers befinden!).
	 * @param gegenstand Der Gegenstand, der dem Spieler ausgeruestet werden soll.
	 */
	public RuesteAusAktion(AusruestbarerGegenstand gegenstand) {
		this.gegenstand = (short) gegenstand.getId();
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(ruestet dem Spieler einen Gegenstand aus).
	 */
	@Override
	public void ausfuehren() {
		SpielWelt.WELT.getSpieler().ruesteAus((AusruestbarerGegenstand) Gegenstand.getGegenstand(gegenstand));
	}
	
}
