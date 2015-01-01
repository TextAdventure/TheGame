package editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * GUI-Componente der IDE. Stellt den Datei-Baum links in der IDE dar.
 * Der FileTree ermöglicht es eine Datei durch Doppelclick auf diese in einen neuen Tab zu laden - falls dise Datei
 * eine gültige Codierung einer Welt enthält.
 * 
 * Über Buttons kann man das angezeigte Verzeichnis ändern.
 * 
 * TODO:
 *  - CurrentDirectory ändern
 *  
 * @author Felix
 *
 */
public class FileTree extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;

	private JButton levelUp;
	private JButton browse;
	private JTree tree;
	private Component center;
	private File currDir;
	private IDE ide;
	
	/**
	 * Helper class. Allows sorting an array of Files s.t. directories come before files, and both types are sortet
	 * lexicographically.
	 * 
	 * @author Felix
	 *
	 */
	private static class FileComparator implements Comparator<File> {
		@Override
		public int compare(File file1, File file2) {
			if(file1.isDirectory())
				if(file2.isDirectory())
					return file1.getName().toLowerCase().compareTo(file2.getName().toLowerCase());
				else 
					return -1;
			else {
				if(file2.isDirectory())
					return 1;
				else 
					return file1.getName().toLowerCase().compareTo(file2.getName().toLowerCase());
			}
		}
		
	}
	
	
	FileTree(IDE ide, File currDir) {
		super(new BorderLayout());
		this.ide = ide;
		this.currDir = currDir;
		
		JPanel button = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		levelUp = new JButton("up");
		levelUp.addActionListener(this);;
		button.add(levelUp);
		
		browse = new JButton("browse...");
		browse.addActionListener(this);
		button.add(browse);
		
		add(button, BorderLayout.NORTH);
		
		
		tree = new JTree(addNodes(currDir));
		tree.addMouseListener(this);
		center = new JScrollPane(tree); 
		add(center, BorderLayout.CENTER);
		
	}

	/**
	 * Erstellt den FileTree für die Wurzel dir. 
	 * 
	 * @param dir Wurzelverzeichnis des angezeigten Datei-Baumes.
	 * @return Wurzel des erstellten Baumes.
	 */
	static DefaultMutableTreeNode addNodes(File dir) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(dir.getName());
		
		File[] list = dir.listFiles();
		if(list != null) {
			Arrays.sort(list, new FileComparator());			
			for(int i = 0; i < list.length; i++)
				node.add(addNodes(list[i]));
		}
		
		return node;
		
	}

	
	/* * * MouseListener * * */
	/**
	 * Registriert Doppelclicks auf Dateien und gibt die gewählte Datei an die IDE zm Öffnen weiter.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			int row = tree.getRowForLocation(e.getX(), e.getY());
			if(row != -1) {
				Object[] path = tree.getPathForRow(row).getPath();
				String p = "";
				for(int i = 1; i < path.length-1; i++) 
					p += path[i].toString() + "/";
				p += path[path.length-1].toString();
				
				File f = new File(currDir.getAbsolutePath() + "/" + p);
				//System.out.println(f.getAbsolutePath());
				if(f.isFile()) {
					ide.load(f);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	
	/**
	 * Realisert die Fuktionalität der Buttons up und browse.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object src = arg0.getSource();
		if(src == levelUp) {			
			if(currDir.getParent() != null) {
				currDir = currDir.getParentFile();
			}
			
		} else if(src == browse) {
			JFileChooser fileChooser = new JFileChooser(currDir);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				currDir = fileChooser.getSelectedFile();
			}
			
		}
		
	}
	
	/**
	 * Berechnet den angezeigten FileTree neu. Dadurch werden neu hinzugefüte Dateien auch angezeigt und falls zu einem
	 * neuen Verzeichnis gewechselt wurde, wird dieses geladen. 
	 */
	void update() {
		remove(center);				
		tree = new JTree(addNodes(currDir));
		tree.addMouseListener(this);
		center = new JScrollPane(tree);
		add(center, BorderLayout.CENTER);
		revalidate();
	}
}
