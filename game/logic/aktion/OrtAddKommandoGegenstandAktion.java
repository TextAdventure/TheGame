package game.logic.aktion;

import game.Ort;
import game.items.Gegenstand;
import game.items.KommandoGegenstand;

public class OrtAddKommandoGegenstandAktion extends Aktion {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort an dem der Einsatz des Gegenstands moeglich ist.
	private Ort ort;
	// Der Gegenstand, der mit dem Kommando "verwende" aufgerufen werden kann.
	private short gegenstand;
	// Die Meldung wird von Aktion uebernommen.
	// Die SpielWelt ebenfalls.
	
	/**
	 * Eine AddKommandoGegenstandAktion fuegt einem Ort ein Ereignis hinzu, das immer ueberprueft wird, wenn
	 * der Gegenstand verwendet wird und dabei wird die Meldung angezeigt.
	 * @param ort Der Ort an dem der Gegenstand verwendbar ist.
	 * @param gegenstand Dieser Gegenstand muss verwendet werden, um die Ereignisse darin ausloesen zu koennen.
	 * @param meldung Die Meldung, die dem Spieler nach der Ausfuehrung angezeigt wird.
	 */
	public OrtAddKommandoGegenstandAktion(Ort ort, KommandoGegenstand gegenstand, String meldung) {
		this.ort = ort;
		this.gegenstand = (short) gegenstand.getId();
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt einem Ort einen Kommandogegenstand hinzu samt Ereignis) 
	 * und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		ort.addKommandoGegenstand((KommandoGegenstand) Gegenstand.getGegenstand(gegenstand));
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}
	
}
