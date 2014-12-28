package gui;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *  Diese Klasse ist das JTextPane, in dem dass Spielgeschehen angezeigt wird.
 */
public class Anzeige extends JTextPane {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// In diesem JScrollPane befindet sich die Anzeige, dadurch kann beliebig weit nach oben gescrollt werden.
	private JScrollPane scroll;
	// Das StyledDocument dieser Anzeige, dadurch laesst sich Farbe und aehnliches einfuegen.
	private StyledDocument document;
	  
	/**
	 *  Eine neue Anzeige wird erstellt, indem man ihr einen PipedInputStream uebergibt, der die Befehle uebermittelt.
	 */
	public Anzeige(){
		scroll = new JScrollPane(this);
	    document = this.getStyledDocument();
	    
	    scroll.setSize(690, 370);
	    
	    setEditable(false);
	}
	  
	/**
	 *  Diese Methode fuegt den uebergebenen String der Anzeige hinzu, davor ist ein Zeilenumbruch.
	 *  text: der neue Text, der hinzugefuegt werden soll.
	 */
	public void println(String text){
		print("\n" + text);
	}
	  
	/**
	 *  Diese Methode fuegt den uebergebenen String der Anzeige hinzu, davor ist KEIN Zeilenumbruch.
	 *  text: der neue Text, der hinzugefuegt werden soll.
	 */
	public void print(String text){
	    try{
	    	SimpleAttributeSet size = new SimpleAttributeSet();
	    	StyleConstants.setFontSize(size, 14);
	    	document.insertString(document.getLength(), text, null);
	    	document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), size, false);
	    }catch(BadLocationException e){
	    	System.err.println(e.getStackTrace());
	    }
	}
	  
	/**
	 *  Diese Methode fuegt den uebergebenen String der Anzeige hinzu und er wird fett, davor ist KEIN Zeilenumbruch.
	 *  text: der neue Text, der hinzugefuegt werden soll.
	 */
	public void printBold(String text){
		print(text);
	    SimpleAttributeSet bold = new SimpleAttributeSet();
	    StyleConstants.setBold(bold, true);
	    document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), bold, false);
	}
	  
	/**
	 *  Diese Methode fuegt den uebergebenen String der Anzeige hinzu, davor ist ein Zeilenumbruch.
	 *  text: der neue Text, der hinzugefuegt werden soll.
	 */
	public void println(){
		println("");
	}
	  
	/**
	 *  Die Anzeige wird geloescht, sodass sie neu beschrieben werden kann.
	 */
	public void clear(){
		try{
			document.remove(0, document.getLength());
	    }catch(BadLocationException e){
	    	System.err.println(e.getStackTrace());
	    }
	}
	  
	/**
	 *  Diese Methode gibt das JScrollPane zurueck.
	 */
	public JScrollPane getJScrollPane(){
	    return scroll;
	}
}