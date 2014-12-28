package editor;

import game.items.Gegenstand;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GegenstandTabelle extends JTable {
	private static final long serialVersionUID = 1L;

	private static String[] colNames = {"Typ", "Name", "NumGen", "Beschreibung", "Eigenschaften"};
	
	private DefaultTableModel model;
	
	GegenstandTabelle(WeltObjekt welt) {
		model = new DefaultTableModel(colNames, 0);
		setModel(model);
		
		Gegenstand[] geg = Gegenstand.GEGENSTAENDE;
		for(Gegenstand g : geg) {
			String eigenschaften = "";
			String[] row = {"", g.getName(), g.getNumGen().toString(), g.getDescription(), eigenschaften};
			model.addRow(row);
		}	
		
	}
	
	void addGegenstand(Gegenstand g) {
		String eigenschaften = "";
		String[] row = {"", g.getName(), g.getNumGen().toString(), g.getDescription(), eigenschaften};
		model.addRow(row);
	}
	
	/**
	 * Löscht alle durch den Benutzer ausgewählten Zeilen (d.h. Gegenstände).
	 * @return Die Zeilenindize, die gelöscht wurden. 
	 */
	int[] removeSelectedRows() {
		int[] rows = getSelectedRows();
		for(int i : rows) 
			model.removeRow(i);
		return rows;
	}
	
	
}
