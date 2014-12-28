package editor;

import game.items.Gegenstand;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;

/**
 * Datenstruktur-Klasse. Enthält alles, was die bearbeitete Spielwelt enthält. Dies ist im Einzelnen:
 *  - alle orte
 *  - alle Ausgänge
 *  - alle Gegenstände
 *  
 * @author Felix
 *
 */
public class WeltObjekt implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Vector<OrtErweitert> orte = new Vector<OrtErweitert>();
	private Vector<AusgangErweitert> ausgaenge = new Vector<AusgangErweitert>();
	private Vector<Gegenstand> gegenstaende = new Vector<Gegenstand>();
	private int startOrt = -1;
	
	
	void addOrt(OrtErweitert ort) {
		orte.add(ort);
	}	
	OrtErweitert[] getOrte() {
		return orte.toArray(new OrtErweitert[0]);
	}	
	void removeOrt(OrtErweitert ort ) {
		for(AusgangErweitert ae : getAusgaenge()) {
			if(ae.ort1 == ort.ort || ae.ort2 == ort.ort) 
				removeAusgang(ae);
		}
		if(orte.indexOf(ort) == startOrt) startOrt = -1;
		orte.remove(ort);
	}
	
	
	
	void addAusgang(AusgangErweitert ausgang) {
		ausgaenge.add(ausgang);
	}	
	void removeAusgang(AusgangErweitert ae) {
		ausgaenge.remove(ae);
	}	
	AusgangErweitert[] getAusgaenge() {
		return ausgaenge.toArray(new AusgangErweitert[0]);
	}
	
	
	void addGegenstand(Gegenstand g) {
		gegenstaende.add(g);
	}
	Gegenstand[] getGegenstaende() {
		return gegenstaende.toArray(new Gegenstand[0]);
	}
	void removeGegenstand(Gegenstand g) {
		gegenstaende.remove(g);
	}
	
	
	
	boolean hasStartOrt() {
		return startOrt != -1;
	}	
	void setStartOrt(OrtErweitert ort) {
		startOrt = orte.indexOf(ort);
	}	
	OrtErweitert getStartOrt() {
		if(startOrt == -1) return null;
		return orte.elementAt(startOrt);
	}
	
	
	
	/**
	 * 
	 * @param xPos
	 * @param yPos
	 * @return The first OrtErweitert-Object for which (xPos,yPos) is within 
	 * the representation of that ort. If no such ort exists, returns null. 
	 */
	OrtErweitert ortAt(int xPos, int yPos) {
		OrtErweitert[] orte = getOrte();
		for(int i = 0; i < orte.length; i++) {
			int x = orte[i].position.x;
			int y = orte[i].position.y;
			int w = orte[i].groesse.width;
			int h = orte[i].groesse.height;
			if(x < xPos && x+w > xPos && y < yPos && y+h > yPos) {
				return orte[i];
			}
		}
		return null;
	}
	
	
	AusgangErweitert ausgangAt(int xPos, int yPos) {
		AusgangErweitert[] ausgaenge = getAusgaenge();
		int tol = 5;
		for(int i = 0; i < ausgaenge.length; i++) {
			Point[] points = ausgaenge[i].points;
			for(int j = 0; j < points.length-1; j++) {
				if(xPos + tol > Math.min(points[j].x, points[j+1].x) &&
						xPos - tol < Math.max(points[j].x, points[j+1].x) &&
						yPos + tol > Math.min(points[j].y, points[j+1].y) &&
						yPos - tol < Math.max(points[j].y, points[j+1].y))
					return ausgaenge[i];
						
			}
		}
		return null;
	}
	
}
