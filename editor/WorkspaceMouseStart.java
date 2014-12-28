package editor;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Subklasse von MouseAdapter. Realisiert die Funktionalität des SET_START-Tools. Jeder Workspace sollte einen
 * WorkspaceMosueStart als MosueListener und MosueMotionListener adden, sonst hat das set Start-Tool keine 
 * Funktionalität!
 * 
 * @author Felix
 *
 */
public class WorkspaceMouseStart extends MouseAdapter {

	private WeltObjekt welt;
	private Component parent;
	private IDE ide;
	
	WorkspaceMouseStart(WeltObjekt welt, Component parent, IDE ide) {
		this.welt = welt;
		this.parent = parent;
		this.ide = ide;
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		if(ide.getSelectedTool() != Tools.START_ORT) return;
		OrtErweitert ort = welt.ortAt(evt.getX(), evt.getY());
		if(ort != null) {
			welt.setStartOrt(ort);
			parent.repaint();
		}
	}
}
