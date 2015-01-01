package gui;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import game.items.Inventar;
import game.items.Stapel;
import game.items.Waehrung;
import util.InventarListener;

/**
 * Zeigt das Inventar mit allen Gegenstaende und Waehrungen des Spielers an.
 * @author Marvin
 */
public class InventarAnzeige extends Anzeige implements InventarListener {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Konstruktor --- */

	/**
	 * Eine InventarAnzeige funtioniert wie die normale Anzeige, ist aber auf das Inventar des
	 * Spielers spezialisiert, das durch einen InventarListener immer aktualisiert wird.
	 * @param breite Die Breite der InventarAnzeige.
	 * @param hoehe Die Hoehe der InventarAnzeige.
	 */
	public InventarAnzeige(int breite, int hoehe) {
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
	 * Fuegt einen Text ohne Zeilenumbruch hinzu und aendert die Groesse dieses Texts.
	 * @param text Der hinzuzufuegende Text.
	 * @param size Die Groesse des Texts.
	 */
	private void printSize(String text, int size) {
		this.print(text);
	    SimpleAttributeSet sas = new SimpleAttributeSet();
	    StyleConstants.setFontSize(sas, size);
	    document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), sas, false);
	}

	// Listener Methode //

	/**
	 * Wird aufgerufen, wenn sich das Inventar des Spielers aendert und uebergibt das neue Inventar, sodass die Anzeige aktualisiert werden kann.
	 * @param evt Das neue Inventar.
	 */
	@Override
	public void inventarUpdate(Inventar evt) {
		clear();
	    printSize("Inventar:", 18);
	    for(Stapel s: evt.getStapel())
	    	printSize("\n" + s.getAnzahl() + " " + s.getGegenstand().getNameExtended(), 14);

	    if(evt.getGeldbeutel().istLeer())
	    	return;

	    print("\n\n");
	    printSize("Währungen:", 18);
	    for(Waehrung w : evt.getGeldbeutel().getWaehrungen())
	    	printSize("\n" + evt.getGeldbeutel().getMenge(w) + " " + evt.getGeldbeutel().getName(w), 14);
	}
	
}