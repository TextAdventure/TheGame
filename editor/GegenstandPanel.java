package editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import game.Ort;
import game.items.Gegenstand;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Teil des OrtDialog. Bietet die M�glichkeit dem Ort Gegenst�nde aller Art hinzu zu f�gen.
 * @author Felix
 *
 */
public class GegenstandPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private WeltObjekt welt;
	private JButton neu, loeschen;
	private JTable tabelle;
	private DefaultTableModel model;
	private Ort ort;
	
	/**
	 * Custom TableModel, welches es erlaubt die Zellen der ersten Spalte zu bearbeiten und dort nur positive ganze Zahlen 
	 * einzutragen. Alle anderen Werte werden nicht �bernommen und alle anderen Zellen sind nicht anw�hlbar,
	 * Hier in der Anwendung geht es draum, dass der Benutezr die Anzahl eines Gegenstandes bequem in der Tabelle eintragen kann.
	 * @author Felix
	 *
	 */
	private class GTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public boolean isCellEditable(int row, int col) {
			return col == 0;
		}		
		
		/**
		 * Die modifizierte setValueAt(Object, int, int)-Methode l�sst in der Spalte nur die Eingabe einer positiven ganzen
		 * Zahl zu. Ansonsten verh�lt sie sich wie die Methode der Superklasse DefaultTableModel. 
		 */
		@Override
		public void setValueAt(Object value, int row, int col) {
			if(col > 0)
				super.setValueAt(value, row, col);
			else {
				try {
					String val = value.toString();
					if(Integer.parseInt(val) > 0) {
						super.setValueAt(value, row, col);
						int anz = Integer.parseInt(val);
						GegenstandPanel.this.setGegenstandAnz((Gegenstand)getValueAt(row, col), anz);
					}
					
				} catch(NumberFormatException e) {
					//der neue Wert ist keine Zahl --> nicht akzeptieren!
				}
			}
			
		}
	}
	
	
	GegenstandPanel(WeltObjekt welt, Ort ort) {
		this.welt = welt;
		this.ort = ort;
		
		setLayout(new BorderLayout());
		initTabelle();
		
		add(new JScrollPane(tabelle), BorderLayout.CENTER);
		
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		neu = new JButton("hinzuf�gen...");
		neu.addActionListener(this);
		buttons.add(neu);
		
		loeschen = new JButton("entfernen");
		loeschen.addActionListener(this);
		buttons.add(loeschen);
		add(buttons, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Erstellt eine neue Tabelle mit allen Gegenst�nden im WeltObjekt. Die neue Tabelle wird sofort auf der GUI plaziert.
	 */
	private void initTabelle() {
		Gegenstand.GEGENSTAENDE = welt.getGegenstaende();
		Gegenstand[] geg = ort.getGegenstaende();
		model = new GTableModel();
		model.setColumnIdentifiers(new String[]{"Anzahl","Gegenstand"});
		if(tabelle == null)		//erster Aufruf
			tabelle = new JTable(model);
		else 					//weitere Aufrufe
			tabelle.setModel(model);
		
		for(Gegenstand g : geg) {
			String anz = String.valueOf(ort.getGegenstandAnzahl(g));
			model.addRow(new Object[]{anz, g});
		}
	}
	
	/**
	 * Legt die Anzahl der Gegenst�nde vom Typ g, die es an diesem Ort geben soll, fest.
	 * @param g Der Gegenstand, dessen Anzahl festgelegt werden soll.
	 * @param anz Die Anzahl, wie oft es diesen Gegenstand geben soll.
	 */
	private void setGegenstandAnz(Gegenstand g, int anz) {
		ort.removeGegenstand(g);
		ort.addGegenstand(g, anz);
	}


	/**
	 * Realisiert die Funktionalit�t des neu-, und des l�schen-Buttons
	 * 
	 *  - neu: �ffnet einen Dialog mit einer JComboBox zum Ausw�hlen eines Gegenstand aus der List der bereits erstellten Gegenst�nde.
	 *  - l�schen: L�scht alle ausgew�hlten Zeilen der Gegenstandsliste.  
	 * 
	 * @param e Das zugeh�rige ActionEvent.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == neu) {
			JComboBox<Gegenstand> auswahl = new JComboBox<Gegenstand>(welt.getGegenstaende());
			if(JOptionPane.showConfirmDialog(this, auswahl, "Gegenstand ausw�hlen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
				Gegenstand g = (Gegenstand)auswahl.getSelectedItem();
				ort.addGegenstand(g, 1);
				//model.addRow(new String[]{"1", g.getName()});
				initTabelle();
			}
			
		} else if(src == loeschen) {
			int[] rows = tabelle.getSelectedRows();
			Arrays.sort(rows);
			for(int i = 0; i < rows.length; i++) {
				ort.removeGegenstand((Gegenstand)model.getValueAt(rows[i]-i, 1));
				model.removeRow(rows[i] - i);				
			}			
		}
		
		
	}


	
	
}
