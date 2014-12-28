package gui;

import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import game.items.Gegenstand;

public class ItemChooser extends Container {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Die JComboBox fuer das auswaehlen des Gegenstands.
	private JComboBox<String> select;
	// Der JSpinner zum einstellen der Gegenstands Anzahl.
	private JTextField number;
	// Die gueltige, groesste Zahl fuer den JSpinner.
	//private int max;TODO
	  
	/**
	 *  Ein neuer ItemChooser wird eine Position uebergeben, an der er sich befindet.               // HIER MUSS MIT DER AKTUALISIERUNG NACHGEBESSERT WERDEN (JTextField)
	 */
	public ItemChooser(int x, int y){
		select = new JComboBox<String>();
	    number = new JTextField();
	    //max = 1;TODO
	    
	    this.add(select);
	    this.add(number);
	    // Zentriert den Text in der JComboBoxes und den JTextField.
	    ((JLabel)select.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	    number.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    this.setBounds(x, y, 330, 25);
	    this.setLayout(null);
	    number.setBounds(0, 0, 40, 25);
	    number.setEnabled(false);
	    select.setBounds(40, 0, 290, 25);
	    
	    select.setFont(select.getFont().deriveFont(20).deriveFont(java.awt.Font.BOLD));
	    number.setFont(select.getFont());
	}
	  
	// ALLE METHODEN FUER DIE JCOMBOBOX
	  
	// Gibt das von der JComboBox ausgewaehlte Element zurueck, als Gegenstand.
	public Gegenstand getSelectedItem(){
	    return Gegenstand.getGegenstand((String)select.getSelectedItem());
	}
	// Gibt den ItemCount der JComboBox zurueck.
	public int getItemCount(){
		return select.getItemCount();
	}
	// Setzt die JComboBox zurueck.
	public void reset(){
	    select.removeAllItems();
	    select.addItem(" ");
	    number.setText("");
	}
	// Fuegt eine neue Auswahlmoeglichkeit hinzu, muss ein Gegenstand sein.
	public void addItem(Gegenstand item){
		select.addItem(item.getName());
	}
	// Entfernt einen Gegenstand aus der Auswahl.
	public void removeItem(Gegenstand item){
		select.removeItem(item.getName());
	}
	// Entfernt einen String aus der Auswahl.
	public void removeFirst(){
	    if(Gegenstand.getGegenstand((String)select.getItemAt(0)) ==  null) select.removeItemAt(0);
	}
	// Setzt den Enabled-Wert auf den uebergebenen boolean.
	public void setEnabled(boolean flag){
	    select.setEnabled(flag);
	}
	// Gibt die JComboBox zurueck, die sie Quelle fuer das ActionEvent ist.
	public JComboBox<String> getSource(){
	    return select;
	}
	// Fuegt der JCombocBox einen ActionListener hinzu.
	public void addActionListener(ActionListener listener){
	    select.addActionListener(listener);
	}
	  
	// ALLE METHODEN FUER DAS JTEXTFIELD

	// Uebergibt einen Wert fuer das JTextField.
	public void setValue(int value){
		SwingUtilities.invokeLater(new TextUpdate(value));
	}
	// Gibt den Wert des JTextFields zurueck.
	public int getValue(){
	    return Integer.getInteger(number.getText());
	}
	// Setzte den Wert des JTextFields zurueck.
	public void resetNumber(){
		number.setText("");
	}
	  
	// Diese Klasse wird gebraucht, um den Text des JTextFields anzupassen.
	private class TextUpdate implements Runnable{
		// Der zuersetzende Wert
	    private int value;
	    
	    // Ein neues TextUpdate wird mit einen Wert initialisiert.
	    public TextUpdate(int value){
	    	this.value = value;
	    }
	    
	    // @override  Die run(), die ausgefuehrt wird.
	    public void run(){
	    	number.setText(String.valueOf(value));
	    }
	}
}