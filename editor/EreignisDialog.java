package editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * Zentraler Dialog der Ereignisverwaltung.
 * 
 * @author Felix
 *
 */
public class EreignisDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton neu, loeschen, bearbeiten;
	private WeltObjekt welt;
	
	private DefaultMutableTreeNode root;
	private JTree ereignisse;
	
	EreignisDialog(WeltObjekt welt) {
		setTitle("Ereiginisse verwalten");
		setModal(false);
		setLayout(new BorderLayout());
		this.welt = welt;

		
		root = new DefaultMutableTreeNode("alle Ereignisse");
		ereignisse = new JTree(root);
		ereignisse.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		
		//TODO
		root.add(ereigissbaumErstellen());
		root.add(ereigissbaumErstellen());
		root.add(ereigissbaumErstellen());
		
		ereignisse.expandRow(0);
		ereignisse.setRootVisible(false);
		add(new JScrollPane(ereignisse), BorderLayout.CENTER);
		
		
		// Button initialisieren
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		neu = new JButton("neu...");
		neu.addActionListener(this);
		buttons.add(neu);
		
		loeschen = new JButton("löschen");
		loeschen.addActionListener(this);
		buttons.add(loeschen);
		
		bearbeiten = new JButton("bearbeiten");
		bearbeiten.addActionListener(this);
		buttons.add(bearbeiten);
		
		add(buttons, BorderLayout.SOUTH);
		
	}
	
	private DefaultMutableTreeNode ereigissbaumErstellen() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Titanic sinkt");
		DefaultMutableTreeNode ereignis = new DefaultMutableTreeNode("Auslösende Ereignisse");
		ereignis.add(new DefaultMutableTreeNode("Spieler betritt Ort"));
		root.add(ereignis);
		
		DefaultMutableTreeNode bedingungen = new DefaultMutableTreeNode("Bedingungen");
		bedingungen.add(new DefaultMutableTreeNode("Spieler hat ausgerüstet Bedingung"));
		bedingungen.add(new DefaultMutableTreeNode("Spieler hat Gegenstand"));
		root.add(bedingungen);
		
		DefaultMutableTreeNode aktionen = new DefaultMutableTreeNode("Aktionen");
		aktionen.add(new DefaultMutableTreeNode("Ort remove Ausgang"));
		root.add(aktionen);
		
		return root;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src == neu) {
			
		} else if(src == loeschen) {

		} else {
			
		}
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		new EreignisDialog(new WeltObjekt()).setVisible(true);
	}
}
