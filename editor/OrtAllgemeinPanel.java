package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import game.Ort;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Teil des OrtDialog. Erstes Panel des Dialogs. Stellt die Komponenten zur Verfügung, mit denen man Name
 * und Beschreibung eines Orts verfassen kann.
 * 
 * Beim Abschluss der Bearbeitung des Orts kann sich der OrtDialog den eingegebenen Namen bzw. die Beschreibung
 * mit getOrtName() und getBeschreibung() holen.
 * 
 * @author Felix
 *
 */
public class OrtAllgemeinPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField name;
	private JTextArea beschreibung;
	private JScrollPane scroll;
	
	OrtAllgemeinPanel(Ort ort) {
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		name = new JTextField(ort.getName());
		Dimension d = name.getPreferredSize();
		name.setPreferredSize(new Dimension(120, d.height));
		p.add(new JLabel("Name: "));
		p.add(this.name);
		add(p, BorderLayout.NORTH);
		
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		
		p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		p.add(new JLabel("Beschreibung:"));
		south.add(p, BorderLayout.NORTH);
		
		p = new JPanel(new BorderLayout());
		this.beschreibung = new JTextArea(ort.getDescription());
		beschreibung.setSize(beschreibung.getMaximumSize());
		beschreibung.setLineWrap(true);
		scroll = new JScrollPane(this.beschreibung);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		p.add(scroll, BorderLayout.CENTER);
		south.add(p, BorderLayout.CENTER);
		
		
		add(south, BorderLayout.CENTER);		
	}
	
	
	
	String getOrtName() {
		return name.getText();
	}
	
	String getBeschreibung() {
		return beschreibung.getText();
	}
}
