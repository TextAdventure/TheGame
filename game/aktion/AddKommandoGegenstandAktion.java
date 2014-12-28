package game.aktion;

import game.Gegenstand;
import game.Ort;
import game.bedingung.Bedingung;

public class AddKommandoGegenstandAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort an dem der Einsatz des Gegenstands moeglich ist.
	private Ort ort;
	// Der Gegenstand, der mit dem Kommando "verwende" aufgerufen werden kann.
	private Gegenstand gegenstand;
	// Die Bedingung, die ueberprueft wird, wenn der Gegenstand verwendet wird.
	private Bedingung bedingung;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine AddKommandoGegenstandAktion fuegt einem @ort eine @bedingung hinzu, die immer ueberprueft wird, wenn
	 * der @gegenstand verwendet wird und dabei wird die @meldung angezeigt.
	 * @param ort Der Ort an dem der Gegenstand verwendbar ist.
	 * @param gegenstand Dieser Gegenstand muss verwendet werden, um die Bedingung zu ueberpruefen.
	 * @param bedingung Die Bedingung wird ueberprueft und gegebenenfalls wird ihre Aktion ausgefuehrt.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public AddKommandoGegenstandAktion(Ort ort, Gegenstand gegenstand, Bedingung bedingung, String meldung) {
		this.ort = ort;
		this.gegenstand = gegenstand;
		this.bedingung = bedingung;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt einem Ort einen Kommandogegenstand hinzu samt Bedingung) 
	 * und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		ort.addKommandoGegenstand(gegenstand, bedingung);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		if(!meldung.equalsIgnoreCase(""))
			spielwelt.println(meldung + "\n\n");
	}
	
}
