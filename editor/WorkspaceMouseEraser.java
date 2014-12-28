package editor;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Subklasse von MouseAdapter. Realisiert die Funktionalität des ERASER-Tools. Jeder Workspace sollte einen
 * WorkspaceMosueEraser als MosueListener und MosueMotionListener adden, sonst hat das Eraser-Tool keine 
 * Funktionalität!
 * 
 * @author Felix
 *
 */
public class WorkspaceMouseEraser extends MouseAdapter {

	private WeltObjekt welt;
	private Component parent;
	private IDE ide;
	
	WorkspaceMouseEraser(WeltObjekt welt, Component parent, IDE ide) {
		this.welt = welt;
		this.parent = parent;
		this.ide = ide;		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(ide.getSelectedTool() != Tools.ERASER) return;
		OrtErweitert ort = welt.ortAt(e.getX(), e.getY());
		if(ort != null) {
			welt.removeOrt(ort);
			parent.repaint();
			return;
		}
		
		AusgangErweitert ausgang = welt.ausgangAt(e.getX(), e.getY());
		if(ausgang != null) {
			welt.removeAusgang(ausgang);
			parent.repaint();
			return;
		}
	}
}
