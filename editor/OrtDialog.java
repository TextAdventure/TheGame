package editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Ort;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Dialog, der alle Optionen bietet, um einen Ort zu bearbeiten. Dazu gehört
 *  - festlegen von Name/Beschreibung
 *  - Untersuchbare Objekte
 *  - Gegenstände
 *  TODO: - NPCs
 *  
 *  Änderungen werden mittels des ok-Buttons gespeichert.
 *  
 * @author Felix
 *
 */
public class OrtDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabs = new JTabbedPane();
	
	private UntersuchObjPanel untersuchObj;
	private OrtAllgemeinPanel allgemein;
	private NPCPanel npcs;
	private GegenstandPanel gegenstaende;
	
	private Ort ort;
	private JButton ok;
	private Component parent;
	
	OrtDialog(Component parent, WeltObjekt welt, Ort ort) {
		//General Frame stuff
		setTitle(ort.getName());
		setModal(false);
		setSize(300, 350);
		setLayout(new BorderLayout());
		this.ort = ort;
		this.parent = parent;
		
		//create gui	
		//allgemeines Panel
		allgemein = new OrtAllgemeinPanel(ort);
		JPanel p = new JPanel(new BorderLayout());
		p.add(allgemein);
		tabs.addTab("Allgemein", p);
		
		
		//untersuchbare Obj Panel
		untersuchObj = new UntersuchObjPanel(ort);
		tabs.addTab("Untersuchb. Obj.", untersuchObj);
		
		//gegenstand Panel
		gegenstaende = new GegenstandPanel(welt, ort);
		tabs.addTab("Gegenstände", gegenstaende);
		
		//NPC panel
		npcs = new NPCPanel(ort.getNPCs(), ort);
		p = new JPanel(new BorderLayout());
		p.add(npcs);
		tabs.addTab("NPCs", p);
		
		
		//effekte Panel
		//tabs.addTab("Effekte", effekte);
		
		add(tabs, BorderLayout.CENTER);
		
		
		
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ok = new JButton("OK");
		ok.addActionListener(this);
		p.add(ok);
		add(p, BorderLayout.SOUTH);
		
	}

	
	/**
	 * Realisiert den ok-Button.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ort.setName(allgemein.getOrtName());
		ort.setBeschreibung(allgemein.getBeschreibung());
		parent.repaint();
		dispose();
	}
	
	
	/**
	 * Leitet die Aufforderung, sich den Focus zu beschaffen an das OrtAllgemeinPanel weiter.
	 */
	void setFocus() {
		allgemein.setFocus();
	}

}
