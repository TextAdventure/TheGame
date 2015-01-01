package editor;

import game.items.Gegenstand;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Zentraler Dialog zur Verwaltung der Gegenstände einer Welt. Zeigt alle erstellten Gegenstände tabellarisch an und
 * bietet die Möglichkeit Gegenstände zu löschen und neue zu erstellen.
 * 
 * TODO: bearbeiten von bereits erzeugten Gegenständen.
 * 
 * @author Felix
 *
 */
public class GegenstandTabelleDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String[] tabelleColNames = {"Typ", "Name", "NumGen", "Beschreibung", "Eigenschaften"};
	
	private WeltObjekt welt;
	private JTable tabelle;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private JButton neu, loeschen;
	
	GegenstandTabelleDialog(WeltObjekt welt) {
		this.welt = welt;
		setTitle("Alle Gegenstände");
		setLayout(new BorderLayout());
		setSize(800, 400);
				
		updateTabelle();
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		neu = new JButton("neuer Gegenstand...");
		neu.addActionListener(this);
		buttons.add(neu);		
		
		loeschen = new JButton("Gegenstand löschen");
		loeschen.addActionListener(this);
		buttons.add(loeschen);
		add(buttons, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Erstellt eine neue Tabelle, die die alte ersetzt. Dadurch werden Änderungen an der Gegenstandsliste der Welt
	 * auch angezeigt. 
	 */
	void updateTabelle() {
		model = new DefaultTableModel(tabelleColNames, 0);
		tabelle = new JTable(model);
		
		Gegenstand[] gegenstaende = welt.getGegenstaende();
		for(Gegenstand g : gegenstaende) {
			String typ = "";
			String eigenschaften = ""; 
			model.addRow(new Object[]{typ, g, g.getNumGen().toString(), g.getBeschreibung(), eigenschaften});
		}
		
		if(scroll != null) remove(scroll);	//erster Aufruf
		scroll = new JScrollPane(tabelle); 
		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Realisiert die Funktionalität der Buttons neu und löschen.
	 * - neu: öffne einen neuen GegenstandDialog.
	 * - löschen: entferne alle ausgewählten Gegenstände aus der Welt und der Tabelle.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object src = arg0.getSource();
		if(src == neu) {
			new GegenstandDialog(this, welt).setVisible(true);
			
		} else if(src == loeschen) {
			Gegenstand.GEGENSTAENDE = welt.getGegenstaende();
			int[] rows = tabelle.getSelectedRows();
			Arrays.sort(rows);
			for(int i = 0; i < rows.length; i++) {
				welt.removeGegenstand((Gegenstand)tabelle.getValueAt(rows[i]-i, 1));
				model.removeRow(rows[i]-i);
			}
			
		}

	}
}
