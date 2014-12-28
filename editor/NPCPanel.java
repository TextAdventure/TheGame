package editor;

import game.Ort;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import npc.NPC;

/**
 * Weiteres Panel des OrtDialogs. Ermöglicht die Verwaltung der NPCs, die sich an diesem Ort befinden.
 * 
 * @author Felix
 *
 */
public class NPCPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Vector<NPC> npcs;
	private Ort ort;
	private JList<String> list;
	private DefaultListModel<String> model;
	
	private JButton add = new JButton("neu");
	private JButton remove = new JButton("entfernen");
	
	NPCPanel(NPC[] npcObjekte, Ort ort) {
		setLayout(new BorderLayout());
		this.ort = ort;
		
		//List
		npcs = new Vector<NPC>();
		model = new DefaultListModel<String>();		
		for(NPC npc : npcObjekte) {
			npcs.add(npc);
			model.addElement(npc.getName());		
		}
		list = new JList<String>(model);
		
		JScrollPane scroll = new JScrollPane(list);
		add(scroll, BorderLayout.CENTER);
		
		
		//Buttons
		add.addActionListener(this);
		remove.addActionListener(this);
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p.add(add);
		p.add(remove);
		add(p, BorderLayout.SOUTH);
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == add) {
			JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File newNPC = fileChooser.getSelectedFile();
				try {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(newNPC));
					Object input = ois.readObject();
					if(input instanceof NPCObjekt) {
						ort.addNPC(((NPCObjekt)input).npc);
						model.addElement(((NPCObjekt)input).npc.getName());
						
					} else {
						JOptionPane.showMessageDialog(this, "Gewählte Datei enthält ist keine NPC-Datei.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					ois.close();
					
				} catch (FileNotFoundException e1) {
					//Da das File von einem FileChooser kommt, sollte das nicht passieren.
					e1.printStackTrace();
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this, "Die gewählte Datei konnte nicht gelesen werden.", "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(this, "Unbekannte Codierung.", "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
			}
			
		} else if(src == remove) {
			ort.removeNPC(npcs.get(list.getSelectedIndex()));
			list.remove(list.getSelectedIndex());
		} 
	}
}
