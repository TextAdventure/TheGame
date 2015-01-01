package editor;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.undo.UndoManager;

/**
 * Custom Component auf Basis des JTabbedPane. Stellt Informationen über den aktuellen Workspace bereit.
 * 
 * Recycled von BrainFuck IDE.
 * 
 * @author Felix
 *
 */
public class BFTabbedPane extends JTabbedPane {
	//A custom TabbedPane with close-Buttons on each tab!	
	private static final long serialVersionUID = 1L;
		
	/**
	 * Hilfsklsse. Realisiert den X-Button in dem Tabs, mit dem man einen Tab schließen kann.
	 * @author Felix
	 *
	 */
	private class BFTabButton extends JButton implements ActionListener {
		//the close-Button
		private static final long serialVersionUID = 1L;
		
		BFTabButton() {
			setPreferredSize(new Dimension(14, 14));
			addActionListener(this);
		}
		
		/**
		 * Schließt den aktuellen Tab.
		 */		
		@Override
		public void actionPerformed(ActionEvent evt) {
			int pos = BFTabbedPane.this.indexOfTabComponent(getParent());
			if(pos != -1)
				BFTabbedPane.this.remove(pos);
			else 
				System.err.println("Error: the tab was not found at the TabbedPane");
		}
			
		/**
		 * Zeichnet das rote X im Button.
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g.create();
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.RED);
			g2d.drawLine(2, 2, getWidth()-2, getHeight()-2);
			g2d.drawLine(getWidth()-2, 2, 2, getHeight()-2);
		}
	}
	
	
	protected class ChangeTabLeftAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent evt) {
			BFTabbedPane.this.previousTab();
		}
	}
	protected class ChangeTabRightAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent evt) {
			BFTabbedPane.this.nextTab();
		}
	}
	
	/**
	 * Erstellt einen neuen Tab mit Titel title und Inhalt comp. Der Tab bekommt auch einen Button zum schließen.
	 */
	@Override
	public void addTab(String title, Component comp) {
		super.addTab(title, comp);
		
		int pos = indexOfComponent(comp);
		if(pos != -1) {
			
			//create a new JPanel
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			panel.setOpaque(false);
			
			JLabel label = new JLabel() {
				private static final long serialVersionUID = 1L;

				@Override
				public String getText() {
					int posInPane = BFTabbedPane.this.indexOfTabComponent(getParent());
					if(posInPane != -1) 
						return BFTabbedPane.this.getTitleAt(posInPane);
					else return "";
				}
			};
			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
			panel.add(label);			
			
			panel.add(new BFTabButton());
			
			setTabComponentAt(pos, panel);
			
			//select the new Tab
			setSelectedIndex(getTabCount() - 1);
		} else
			System.err.println("Error: the tab has not been added to the JTabbedPane");
	}
	
	/**
	 * Wählt den nächsten Tab als aktiven Tab aus. Falls gerade der letzte Tab ausgewählt war, 
	 * so wird als nächstes der erste Tab ausgewählt.
	 */
	public void nextTab() {
		setSelectedIndex((getSelectedIndex() + 1) % getTabCount());
	}
	
	/**
	 * Wählt den Tab links vom aktuell ausgewählten als aktiv aus. Falls der erste Tab ausgewählt war, so wird
	 * durch previousTab() der letzt Tab als aktiv ausgewählt. 
	 */
	public void previousTab() {
		setSelectedIndex((getTabCount() + getSelectedIndex() -1) % getTabCount());
	}
	
	protected Action getNextTabAction() {
		return new ChangeTabRightAction();
	}
	protected Action getPreviousTabAction() {
		return new ChangeTabLeftAction();
	}
	
	public UndoManager getCurrentUndoManager() {
		return null;
	}
	
	/**
	 * Gibt die Datei zurück, in der der aktuelle Workspace gespeichert ist.
	 * @return Die Datei, in der der aktuelle Workspace gespeichert ist. Falls der Workspace noch nie gespeichert wurde 
	 * und nicht aus einer Datei geladen wurde, so ist noch keine Datei festgelegt und es wird null zurück gegeben.
	 */
	public File getCurrentFile() {
		if(getCurrentWorkspace() == null) return null;
		return getCurrentWorkspace().getFile();
	}
	
	/**
	 * Legt die Datei fest, die zu dem aktuellen Workspace gehören soll.
	 * @param file Die Datei, in der der Workspace beim Speichern (nicht Soeichern unter, d.h. ohne Auswahl einer Datei) 
	 * gespeichert werden soll.
	 */
	public void setCurrentFile(File file) {
		if(getCurrentWorkspace() == null) return;
		getCurrentWorkspace().setFile(file);
	}
	
	/**
	 * Gibt den Aktuell aktiven Workspace zurück.
	 * @return Den Workspace im aktuell ausgewählten Tab. Falls kein Tab geöffnet ist, oder im aktuellen Tab
	 * kein Workspace in einem JScrollPane angezeigt wird, so wird null zurück gegeben. 
	 */
	public Workspace getCurrentWorkspace() {
		Component c = getSelectedComponent();
		if(c instanceof JScrollPane) {
			Component comp = ((JScrollPane)c).getViewport().getComponent(0); 
			if(comp instanceof Workspace)
				return (Workspace)comp;
			else 
				return null;
		} else
			return null;
	}
	
	/**
	 * Setzt den Titel des aktuell ausgewählten Tabs auf den Namen der Datei, in der der aktuelle Workspace gesoeichert ist.
	 * Falls getCurrentFile() null liefert, so wird nichts getan.
	 */
	public void updateCurrentTitle() {
		if(getCurrentFile() == null) return;
		setTitleAt(getSelectedIndex(), getCurrentFile().getName());
		revalidate();
	}
	
}
