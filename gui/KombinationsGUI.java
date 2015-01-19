package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.SpielWelt;
import game.items.Gegenstand;
import game.items.Inventar;
import game.items.Kombination;
import game.items.Stapel;

/**
 * Die KombinationsGUI kann aufgerufen werden und bietet dann die Moeglichkeit, dass der Spieler Gegenstaende kombiniert.
 * @author Marvin
 */
public class KombinationsGUI extends JFrame implements ActionListener, ChangeListener {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Das Inventar des Spielers
	private transient Inventar inventar;

	// Alle Gegenstaende, die aus dem Inventar des Spielers entfernt werden muessen.
	private transient Vector<Gegenstand> zuEntfernen;

	// Die JComboBoxes(Dropdownmenu)
	private transient ItemChooser[] edukte;

	// Das Resultat
	private transient JTextField produktTF;
	private transient Gegenstand produkt;
	private transient JSpinner produktS;

	// Alle Buttons in diesem Frame
	private transient JButton schliessen;
	private transient JButton herstellen;

	// Der Fortschrittsbalken
	private transient Fortschritt fortschritt;

	// Die aktuelle Kombination
	private transient Kombination kombination;

	/* --- Konstruktor --- */
	
	/**
	 * Erstellt eine neu KombinationsGUI mit der man Gegenstaende kombinieren kann.
	 * @param gui
	 * @param inventar
	 */
	public KombinationsGUI(GUI gui, Inventar inventar) {
	    super("Kombinieren");
	    this.setVisible(false);

	    this.inventar = inventar;

	    this.setSize(400, 340);
	    this.setLocation((gui.getWidth() - getWidth()) / 2 + gui.getX(), (gui.getHeight() - getHeight()) / 2 + gui.getY());
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setLayout(null);

	    produktTF = new JTextField();
	    SpinnerNumberModel snm = new SpinnerNumberModel(1, 1, 99, 1);
	    produktS = new JSpinner(snm);
	    schliessen = new JButton("Schließen");
	    herstellen = new JButton("Herstellen");
	    fortschritt = new Fortschritt(50, 220, 300, 25);

	    zuEntfernen = new Vector<Gegenstand>();

	    edukte = new ItemChooser[3];
	    for(int i = 0; i < edukte.length; i++){
	    	edukte[i] = new ItemChooser(25, 20 + 65 * i);
	    	edukte[i].addActionListener(this);
	    	this.add(edukte[i]);
	    }

	    updateWerte();

	    produktTF.setBounds(65, 235, 290, 25);
	    produktTF.setEditable(false);
	    produktTF.setHorizontalAlignment(JTextField.CENTER);
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

	/**
	 * Wird aufgerufen, wenn ein Button geklickt wird. 
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource().equals(schliessen))
			setVisible(false);
		// HIER MUSS NOCH DIE ANZAHL ANGEPASST WERDEN
	    if(evt.getSource().equals(herstellen)) {
	    	for(int i = 0; i < ((Integer)produktS.getValue()).intValue(); i++) {
		    	stelleHer(kombination.getDauer());
		    	inventar.addGegenstand(kombination.getStapel(produkt));
	    	}
	    	for(ItemChooser ic : edukte) {
	    		Gegenstand g = ic.getSelectedItem();
	    		inventar.removeGegenstand(kombination.getStapel(g));
	    		ic.reset();
	    	}
	    	updateWerte();
	    	return;
	    }

	    for(ItemChooser ic: edukte)
	    	ic.resetNumber();

	    if(evt.getSource().equals(edukte[0].getSource())) {
	    	if(!(edukte[0].getSelectedItem() == null))
	    		edukte[0].removeFirst();
	    	else
	    		return;
	    	Vector<Gegenstand> ge = Kombination.istKombinierbarMit(edukte[0].getSelectedItem());
	    	if(!ge.isEmpty()) {
	    		ge = sort(ge);
	    		for(int j = 1; j < 3; j++)
	    			edukte[j].reset();
	    		for(Gegenstand g : ge)
	    			if(inventar.containsGegenstand(g))
	    				edukte[1].addItem(g);
	    		edukte[1].setEnabled(true);
	    		edukte[2].setEnabled(false);
	    	} else {
	    		edukte[1].setEnabled(false);
	    	}
	    }

	    if(evt.getSource().equals(edukte[1].getSource())){
	    	if(edukte[1].getItemCount() < 1)
	    		return;
	    	if(!(edukte[1].getSelectedItem() == null))
	    		edukte[1].removeFirst();
	    	else
	    		return;
	    	Vector<Gegenstand> ge = Kombination.istKombinierbarMit(edukte[0].getSelectedItem(), edukte[1].getSelectedItem());
	    	if(!ge.isEmpty()) {
	    		ge = sort(ge);
	    		edukte[2].reset();
	    		for(Gegenstand g : ge.toArray(new Gegenstand[0]))
	    			if(inventar.containsGegenstand(g))
	    				edukte[2].addItem(g);
	    		edukte[2].setEnabled(true);
	    	    check();
	    	} else {
	    		edukte[2].setEnabled(false);
	    	}
	    }


	    if(evt.getSource().equals(edukte[2].getSource())){
	    	if(!(edukte[2].getSelectedItem() == null))
	    		edukte[2].removeFirst();
	    }
	}
	
	/**
	 * Wird aufgerufen, wenn einer der JSpinner veraendert wurde.
	 */
	@Override
	public void stateChanged(ChangeEvent evt) {
		int value = ((Integer)produktS.getValue()).intValue();
	    for(ItemChooser ic : edukte) {
	    	if(ic.getSelectedItem() != null) {
	    		ic.setValue(kombination.getStapel(ic.getSelectedItem()).getAnzahl() * value);
	    	}
	    }
	    check();
	}

	// Diese Methode sortiert den uebergebenen Vector.
	private Vector<Gegenstand> sort(Vector<Gegenstand> gegenstaende) {
		Vector<Gegenstand> ge = new Vector<Gegenstand>();
	    Inventar i = new Inventar();
	    for(Gegenstand g : gegenstaende.toArray(new Gegenstand[0]))
	    	i.addGegenstand(g, 1);
	    i.sortiere();
	    for(Gegenstand g : i.getAlleGegenstaende())
	    	ge.add(g);
	    return ge;
	}

	// Aktualisiert das verwendete Inventar.
	public void setInventory(Inventar inventar) {
		this.inventar = inventar;
	    zuEntfernen.removeAllElements();
	    this.setVisible(true);
	    updateWerte();
	}

	// Aktualisiert alle ComboBoxes
	private void updateWerte() {
	    Vector<Gegenstand> alle = Kombination.istKombinierbarMit(null);
	    Vector<Gegenstand> schnittmenge = new Vector<Gegenstand>();

	    for(Gegenstand g : alle.toArray(new Gegenstand[0]))
	    	if(inventar.containsGegenstand(g)) schnittmenge.add(g);
	    schnittmenge = sort(schnittmenge);
	    for(ItemChooser ic : edukte) {
	    	ic.reset();
	    	ic.setEnabled(false);
	    }
	    edukte[0].setEnabled(true);
	    for(Gegenstand g : schnittmenge.toArray(new Gegenstand[0]))
	    	if(inventar.containsGegenstand(g)) edukte[0].addItem(g);
	    produktS.setValue(1);
	}

	// Ueberprueft, ob es sich um ein gueltiges Rezept handelt.
	private void check() {
	    Gegenstand e1 = edukte[0].getSelectedItem();
	    Gegenstand e2 = edukte[1].getSelectedItem();
	    Gegenstand e3 = edukte[2].getSelectedItem();
	    if(e3 != null)
	    	produkt = Kombination.kombiniere(e1, e2, e3);
	    else
	    	produkt = Kombination.kombiniere(e1, e2);
	    if(produkt != null) {
	    	kombination = Kombination.getKombination(produkt);
	    	for(ItemChooser ic : edukte)
	    		if(kombination.getStapel(ic.getSelectedItem()) != null)
	    			ic.setValue(kombination.getStapel(ic.getSelectedItem()).getAnzahl());
	    	if(!kombination.istEinrichtungVorhanden(SpielWelt.WELT.getAktuellePosition())) {
	    		produktTF.setText("Benötigt " + kombination.getEinrichtung().getName());
	    		kombination = null;
	    		produktS.setEnabled(false);
	    		herstellen.setEnabled(false);
	    		return;
	    	}
	    	produktTF.setText(produkt.getName());
	    	int value = ((Integer)produktS.getValue()).intValue();
	    	Stapel s1 = new Stapel(e1, kombination.getStapel(e1).getAnzahl() * value);
	    	Stapel s2 = new Stapel(e2, kombination.getStapel(e2).getAnzahl() * value);
	    	Stapel s3;

	    	if(kombination.getStapel(e3) == null)
	    		s3 = null;
	    	else
	    		s3 = new Stapel(e3, kombination.getStapel(e3).getAnzahl() * value);

	    	if(inventar.containsGegenstand(s1) && inventar.containsGegenstand(s2) && (inventar.containsGegenstand(s3) | s3 == null)) {
	    		herstellen.setEnabled(true);
	    		produktS.setEnabled(true);
	    	}
	    } else {
	    	produktTF.setText("");
	    	kombination = null;
	    	produktS.setEnabled(false);
	    	herstellen.setEnabled(false);
	    }
	}

	// Diese Methode laesst den Timer die uebergebene Zeit laufen.
	private void stelleHer(long dauer) {
		for(ItemChooser ic : edukte)
			ic.setEnabled(false);
	    long start = System.currentTimeMillis();
	    long zeit = start;
	    while(zeit < start + dauer) {
	    	try {
	    		Thread.sleep(20);
	    	} catch(InterruptedException e) {
	    		System.err.println("KombinationsGUI: Das Programm konnte nicht warten");
	    	}
	    	zeit = System.currentTimeMillis();
	    	fortschritt.setValue((double)(zeit - start) / (double)dauer, getGraphics());
	    }
    	fortschritt.setValue(0.0, getGraphics());
    	this.paintComponents(getGraphics());
	}

	private class Fortschritt extends Canvas {

		// Die serielle Versionsnummer.
		private static final long serialVersionUID = 1L;

		// Der Fortschrittswert.
		private double value;

		public Fortschritt(int x, int y, int width, int height) {
			value = 0.0;
			this.setBounds(x, y, width, height - 10);
		}

		public void setValue(double value, Graphics g) {
			this.value = value;
			paint(g);
		}
		
		public void paint(Graphics g) {
			g.setColor(new Color(50, 250, 100));
			g.fillRect(getX(), getY(), (int)Math.round(getWidth() * value), getHeight());
		}

	}
}