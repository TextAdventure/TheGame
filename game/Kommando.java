package game;

import game.bedingung.Bedingung;

import java.io.Serializable;
import java.util.Vector;


/**
 *  Diese Klasse ist ein Kommando, welches bei der Eingabe ausgefuehrt wird.
 */
public class Kommando implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Der Befehl, der eingegeben werden muss.
	private String befehl;
	// Alle Synonyme, die fuer den Befehl verwendet werden koennen.
	private Vector<String> synonyme;
	// Alle Suffixe fuer diesen Befehl.
	private Vector<String> suffixe;
	  
	// Die Bedingung, die ausgefuehrt wird, wenn das Kommando eingegeben wurde.
	private Bedingung bedingung;
	  
	// Der Rest der Eingabe, der uebrig bleibt, dieser kann dann ausgelesen werden und ermoeglicht dadurch Gegenstaende mit Leerzeichen
	private static String eingabe = "";

	/**
	 *  Ein neues Kommando wird lediglich mit mit einem Befehlswort initialisiiert.
	 *  verSynonyme: eines dieser kann eingegeben werden, um den Befehl zu verwenden.
	 *  suffixe: die Suffixe fuer die Befehle.
	 */
	private Kommando(String[] verbSynonyme, String[] suffixe){
		this.befehl = verbSynonyme[0];
	    synonyme = new Vector<String>();
	    for(String s: verbSynonyme){
	    	synonyme.add(s);
	    }
	    this.suffixe  = new Vector<String>();
	    for(String s: suffixe){
	    	this.suffixe.add(s);
	    }
	}
	  
	/**
	 *  Dieser public-Konstuktor erstellt ein Custom Command, welches nur an einem Ort funktioniert und nicht global gilt.
	 */
	public Kommando(String befehl, Bedingung bedingung){
	    this.befehl = befehl;
	    this.bedingung = bedingung;
	}
	  
	/**
	 *  Diese Methode ueberprueft das Kommando und gibt den entsprechenden Befehl zurueck.
	 *  kommando: das Kommando, von welchem man den Befehl will.
	 */
	public static String getBefehl(Kommando kommando){
	    return kommando.befehl;
	}
	  
	/**
	 *  Diese Methode gibt den entsprechenden Befehl zurueck.
	 */
	public String getBefehl(){
	    return this.befehl;
	}
	  
	/**
	 *  Diese Methode gibt ein Klon der Liste aller Synonyme zurueck.
	 */
	public String[] getSynonyme(){
	    return synonyme.toArray(new String[0]);
	}
	  
	/**
	 *  Diese Methode gibt eine Kopie der Liste aller Synonyme zurueck.
	 */
	public Vector<String> synonyme(){		
	    return (Vector<String>)synonyme.clone();
	}
	  
	/**
	 *  Diese Methode gibt das Suffix fuer ein Befehl zurueck.
	 *  index: die Position des Befehls.
	 */
	public String getSuffix(int index){
	    return suffixe.get(index);
	}
	  
	/**
	 *  Diese Methode gibt die Bedingung zurueck.
	 */
	public Bedingung getBedingung(){
	    return bedingung;
	}
	  
	/**
	 *  Diese Methode gibt den Rest der Eingabe zuruck, nachdem die Befehle entfernt wurden.
	 */
	public static String getEingabe(){
	    String e = eingabe;
	    eingabe = "";
	    return e;
	}
	  
	/**
	 *  Diese Methode gibt das entsprechende Kommando zurueck.
	 *  befehl: der Name des Kommandos.
	 */
	public static Kommando getKommando(String befehl) throws StringIndexOutOfBoundsException{
	    boolean beginnt = false;
	    
	    for(String s: UNTERSUCHEN.getSynonyme()){
	    	if(befehl.toLowerCase().startsWith(s)){
	    		eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return UNTERSUCHEN;
	    	}
	    }
	    for(int i = 0; i < ABLEGEN.synonyme().size(); i++){
	    	String praefix = ABLEGEN.synonyme().get(i);
	    	String suffix = ABLEGEN.getSuffix(i);
	    	if(befehl.toLowerCase().startsWith(praefix)) beginnt = true;
	    	if(beginnt && befehl.toLowerCase().endsWith(suffix)){
	    		if(!suffix.equals("")) eingabe = befehl.substring(befehl.indexOf(' '), befehl.lastIndexOf(' ')).trim();
	    		else eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return ABLEGEN;
	    	}
	    	beginnt = false;
	    }
	    for(int i = 0; i < NEHMEN.synonyme().size(); i++){
	    	String praefix = NEHMEN.synonyme().get(i);
	    	String suffix = NEHMEN.getSuffix(i);
	    	if(befehl.toLowerCase().startsWith(praefix)) beginnt = true;
	    	if(beginnt && befehl.toLowerCase().endsWith(suffix)){
	    		if(!suffix.equals("")) eingabe = befehl.substring(befehl.indexOf(' '), befehl.lastIndexOf(' ')).trim();
	    		else eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return NEHMEN;
	    	}
	    	beginnt = false;
	    }
	    for(int i = 0; i < VERWENDEN.synonyme().size(); i++){
	    	String praefix = VERWENDEN.synonyme().get(i);
	    	String suffix = VERWENDEN.getSuffix(i);
	    	if(befehl.toLowerCase().startsWith(praefix)) beginnt = true;
	    	if(beginnt && befehl.toLowerCase().endsWith(suffix)){
	    		if(!suffix.equals("")) eingabe = befehl.substring(befehl.indexOf(' '), befehl.lastIndexOf(' ')).trim();
	    		else eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return VERWENDEN;
	    	}
	    	beginnt = false;
	    }
	    for(String s: KOMBINIEREN.getSynonyme()){
	    	//String str = s.split(" ")[0];
	    	if(befehl.toLowerCase().startsWith(s)){
	    		//eingabe = befehl.substring(befehl.indexOf(' ')).trim();
	    		return KOMBINIEREN;
	    	}
	    }
	    for(int i = 0; i < AUSRUESTEN.synonyme().size(); i++){
	    	String praefix = AUSRUESTEN.synonyme().get(i);
	    	String suffix = AUSRUESTEN.getSuffix(i);
	    	if(befehl.toLowerCase().startsWith(praefix)) beginnt = true;
	    	if(beginnt && befehl.toLowerCase().endsWith(suffix)){
	    		if(!suffix.equals("")) eingabe = befehl.substring(befehl.indexOf(' '), befehl.lastIndexOf(' ')).trim();
	    		else eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return AUSRUESTEN;
	    	}
	    	beginnt = false;
	    }
	    for(String s: AUSRUESTUNG.getSynonyme()){
	    	if(befehl.toLowerCase().startsWith(s)){
	    		// man braucht das Kommando sowieso nicht
	    		return AUSRUESTUNG;
	    	}
	    }
	    for(String s: STATUS.getSynonyme()){
	    	if(befehl.toLowerCase().startsWith(s)){
	    		// man braucht das Kommando sowieso nicht
	    		return STATUS;
	    	}
	    }
	    for(String s: INFO.getSynonyme()){
	    	if(befehl.toLowerCase().startsWith(s)){
	    		eingabe = befehl.substring(Math.max(befehl.indexOf(' '), 0)).trim();
	    		return INFO;
	    	}
	    }
	    for(String s: OEFFNEN.getSynonyme()){
	    	if(befehl.toLowerCase().startsWith(s)){
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
	public static final Kommando ABLEGEN = new Kommando(new String[]{"lege", "nimm"}, new String[]{"ab", "ab"});
	// Der Wert fuer das Ausruestung Befehl.
	public static final Kommando AUSRUESTUNG = new Kommando(new String[]{"ausrüstung"}, new String[]{""});
	// Der Wert fuer das Status Kommando.
	public static final Kommando STATUS = new Kommando(new String[]{"status", "statistik", "stats"}, new String[]{"", "", ""});
	// Der Wert fuer das Info(Gegenstand) Kommando.
	public static final Kommando INFO = new Kommando(new String[]{"info"}, new String[]{""});
	// Der Wert fuer das Oeffnen Kommando.
	public static final Kommando OEFFNEN = new Kommando(new String[]{"öffne", "plündere", "leere"}, new String[]{"", "", ""});
}