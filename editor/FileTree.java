package editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * GUI-Componente der IDE. Stellt den Datei-Baum links in der IDE dar.
 * 
 * TODO:
 *  - Datei öffnen durch Doppelclick
 *  - CurrentDirectory ändern
 *  
 * @author Felix
 *
 */
public class FileTree extends JTree implements MouseListener {
	private static final long serialVersionUID = 1L;

	FileTree(File dir) {
		super(addNodes(null, dir));
	}

	static DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
		String curPath = dir.getName();
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
		if (curTop != null) { // should only be null at root
			curTop.add(curDir);
		}
		
		Vector<String> ol = new Vector<String>();
		String[] tmp = dir.list();
		for (int i = 0; i < tmp.length; i++)
			ol.addElement(tmp[i]);		
		Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
		
		File f;
		Vector<String> files = new Vector<String>();
		// Make two passes, one for Dirs and one for Files. This is #1.
		for (int i = 0; i < ol.size(); i++) {
			String thisObject = ol.elementAt(i);
			String newPath = thisObject;
			if ((f = new File(newPath)).isDirectory())
				addNodes(curDir, f);
			else
				files.addElement(thisObject);
		}
		
		// Pass two: for files.
		for (int fnum = 0; fnum < files.size(); fnum++) {
			DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(files.elementAt(fnum));
			curDir.add(dmtn);
		}
		
		return curDir;
	}

	
	/* * * MouseListener * * */
	@Override
	public void mouseClicked(MouseEvent e) {
		//TODO
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
