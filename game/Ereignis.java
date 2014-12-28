package game;

import game.bedingung.Bedingung;

import java.io.Serializable;


public class Ereignis implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Statisches Feld mit allen Ereignis-Typen.
	// Der Spieler betritt einen bestimmten Ort.
	public static final int SPIELERBETRITT = 0;
	// Der Spieler verlaesst einen bestimmten Ort.
	public static final int SPIELERVERLAESST = 1;
	  
	// Der Typ des Ereignis.
	private int typ;
	// Die SpielWelt, in der das Ereignis auftritt.
	private SpielWelt welt;
	// Die Bedingung, die wenn sie erfuellt ist, eine Aktion ausloest.
	private Bedingung bedingung;
	// Der Ort Parameter.
	private Ort ort;
	
	//---------------------
	//  ALLE KONSTRUKTOREN
	//----------------------
	/**
	 *  Ein Ereignis, welches mit einem Ort zusammenhaengt.
	 *  ort: der Ort an dem diese Bedingung geprueft wird.
	 */
	public Ereignis(int typ, SpielWelt spielwelt, Ort ort, Bedingung bedingung){
	    this.typ = typ;
	    welt = spielwelt;
	    welt.addEreignis(this);
	    this.ort = ort;
	    this.bedingung = bedingung;
	}
	  
	//-----------------
	//  ALLE METHODEN  
	//-----------------
	
	/**
	 *  Gibt den Typ zurueck.
	 */
	public int getTyp(){
		return typ;
	}
	
	/**
	 *  Gibt die Bedingung zurueck, die sie hat.
	 */
	public Bedingung getBedingung(){
	    return bedingung;
	}
	  
	/**
	 * Das Ereignis wird ausgefuehrt.
	 */
	public void eingetreten(){
	    switch(typ){
	    	case(SPIELERBETRITT): if(welt.aktuellePosition() == ort) bedingung.pruefen(); break;
	    	case(SPIELERVERLAESST): if(welt.aktuellePosition() == ort) bedingung.pruefen(); break;
	    	default: break;
	    }
	}
}