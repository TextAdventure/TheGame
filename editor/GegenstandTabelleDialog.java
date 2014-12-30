package editor;

import game.items.Gegenstand;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import util.NumerusGenus;


public class GegenstandTabelleDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private WeltObjekt welt;
	private GegenstandTabelle tabelle;
	private JButton neu, loeschen;
	
	GegenstandTabelleDialog(WeltObjekt welt) {
		this.welt = welt;
		setTitle("Alle Gegenstände");
		setLayout(new BorderLayout());
		setSize(800, 400);
		
		tabelle = new GegenstandTabelle(welt);
		add(new JScrollPane(tabelle), BorderLayout.CENTER);
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		neu = new JButton("neuer Gegenstand...");
		neu.addActionListener(this);
		buttons.add(neu);		
		
		loeschen = new JButton("Gegenstand löschen");
		loeschen.addActionListener(this);
		buttons.add(loeschen);
		add(buttons, BorderLayout.SOUTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object src = arg0.getSource();
		if(src == neu) {
			new GegenstandDialog(this, welt).setVisible(true);
			
		} else if(src == loeschen) {
			tabelle.removeSelectedRows();
			
		}

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		WeltObjekt welt = new WeltObjekt();
		welt.addGegenstand(new Gegenstand(new String[]{"Auto"}, "Autos", NumerusGenus.NEUTRUM, "Ein kleines rotes Auto."));
		
		new GegenstandTabelleDialog(welt).setVisible(true);
	}
}
