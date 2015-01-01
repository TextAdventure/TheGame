package gui;

import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import util.StringListener;

public class Eingabefeld extends JTextArea implements StringListener, DocumentListener {

	// Die seriell Versionsnummer
	private static final long serialVersionUID = 1L;

	// Die moeglichen Modi fuer das Eingabefeld.
	private static enum Mode {EINFUEGEN, VERVOLLSTAENDIGEN};
	// Der Modus dieses Eingabefelds.
	private Mode modus;

	// Die maximale Laenge des Strings in Pixel, die in das Textfeld passen.
	private final int maxLaenge;

	// Die Liste aller bekannten Woerter.
	private ArrayList<String> woerter;
	// Die zuletzt eingegebenen Kommandos.
	private ArrayList<String> kommandos;

	// Die Nummer des aktuelle Kommando.
	private int iterator;
	// Das aktuelle Kommando.
	private String aktuellesKommando;
	// Das zuletzt vervollstaendigte Praefix und Wort.
	private String letztesPraefix;
	private String letztesWort;
	// Die GUI, in der sich das Eingabefeld befindet.
	private GUI gui;

	/**
	 *  Ein neues Eingabefeld wird ohne Parameter initialisiert.
	 */
	public Eingabefeld(GUI gui){
		this.gui = gui;

	    this.setSize(690, 20);

	    maxLaenge = this.getWidth() - 5;

	    iterator = 0;

	    getDocument().addDocumentListener(this);

	    woerter = new ArrayList<String>();

	    kommandos = new ArrayList<String>();

	    // Auto-Vervollstaendigung uebernehmen.
	    InputMap ime = getInputMap();
	    ActionMap ame = getActionMap();
	    ime.put(KeyStroke.getKeyStroke("ENTER"), "EXE");
	    ame.put((Object)"EXE", (Action)new AktionAusfuehren());

	    // Letztes Kommando anzeigen.
	    InputMap iml = getInputMap();
	    ActionMap aml = getActionMap();
	    iml.put(KeyStroke.getKeyStroke("UP"), "LKOM");
	    aml.put((Object)"LKOM", (Action)new AktionLetztesKommando());

	    // Naechstes Kommando anzeigen.
	    InputMap imn = getInputMap();
	    ActionMap amn = getActionMap();
	    imn.put(KeyStroke.getKeyStroke("DOWN"), "NKOM");
	    amn.put((Object)"NKOM", (Action)new AktionNaechstesKommando());
	}

	/**
	 *  Diese Methode gibt ein String aus allen bekannten Woertern zurueck, aufgrund eines Wort Teils.
	 *  word: das Wort nach dem gesucht wird.
	 */
	private String getWord(String word){
		for(String s: woerter.toArray(new String[0])){
			if(s.toLowerCase().startsWith(word.toLowerCase())){
				return s;
			}
	    }
	    return "";
	}

	// Die Listener Methoden

	// @override                nichts tun
	public void changedUpdate(DocumentEvent evt){}

	// @override                nichts tun
	public void removeUpdate(DocumentEvent evt){}

