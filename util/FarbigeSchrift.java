package util;

import java.awt.Color;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Eine kleine Hilfsklasse, die es ermoeglicht Text in einem EditorPane farbig zu machen
 */
public class FarbigeSchrift extends DefaultStyledDocument {
	
	// Die serielle Versionnummer
	private static final long serialVersionUID = 1L;

	/**
	 * Veraendert die Farbe des Texts 
	 * start: der Beginn der Umfaerbung
	 * ende:  Ende der Umfaerbung
	 * farbe: die neue Farbe des Texts
	 */
	public void setzeFarbe(int start, int ende, Color farbe){
	    SimpleAttributeSet sas = new SimpleAttributeSet();
	    StyleConstants.setForeground(sas, farbe);
	    setCharacterAttributes(start, ende - start, sas, false);
	}
}
