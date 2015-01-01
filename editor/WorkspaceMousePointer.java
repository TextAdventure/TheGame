package editor;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Unterklasse von MouseAdapter, die die Funktionalitäten des Zeiger-Tools realisert. Jeder Workspace sollte einen
 * WorkspaceMousePointer sowohl als MouseListener, als auch als MouseMotionListener adden, sonst hat das Zeiger-
 * Werkzeug keine Funktion!
 * 
 * Größe und Position von Orten variieren durch Drag and Drop
 * -> TODO: Ausgänge nicht zerstören
 * 
 * @author Felix
 *
 */
public class WorkspaceMousePointer extends MouseAdapter {

	private WeltObjekt welt;
	private Component parent;
	private IDE ide;
	
	//Wird der Mauszeiger an den Rand eines Ortes geführt, so wird in den bits von mausPos gespeichert, welcher Rand es war.
	//1. Bit = oben
	//2. Bit = links
	//3. Bit = unten
	//4. Bit = rechts
	private byte mausPos = 0;

	private int mouseX, mouseY;
	private OrtErweitert inBearbeitung = null;
	
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
			OrtDialog dialog = new OrtDialog(parent, welt, ort.ort);
			dialog.setVisible(true);
			dialog.setFocus();
			
		} else {
			AusgangErweitert ausgang = welt.ausgangAt(e.getX(), e.getY());
			if(ausgang != null) {
				new AusgangDialog(ausgang).setVisible(true);;
			}
		}
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.POINTER) return;
		OrtErweitert ort = welt.ortAt(evt.getX(), evt.getY());
		if(ort != null && mausPos != 0) {
			inBearbeitung = ort;
			mouseX = evt.getX();
			mouseY = evt.getY();
		} else {
			inBearbeitung = null;
		}
	}

	
	
	
	// - - - MouseMotionListener  - - -

	@Override
	public void mouseDragged(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.POINTER ||  inBearbeitung == null) return;
		
		Dimension groesse = inBearbeitung.groesse;
		Point position = inBearbeitung.position;
		int x = position.x, y = position.y, w = groesse.width, h = groesse.height;
		
		switch(mausPos) {
		case 1:		//oben 
			 h += mouseY - evt.getY();
			 y -= mouseY - evt.getY();
			 break;
			 
		case 2:		//links
			w += mouseX- evt.getX();
			x -= mouseX - evt.getX();
			break;
			
		case 3:		//links - oben (bewegen)
			x -= mouseX - evt.getX();
			y -= mouseY - evt.getY();
			break;
			
		case 4:		//unten
			h -= mouseY - evt.getY();
			break;
			
		case 6:		//unten-links
			w += mouseX- evt.getX();
			x -= mouseX - evt.getX();
			h -= mouseY - evt.getY();
			break;
			
		case 8:		//rechts
			w -= mouseX - evt.getX();
			break;

		case 9:		//rechts-oben
			h += mouseY - evt.getY();
			y -= mouseY - evt.getY();
			w -= mouseX - evt.getX();
			break;

		case 12:	//rechts-unten
			w -= mouseX - evt.getX();
			h -= mouseY - evt.getY();
			break;
			
		default: 
		}
		
		w = Math.max(w, 40);
		h = Math.max(h, 40);
		inBearbeitung.groesse = new Dimension(w, h);
		inBearbeitung.position = new Point(x, y);
		mouseX = evt.getX();
		mouseY = evt.getY();
		parent.repaint();
		
	}
	
	
	@Override
	public void mouseMoved(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.POINTER) return;
		
		/*
		 *  changes Cursor if
		 *  	- mouse over border of Ort
		 *  	- TODO mouse over Ausgang
		 */
		OrtErweitert ort = welt.ortAt(evt.getX(), evt.getY());
		int tol = 10;
		if(ort != null) {
			if(evt.getX() - ort.position.x < tol) {
				if(evt.getY() - ort.position.y < tol) {
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
					mausPos = 3;
				} else {
					if(ort.position.y+ort.groesse.height - evt.getY() < tol) {
						parent.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
						mausPos = 6;
								
					} else {
						parent.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
						mausPos = 2;
					}
						
				}
				
				
			} else if(ort.position.x+ort.groesse.width - evt.getX() < tol) {
				if(evt.getY() - ort.position.y < tol) {
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
					mausPos = 9;
					
				} else {
					if(ort.position.y+ort.groesse.height - evt.getY() < tol) {
						parent.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
						mausPos = 12;
						
					} else {
						parent.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
						mausPos = 8;
					}
				} 
				
				
			} else {
				if(evt.getY() - ort.position.y < tol) {
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					mausPos = 1;
					
				} else if(ort.position.y+ort.groesse.height - evt.getY() < tol) {
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					mausPos = 4;  
							
				} else {
					parent.setCursor(Cursor.getDefaultCursor());
					mausPos = 0;
				}				
			}				
			
			
		} else {
			parent.setCursor(Cursor.getDefaultCursor());
			mausPos = 0;
		}
			
		
	}

}
