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

	// Listener Methode //

	/**
	 * Wird aufgerufen, wenn sich das Inventar des Spielers aendert und uebergibt das neue Inventar, sodass die Anzeige aktualisiert werden kann.
	 * @param evt Das neue Inventar.
	 */
	@Override
	public void inventarUpdate(Inventar evt) {
		clear();
	    printSize("Inventar", 18);
	    for(Stapel s : evt.getStapel())
	    	if(s.getAnzahl() > 1)
	    		println(Integer.toString(s.getAnzahl()) + " " + s.getGegenstand().getPluralExtended());
	    	else
	    		println(s.getGegenstand().getNameExtended());

	    if(evt.getGeldbeutel().istLeer())
	    	return;

	    print("\n\n");
	    printSize("Währungen", 18);
	    for(Waehrung w : evt.getGeldbeutel().getWaehrungen())
	    	println(Integer.toString(evt.getGeldbeutel().getMenge(w)) + " " + evt.getGeldbeutel().getNameExtended(w));
	}
	
}