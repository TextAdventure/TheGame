package game.logic.aktion;

import game.SpielWelt;
import game.items.AusruestbarerGegenstand;
import game.items.Gegenstand;

public class RuesteAusAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der dem Spieler ausgeruestet wird(dieser muss sich im Inventar des Spielers befinden!).
	private short gegenstand;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine RuesteAusAktion ruestet dem Spieler einen @gegenstand aus (dieser muss sich im Inventar des Spielers befinden!) und gibt danach eine @meldung aus.
	 * @param gegenstand Der Gegenstand, der dem Spieler ausgeruestet werden soll.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public RuesteAusAktion(AusruestbarerGegenstand gegenstand, String meldung) {
		this.gegenstand = (short) gegenstand.getId();
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(ruestet dem Spieler einen Gegenstand aus) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		SpielWelt.WELT.getSpieler().ruesteAus((AusruestbarerGegenstand) Gegenstand.getGegenstand(gegenstand));
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}
