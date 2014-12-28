package editor;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

/**
 * Subklasse von MouseAdapter. Realisiert die Funktionalität der NEW_AUSGANG-Tools. Jeder Workspace sollte einen
 * WorkspaceMosueAusagang als MosueListener und MosueMotionListener adden, sonst hat das new Ausgang-Tool keine 
 * Funktionalität!
 * 
 * @author Felix
 *
 */
public class WorkspaceMouseAusgang extends MouseAdapter {

	private WeltObjekt welt;
	private Component parent;			//zum repainten
	private IDE ide;
	private boolean definiereAusgang = false; 	//true = es wird gerade ein neuer Ausgang definiert
	private Stack<Point> points = new Stack<Point>();
	private AusgangErweitert ausgang = null;		//aktuell bearbeiteter Ausgang
	
	
	WorkspaceMouseAusgang(WeltObjekt welt, Component parent, IDE ide) {
		this.welt = welt;
		this.parent = parent;
		this.ide = ide;
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.NEW_AUSGANG) return;
		/*
		 * kein Ausgang definiert
		 * 		Ort geclickt
		 * 			beginn definition
		 * Ausgang definiert
		 * 		Punkt update
		 * 		Ort geclickt
		 * 			Ende defintion
		 */
		OrtErweitert ort = welt.ortAt(evt.getX(), evt.getY());
		Point p = new Point(evt.getX(), evt.getY()); 
		
		if(!definiereAusgang) {
			if(ort != null) {
				ausgang = new AusgangErweitert();
				ausgang.ort1 = ort.ort;
				welt.addAusgang(ausgang);
				definiereAusgang = true;
				points.push(p);
				points.push(p);
				ausgang.points = points.toArray(new Point[0]);
				parent.repaint();
			}				
			
		} else {
			if(ort != null) {
				definiereAusgang = false;
				ausgang.ort2 = ort.ort;
				ausgang = null;
				points = new Stack<Point>();
				
			} else {
				points.push(p);
				ausgang.points = points.toArray(new Point[0]);
				parent.repaint();
			}
			
		}		
	}
	
	
	@Override
	public void mouseMoved(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.NEW_AUSGANG) return;
		if(definiereAusgang) {
			points.pop();
			Point alt = points.pop();
			Point p = parallelerPunkt(alt, evt.getPoint());
			points.push(alt);
			points.push(p);
			ausgang.points = points.toArray(new Point[0]);
			parent.repaint();					
		}
	}
	
	
	private Point parallelerPunkt(Point alt, Point neu) {
		if(Math.abs(alt.x - neu.x) < Math.abs(alt.y - neu.y) ) {
			return new Point(alt.x, neu.y);
		} else 
			return new Point(neu.x, alt.y);
	}
	
}
