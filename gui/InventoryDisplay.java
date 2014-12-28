package gui;

import game.Stapel;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import util.InventoryEvent;
import util.InventoryListener;

public class InventoryDisplay extends JTextPane implements InventoryListener {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Das JScrollPane, in dem sich das TextPane befindet.
	private JScrollPane scroll;
	// Das Document, das beschrieben wird.
	private StyledDocument document;
	  
	/**
	 *  Ein InventoryDisplay wird den Inventar uebergeben, sodass dieses das InventoryDisplay aktualisiert.
	 */
	public InventoryDisplay(){
		scroll = new JScrollPane(this);
	    document = this.getStyledDocument();
	    SimpleAttributeSet font = new SimpleAttributeSet();
	    StyleConstants.setFontSize(font, 14);
	    document.setParagraphAttributes(0, document.getLength(), font, false);

	    scroll.setSize(290, 400);
	    
	    this.setEditable(false);
	}
	  
	/**
	 *  Diese Methode gibt das JScrollPane zurueck.
	 */
	public JScrollPane getJScrollPane(){
	    return scroll;
	}
	  
	/**
	 *  Diese Methode fuegt einen String dem JTextPane hinzu, ohne Zeilenumbruch.
	 *  text: der uebergebene Text.
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
	 *  Diese Methode fuegt einen String dem JTextPane hinzu und veraendert die Groesse des Texts.
	 *  text: der uebergebene Text.
	 *  size: die groesse des Texts.
	 */
	private void printSize(String text, int size){
		print(text);
	    SimpleAttributeSet sas = new SimpleAttributeSet();
	    StyleConstants.setFontSize(sas, size);
	    document.setCharacterAttributes(document.getLength() - text.length(), document.getLength(), sas, false);
	}
	  
	/**
	 *  Das InventoryDisplay wird geloescht, sodass es neu beschrieben werden kann.
	 */
	private void clear(){
		try{
			document.remove(0, document.getLength());
		}catch(BadLocationException e){
			System.err.println(e.getStackTrace());
	    }
	}
	  
	/** @override
	 *  Ueberschreibt die update Methode des Listeners.
	 */
	public void inventoryUpdate(InventoryEvent evt){
		clear();
	    printSize("Inventar:", 18);
	    for(Stapel s: evt.getInventory().getAlleStapel()){
	    	//if(s == null) continue;
	    	print("\n" + s.getAnzahl() + " x " + s.getGegenstand().getName());
	    }
	}
}