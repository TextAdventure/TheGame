package editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * Teil des OrtDialog. Bietet die 
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
	 * Custom TableModel, welches es erlaubt die Zellen der ersten Spalte zu bearbeiten, die anderen nicht.
	 * Hier in der Anwendung geht es draum, dass der Benutezr die Anzahl eines Gegenstandes bequem in der Tabelle eintragen kann.
	 * @author Felix
	 *
	 */
	private static class GTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public boolean isCellEditable(int row, int col) {
			return col == 0 && col > 0;
		}		
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			if(row == 0 || col > 0)
				super.setValueAt(value, row, col);
			else {
				try {
					String val = value.toString();
					if(Integer.parseInt(val) >= 0) {
						super.setValueAt(value, row, col);
						//notify Listener
						System.out.println("listener");
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
		neu = new JButton("hinzufügen...");
		neu.addActionListener(this);
		buttons.add(neu);
		
		loeschen = new JButton("entfernen");
		loeschen.addActionListener(this);
		buttons.add(loeschen);
		add(buttons, BorderLayout.SOUTH);
		
	}
	
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
			//System.out.println("GegenstandPAnel null? " + (model == null));
			//System.out.println("GegenstandPAnel null2? " + (g == null));
			model.addRow(new String[]{anz, g.getName()});
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == neu) {
			JComboBox<Gegenstand> auswahl = new JComboBox<Gegenstand>(welt.getGegenstaende());
			if(JOptionPane.showConfirmDialog(this, auswahl, "Gegenstand auswählen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
				Gegenstand g = (Gegenstand)auswahl.getSelectedItem();
				ort.addGegenstand(g, 1);
				//model.addRow(new String[]{"1", g.getName()});
				initTabelle();
			}
			
		} else if(src == loeschen) {
			int[] rows = tabelle.getSelectedRows();
			for(int i : rows) {
				ort.removeGegenstand(ort.getGegenstand((String)model.getValueAt(i, 1)).getGegenstand());
				model.removeRow(i);				
			}			
		}
		
		
	}


	
	
}
