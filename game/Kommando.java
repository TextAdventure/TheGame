package game;

import java.io.Serializable;

import game.logic.ereignis.Ereignis;

/**
 *  Es gibt zwei Arten von Kommandos, erstens die, die ueberall verwendet werden koennen, wie untersuchen,
 *  und dann gibt es noch solche, die nur an bestimmten Orten und Abenteuern gelten. Diese werden mit
 *  einem separaten Konstruktor erstellt und sind nich in dieser Klasse gespeichert.
 *  @author Marvin
 */
public class Kommando implements Serializable {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	/* --- statische Variable --- */
	
	// Der Rest der Eingabe, der uebrig bleibt, dieser kann dann ausgelesen werden und ermoeglicht dadurch Gegenstaende mit Leerzeichen.
	private static String eingabe = "";
	
	/* --- Variablen --- */
	
	// Der Befehl, der eingegeben werden muss.
	private String[] befehle;
	// Alle Praefixe fuer diesen Befehl.
	private String[] praefix;
	// Alle Suffixe fuer diesen Befehl.
	private String[] suffix;

	// Das Ereignis, das ausgefuehrt wird, wenn das Kommando eingegeben wurde. Wird fuer eigene Kommandos benoetigt.
	private Ereignis ereignis;
	
	/* --- Konstruktor --- */
	
	/**
	 * Ein neues Kommando, welches fuer alle erstellen Spiele gilt, so ein Kommando
	 * muss in dieser Klasse statisch erstellt werden und wird auch hier verwaltet.
	 * @param praefix Die Praefixe fuer dieses Kommando.
	 * @param suffix Die Suffixe fuer dieses Kommando.
	 */
	private Kommando(String[] praefix, String[] suffix) {
	    this.praefix = praefix;
	    this.suffix  = suffix;
	}
	
	/**
	 * Dieser public-Konstruktor erstellt ein Kommando, das nur in bestimmten Abenteuern funktioniert.
	 * @param ereignis Das Ereignis, welches ausgefuehrt wird, sobald es eingegeben wird.
	 * @param befehl Die Befehle, die verwendet werden koennen um das Kommando auszuloesen.
	 */
	public Kommando(Ereignis ereignis, String... befehl) {
	    this.befehle = befehl;
	    this.ereignis = ereignis;
	}
	
	/* --- Methoden --- */
	
	/**
	 * Ueberprueft, ob es sich bei der Eingabe um ein gueltiges Kommando handelt oder nicht.
	 * @param eingabe Die Eingabe, die getaetig wurde.
	 * @return True, wenn es ein Befehl ist, ansonsten false.
	 */
	public boolean istBefehl(String eingabe) {
		for(String s : befehle)
			if(s.equalsIgnoreCase(eingabe))
				return true;
		return false;
	}
	
	/**
	 * Gibt alle moeglichen Praefixe zurueck.
	 * @return Alle Praefixe dieses Kommandos.
	 */
	public String[] getPraefixe() {
	    return praefix.clone();
	}
	
	/**
	 * Gibt das Suffix fuer ein Praefix zurueck.
	 * @param index Die Position des Praefixes.
	 * @return Das entsprechened Suffix fuer dieses Praefix.
	 */
	public String getSuffix(int index) {
	    return suffix[index];
	}
	
	/**
	 * Das Ereignis ist eingetreten und prueft nun, ob eine Aktion durchgefuehrt wird.
	 * @return True, wenn das Ereignis geloescht werden kann, ansonsten false.
	 */
	public boolean eingetreten() {
		return ereignis.eingetreten();
	}
	
	/* --- statische Methoden --- */
	
	/**
	 * Gibt den Rest der Eingabe zuruck, nachdem die Schluesselwoerter entfernt wurden und setzt sie zurueck.
	 * @param Die restliche Eingabe.
	 */
	public static String getEingabe() {
	    String e = eingabe;
	    eingabe = "";
	    return e;
	}
	
