package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.BevelBorder;
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
public class KombinationsGUI extends JDialog implements ActionListener, ChangeListener {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Das Inventar des Spielers
	private transient Inventar inventar;

	// Hier werden die Ausgangsgegenstaende ausgewaehlt.
	private transient ItemChooser[] edukte;

	// Das Resultat
	private transient InventarAnzeige produkt;
	private transient JSpinner produktS;

	// Alle Buttons in diesem Frame
	private transient JButton schliessen;
	private transient JButton herstellen;

	// Der Fortschrittsbalken
	private transient Fortschritt fortschritt;

	// Die aktuelle Kombination
	private transient Kombination kombination;
	
	// Der Buffer des Dialogs.
	private transient BufferStrategy buffer;

	/* --- Konstruktor --- */
	
	/**
	 * Erstellt eine neue KombinationsGUI mit der man Gegenstaende kombinieren kann.
	 * @param gui Die GUI, die diese GUI erstellt hat.
	 * @param inventar Das Inventar des Spielers.
	 */
	public KombinationsGUI(GUI gui, Inventar inventar) {
	    super(gui, "Kombinieren", true);
	    this.setVisible(false);

	    this.inventar = inventar;
	    
	    this.setLocationRelativeTo(gui);
	    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	    // Die Buttons
	    herstellen = new JButton("Herstellen");
	    schliessen = new JButton("Schließen");
	    
	    JPanel buttons = new JPanel();
	    buttons.setLayout(new GridLayout(1, 2, 50, 0));
	    buttons.add(herstellen);
	    buttons.add(schliessen);
	    buttons.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
	    
	    // Das Produkt und der Fortschrittsbalken
	    produkt = new InventarAnzeige(0, 0);
	    produkt.setPreferredSize(new Dimension(330, 28));
	    produkt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	    produktS = new JSpinner();
	    produktS.setPreferredSize(new Dimension(60, 25));
	    fortschritt = new Fortschritt();

	    // Produkt Panel	    
	    JPanel produktPanel = new JPanel();
	    produktPanel.setLayout(new BorderLayout());
	    produktPanel.add(produktS, BorderLayout.WEST);
	    produktPanel.add(produkt, BorderLayout.CENTER);

	    produktPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),
	    		BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Produkt")));
	    
