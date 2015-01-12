package gui;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import util.IPrintable;
import game.entity.Faehigkeit;
import game.entity.Spieler;
import game.items.Waffenart;

/**
 * Zeigt nuetzliche Informationen zu Dingen an, wie z.B. Gegenstaende und Faehigkeiten. Um etwas darin dazustellen muss man es markieren und I druecken.
 * @author Marvin
 */
public class InfoPanel extends Anzeige {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Konstruktor --- */

	/**
	 * Ein InfoPanel zeigt nuetzliche Informationen an, ohne das die eigentliche Anzeige dafuer gemutzt wird.
	 * @param breite Die Breite des InfoPanels.
	 * @param hoehe Die Hoehe des InfoPanels.
	 */
	public InfoPanel(int breite, int hoehe) {
		super(breite, hoehe);
	}
	
	/* --- Methoden --- */
	
	/**
	 * Zeigt die Informationen zu einem IPrintable in dem InfoPanel an.
	 * @param print Das anzuzeigende IPrintable.
	 */
	@Override
	public void printPrintable(IPrintable print) {
		clear();
		super.printPrintable(print);
	}	
	
	/**
	 * Zeigt alle Informationen zu einer Faehigkeit an.
	 * @param faehigkeit Die Faehigkeit, die angezeigt werden soll.
	 * @param spieler Der Spieler wird benoetigt, um seien Attribute zu kennen.
	 */
	public void zeigeFaehigkeitAn(Faehigkeit faehigkeit, Spieler spieler) {
		clear();
		
		if(faehigkeit == null)
			return;
		
		colorText(faehigkeit.getName(), true);
		
		println("Schadensart: " + faehigkeit.getSchadensart().getName());		
		println("Schaden: " +  faehigkeit.getBonusExtended(spieler));
		println("\nMögliche Waffen:");
		for(Waffenart wa : faehigkeit.getGueltigeWaffen())
			println("\t" + wa.getName());
	}
	
	/**
	 * Fuegt einen Text hinzu, OHNE einen Zeilenumbruch(wird ueberschreiben, um einen kleinere Schriftgroesse zu verwenden).
	 * @param text Der Text, der angefuegt werden soll.
	 */
	@Override
	public void print(String text) {
		super.print(text);
		
		SimpleAttributeSet size = new SimpleAttributeSet();
    	StyleConstants.setFontSize(size, 12);
    	document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), size, false);
	}
	
}