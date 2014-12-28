package editor;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.undo.UndoManager;

/**
 * Custom Component auf Basis des JTabbedPane.
 * 
 * Recycled von BrainFuck IDE.
 * 
 * @author Felix
 *
 */
public class BFTabbedPane extends JTabbedPane {
	//A custom TabbedPane with close-Buttons on each tab!	
	private static final long serialVersionUID = 1L;
		
	private class BFTabButton extends JButton implements ActionListener {
		//the close-Button
		private static final long serialVersionUID = 1L;
		
		BFTabButton() {
			setPreferredSize(new Dimension(14, 14));
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent evt) {
			int pos = BFTabbedPane.this.indexOfTabComponent(getParent());
			if(pos != -1)
				BFTabbedPane.this.remove(pos);
			else 
				System.err.println("Error: the tab was not found at the TabbedPane");
		}
			
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
	
	public void nextTab() {
		setSelectedIndex((getSelectedIndex() + 1) % getTabCount());
	}
	
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
	
	public File getCurrentFile() {
		Component c = getSelectedComponent();
		if(c instanceof Workspace)
			return ((Workspace)c).getFile();
		else 
			return null;
	}
	
	public boolean setCurrentFile(File file) {
		Component c = getSelectedComponent();
		if(c instanceof Workspace) {
			((Workspace)c).setFile(file);
			return true;
		} else
			return false;
	}
	
	public Workspace getCurrentWorkspace() {
		Component c = getSelectedComponent();
		if(c instanceof JScrollPane)
			return (Workspace)((JScrollPane)c).getViewport().getComponent(0);
		else
			return null;
	}
	
	public void updateCurrentTitle() {
		Component c = getSelectedComponent();
		if(c instanceof Workspace) 
			setTitleAt(getSelectedIndex(), ((Workspace)c).getName());
	}
	
}