	    JPanel balken = new JPanel();
	    balken.setLayout(new BorderLayout());
	    balken.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),
	    		BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fortschritt")));
	    fortschritt.setPreferredSize(new Dimension(300, 20));
	    balken.add(fortschritt, BorderLayout.CENTER);
	    
	    JPanel fortschrittPanel = new JPanel();
	    fortschrittPanel.setLayout(new BorderLayout());
	    fortschrittPanel.add(balken, BorderLayout.NORTH);
	    fortschrittPanel.add(produktPanel, BorderLayout.CENTER);
	    fortschrittPanel.add(buttons, BorderLayout.SOUTH);
	    
	    // Die Edukt ItemChooser
	    JPanel eduktPanel = new JPanel();
	    eduktPanel.setLayout(new GridLayout(3, 1, 0, 10));
	    eduktPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
	    
	    edukte = new ItemChooser[3];
	    for(int i = 0; i < edukte.length; i++){
	    	edukte[i] = new ItemChooser((i+1) + ". Indgredienz");
	    	edukte[i].addActionListener(this);
	    	edukte[i].addChangeListener(this);
	    	eduktPanel.add(edukte[i]);
	    }
	    
	    // alles zusammenfuegen
	    
	    produkt.setEditable(false);
	    produktS.setEnabled(false);
	    produktS.addChangeListener(this);
	    schliessen.addActionListener(this);
	    herstellen.addActionListener(this);
	    herstellen.setEnabled(false);
	    
	    this.setLayout(new BorderLayout());
	    
	    this.add(eduktPanel, BorderLayout.NORTH);
	    this.add(fortschrittPanel, BorderLayout.SOUTH);
	    
	    this.pack();
	    
	    this.setResizable(false);
	    
	    // Buffer initilisieren
	    this.createBufferStrategy(2);
	    buffer = this.getBufferStrategy();
	    buffer.show();
	    
	    resetWerte();
	}
	
	/* --- Methoden --- */

	/**
	 * Ruft die GUI wieder in den Vordergrund, indem sie wieder sichtbar wird, dadurch wird sie gestartet.
	 * @param inventar Das neue Inventar des Spielers.
	 */
	public void setInventar(Inventar inventar) {
		this.inventar = inventar;
	    resetWerte();
	    this.setVisible(true);
	}
		
	/**
	 * Setzt alle Werte der ItemChooser zurueck.
	 */
	private void resetWerte() {
		Vector<Gegenstand> alle = Kombination.istKombinierbarMit(null);
		Vector<Gegenstand> schnittmenge = new Vector<Gegenstand>();

		for(Gegenstand g : alle)
			if(!schnittmenge.contains(g))
				schnittmenge.add(g);
		Collections.sort(schnittmenge);
		for(ItemChooser ic : edukte) {
			ic.reset();
			ic.setEnabled(false);
		}
		edukte[0].setEnabled(true);
		edukte[0].setWerte(schnittmenge, inventar);
		produktS.setValue(1);
	}
	
	/**
	 * Ueberprueft, ob die eingestellte Kombination gueltig ist und der Spieler die Gegenstaende dafuer besitzt.
	 */
	private void ueberpruefe() {
		boolean kombiniert = false;
		// ein Edukt Kombination
		kombination = Kombination.kombiniere(edukte[0].getStapel());
		if(kombination != null)
			kombiniert = true;
		// zwei Edukt Kombination
		if(!kombiniert) {
			kombination = Kombination.kombiniere(edukte[0].getStapel(), edukte[1].getStapel());
			if(kombination != null)
				kombiniert = true;
		}
		// drei Edukt Kombination
		if(!kombiniert) {
			kombination = Kombination.kombiniere(edukte[0].getStapel(), edukte[1].getStapel(), edukte[2].getStapel());
			if(kombination != null)
				kombiniert = true;
		}
		// Es wurde eine gueltige Kombination gefunden.
		if(kombiniert) {
			produkt.clear();
			// Es fehlt die Kombinierungseinrichtung.
			if(!kombination.istEinrichtungVorhanden(SpielWelt.WELT.getAktuellePosition())) {
				produkt.print("Benötigt: " + kombination.getEinrichtung().getName());
	    		kombination = null;
	    		produktS.setEnabled(false);
	    		herstellen.setEnabled(false);
	    		return;
	    	}
			produkt.print(kombination.getProdukt().getGegenstand().getNameExtended());
			//produktS.setEnabled(true); TODO Etwas aendern an der Moeglichkeit mehrere Gegenstaende auf einmal herzustellen.
			herstellen.setEnabled(true);
		}
		
		// Es gibt gar keine gueltige Kombination.
		if(!kombiniert) {
			produkt.print("");
			kombination = null;
			produktS.setEnabled(false);
			herstellen.setEnabled(false);
		}
	}
	
	/**
	 * Die GUI wartet die uebergebene Zeit und der Fortschrittsbalken laeuft voll.
	 * @param dauer Die Zeit, die es dauert, bis es fertig ist.
	 */
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
	    	fortschritt.setValue((double)(zeit - start) / (double)dauer);
	    }
    	fortschritt.reset();
    	fortschritt.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		if(buffer.contentsLost()) {
			// Die BufferStrategy wird erstellt, um ein optimales Ergebnis bei der Visualisierung zu erzielen
			this.createBufferStrategy(2);
			buffer = this.getBufferStrategy();
		}
		this.paintComponents(buffer.getDrawGraphics());
		buffer.show();
	}
	
	/* --- ueberschriebene Methoden --- */

	/**
	 * Wird aufgerufen, wenn ein Button geklickt wird. 
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		// Die GUI wird "geschlossen", indem sie unsichtbar wird.
		if(evt.getSource().equals(schliessen))
			setVisible(false);
		
		// HIER MUSS NOCH DIE ANZAHL ANGEPASST WERDEN
	    if(evt.getSource().equals(herstellen)) {
	    	/*for(int i = 0; i < ((Integer)produktS.getValue()).intValue(); i++) { TODO
	    		stelleHer(kombination.getDauer());
	    		inventar.addGegenstand(kombination.getProdukt());
	    	}*/
	    	stelleHer(kombination.getDauer());
	    	inventar.addGegenstand(kombination.getProdukt());
	    	for(Stapel s : kombination.getAlleEdukte())
	    		inventar.removeGegenstand(s);
	    	resetWerte();
	    	ueberpruefe();
	    	return;
	    }

	    if(evt.getSource().equals(edukte[0].getJComboBox())) {
	    	// Der Spieler hat "Leer" ausgewaehlt.
	    	if(edukte[0].getSelectedItem() == null) {
	    		edukte[0].setMaxWert(0);
	    		edukte[1].reset();
	    		edukte[2].reset();
	    		ueberpruefe();
	    		return;
	    	}
	    	// Wenn ein gueltiger Gegstand ausgewaehlt wurde, dann wird der JSpinner freigegeben.
	    	edukte[0].setMaxWert(inventar.getStapel(edukte[0].getSelectedItem()).getAnzahl());
	    		
	    	Vector<Gegenstand> ge = Kombination.istKombinierbarMit(edukte[0].getSelectedItem());
	    	if(!ge.isEmpty()) {
	    		Collections.sort(ge);
	    		edukte[1].setWerte(ge, inventar);
	    		edukte[1].setEnabled(true);
	    		edukte[2].reset();
	    		edukte[2].setEnabled(false);
	    		ueberpruefe();
	    	}
	    }

	    if(evt.getSource().equals(edukte[1].getJComboBox())){
	    	if(edukte[1].getSelectedItem() == null) {
	    		edukte[1].setMaxWert(0);
	    		edukte[2].reset();
	    		ueberpruefe();
	    		return;
	    	}
	    	// Wenn ein gueltiger Gegstand ausgewaehlt wurde, dann wird der JSpinner freigegeben.
	    	edukte[1].setMaxWert(inventar.getStapel(edukte[1].getSelectedItem()).getAnzahl());
	    	
	    	Vector<Gegenstand> ge = Kombination.istKombinierbarMit(edukte[0].getSelectedItem(), edukte[1].getSelectedItem());
	    	if(!ge.isEmpty()) {
	    		Collections.sort(ge);
	    		edukte[2].setWerte(ge, inventar);
	    		edukte[2].setEnabled(true);
	    	    ueberpruefe();
	    	}
	    }

	    if(evt.getSource().equals(edukte[2].getJComboBox())){
	    	if(edukte[2].getSelectedItem() == null) {
	    		edukte[2].setMaxWert(0);
	    		ueberpruefe();
	    		return;
	    	}
	    	// Wenn ein gueltiger Gegstand ausgewaehlt wurde, dann wird der JSpinner freigegeben.
	    	edukte[2].setMaxWert(inventar.getStapel(edukte[2].getSelectedItem()).getAnzahl());
	    }
	}
	
	/**
	 * Wird aufgerufen, wenn einer der JSpinner veraendert wurde.
	 */
	@Override
	public void stateChanged(ChangeEvent evt) {
		/*if(evt.getSource().equals(this.produktS)) { TODO Die Einstellmoeglichkeit fuer die Menge der herzustellenden Gegenstaende ueberarbeiten
			int value = ((Integer)produktS.getValue()).intValue();
			for(ItemChooser ic : edukte)
				if(ic.getSelectedItem() != null)
					ic.setMaxWert(kombination.getStapel(ic.getSelectedItem()).getAnzahl() * value);
			ueberpruefe();
		}*/
		
		// Es wird immer geprueft, ob es eine gueltige Kombination ist.
		ueberpruefe();
	}
	
	/* --- private Klasse --- */
	
	/**
	 * Ein Forstschrittsbalken, der von der KombinationsGUI verwendet wird, um den Fortschritt beim Herstellen anzuzeigen.
	 * @author Marvin
	 */
	private class Fortschritt extends Canvas {

		// Die serielle Versionsnummer.
		private static final long serialVersionUID = 1L;

		// Der Fortschritt in Prozent.
		private transient double wert;

		/* --- Konstruktor --- */
		
		/**
		 * Erstellt einen neuen Fortschrittsbalken mit seiner Position und der Hoehe und Breite.
		 * @param x Die x-Koordinate des Fortschrittsbalken.
		 * @param y Die y-Koordinate des Fortschrittsbalken.
		 * @param breite Die Breite des Balken.
		 * @param hoehe Die Hoehe des Balken.
		 */
		public Fortschritt() {
			wert = 0.0;
		}

		/* --- Methoden --- */
		
		/**
		 * Legt den Wert des Fortschritssbalken in Prozent fest und zeichnet ihn neu.
		 * @param wert Der aktuelle Fortschritt in Prozent.
		 */
		public void setValue(double wert) {
			this.wert = wert;
			this.paint(this.getGraphics());
		}
		
		/**
		 * Setzt den Fortschrittsbalken zurueck und zeichnet ihn erneut.
		 */
		public void reset() {
			this.wert = 0.0;
			this.getGraphics().setColor(new Color(50, 250, 100));
			this.getGraphics().fillRect(0, 0, 0, this.getHeight());
		}
		
		/**
		 * Zeichnet den Fortschrittsbalken.
		 * @param g Die grafische Oberflaeche, auf der gezeichnet werden soll.
		 */
		@Override
		public void paint(Graphics g) {
			g.setColor(new Color(50, 250, 100));
			// Koordianten relativ zu diesem Objekt, deshalb 0, 0
			g.fillRect(0, 0, (int) Math.round(this.getWidth() * wert), this.getHeight());
		}

	}
}