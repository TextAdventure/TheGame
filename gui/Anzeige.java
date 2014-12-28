package gui;

import java.awt.Color;

import game.Ort;
import game.UntersuchbaresObjekt;
import game.entity.EntityAttribut;
import game.entity.EntityResistenz;
import game.items.Gegenstand;

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
public class Anzeige extends JTextPane {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// In diesem JScrollPane befindet sich die Anzeige, dadurch kann beliebig weit nach oben gescrollt werden.
	protected JScrollPane scroll;
	// Das StyledDocument dieser Anzeige, dadurch laesst sich Farbe und aehnliches einfuegen.
	protected StyledDocument document;

	/**
	 *  Eine neue Anzeige wird erstellt, indem man ihr einen PipedInputStream uebergibt, der die Befehle uebermittelt.
	 */
	public Anzeige() {
		scroll = new JScrollPane(this);
	    document = this.getStyledDocument();

	    scroll.setSize(690, 370);

	    setEditable(false);
	}

	/**
	 *  Diese Methode fuegt den uebergebenen String der Anzeige hinzu, davor ist ein Zeilenumbruch.
	 *  text: der neue Text, der hinzugefuegt werden soll.
	 */
	public void println(String text) {
		print("\n" + text);
	}

	/**
	 *  Diese Methode fuegt den uebergebenen String der Anzeige hinzu, davor ist KEIN Zeilenumbruch.
	 *  text: der neue Text, der hinzugefuegt werden soll.
	 */
	public void print(String text) {
		if(text.contains("<c=") && !text.contains("<b"))
			colorText(text, false);
		else if(text.contains("<c=") && text.contains("<b"))
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
	 *  Diese Methode fuegt den uebergebenen String der Anzeige hinzu, davor ist ein Zeilenumbruch.
	 *  text: der neue Text, der hinzugefuegt werden soll.
	 */
	public void println() {
		println("");
	}

	/**
	 * Gibt ein IPrintable aus, zuerst den Namen(fett), dann neue Zeile und zu Schluss die Beschreibung.
	 * Dabei werden Farben ersetzt und auch Parameter.
	 */
	public void printPrintable(IPrintable print) {
		String name = print.getNameExtended();
		String description = print.getDescription();


		// Alle Parameter ersetzen
		for(String s : print.getParams()) {
			name = name.replaceAll("<p=" + s + ">" , print.getParam(s));
			description = description.replaceAll("<p=" + s + ">", print.getParam(s));
		}

		// faerben
		colorText(name, true);

		// Evtl. Werte ausgeben
		if(print instanceof Gegenstand) {
			Gegenstand g = (Gegenstand)print;
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
			if(print instanceof Ort) ((Ort) print).besuchen();
			if(print instanceof UntersuchbaresObjekt) ((UntersuchbaresObjekt) print).untersuchen();
			colorText("\n" + description, false);
		} else
			colorText("\n\n" + description, false);
	}

	/**
	 *
	 * @param text
	 * @param bold
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

			Color c = new Color(0, 0, 0);
			try {
				c = new Color(Integer.decode("0x" + s.substring(0, 6).toLowerCase()));
			} catch (NumberFormatException e) {
				c = Farbe.getFarbe(s.substring(0, s.indexOf('>'))).getColor();
			}

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
	 *  Die Anzeige wird geloescht, sodass sie neu beschrieben werden kann.
	 */
	public void clear() {
		try {
			document.remove(0, document.getLength());
	    } catch(BadLocationException e) {
	    	System.err.println(e.getStackTrace());
	    }
	}

	/**
	 *  Diese Methode gibt das JScrollPane zurueck.
	 */
	public JScrollPane getJScrollPane() {
	    return scroll;
	}


}
