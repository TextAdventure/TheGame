package gui;

import game.entity.Faehigkeit;
import game.entity.Spieler;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import util.SpielerListener;

/**
 * Zeigt die Faehigkeiten des Spielers an, wird durch den SpielerListener aktuell gehalten.
 * @author Marvin
 */
public class FaehigkeitenAnzeige extends Anzeige implements SpielerListener {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Konstruktor --- */
	
	/**
	 * Eine FaehigkeitenAnzeige funtioniert wie die normale Anzeige, ist aber auf die Faehigkeiten des
	 * Spielers spezialisiert, das durch einen SpielerListener immer aktualisiert wird.
	 * @param breite Die Breite der FaehigkeitenAnzeige.
	 * @param hoehe Die Hoehe der FaehigkeitenAnzeige.
	 */
	public FaehigkeitenAnzeige(int breite, int hoehe) {
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
	
	// Listener Methode //
	
	/**
	 * Wird aufgerufen, wenn sich der Spieler veraendert hat und uebergibt den neuen Spieler, sodass die Anzeige aktualisiert werden kann.
	 * @param evt Der neue Spieler.
	 */
	@Override
	public void spielerUpdate(Spieler evt) {
		clear();
	    printSize("Fähigkeiten", 18);
	    for(Faehigkeit f : evt.getFaehigkeiten())
	    	print("\n" + f.getName());		
	}

}