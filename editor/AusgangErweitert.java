package editor;

import java.awt.Point;
import java.io.Serializable;

import game.Ort;
import game.Ausgang;

/**
 * Datenstruktur, die alle Informationen �ber einen Weg zwischen zwei Orten enth�lt. Dies sind:
 *  - die verbundenen Orte
 *  - die Punkte f�r die graphische Darstellung
 *  - die Richtungsbezeichner
 *  - welche Richtung(en) angeboten werden.
 * 
 * @author Felix
 *
 */
public class AusgangErweitert implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Ort ort1 = null;
	Ort ort2 = null;
	Point[] points;
	boolean von1nach2 = true;
	boolean von2nach1 = true;
	int bez_von1nach2 = Ausgang.UNDEFINIERT;
	int bez_von2nach1 = Ausgang.UNDEFINIERT;
	
}