	// @override
	public void insertUpdate(DocumentEvent evt){
		// Wenn die Eingabe zu gross wird, wird sie abgeschnitten.
		FontMetrics fm = getFontMetrics(getFont());
	    if(fm.stringWidth(getText()) > maxLaenge){
	    	SwingUtilities.invokeLater(new Vervollstaendigung("", getText().length() - evt.getLength(), evt.getLength(), true));
	    }
	    if(evt.getLength() != 1){
	    	// nach Farbe ueberpruefen
	    	return;
	    }

	    // Wird fuer Kommandowechsel benoetigt.
	    aktuellesKommando = getText();
	    iterator = 0;

	    int pos = evt.getOffset();
	    String text = null;
	    try{
	    	text = getText(0, pos + 1);
	    }catch(BadLocationException e){
	    	System.err.println("Die Eingabe konnte nicht gelesen werden.");
	    }

	    // Die Auto-Vervollstaendigung

	    // Herausfinden, wo das Wort beginnt.
	    int beginn;
	    // Rueckwaertssuche
	    for(beginn = pos; beginn >= 0; beginn--){
	    	// Es wird solange gesucht, bis der erste nicht-Buchstabe gefunden wird.
	    	if(!Character.isLetter(text.charAt(beginn))) break;
	    }
	    // Handelt es sich um midestens drei Buchstaben, dann wird eine Auto-Vervollstaendigung angesetzt, ansonsten ist hier Schluss.
	    if(pos - beginn < 2){
	    	return;
	    }

	    // Das Praefix wird in Kleinbuchstaben verglichen.
	    String praefix = text.substring(beginn + 1);

	    // Alternative Methode
	    if(!getWord(praefix.toLowerCase()).equals("")){
	    	// Die fertige Vervollstaendigung wird gebildet.
	    	letztesPraefix = praefix;
	    	letztesWort = getWord(praefix);
	    	String fertig = letztesWort.substring(pos - beginn);
	    	// Es wird einen neue Aufgabe erstellt, die spaeter erledigt wird, da wir von hier aus das JTextField nicht modifizieren koennen.
	    	if(fertig.length() > 0) SwingUtilities.invokeLater(new Vervollstaendigung(fertig, pos + 1, 0, false));
	    	return;
	    }
	    modus = Mode.EINFUEGEN;
	}

	/**
	 *  Immer wenn ein Event auftritt wird das neue Wort ueberprueft und ggf der Liste hinzugefuegt.
	 */
	@Override
	public void actionPerformed(String string) {
		if(!woerter.contains(string)) {
			woerter.add(string);
			Collections.sort(woerter);
	    }
	}

	/**
	 *  Diese Subklasse fuehrt die Textvervollstaendigung aus.
	 */
	private class Vervollstaendigung implements Runnable{
	    // Das Wort, welches vervollstaendigt wird.
	    private String wort;
	    // Der Beginn des Worts.
	    private int beginn;
	    // Die Laenge der Vervollstaendigung.
	    private int laenge;
	    // Gibt an, ob ersetzt werden soll.
	    private boolean ersetzen;

	    /**
	     *  Ein neuer Vervollstaendigungs-Auftrag wird erzeugt mit einem Wort und einem Beginn.
	     *  vervollstaendigung: die Vervollstaendigung, die noch an das Wort angebracht werden muss.
	     *  beginn: der Beginn, der Vervollstaendigung.
	     */
	    public Vervollstaendigung(String vervollstaendigung, int beginn, int laenge, boolean ersetzen){
	    	wort = vervollstaendigung;
	    	this.beginn = beginn;
	    	this.laenge = laenge;
	    	this.ersetzen = ersetzen;
	    }

	    /**
	     *  Das Skript, das ausgefuehrt wird.
	     */
	    public void run(){
	    	// Der neue Text wird gebildet.
	    	if(ersetzen){
	    		// Der alte Text soll erstzt werden.
	    		replaceRange(wort, beginn, Math.min(beginn + laenge, getText().length()));
	    	}else{
	    		insert(wort, beginn);
	    	}
	    	setCaretPosition(beginn + wort.length());
	    	moveCaretPosition(beginn);
	    	modus = Mode.VERVOLLSTAENDIGEN;
	    }
	}

	/**
	 *  Diese Subklasse fuehrt eine Aktion aus, wenn Enter gedrueckt wird.
	 */
	private class AktionAusfuehren extends AbstractAction{

		// Die serielle Versionsnummer
		private static final long serialVersionUID = 1L;