	/**
	 * Man uebergibt die Eingabe des Spielers und hier wird ueberprueft, ob es sich um ein globales Kommando handelt und
	 * gibt es in diesem Fall zurueck. Die restliche Eingabe kann mit getEingabe() ausgelesen werden.
	 * @param befehl Die Eingabe des Spielers.
	 * @return Das globale Kommando, dem die Eingabe des Spielers entspricht, ansonsten INVALID.
	 * @throws StringIndexOutOfBoundsException Wird geworfen, falls der String doch nicht die Schluesselwoerter enthaelt
	 * und es dadurch zu einem Fehler beim auslesen kommt.
	 */
	public static Kommando getKommando(String befehl) throws StringIndexOutOfBoundsException {
	    boolean beginnt = false;
	    
	    for(String s: UNTERSUCHEN.getPraefixe()) {
	    	if(befehl.toLowerCase().startsWith(s)) {
	    		eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return UNTERSUCHEN;
	    	}
	    }
	    
	    for(int i = 0; i < ABLEGEN.getPraefixe().length; i++) {
	    	String praefix = ABLEGEN.getPraefixe()[i];
	    	String suffix = ABLEGEN.getSuffix(i);
	    	if(befehl.toLowerCase().startsWith(praefix))
	    		beginnt = true;
	    	if(beginnt && befehl.toLowerCase().endsWith(suffix)) {
	    		if(!suffix.equals(""))
	    			eingabe = befehl.substring(befehl.indexOf(' '), befehl.lastIndexOf(' ')).trim();
	    		else
	    			eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return ABLEGEN;
	    	}
	    	beginnt = false;
	    }
	    
	    for(int i = 0; i < NEHMEN.getPraefixe().length; i++) {
	    	String praefix = NEHMEN.getPraefixe()[i];
	    	String suffix = NEHMEN.getSuffix(i);
	    	if(befehl.toLowerCase().startsWith(praefix))
	    		beginnt = true;
	    	if(beginnt && befehl.toLowerCase().endsWith(suffix)) {
	    		if(!suffix.equals(""))
	    			eingabe = befehl.substring(befehl.indexOf(' '), befehl.lastIndexOf(' ')).trim();
	    		else
	    			eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return NEHMEN;
	    	}
	    	beginnt = false;
	    }
	    
	    for(int i = 0; i < VERWENDEN.getPraefixe().length; i++) {
	    	String praefix = VERWENDEN.getPraefixe()[i];
	    	String suffix = VERWENDEN.getSuffix(i);
	    	if(befehl.toLowerCase().startsWith(praefix))
	    		beginnt = true;
	    	if(beginnt && befehl.toLowerCase().endsWith(suffix)) {
	    		if(!suffix.equals(""))
	    			eingabe = befehl.substring(befehl.indexOf(' '), befehl.lastIndexOf(' ')).trim();
	    		else
	    			eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return VERWENDEN;
	    	}
	    	beginnt = false;
	    }
	    
	    for(String s : KOMBINIEREN.getPraefixe()) {
	    	if(befehl.toLowerCase().startsWith(s)) {
	    		// man braucht das Kommando sowieso nicht
	    		return KOMBINIEREN;
	    	}
	    }
	    
	    for(int i = 0; i < AUSRUESTEN.getPraefixe().length; i++) {
	    	String praefix = AUSRUESTEN.getPraefixe()[i];
	    	String suffix = AUSRUESTEN.getSuffix(i);
	    	if(befehl.toLowerCase().startsWith(praefix))
	    		beginnt = true;
	    	if(beginnt && befehl.toLowerCase().endsWith(suffix)) {
	    		if(!suffix.equals(""))
	    			eingabe = befehl.substring(befehl.indexOf(' '), befehl.lastIndexOf(' ')).trim();
	    		else
	    			eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return AUSRUESTEN;
	    	}
	    	beginnt = false;
	    }
	    
	    for(String s : AUSRUESTUNG.getPraefixe()) {
	    	if(befehl.toLowerCase().startsWith(s)) {
	    		// man braucht das Kommando sowieso nicht
	    		return AUSRUESTUNG;
	    	}
	    }
	    
	    for(String s : STATUS.getPraefixe()) {
	    	if(befehl.toLowerCase().startsWith(s)) {
	    		// man braucht das Kommando sowieso nicht
	    		return STATUS;
	    	}
	    }
	    
	    for(String s : INFO.getPraefixe()) {
	    	if(befehl.toLowerCase().startsWith(s)) {
	    		eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return INFO;
	    	}
	    }
	    
	    for(String s : OEFFNEN.getPraefixe()) {
	    	if(befehl.toLowerCase().startsWith(s)) {
	    		eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return OEFFNEN;
	    	}
	    }
	    return INVALID;
	}
	
	// Statische Variablen, die alle Kommandos repraesentieren, die ueberall verwendet werden koennen.
	
	// Der Wert fuer ein ungueltiges Kommando.
	public static final Kommando INVALID = new Kommando(new String[]{"invalid"}, new String[]{""});
	// Der Wert fuer den Untersuchen Befehl.
	public static final Kommando UNTERSUCHEN = new Kommando(new String[]{"untersuche", "erforsche", "überprüfe", "betrachte"}, new String[]{"", "", "", ""});
	// Der Wert fuer den Nehmen Befehl.
	public static final Kommando NEHMEN = new Kommando(new String[]{"nimm", "nimm", "nimm", "hebe", "stecke", "pflücke", "ernte"}, new String[]{"mit", "auf", "", "auf", "ein", "", ""});
	// Der Wert fuer den Verwenden Befehl.
	public static final Kommando VERWENDEN = new Kommando(new String[]{"verwende", "benutze", "setze"}, new String[]{"", "", "ein"});
	// Der Wert fuer den Kombinieren Befehl.
	public static final Kommando KOMBINIEREN = new Kommando(new String[]{"kombiniere", "vereinige", "benutze"}, new String[]{"", "", ""});
	// Der Wert fuer das Ausruesten Befehl.
	public static final Kommando AUSRUESTEN = new Kommando(new String[]{"rüste", "setze", "ziehe", "lege"}, new String[]{"aus", "auf", "an", "an"});
	// Der Wert fuer das Ablegen Befehl.
	public static final Kommando ABLEGEN = new Kommando(new String[]{"lege", "nimm", "ziehe"}, new String[]{"ab", "ab", "aus"});
	// Der Wert fuer das Ausruestung Befehl.
	public static final Kommando AUSRUESTUNG = new Kommando(new String[]{"ausrüstung"}, new String[]{""});
	// Der Wert fuer das Status Kommando.
	public static final Kommando STATUS = new Kommando(new String[]{"status", "statistik", "stats"}, new String[]{"", "", ""});
	// Der Wert fuer das Info(Gegenstand) Kommando.
	public static final Kommando INFO = new Kommando(new String[]{"info"}, new String[]{""});
	// Der Wert fuer das Oeffnen Kommando.
	public static final Kommando OEFFNEN = new Kommando(new String[]{"öffne", "plündere", "leere"}, new String[]{"", "", ""});

}