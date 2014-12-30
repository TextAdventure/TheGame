package editor;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Unterklasse von MouseAdapter, die die Funktionalitäten des Zeiger-Tools realisert. Jeder Workspace sollte einen
 * WorkspaceMousePointer sowohl als MouseListener, als auch als MouseMotionListener adden, sonst hat das Zeiger-
 * Werkzeug keine Funktion!
 * 
 * TODO:
 *  - Größe und Position von Orten variieren durch Drag and Drop
 * 
 * @author Felix
 *
 */
public class WorkspaceMousePointer extends MouseAdapter {

	private WeltObjekt welt;
	private Component parent;
	private IDE ide;
	
	WorkspaceMousePointer(WeltObjekt welt, Component parent, IDE ide) {
		this.welt = welt;
		this.parent = parent;
		this.ide = ide;
	}
	
	
	// - - - MouseListener  - - -
	@Override
	public void mouseClicked(MouseEvent e) {
		if(ide.getSelectedTool() != Tools.POINTER) return;
		/*
		 * detect clicks on:
		 * 	- ort --> open OrtDialog
		 * 	- ausgang --> open AusgangDialog
		 */
		OrtErweitert ort = welt.ortAt(e.getX(), e.getY());
		if(ort != null) {
			new OrtDialog(parent, welt, ort.ort).setVisible(true);
			
		} else {
			AusgangErweitert ausgang = welt.ausgangAt(e.getX(), e.getY());
			if(ausgang != null) {
				new AusgangDialog(ausgang).setVisible(true);;
			}
		}
		
		
	}

	
	
	
	// - - - MouseMotionListener  - - -

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(ide.getSelectedTool() != Tools.POINTER) return;
		
		/*
		 *  changes Cursor if
		 *  	- mouse over border of Ort
		 *  	- mouse over Ausgang
		 */
		OrtErweitert ort = welt.ortAt(arg0.getX(), arg0.getY());
		int tol = 10;
		if(ort != null) {
			if(arg0.getX() - ort.position.x < tol) {
				if(arg0.getY() - ort.position.y < tol) {
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				} else {
					if(ort.position.y+ort.groesse.height - arg0.getY() < tol)
						parent.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
					else
						parent.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				}
				
				
			} else if(ort.position.x+ort.groesse.width - arg0.getX() < tol) {
				if(arg0.getY() - ort.position.y < tol) {
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
				} else if(ort.position.y+ort.groesse.height - arg0.getY() < tol)
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
				else 
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				
				
			} else if(arg0.getY() - ort.position.y < tol || ort.position.y+ort.groesse.height - arg0.getY() < tol) {
				parent.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			} else
				parent.setCursor(Cursor.getDefaultCursor());
			
			
		} else 
			parent.setCursor(Cursor.getDefaultCursor());
		
	}

}