		// Braucht weder Variablen noch Konstruktor.
	    /**
	     *  Diese Methode wird ausgefuehrt, wenn ein bestimtes Ereignis war.
	     */
		public void actionPerformed(ActionEvent evt){
			if(modus == Mode.VERVOLLSTAENDIGEN){
				// Einfuegen der Auto-Vervollstaendigung.
				int ende = getSelectionEnd();
				insert(" ", ende);
				setCaretPosition(ende + 1);
				modus = Mode.EINFUEGEN;
			}else{
				String wort = getText().trim();
				iterator = 0;

				// Das Kommando wird an das Spiel uebergeben.
				gui.uebergebeBefehl(wort);

				// Das eingegebene Kommando der Liste hinzufuegen.
				if(!wort.equals("")){
					// Zunaechst wird ueberprueft, ob sich das Kommando nicht schon in der Liste befindet.
					boolean gefunden = false;
					for(String s: kommandos.toArray(new String[0])){
						if(s.equalsIgnoreCase(wort)){
							// Das ueberfluessige Kommando wird geloescht und an "erster"(eig. letzte) Stelle neu eingefuegt.
							kommandos.remove(s);
							// Es wird sichergestellt, dass auch alle Plaetze aufgefuellt werden.
							kommandos.trimToSize();
							kommandos.add(wort);
							gefunden = true;
						}
					}
					if(!gefunden) kommandos.add(wort);
				}
				setText("");
			}
		}
	}

	/**
	 *  Diese Subklasse holt das letzte eingegebene Kommando zurueck.
	 */
	private class AktionLetztesKommando extends AbstractAction{

		// Die serielle Versionsnummer
		private static final long serialVersionUID = 1L;

		// Braucht weder Variablen noch Konstruktor.
	   	/**
	   	 *  Diese Methode wird ausgefuehrt, wenn man die Pfeiltaste nach oben drueckt.
	   	 */
		public void actionPerformed(ActionEvent evt){
			if(modus == Mode.VERVOLLSTAENDIGEN){
				int n = woerter.indexOf(getWord(letztesWort));
				// Der Index ist zu gross.
				if(n + 1 >= woerter.size()) return;
				if(woerter.get(n + 1).toLowerCase().startsWith(letztesPraefix.toLowerCase())){
					letztesWort = woerter.get(n + 1);
					String fertig = letztesWort.substring(letztesPraefix.length());
					SwingUtilities.invokeLater(new Vervollstaendigung(fertig, getSelectionStart(), woerter.get(n).length() - 2, true));
				}
				return;
			}
			if(iterator != -1 && iterator < kommandos.size()){
				String kommando = kommandos.get(kommandos.size() - iterator - 1);
				setText(kommando);
				setCaretPosition(0);
				moveCaretPosition(kommando.length());
				iterator++;
			}
		}
	}

	/**
	 *  Diese Subklasse holt das naechste eingegebene Kommando zurueck.
	 */
	private class AktionNaechstesKommando extends AbstractAction {
		// Die serielle Versionsnummer
		private static final long serialVersionUID = 1L;

		// Braucht weder Variablen noch Konstruktor.
	    /**
	     *  Diese Methode wird ausgefuehrt, wenn man die Pfeiltaste nach unten drueckt.
	     */
	    public void actionPerformed(ActionEvent evt){
	    	if(modus == Mode.VERVOLLSTAENDIGEN){
	    		int n = woerter.indexOf(getWord(letztesWort));
	    		// Das Wort ist nicht enthalten.
	    		if(n - 1 < 0) return;
	    		if(woerter.get(n - 1).toLowerCase().startsWith(letztesPraefix.toLowerCase())){
	    			letztesWort = woerter.get(n - 1);
	    			String fertig = letztesWort.substring(letztesPraefix.length());
	    			SwingUtilities.invokeLater(new Vervollstaendigung(fertig, getSelectionStart(), woerter.get(n).length() - 2, true));
	    		}
	    		return;
	    	}
	    	if(iterator - 1 == 0){
	    		iterator--;
	    		setText(aktuellesKommando);
	    		setCaretPosition(0);
	    		moveCaretPosition(aktuellesKommando.length());
	    	}else if(iterator - 1 > 0){
	    		iterator--;
	    		String kommando = kommandos.get(kommandos.size() - iterator);
	    		setText(kommando);
	    		setCaretPosition(0);
	    		moveCaretPosition(kommando.length());
	    	}
	    }
	}
}
