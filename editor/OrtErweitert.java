package editor;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

import game.Ort;

/**
 * Datenstruktur und Wrapper-Klasse. Speichert zu einem game.Ort die Daten für die graphische Darstellung.
 * Dise sind
 *  - Dimension groesse
 *  - Point position
 *   
 * @author Felix
 *
 */
public class OrtErweitert implements Serializable {
	private static final long serialVersionUID = 1L;
	
	final Ort ort;
	Point position;
	Dimension groesse;
	
	OrtErweitert(Ort ort, Point position, Dimension groesse) {
		this.ort = ort;
		this.position = position;
		this.groesse = groesse;
	}
}
