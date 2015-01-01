package editor;

import game.SpielWelt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;

/**
 * Hauptklasse des Editors. Repräsentiert den Main-Frame erzeugt die Menüleiste und stellt deren Funktionalität bereit.
 * 
 * - Übersetzer Menü
 * 
 * TODO:
 *  - Undo/Redo
 *  - Edit Menü überarbeiten
 *  - Exception Handling
 *  
 * @author Felix
 *
 */
public class IDE extends JFrame implements ChangeListener {
	private static final long serialVersionUID = 1L;

	private BFTabbedPane tabbedPane;
	private WorldToolbar tools;
	private FileTree tree;
	
	//io stuff
	private JFileChooser fileDialog = new JFileChooser(System.getProperty("user.dir"));
	
	//undo/redo stuff
	private UndoAction undoAction;
	private RedoAction redoAction;
	
	IDE() {
		super("TextAdventure Editor");	
		
		//general stuff for the window
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((d.width - 700) / 2, (d.height - 500) / 2, 700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		/* * * building the GUI * * */		
			
		//building the menu bar
		JMenuBar menu = new JMenuBar();
		menu.add(createFileMenu());
		menu.add(createEditMenu());
		menu.add(createRunMenu());
		setJMenuBar(menu);		
		
		//the central TabbedPane
		JPanel p = new JPanel(new BorderLayout());
		tabbedPane = new BFTabbedPane();
		tabbedPane.addChangeListener(this);
		p.add(tabbedPane, BorderLayout.CENTER);
		
		//Toolbar
		tools = new WorldToolbar();
		tools.gegenstandActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Workspace currentWS = IDE.this.tabbedPane.getCurrentWorkspace();
				if(currentWS != null)
					new GegenstandTabelleDialog(currentWS.getWorld()).setVisible(true);
			}
		});
		p.add(tools, BorderLayout.NORTH);
		add(p, BorderLayout.CENTER);
		
		//FileTree
		tree = new FileTree(this, new File(System.getProperty("user.dir")));
		add(tree, BorderLayout.WEST);
		
		
	}
	
	@SuppressWarnings("serial")
	private class UndoAction extends AbstractAction {
		public UndoAction() {
			super("Undo");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			UndoManager manager = IDE.this.tabbedPane.getCurrentUndoManager();
			if(manager.canUndo()) manager.undo();
			
		}		
	}
	
	@SuppressWarnings("serial")
	private class RedoAction extends AbstractAction {
		public RedoAction() {
			super("Redo");
		}
		
		@Override
		public void actionPerformed(ActionEvent evt) {
			UndoManager manager = IDE.this.tabbedPane.getCurrentUndoManager();
			if(manager.canRedo()) manager.redo();
		}
	}
	
	/* * * IO-Actions * * */	
	@SuppressWarnings("serial")
	private class NewAction extends AbstractAction {
		NewAction() {
			super("New");
		}		
		@Override
		public void actionPerformed(ActionEvent e) {
			IDE.this.newFile();
		}		
	}
	
	@SuppressWarnings("serial")
	private class LoadAction extends AbstractAction {
		LoadAction() {
			super("Open");
		}		
		@Override
		public void actionPerformed(ActionEvent e) {
			IDE.this.load();			
		}		
	}
	
	@SuppressWarnings("serial")
	private class SaveAction extends AbstractAction {
		SaveAction() {
			super("Save");
		}		
		@Override
		public void actionPerformed(ActionEvent evt) {
			IDE.this.save();
		}
	}
	
	@SuppressWarnings("serial")
	private class SaveAsAction extends AbstractAction {
		SaveAsAction() {
			super("Save As...");
		}		
		@Override
		public void actionPerformed(ActionEvent e) {
			IDE.this.saveAs();			
		}		
	}	
	/* * * IO-Actions End * * */
	
	@SuppressWarnings("serial")
	private class ToJavaAction extends AbstractAction {
		ToJavaAction() {
			super("Nach Java übersetzen");
		}
		@Override
		public void actionPerformed(ActionEvent evt) {
			String code = "";
			try {
				code = Uebersetzer.toJavaCode(IDE.this.tabbedPane.getCurrentWorkspace().getWorld());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(IDE.this, "Beim Lesen des vordefinierten Java-Code-Frameworks ist ein Fehler aufgetreten.", "IOException", JOptionPane.ERROR_MESSAGE);
			}
			if(code.length() > 0) {
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(new File("WorldGenerator.java"));
					fos.write(code.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
	@SuppressWarnings("serial")
	private class ToDatAction extends AbstractAction {
		ToDatAction() {
			super("In .dat-Datei übersetzen");
		}		
		@Override
		public void actionPerformed(ActionEvent e) {
			Workspace workspace = tabbedPane.getCurrentWorkspace();
			if(workspace != null) {
				
				//speichern
				JFileChooser fileChooser = new JFileChooser();
				if(fileChooser.showSaveDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
					try {
						SpielWelt spielWelt = Uebersetzer.toSpielWelt(workspace.getWorld());
						File file = fileChooser.getSelectedFile();
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
						oos.writeObject(spielWelt);
						oos.close();
						
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(IDE.this, "Die ausgewählte Datei existiert nicht!", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(IDE.this, "Beim Schreiben der Datei ist ein Fehler aufgetreten.", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					
				}
				
				
			}
			
		}
		
	}
	
	@SuppressWarnings("serial")
	private class QuitAction extends AbstractAction {
		QuitAction() {
			super("Quit");
		}
		@Override
		public void actionPerformed(ActionEvent evt) {
			System.exit(0);
		}
	}
	
	/* * * IO-Methods * * */	
	/**
	 * Öffnet einen neuen Tab mit einem neuen, leeren Workspace.
	 */
	private void newFile() {
		JScrollPane bfComp = new JScrollPane(new Workspace(this, new WeltObjekt()));
		/*KeyStroke altLeft = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, Event.ALT_MASK);
		bfComp.textPane.getInputMap().put(altLeft, tabbedPane.getPreviousTabAction());
		KeyStroke altRight = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, Event.ALT_MASK);
		bfComp.textPane.getInputMap().put(altRight, tabbedPane.getNextTabAction());*/
		tabbedPane.addTab("unbennant", bfComp);
		updateTitle();
	}
	
	/**
	 * Speichert den aktuellen Workspace in dessen Datei. Falls der aktuelle Workspace noch keine zugehörige Datei hat,
	 * d.h. der Workspace wurde noch nie gespeichert, dann wird saveAs() aufgerufen.
	 */
	private void save() {
		File file = tabbedPane.getCurrentFile();
		if(file == null) {
			System.out.println("saveas");
			saveAs();
			return;
		}
		save(tabbedPane.getCurrentWorkspace(), file);		
	}
	
	/**
	 * Speichert den übergebenen Workspace in die Datei file.
	 * @param workspace
	 * @param file
	 */
	private void save(Workspace workspace, File file) {
		WeltObjekt welt = workspace.getWorld();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(welt);
			oos.close();
			
			tree.update();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Beim Schreiben der Datei ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}		
	}
	
	/**
	 * Speichert einen Workspace zum ersten Mal, bzw. in eine neue Datei. Der Benutzer kann über einen File-Dialog
	 * auswählen, wo und unter welchem Namen der Workspace gespeichert werden soll.
	 */
	private void saveAs() {
		if(fileDialog.showSaveDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
			File file = fileDialog.getSelectedFile();
			System.out.println(file.getAbsolutePath());
			save(tabbedPane.getCurrentWorkspace(), file);
			tabbedPane.setCurrentFile(file);
			tabbedPane.updateCurrentTitle();
			updateTitle();
		}
	}
	
	/**
	 * Öffnet einen File-Dialog und dann die gewählte Datei in einem neuen Tab. Falls die gewählte Datei keine gültige
	 * Codierung einer Welt enthält, wird dies dem Benutzer mitgeteilt.  
	 */
	private void load() {
		if(fileDialog.showOpenDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
			File file = fileDialog.getSelectedFile();
			load(file);
		}
	}

	/**
	 * Öffnet die Datei file in einem neuen Tab. Falls die gewählte Datei keine gültige
	 * Codierung einer Welt enthält, wird dies dem Benutzer mitgeteilt.  
	 */	
	void load(File file) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			Object input = ois.readObject();
			ois.close();
			if(!(input instanceof WeltObjekt)) {
				JOptionPane.showMessageDialog(this, "Die Datei " + file.getName() + " enthält keine Codierung einer Welt.", "Ungültige Datei", JOptionPane.ERROR_MESSAGE);
				return;
			}
			WeltObjekt ort = (WeltObjekt)input;
			JScrollPane bfComp =  new JScrollPane(new Workspace(this, file, ort));
			tabbedPane.addTab(file.getName(), bfComp);
			tabbedPane.setCurrentFile(file);
			
		} catch(IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Fehler beim Lesen der Datei aufgetraten.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Sourcecode beschädigt! Klasse nicht gefunden!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		updateTitle();
	}
	/* * * IO-Methods End * * */
	
	@Override
	public void stateChanged(ChangeEvent evt) {
		updateTitle();
	}
	
	public void updateTitle() {
		File currentFile = tabbedPane.getCurrentFile();
		if(currentFile != null) 
			setTitle("Text Adventure - " + currentFile.getName());
		else 
			setTitle("Text Adventure - unnamed");
	}
	
	/* * * Construction-Methods * * */
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("Datei");
		
		//newFile
		JMenu neu = new JMenu("neu...");
		JMenuItem newWorld = new JMenuItem(new NewAction());
		KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
		newWorld.setAccelerator(ctrlN);		
		neu.add(newWorld);
		
		JMenuItem newNPC = new JMenuItem("neuer NPC");
		neu.add(newNPC);
		menu.add(neu);
		
		//open
		JMenuItem load = new JMenuItem(new LoadAction());
		KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK);
		load.setAccelerator(ctrlO);
		menu.add(load);
		
		menu.addSeparator();
		
		//save
		JMenuItem save = new JMenuItem(new SaveAction());
		KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
		save.setAccelerator(ctrlS);
		menu.add(save);
		//saveAs
		JMenuItem saveAs = new JMenuItem(new SaveAsAction());
		//KeyStroke ctrlShiftS = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
		//saveAs.setAccelerator(ctrlShiftS);
		menu.add(saveAs);
		
		menu.addSeparator();
		
		//exit
		JMenuItem exit = new JMenuItem(new QuitAction());
		KeyStroke ctrlQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK);
		exit.setAccelerator(ctrlQ);
		menu.add(exit);
		
		return menu;
	}
	
	private JMenu createEditMenu() {
		JMenu menu = new JMenu("Bearbeiten");
		
		//undo
		undoAction = new UndoAction();
		JMenuItem undoItem = new JMenuItem(undoAction);
		KeyStroke ctrlZ = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
		undoItem.setAccelerator(ctrlZ);
		menu.add(undoItem);		
		//redo
		redoAction = new RedoAction();
		JMenuItem redoItem = new JMenuItem(redoAction);
		KeyStroke ctrlY = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);
		redoItem.setAccelerator(ctrlY);
		menu.add(redoItem);	
		
		menu.addSeparator();
		
		//cut
		JMenuItem cut = new JMenuItem(DefaultEditorKit.cutAction);
		KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK);
		cut.setAccelerator(ctrlX);
		menu.add(cut);
		//copy
		JMenuItem copy = new JMenuItem(DefaultEditorKit.copyAction);
		KeyStroke ctrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK);
		copy.setAccelerator(ctrlC);
		menu.add(copy);
		//paste
		JMenuItem paste = new JMenuItem(DefaultEditorKit.pasteAction);
		KeyStroke ctrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK);
		paste.setAccelerator(ctrlV);
		menu.add(paste);
		
		menu.addSeparator();
		
		//selectAll
		JMenuItem selectAll = new JMenuItem(DefaultEditorKit.selectAllAction);
		KeyStroke ctrlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK);
		selectAll.setAccelerator(ctrlA);
		menu.add(selectAll);
		
		return menu;
	}
	
	public JMenu createRunMenu() {
		JMenu menu = new JMenu("Übersetzen");

		JMenuItem translateJava = new JMenuItem(new ToJavaAction());
		menu.add(translateJava);
		
		JMenuItem translateDat = new JMenuItem(new ToDatAction());
		menu.add(translateDat);
		
		
		return menu;
	}

	
	void showWorldTools() {
		
	}
	void showNPCTools() {
		
	}
	
	
	Tools getSelectedTool() {
		return tools.getSelectedTool();
	}
	

	public static void main(String[] args) {
		new IDE().setVisible(true);
	}
}

