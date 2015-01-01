package editor;

import game.Ausgang;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Dialog, der die Funktionalität, einen Ausgang zu bearbeit, bietet. Dazu gehören
 *  - festlegen, welche Richtungen angeboten werden
 *  - Richtungsbezeichner (auch eigene möglich)
 *  
 *  TODO: Schlüssel hinzufügen
 *   
 * @author Felix
 *
 */
public class AusgangDialog extends JDialog implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;

	
	private AusgangErweitert ausgang;
	private JComboBox<String> von1nach2Richtung;
	private JComboBox<String> von2nach1Richtung;
	private JCheckBox von1nach2Bool;
	private JCheckBox von2nach1Bool;
	private JButton ok;
	
	AusgangDialog(AusgangErweitert ausgang ) {
		//super(owner, "Ausgang", false);
		setTitle("Ausgang");
		setModal(false);
		this.ausgang = ausgang;
		setSize(300, 250);
		setLayout(new BorderLayout());
		
		//Orte mit Doppelpfeilen erstellen, sowie ComboBoxes
		JPanel p = new JPanel(null);
		
		JLabel label1 = new JLabel(ausgang.ort1.getName());
		JLabel label2 = new JLabel(ausgang.ort2.getName());
		JLabel pfeile = new JLabel(new ImageIcon("EditorGraphics/DoppelPfeil.png"));
		
		von1nach2Richtung = new JComboBox<String>(Ausgang.richtungen);
		von1nach2Richtung.setEditable(true);
		if(ausgang.bez_von1nach2 >= 0) 
			von1nach2Richtung.setSelectedIndex(ausgang.bez_von1nach2);
		else
			von1nach2Richtung.setSelectedItem(ausgang.eigeneBez_von1nach2);		
		
		von2nach1Richtung = new JComboBox<String>(Ausgang.richtungen);
		von2nach1Richtung.setEditable(true);
		if(ausgang.bez_von2nach1 >= 0) 
			von2nach1Richtung.setSelectedIndex(ausgang.bez_von2nach1);
		else 
			von2nach1Richtung.setSelectedItem(ausgang.bez_von2nach1);
		
		//PreferredSize berechnen
		Dimension d1 = label1.getPreferredSize();
		Dimension d2 = label2.getPreferredSize();
		Dimension pf = pfeile.getPreferredSize();
		
		int width = d1.width + pf.width + d2.width + 20;
		int x = (getWidth() - width)/2;
		label1.setLocation(x, 40);
		label2.setLocation(x + 20 + d1.width + pf.width, 40);
		pfeile.setLocation(x + 10 + d1.width, 35);
		von1nach2Richtung.setLocation(x + 10 + d1.width, 5);
		von2nach1Richtung.setLocation(x + 10 + d1.width, 65);
		label1.setSize(d1);
		label2.setSize(d2);
		pfeile.setSize(pf);
		von1nach2Richtung.setSize(von1nach2Richtung.getPreferredSize());
		von2nach1Richtung.setSize(von2nach1Richtung.getPreferredSize());
		
		p.add(label1);
		p.add(label2);
		p.add(pfeile);
		p.add(von1nach2Richtung);
		p.add(von2nach1Richtung);
		
		p.setPreferredSize(new Dimension(width, 90));
		add(p, BorderLayout.NORTH);
		
		
		//Checkboxes, welche Richtung genutzt werden soll
		p = new JPanel(new FlowLayout(FlowLayout.LEADING));
		von1nach2Bool = new JCheckBox("Weg von " + ausgang.ort1.getName() + " nach " + ausgang.ort2.getName(), ausgang.von1nach2);
		von1nach2Bool.addChangeListener(this);
		p.add(von1nach2Bool);
		
		von2nach1Bool = new JCheckBox("Weg von " + ausgang.ort2.getName() + " nach " + ausgang.ort1.getName(), ausgang.von2nach1);
		von2nach1Bool.addChangeListener(this);
		p.add(von2nach1Bool);
		add(p, BorderLayout.CENTER);
		
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ok = new JButton("OK");
		ok.addActionListener(this);
		p.add(ok);
		add(p, BorderLayout.SOUTH);
		
	}

	/**
	 * Realisiert die Funktionalität des ok-Buttons. Das heißt im Detail:
	 * Auslesen der Richtungsbezeichner und Checkboxes und gewählte Optionen im AusgangErweitert-Objekt speichern.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ausgang.bez_von1nach2 = (byte)von1nach2Richtung.getSelectedIndex();
		if(von1nach2Richtung.getSelectedIndex() == -1) 
			ausgang.eigeneBez_von1nach2 = (String)von1nach2Richtung.getSelectedItem();
		ausgang.bez_von2nach1 = (byte)von2nach1Richtung.getSelectedIndex();
		if(von2nach1Richtung.getSelectedIndex() == -1) 
			ausgang.eigeneBez_von2nach1 = (String)von1nach2Richtung.getSelectedItem();
		ausgang.von1nach2 = von1nach2Bool.isSelected();
		ausgang.von2nach1 = von2nach1Bool.isSelected();
		dispose();
	}

	/**
	 * Setzt die Editierbarkeit der ComboBoxes falls eine Richtung (de)aktiviert wurde. 
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == von1nach2Bool) {
			von1nach2Richtung.setEnabled(von1nach2Bool.isSelected());
		} else {
			von2nach1Richtung.setEnabled(von2nach1Bool.isSelected());
		}
		
	}
}
