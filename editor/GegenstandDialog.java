package editor;

import game.items.Gegenstand;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import util.NumerusGenus;

public class GegenstandDialog extends JDialog implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;

	private static final String[] typen = {"Gegenstand", "KommandoGegenstand", "VerwendbarerGegenstand", "Waffe", "Rüstung", "Accesoire"};
	private WeltObjekt welt;
	private GegenstandTabelleDialog parent;
	
	private JTextField namen;
	private JComboBox<NumerusGenus> numGen;
	private JTextField plural;
	private JTextArea beschreibung;
	private JComboBox<String> typ;
	
	private JPanel eigenschaften;
	
	GegenstandDialog(GegenstandTabelleDialog parent, WeltObjekt welt) {
		this.parent = parent;		
		this.welt = welt;
		setModal(false);
		setTitle("neuer Gegenstand");
		
		setLayout(new BorderLayout());
		
		add(createAllgemeinPanel(), BorderLayout.NORTH);
		
		eigenschaften = new JPanel(new CardLayout());
		eigenschaften.add(createGegenstandPanel(), typen[0]);
		eigenschaften.add(createKommandoGegenstandPanel(), typen[1]);
		eigenschaften.add(createVerwendbarerGegenstandPanel(), typen[2]);
		eigenschaften.add(createWaffePanel(), typen[3]);
		eigenschaften.add(createRuestungPanel(), typen[4]);
		eigenschaften.add(createAccesoirePanel(), typen[5]);
		add(eigenschaften, BorderLayout.CENTER);
		
		JPanel button = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton ok = new JButton("OK");
		ok.addActionListener(this);
		button.add(ok);
		add(button, BorderLayout.SOUTH);
		
		pack();
		
	}
	
	private JPanel createAllgemeinPanel() {
		JPanel allgemein = new JPanel(new GridBagLayout());
		
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH; 
		c.gridx = 0;
		c.gridy = 0;
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(new JLabel("Namen (mit ; trennen):"));
		allgemein.add(p, c);		
		
		namen = new JTextField();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		allgemein.add(namen, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(new JLabel("Grammatik. Geschlecht:"));
		allgemein.add(p, c);		
		
		NumerusGenus[] numGenArr = new NumerusGenus[]{NumerusGenus.FEMININ, NumerusGenus.MASKULIN, NumerusGenus.NEUTRUM, NumerusGenus.PLURAL}; 
		numGen = new JComboBox<NumerusGenus>(numGenArr);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		allgemein.add(numGen, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(new JLabel("Name im Plural:"));
		allgemein.add(p, c);	
		
		plural = new JTextField();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		allgemein.add(plural, c);
				
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(new JLabel("Beschreibung:"));
		allgemein.add(p, c);	
		
		beschreibung = new JTextArea();
		beschreibung.setWrapStyleWord(true);
		beschreibung.setLineWrap(true);
		beschreibung.setPreferredSize(new Dimension(beschreibung.getPreferredSize().width, 50));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		JScrollPane scroll = new JScrollPane(beschreibung);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		allgemein.add(scroll, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 4;
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(new JLabel("Typ:"));
		allgemein.add(p, c);
		
		typ = new JComboBox<String>(typen);
		typ.addItemListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		allgemein.add(typ, c);
		
		return allgemein;
	}
	

	
	private JPanel createGegenstandPanel() {//TODO
		JPanel geg = new JPanel();
		geg.setBackground(java.awt.Color.CYAN);
		return geg;
	}
	
	private JPanel createKommandoGegenstandPanel() {//TODO
		JPanel kommGeg = new JPanel();
		kommGeg.setBackground(java.awt.Color.BLACK);
		return kommGeg;
	}
	
	private JPanel createVerwendbarerGegenstandPanel() {//TODO
		JPanel verwGeg = new JPanel();
		verwGeg.setBackground(java.awt.Color.BLUE);
		return verwGeg;
	}
	
	private JPanel createWaffePanel() {	//TODO
		JPanel waffe = new JPanel();
		waffe.setBackground(java.awt.Color.GREEN);
		return waffe;
	}
	
	private JPanel createRuestungPanel() {//TODO
		JPanel ruestung = new JPanel();
		ruestung.setBackground(java.awt.Color.RED);
		return ruestung;
	}
	
	private JPanel createAccesoirePanel() {//TODO
		JPanel accesoire = new JPanel();
		accesoire.setBackground(java.awt.Color.WHITE);
		return accesoire;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(typ.getSelectedIndex()) {
		case 0:
			Gegenstand g = new Gegenstand(namen.getText().split(";"), plural.getText(), (NumerusGenus)numGen.getSelectedItem(), beschreibung.getText());
			welt.addGegenstand(g);
			
			break;
		
		case 1:
			
		case 2:
			
		case 3:
			
		case 4:
			
		case 5:
			
		default:
			
		}
		
		parent.updateTabelle();
		parent.revalidate();
		dispose();
		
	}
	
	public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(eigenschaften.getLayout());
        cl.show(eigenschaften, typen[typ.getSelectedIndex()]);
    }
	
}
