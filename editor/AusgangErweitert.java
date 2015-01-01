package editor;

import java.awt.Point;
import java.io.Serializable;

import game.Ort;
import game.Ausgang;

/**
 * Datenstruktur, die alle Informationen über einen Weg zwischen zwei Orten enthält. Dies sind:
 *  - die verbundenen Orte
 *  - die Punkte für die graphische Darstellung
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
	byte bez_von1nach2 = Ausgang.EIGENE;
	byte bez_von2nach1 = Ausgang.EIGENE;
	String eigeneBez_von1nach2 = "";
	String eigeneBez_von2nach1 = "";
	
}
