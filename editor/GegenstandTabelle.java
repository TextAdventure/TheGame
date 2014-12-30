package editor;

import java.util.Arrays;

import game.items.Gegenstand;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GegenstandTabelle extends JTable {
	private static final long serialVersionUID = 1L;

	private static String[] colNames = {"Typ", "Name", "NumGen", "Beschreibung", "Eigenschaften"};
	private WeltObjekt welt;
	private DefaultTableModel model;
	
	GegenstandTabelle(WeltObjekt welt) {
		this.welt = welt;
		model = new DefaultTableModel(colNames, 0);
		setModel(model);
		
		Gegenstand[] geg = welt.getGegenstaende();
		for(Gegenstand g : geg) {
			addGegenstand(g);
		}	
		
	}
	
	/**
	 * F�gt der Tabelle eine neue Zeile hinzu.
	 * @param g
	 */
	void addGegenstand(Gegenstand g) {
		String eigenschaften = "";
		String[] row = {"", g.getName(), g.getNumGen().toString(), g.getDescription(), eigenschaften};
		model.addRow(row);
	}
	
	/**
	 * L�scht alle durch den Benutzer ausgew�hlten Zeilen (d.h. Gegenst�nde).
	 * @return Die Zeilenindize, die gel�scht wurden. 
	 */
	int[] removeSelectedRows() {
		int[] rows = getSelectedRows();
		Arrays.sort(rows);
		for(int i = 0; i < rows.length; i++) {
			model.removeRow(rows[i]-i);
		}
		return rows;
	}
	
	
}
