package gui;

import game.Gegenstand;
import game.Inventar;
import game.Kombination;
import game.Stapel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class KombinationsGUI extends JFrame implements ActionListener, ChangeListener {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Der eigentliche JFrame des Programms.
	private GUI parent;
	// Das Inventar des Spielers
	private Inventar inventory;
	  
	// Alle Gegenstaende, die aus dem Inventar des Spielers entfernt werden muessen.
	private Vector<Gegenstand> zuEntfernen;
	  
	// Die JComboBoxes(Dropdownmenu)
	private ItemChooser[] edukte;
	  
	// Das Resultat
	private JTextField produktTF;
	private Gegenstand produkt;
	private JSpinner produktS;
	  
	// Alle Buttons in diesem Frame
	private JButton schliessen;
	private JButton herstellen;
	  
	// Der Fortschrittsbalken
	private JProgressBar fortschritt;
	  
	// Die aktuelle Kombination
	private Kombination kombination;

	public KombinationsGUI(GUI parent, Inventar inventory){
	    super("Kombinieren");
	    this.setVisible(false);
	    
	    this.parent = parent;
	    this.inventory = inventory;

	    this.setSize(400, 340);
	    this.setLocation((parent.getWidth() - getWidth()) / 2 + parent.getX(), (parent.getHeight() - getHeight()) / 2 + parent.getY());
	    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    this.setLayout(null);

	    produktTF = new JTextField();
	    SpinnerNumberModel snm = new SpinnerNumberModel(1, 1, 99, 1);
	    produktS = new JSpinner(snm);
	    schliessen = new JButton("Schlieﬂen");
	    herstellen = new JButton("Herstellen");
	    fortschritt = new JProgressBar(0, 100);
	    
	    zuEntfernen = new Vector<Gegenstand>();

	    edukte = new ItemChooser[3];
	    for(int i = 0; i < edukte.length; i++){
	    	edukte[i] = new ItemChooser(25, 20 + 65 * i);
	    	edukte[i].addActionListener(this);
	    	this.add(edukte[i]);
	    }
	    
	    updateWerte();

	    fortschritt.setBounds(50, 195, 300, 25);
	    produktTF.setBounds(65, 235, 290, 25);
	    produktTF.setEditable(false);
	    produktTF.setHorizontalAlignment(SwingConstants.CENTER);
	    produktS.setBounds(25, 235, 40, 25);
	    produktS.setEnabled(false);
	    produktS.addChangeListener(this);
	    schliessen.setBounds(250, 275, 100, 25);
	    schliessen.addActionListener(this);
	    herstellen.setBounds(50, 275, 100, 25);
	    herstellen.addActionListener(this);
	    herstellen.setEnabled(false);
	    this.add(fortschritt);
	    this.add(produktTF);
	    this.add(produktS);
	    this.add(schliessen);
	    this.add(herstellen);
	    
	    this.setResizable(false);
	}
	  
	// @override  Wird immer aufgerufen, wenn ein neues Element angewaehlt wird.
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource().equals(schliessen)){
			setVisible(false);
	    }                                                                           // HIER MUESS NOCH DIE ANZAHL ANGEPASST WERDEN
	    if(evt.getSource().equals(herstellen)){
	    	//stelleHer(kombination.getDauer());                                ERST AKTUALISIEREN
	    	inventory.addGegenstand(kombination.getStapel(produkt));
	    	for(ItemChooser ic: edukte){
	    		Gegenstand g = ic.getSelectedItem();
	    		inventory.removeGegenstand(kombination.getStapel(g));
	    		ic.reset();
	    	}
	    	updateWerte();
	    	return;
	    }
	    for(ItemChooser ic: edukte){
	    	ic.resetNumber();
	    }
	    if(evt.getSource().equals(edukte[0].getSource())){
	    	if(!(edukte[0].getSelectedItem() == null)) edukte[0].removeFirst();
	    	else return;
	    	Vector<Gegenstand> ge = Kombination.istKombinierbarMit(edukte[0].getSelectedItem());
	    	if(!ge.isEmpty()){
	    		ge = sort(ge);
	    		for(int j = 1; j < 3; j++){
	    			edukte[j].reset();
	    		}
	    		for(Gegenstand g: ge.toArray(new Gegenstand[0])){
	    			if(inventory.containsGegenstand(g)) edukte[1].addItem(g);
	    		}
	    		edukte[1].setEnabled(true);
	    		edukte[2].setEnabled(false);
	    	}else{
	    		edukte[1].setEnabled(false);
	    	}
	    }
	    if(evt.getSource().equals(edukte[1].getSource())){
	    	if(edukte[1].getItemCount() < 1) return;
	    	if(!(edukte[1].getSelectedItem() == null)) edukte[1].removeFirst();
	    	else return;
	    	Vector<Gegenstand> ge = Kombination.istKombinierbarMit(edukte[0].getSelectedItem(), edukte[1].getSelectedItem());
	    	if(!ge.isEmpty()){
	    		ge = sort(ge);
	    		edukte[2].reset();
	    		for(Gegenstand g: ge.toArray(new Gegenstand[0])){
	    			if(inventory.containsGegenstand(g)) edukte[2].addItem(g);
	    		}
	    		edukte[2].setEnabled(true);
	    	}else{
	    		edukte[2].setEnabled(false);
	    	}
	    }
	    if(evt.getSource().equals(edukte[2].getSource())){
	    	if(!(edukte[2].getSelectedItem() == null)) edukte[2].removeFirst();
	    }
	    check();
	}
	  
	// @override Wird immer ausgefuehrt, wenn der JSpinner veraendert wurde.
	public void stateChanged(ChangeEvent evt){
		int value = ((Integer)produktS.getValue()).intValue();
	    for(ItemChooser ic: edukte){
	    	if(ic.getSelectedItem() != null){
	    		ic.setValue(kombination.getStapel(ic.getSelectedItem()).getAnzahl() * value);
	    		System.out.println(kombination.getStapel(ic.getSelectedItem()).getAnzahl() * value);
	    	}	
	    }
	    check();
	}
	  
	// Diese Methode sortiert den uebergebenen Vector.
	private Vector<Gegenstand> sort(Vector<Gegenstand> gegenstaende){
		Vector<Gegenstand> ge = new Vector<Gegenstand>();
	    Inventar i = new Inventar();
	    for(Gegenstand g: gegenstaende.toArray(new Gegenstand[0])){
	    	i.addGegenstand(g, 1);
	    }
	    i.sortiere();
	    for(Gegenstand g: i.getAlleGegenstaende()){
	    	ge.add(g);
	    }
	    return ge;
	}
	  
	// Aktualisiert das verwendete Inventar.
	public void setInventory(Inventar inventory){
		this.inventory = inventory;
	    zuEntfernen.removeAllElements();
	    this.setVisible(true);
	    updateWerte();
	}
	  
	// Aktualisiert alle ComboBoxes
	private void updateWerte(){
	    Vector<Gegenstand> alle = Kombination.istKombinierbarMit(null);
	    Vector<Gegenstand> schnittmenge = new Vector<Gegenstand>();

	    for(Gegenstand g: alle.toArray(new Gegenstand[0])){
	    	if(inventory.containsGegenstand(g)) schnittmenge.add(g);
	    }
	    schnittmenge = sort(schnittmenge);
	    for(ItemChooser ic: edukte){
	    	ic.reset();
	    	ic.setEnabled(false);
	    }
	    edukte[0].setEnabled(true);
	    for(Gegenstand g: schnittmenge.toArray(new Gegenstand[0])){
	    	if(inventory.containsGegenstand(g)) edukte[0].addItem(g);
	    }
	    produktS.setValue(1);
	}
	  
	// Ueberprueft, ob es sich um ein gueltiges Rezept handelt.
	private void check(){
	    Gegenstand e1 = edukte[0].getSelectedItem();
	    Gegenstand e2 = edukte[1].getSelectedItem();
	    Gegenstand e3 = edukte[2].getSelectedItem();
	    if(e3 != null) produkt = Kombination.kombiniere(e1, e2, e3);
	    else produkt = Kombination.kombiniere(e1, e2);
	    if(produkt != null){
	    	produktTF.setText(produkt.getName());
	    	kombination = Kombination.getKombination(produkt);
	    	int value = ((Integer)produktS.getValue()).intValue();
	    	Stapel s1 = new Stapel(e1, kombination.getStapel(e1).getAnzahl() * value);
	    	Stapel s2 = new Stapel(e2, kombination.getStapel(e2).getAnzahl() * value);
	    	Stapel s3;
	    	if(kombination.getStapel(e3) == null) s3 = null;
	    	else s3 = new Stapel(e3, kombination.getStapel(e3).getAnzahl() * value);
	    	if(inventory.containsGegenstand(s1) && inventory.containsGegenstand(s2) && (inventory.containsGegenstand(s3) | s3 == null)){
	    		herstellen.setEnabled(true);
	    		produktS.setEnabled(true);
	    	}
	    	for(ItemChooser ic: edukte){
	    		if(kombination.getStapel(ic.getSelectedItem()) != null) ic.setValue(kombination.getStapel(ic.getSelectedItem()).getAnzahl());
	    	}
	    }else{
	    	produktTF.setText("");
	    	kombination = null;
	    	produktS.setEnabled(false);
	    	herstellen.setEnabled(false);
	    }
	}
	  
	// Diese Methode laesst den Timer die uebergebene Zeit laufen.
	private void stelleHer(long dauer){
		/*for(JComboBox jcb: edukte){
	      jcb.setEnabled(false);
	    }
	    long start = System.currentTimeMillis();
	    long zeit = start;
	    while(zeit < start + dauer){
	      try{
	        Thread.sleep(100);                                                            EINFACH SCHLECHT!!!
	      }catch(InterruptedException e){
	        System.err.println("Das Programm konnt nicht warten!");
	      }
	      zeit = System.currentTimeMillis();
	      fortschritt.setValue((int)((double)(zeit - start) / (double)dauer * 100));
	      System.out.println(fortschritt.getPercentComplete());
	      fortschritt.paint(this.getGraphics());
	    }*/
	}
}