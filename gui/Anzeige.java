package gui;

import java.awt.Color;

import game.Ort;
import game.UntersuchbaresObjekt;
import game.entity.EntityAttribut;
import game.entity.EntityResistenz;
import game.items.AusruestbarerGegenstand;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import util.Farbe;
import util.IPrintable;

/**
 *  Diese Klasse ist das JTextPane, in dem dass Spielgeschehen angezeigt wird.
 */
/**
 * Ein veraendertes JTextPane, das die Moeglichkeit bietet Text einzufaerben, ihn fett, unterstrichen oder kursiv zu machen.
 * @author Marvin
 */
public class Anzeige extends JTextPane {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// In diesem JScrollPane befindet sich die Anzeige, dadurch kann beliebig weit nach oben gescrollt werden.
	protected JScrollPane scroll;
	// Das StyledDocument dieser Anzeige, dadurch laesst sich Farbe und aehnliches einfuegen.
	protected StyledDocument document;

	/* --- Konstruktor --- */
	
	/**
	 * Eine Anzeige unterstuetzt die Darstellung von formatiertem Text und eine Vielzahl anderer Funktionen,
	 * wie das darstellen von IPrintables. Sie kann auch nicht von Hand veraendert werden und hat Scrollbars,
	 * damit mehr Text angezeigt werden kann.
	 * @param breite Die Breite der Anzeige.
	 * @param hoehe Die Hoehe der Anzeige.
	 */
	public Anzeige(int breite, int hoehe) {
		scroll = new JScrollPane(this);
	    document = this.getStyledDocument();

	    scroll.setSize(breite, hoehe);

	    setEditable(false);
	}
	
	/* --- Methoden --- */

	/**
	 * Fuegt den Text der Anzeige hinzu, davor befindet sich aber ein Zeilenumbruch.
	 * @param text Der Text, der der Anzeige hinzugefuegt werden soll.
	 */
	public void println(String text) {
		print("\n" + text);
	}

	/**
	 * Fuegt den Text direkt an den bereits vorhandenen hinzu, ohne einen Zeilenumbruch.
	 * @param text Der Text, der der Anzeige hinzugefuegt werden soll.
	 */
	public void print(String text) {
		if(text.contains("<c=") && !text.contains("<b>"))
			colorText(text, false);
		else if(text.contains("<c=") && text.contains("<b>"))
			colorText(text, true);
		else
			try {
				document.insertString(document.getLength(), text, null);

				SimpleAttributeSet size = new SimpleAttributeSet();
				StyleConstants.setFontSize(size, 14);
				document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), size, false);
			} catch(BadLocationException e) {
				System.err.println(e.getStackTrace());
			}
	}
	
	/**
	 * Fuegt einen Text ohne Zeilenumbruch hinzu und aendert die Groesse dieses Texts.
	 * @param text Der hinzuzufuegende Text.
	 * @param size Die Groesse des Texts.
	 */
	public void printSize(String text, int size) {
		this.print(text);
	    SimpleAttributeSet sas = new SimpleAttributeSet();
	    StyleConstants.setFontSize(sas, size);
	    document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), sas, false);
	}

	/**
	 *  Diese Methode fuegt den uebergebenen String der Anzeige hinzu und er wird fett, davor ist KEIN Zeilenumbruch.
	 *  text: der neue Text, der hinzugefuegt werden soll.
	 *
	public void printBold(String text) {
		print(text);
	    SimpleAttributeSet bold = new SimpleAttributeSet();
	    StyleConstants.setBold(bold, true);
	    document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), bold, false);
	}*/

	/**
	 * Fuegt der Anzeige eine Leerzeile hinzu.
	 */
	public void println() {
		println("");
	}

	/**
	 * Gibt ein IPrintable aus, zuerst den Namen(fett), dann neue Zeile und zu Schluss die Beschreibung.
	 * Dabei werden Farben ersetzt und auch Parameter.
	 * @param print Das auszugebende Objekt.
	 */
	public void printPrintable(IPrintable print) {
		String name = print.getNameExtended();
		String beschreibung = print.getBeschreibung();


		// Alle Parameter ersetzen
		for(String s : print.getParams()) {
			name = name.replaceAll("<p=" + s + ">" , print.getParam(s));
			beschreibung = beschreibung.replaceAll("<p=" + s + ">", print.getParam(s));
		}
		
		// faerben
		colorText(name, true);

		// Evtl. Werte ausgeben
		if(print instanceof AusruestbarerGegenstand) {
			AusruestbarerGegenstand g = (AusruestbarerGegenstand)print;
			if(g.hasWerte()) {
				println();
				for(EntityAttribut ea : g.getAttribute()) {
					// Vorzeichen bestimmen
					if(ea.getWert() < 0)
						println(ea.getWert() + " " + ea.getAttribut().getName());
					else if(ea.getWert() > 0)
						println("+" + ea.getWert() + " " + ea.getAttribut().getName());
				}
				for(EntityResistenz er : g.getResistenzen()) {
					// Vorzeichen bestimmen
					if(er.getWert() < 0)
						println(er.getWert() + "% " + er.getResistenz().getName());
					else if(er.getWert() > 0)
						println("+" + er.getWert() + "% " + er.getResistenz().getName());
				}
			}
		}

		// Einen Ort besuchen
		if(print instanceof Ort || print instanceof UntersuchbaresObjekt) {
			if(print instanceof Ort)
				((Ort) print).besuchen();
			if(print instanceof UntersuchbaresObjekt)
				((UntersuchbaresObjekt) print).untersuchen();
			colorText("\n" + beschreibung, false);
		} else {
			colorText("\n\n" + beschreibung, false);
		}
	}

	/**
	 * Faerbt den Text mit Farbcodes, die darin stehen und fuegt ihn direkt hinzu, ohne Zeilenumbruch.
	 * @param text Der hinzuzufuegende Text.
	 * @param bold True, wenn er fett sein soll, ansonsten false.
	 */
	protected void colorText(String text, boolean bold) {
		String[] split = text.split("<c=");
		for(String s : split) {
			if(!s.contains("</c>")) {
				print(s);

				SimpleAttributeSet boldText = new SimpleAttributeSet();
				if(bold)
					StyleConstants.setBold(boldText, true);
				document.setCharacterAttributes(this.document.getLength() - s.length(), s.length(), boldText, false);
				continue;
			}
			
			Color c = Farbe.getFarbe(s.substring(0, s.indexOf('>'))).getColor();

			String actual = s.substring(s.indexOf(">") + 1, s.indexOf("</c>"));
			int index = actual.length();
			actual += s.substring(s.indexOf("</c>") + 4);
			print(actual);

			SimpleAttributeSet colored = new SimpleAttributeSet();
			StyleConstants.setForeground(colored, c);
			if(bold)
				StyleConstants.setBold(colored, true);
			document.setCharacterAttributes(this.document.getLength() - actual.length(), index, colored, false);
		}

	}

	/**
	 * Loescht den gesamten Inhalt der Anzeige, dadurch kann sie wieder beschrieben werden.
	 */
	public void clear() {
		try {
			document.remove(0, document.getLength());
	    } catch(BadLocationException e) {
	    	System.err.println(e.getStackTrace());
	    }
	}

	/**
	 * Gibt das JScrollPane zurueck, in dem sich diese Anzeige befindet.
	 * @return Das JScrollPane, in dem diese Anzeige ist.
	 */
	public JScrollPane getJScrollPane() {
	    return scroll;
	}
	
}