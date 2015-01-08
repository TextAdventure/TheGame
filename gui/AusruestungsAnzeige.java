package gui;

import game.entity.Spieler;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import util.SpielerListener;

public class AusruestungsAnzeige extends Anzeige implements SpielerListener {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Konstruktor --- */
	
	/**
	 * Eine AusruestungsAnzeige zeigt die Ausruestung des Spielers.
	 * @param breite Die Breite der AusruestungsAnzeige.
	 * @param hoehe Die Hoehe der AusruestungsAnzeige.
	 */
	public AusruestungsAnzeige(int breite, int hoehe) {
		super(breite, hoehe);

		SimpleAttributeSet font = new SimpleAttributeSet();
	    StyleConstants.setFontSize(font, 14);
	    document.setParagraphAttributes(0, document.getLength(), font, false);
	}
	
	/* --- Methoden --- */

	/**
	 * Fuegt einen Text ohne Zeilenumbruch hinzu und zentriert alles in der Mitte der Anzeige.
	 * @param text Der hinzuzufuegende Text.
	 */
	@Override
	public void print(String text) {
	    super.print(text);
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
		document.setParagraphAttributes(0, document.getLength(), sas, false);
	}

	/**
	 * Wird aufgerufen, wenn der Spieler sich veraendert hat, in diesem Fall etwas ausgeruestet hat.
	 * @param evt Der neue Spieler wird uebergeben.
	 */
	@Override
	public void spielerUpdate(Spieler evt) {
		clear();
		evt.getAusruestung().zeigeAn(this);
	}

}