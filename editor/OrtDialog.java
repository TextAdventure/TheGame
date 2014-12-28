package editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Ort;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Dialog, der alle Optionen bietet, um einen Ort zu bearbeiten. Dazu gehört
 *  - festlegen von Name/Beschreibung
 *  - Untersuchbare Objekte
 *  - Gegenstände
 *  - NPCs
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
	private EffektePanel effekte;
	
	private Ort ort;
	private JButton ok;
	private JFrame owner;
	
	OrtDialog(JFrame owner, WeltObjekt welt, Ort ort) {
		//General Frame stuff
		super(owner, ort.getName(), false);
		setSize(300, 350);
		setLayout(new BorderLayout());
		this.ort = ort;
		this.owner = owner;
		
		//create gui	
		//allgemeines Panel
		allgemein = new OrtAllgemeinPanel(ort);
		JPanel p = new JPanel(new BorderLayout());
		p.add(allgemein);
		tabs.addTab("Allgemein", p);
		
		
		//untersuchbare Obj Panel
		/*String[] objekteNamen = ort.getUntersuchbareObjekteNamen();
		if(objekteNamen.length == 1 && objekteNamen[0] == "NICHTS") {
			objekteNamen = new String[0];
			System.out.println("NICHTS");
		}
		UntersuchbaresObjekt[] objekte = new UntersuchbaresObjekt[objekteNamen.length];
		for(int i = 0; i < objekte.length; i++) {
			objekte[i] = ort.getUntersuchbaresObjekt(objekteNamen[i]);
			System.out.println(objekteNamen[i]);
		}
		untersuchObj = new UntersuchObjPanel(objekte, ort);*/
		untersuchObj = new UntersuchObjPanel(ort.getUnteruschbareObjekte(), ort);
		p = new JPanel(new BorderLayout());
		p.add(untersuchObj);
		tabs.addTab("Untersuchb. Obj.", p);
		
		
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

	
	/* * * ActionListener * * */
	@Override
	public void actionPerformed(ActionEvent e) {
		ort.setName(allgemein.getOrtName());
		ort.setBeschreibung(allgemein.getBeschreibung());
		//TODO owner.repaint();
		dispose();
	}

}
