package gui;

import game.entity.Faehigkeit;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import util.PlayerEvent;
import util.PlayerListener;

public class SkillDisplay extends JTextPane implements PlayerListener{

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Die Variablen --- */
	
	// Das JScrollPane, in dem sich das TextPane befindet.
	private JScrollPane scroll;
	// Das Document, das beschrieben wird.
	private StyledDocument document;
	
	/* --- Der Konstruktor --- */
	
	/**
	 * Ein SkillDisplay wird ein Spieler uebergeben, sodass dieses das SkillDisplay aktualisiert.
	 */
	public SkillDisplay() {
		scroll = new JScrollPane(this);
	    document = this.getStyledDocument();
	    SimpleAttributeSet font = new SimpleAttributeSet();
	    StyleConstants.setFontSize(font, 14);
	    document.setParagraphAttributes(0, document.getLength(), font, false);

	    scroll.setSize(290, 400);
	    
	    this.setEditable(false);
	}
	  
	/* --- Die Methoden --- */
	
	/**
	 * Gibt das JScrollPane zurueck.
	 * @return Sein JScrollPane.
	 */
	public JScrollPane getJScrollPane(){
	    return scroll;
	}
	  
	/**
	 * Fuegt einen String dem JTextPane hinzu, ohne Zeilenumbruch.
	 * @param text Der zuschreibende Text.
	 */
	private void print(String text){
	    try{
	    	document.insertString(document.getLength(), text, null);
	    	SimpleAttributeSet sas = new SimpleAttributeSet();
	    	StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
	    	document.setParagraphAttributes(0, document.getLength(), sas, false);
	    }catch(BadLocationException e){
	    	System.err.println(e.getStackTrace());
	    }
	}
	  
	/**
	 * Fuegt einen String dem JTextPane hinzu und veraendert die Groesse des Texts.
	 * @param text Der zuschreibende Text.
	 * @param size Die Groesse des Texts.
	 */
	private void printSize(String text, int size) {
		print(text);
	    SimpleAttributeSet sas = new SimpleAttributeSet();
	    StyleConstants.setFontSize(sas, size);
	    document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), sas, false);
	}
	  
	/**
	 * Das SkillDisplay wird geloescht, sodass es neu beschrieben werden kann.
	 */
	private void clear() {
		try {
			document.remove(0, document.getLength());
		} catch(BadLocationException e) {
			System.err.println(e.getStackTrace());
	    }
	}

	@Override
	public void playerChanged(PlayerEvent evt) {
		clear();
	    printSize("Fähigkeiten:", 18);
	    for(Faehigkeit f : evt.getPlayer().getFaehigkeiten()){
	    	print("\n" + f.getName());
	    }
		
	}
	
}
