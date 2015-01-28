package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;

import game.items.Gegenstand;
import game.items.Inventar;
import game.items.Stapel;

/**
 * Diese Komponente wird fuer die KombinationsGUI benoetigt.
 * @author Marvin
 */
public class ItemChooser extends JPanel {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Die JComboBox fuer das auswaehlen des Gegenstands.
	private transient JComboBox<String> auswahl;
	// Der JSpinner mit dem man die Anzahl der Gegenstaende einstellen kann.
	private transient JSpinner anzahl;
	
	/**
	 * Ein ItemChooser wird von der KombinationsGUI verwendet, um dem Spieler bei der Auswahl der Gegenstaende, die kombiniert werden sollen, zu helfen.
	 * @param x Die x-Koordinate der Komponente.
	 * @param y Die y-Koordiante der Komponente.
	 * @param titel Der Name dieses ItemChoosers.
	 */
	public ItemChooser(String titel) {
		auswahl = new JComboBox<String>();
	    // Der JSpinner hat noch kein Model bis jetzt.
	    anzahl = new JSpinner(); //new SpinnerNumberModel(1, 1, 100, 1));

	    
	    // Zentriert den Text in der JComboBoxes.
	    ((JLabel)auswahl.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	    
	    JPanel panel = new JPanel();

	    SpringLayout layout = new SpringLayout();
	    panel.setLayout(layout);
	    
	    anzahl.setPreferredSize(new Dimension(60, 25));
	    panel.add(anzahl);
	    panel.add(auswahl);
	    
	    layout.putConstraint(SpringLayout.WEST, anzahl, 0, SpringLayout.WEST, panel);
	    layout.putConstraint(SpringLayout.NORTH, anzahl, 0, SpringLayout.NORTH, panel);
	    
	    layout.putConstraint(SpringLayout.WEST, auswahl, 5, SpringLayout.EAST, anzahl);
	    layout.putConstraint(SpringLayout.NORTH, auswahl, 0, SpringLayout.NORTH, panel);
	    layout.putConstraint(SpringLayout.EAST, auswahl, -2, SpringLayout.EAST, panel);
	    
	    panel.setPreferredSize(new Dimension(330, 28));
	    
	    this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
	    		BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), titel)));
	    
	    this.setLayout(new BorderLayout());
	    this.add(panel, BorderLayout.CENTER);
	    
	    //this.setBounds(x, y, 400, 60);
	    
	    //number.setBounds(0, 0, 40, 25);
	    anzahl.setEnabled(false);
	    //select.setBounds(40, 0, 290, 25);
	    
	    auswahl.setFont(auswahl.getFont().deriveFont(20).deriveFont(Font.BOLD));
	    anzahl.setFont(auswahl.getFont());
	}
	
	/* --- Methoden --- */
	
	/**
	 * Setzt diesen ItemChooser zurueck.
	 */
	public void reset() {
	    auswahl.removeAllItems();
	    auswahl.addItem(" ");
	    setMaxWert(0);
	}
	
	/**
	 * Legt fest, ob diese Komponente verwendet werden kann oder nicht.
	 */
	public void setEnabled(boolean enable) {
	    auswahl.setEnabled(enable);
	    anzahl.setEnabled(enable);
	}
	
	/**
	 * Gibt den Stapel zurueck, der sich aus dem ausgewaehlten Gegenstand und der eingestellten Anzahl ergibt.
	 * @return Den Stapel dieses ItemChoosers.
	 */
	public Stapel getStapel() {
		int i = 0;
		try {
			if(anzahl.getValue() instanceof String)
				i = Integer.valueOf((String) anzahl.getValue());
			else
				i = ((Integer) anzahl.getValue()).intValue();
		} catch(NumberFormatException e) {
			// Der JSpinner hat gerade das String Format und deshalb ist es nicht schlimm, dass hier keine Zahl ausgegeben werden kann.
		}
		return new Stapel(this.getSelectedItem(), i);
	}

	// ALLE METHODEN FUER DIE JCOMBOBOX
	
	/**
	 * Gibt das ausgewaehlte Element als Gegenstand zurueck.
	 * @return Das aktuell ausgewaehlte Element als Gegenstand.
	 */
	public Gegenstand getSelectedItem() {
	    return Gegenstand.getGegenstand((String) auswahl.getSelectedItem());
	}
	
	/**
	 * Fuegt einen neuen Gegenstand als Auswahlmoeglichkeit hinzu.
	 * @param gegenstand Der Gegenstand, der hinzugefuegt werden soll.
	 */
	public void addItem(Gegenstand gegenstand){
		auswahl.addItem(gegenstand.getName());
	}
	
	/**
	 * Entfernt das erste Element aus der Liste, falls sich dort kein gueltiger Gegenstand befindet.
	 */
	public void removeFirst() {
	    if(Gegenstand.getGegenstand((String)auswahl.getItemAt(0)) ==  null)
	    	auswahl.removeItemAt(0);
	}
	
	/**
	 * Gibt die JComboBox zurueck.
	 * @return Die JComboBox
	 */
	public JComboBox<String> getJComboBox() {
	    return auswahl;
	}
	
	/**
	 * Fuegt der JComboBox einen Listener hinzu.
	 * @param listener Der Listener, der hinzugefuegt werden soll.
	 */
	public void addActionListener(ActionListener listener) {
	    auswahl.addActionListener(listener);
	}
	
	/**
	 * Legt die Liste der Gegenstaende fuer die JComboBox fest, davor wird sie zurueckgesetzt, dabei wird geprueft, ob sich
	 * dieser Gegenstand in dem uebergebenen Inventar befindet.
	 * @param gegenstaende Die Liste aller Gegenstaende, die angezeigt werden soll.
	 * @param inventar Das Inventar, in dem sich die Gegenstaende befinden muessen, damit sie hinzugefuegt werden.
	 */
	public void setWerte(Vector<Gegenstand> gegenstaende, Inventar inventar) {
		reset();
		// Ein leerer Platzhalter.
		auswahl.addItem("");
		for(Gegenstand g : gegenstaende)
			if(inventar.containsGegenstand(g))
				auswahl.addItem(g.getName());
		auswahl.removeItemAt(0);
		auswahl.setSelectedIndex(0);
	}

	// ALLE METHODEN FUER DEN JSPINNER

	/**
	 * Legt den maximalen Wert des JSpinners fest, der minimale betraegt immer 1, 0 bedeutet, dass er leer ist und mit einem String ausgestattet wird.
	 * @param wert Der neue maximale Wert des JSpinners.
	 */
	public void setMaxWert(int wert) {
		if(wert == 0) {
			SpinnerListModel model = new SpinnerListModel();
			Vector<String> list = new Vector<String>();
			list.add("");
			model.setList(list);
			anzahl.setModel(model);
		} else
			anzahl.setModel(new SpinnerNumberModel(1, 1, wert, 1));
	}
	
	/**
	 * Gibt den JSpinner zurueck.
	 * @return Den JSpinner.
	 */
	public JSpinner getJSpinner() {
		return anzahl;
	}
	
	/**
	 * Fuegt dem JSpinner einen ChangeListener hinzu.
	 * @param listener Der Listener, der hinzugefuegt werden soll.
	 */
	public void addChangeListener(ChangeListener listener) {
		anzahl.addChangeListener(listener);
	}

}