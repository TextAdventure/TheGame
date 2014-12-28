package game;

import util.NumerusGenus;

/**
 *  Diese Klasse repraesentiert alle Ruestungsteile fuer den Spieler.
 */
public class Ruestung extends Gegenstand {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	// Statische Variablen fuer alle moeglichen Arten von Ruestung.
	// Der Wert fuer einen Helm.
	public static final byte HELM = 0;
	// Der Wert fuer einen Brustpanzer.
	public static final byte BRUSTPANZER = 1;
	// Der Wert fuer Handschuhe.
	public static final byte HANDSCHUHE = 2;
	// Der Wert fuer eine Hose.
	public static final byte HOSE = 3;
	// Der Wert fuer Schuhe.
	public static final byte SCHUHE = 4;
	  
	  
	// Der Typ der Ruestung.
	private byte typ;
	  
	/**
	 *  Eine neue Ruestung wird wie ein Gegenstand erstellt, aber es werde noch die Statuswerte uebergeben.
	 *  art: die Art des Ruestungsteil, z.B. Helm.
	 */
	public Ruestung(String[] nameRuestung, NumerusGenus numerusGenus, String beschreibung, int lebenspunkte, int magiepunkte, int angriff, int verteidigung, int magAngriff, int magVerteidigung, int praezision, int flinkheit, byte typ){
	    super(nameRuestung, numerusGenus, beschreibung);
	    this.lp = lebenspunkte;
	    this.mp = magiepunkte;
	    this.ang = angriff;
	    this.def = verteidigung;
	    this.magAng = magAngriff;
	    this.magDef = magVerteidigung;
	    this.prz = praezision;
	    this.flk = flinkheit;
	    this.typ = typ;
	}
	  
	/**
	 *  Diese Methode gibt den Typ der Ruestung zurueck.
	 */
	public byte getTyp(){
	    return typ;
	}
	  
	/**
	 *  Diese statische Methode gibt entsprechend des Typs eines Ruestungsteils, den Namen dieses Typs zurueck.
	 *  typ: der Typ der Ruestung.
	 */
	public static String getTypName(byte typ){
		switch(typ){
	      	case(HELM): return "Helm";
	      	case(BRUSTPANZER): return "Brustpanzer";
	      	case(HANDSCHUHE): return "Handschuhe";
	      	case(HOSE): return "Hose";
	      	case(SCHUHE): return "Schuhe";
	      	default: return "";
	    }
	}
	  
	/**
	 *  Diese statische Methode gibt ein Ruestungsteil, den Namen dieses Typs zurueck.
	 *  ruestung: die Ruestung, fuer die man den Typnamen wissen moechte.
	 */
	public static String getTypName(Ruestung ruestung){
	    return getTypName(ruestung.getTyp());
	}
}