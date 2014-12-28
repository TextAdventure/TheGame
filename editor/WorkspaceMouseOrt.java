package editor;

import game.Ort;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Subklasse von MouseAdapter. Realisiert die Funktionalität der NEW_ORT-Tools. Jeder Workspace sollte einen
 * WorkspaceMosueOrt als MosueListener und MosueMotionListener adden, sonst hat das new Ort-Tool keine 
 * Funktionalität!
 * 
 * @author Felix
 *
 */
public class WorkspaceMouseOrt extends MouseAdapter {

	private Component parent;
	private WeltObjekt welt;
	private IDE ide;
	private boolean definiereOrt = false;
	private OrtErweitert ort = null;
	private Point start = null;
	private int minSize = 40;
	
	
	WorkspaceMouseOrt(WeltObjekt welt, Component parent, IDE ide) {
		this.welt = welt;
		this.parent = parent;
		this.ide = ide;
	}
	
	@Override
	public void mousePressed(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.NEW_ORT) return;
		definiereOrt = true;
		start = evt.getPoint();
		ort = new OrtErweitert(new Ort("unbenannt", ""), start, new Dimension(minSize, minSize));
		welt.addOrt(ort);
		parent.repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.NEW_ORT) return;
		definiereOrt = false;
		
	}
	
	
	@Override
	public void mouseDragged(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.NEW_ORT) return;
		if(definiereOrt) {
			int w = Math.max(Math.abs(evt.getX()-start.x), minSize);
			int h = Math.max(Math.abs(evt.getY()-start.y), minSize);
			ort.groesse = new Dimension(w, h);
			ort.position = new Point(Math.min(evt.getX(), start.x), Math.min(evt.getY(), start.y));
			parent.repaint();
		}		
	}
}
