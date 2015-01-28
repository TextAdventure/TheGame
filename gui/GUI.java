package gui;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import game.Interpreter;
import game.SpielWelt;
import game.entity.Faehigkeit;
import game.items.Gegenstand;

/**
 *  Diese GUI beinhaltet alle fuer das Spiel relevanten Dinge.
 */
public class GUI extends JFrame implements CaretListener {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der letzte Text der Info Eingabe.
	private String text;

	// Der Hintergrund fuer den Frame.
	private BufferedImage background;
	// Die Anzeige fuer das Spielgeschehen.
	private Anzeige anzeige;
	// Das Eingabefeld fuer den Spieler.
	private Eingabefeld eingabe;
	// Das Infofeld, in dem Informationen fuer den Spieler angezeigt werde.
	private InfoPanel info;
	// Die Karte der Spielumgebung.
	private MiniMap map;

	// Das JTabbedPane, in dem sich alle Displays befinden.
	private JTabbedPane displays;
	// Das eigentliche Inventar, es wird erst in ein JScrollPane gepackt.
	private InventarAnzeige inventar;
	// Die Faehigkeiten des Spielers werden hier angezeigt.
	private FaehigkeitenAnzeige faehigkeiten;
	// Die Ausruestung des Spielers wird hier angezeigt.
	private AusruestungsAnzeige ausruestung;

	// Die GUI, in der kombiniert werden kann.
	private KombinationsGUI kombination;

	// Das Spiel wird hiermit gestartet.
	private transient Interpreter interpreter;

	/**
	 * Eine neue GUI.
	 */
	public GUI() {
	    super("Super Text Adventure");

	    anzeige = new Anzeige(690, 370);
	    eingabe = new Eingabefeld(this);
	    info = new InfoPanel(345, 220);
	    
	    displays = new JTabbedPane();
	    inventar = new InventarAnzeige(290, 400);
	    faehigkeiten = new FaehigkeitenAnzeige(290, 400);
	    ausruestung = new AusruestungsAnzeige(290, 400);
	    
	    map = new MiniMap();
	    /*directions = new JPanel();
	    playerInfo = new JPanel();*/

	    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    
	    // Layout und Components //
	    
	    setPreferredSize(new java.awt.Dimension(1000, 655));
	    setBounds(5, 5, 1000, 655);
	    setLayout(null);

	    setResizable(false);
	    
	    add(anzeige.getJScrollPane());
	    add(eingabe);
	    add(info.getJScrollPane());
	    displays.addTab("Inventar", inventar.getJScrollPane());
	    displays.addTab("Fähigkeiten", faehigkeiten.getJScrollPane());
	    displays.addTab("Ausrüstung", ausruestung.getJScrollPane());
	    add(displays);
	    add(map);
	    /*add(directions);
	    add(playerInfo);*/	    
	    
	    anzeige.getJScrollPane().setLocation(5, 5);
	    eingabe.setLocation(5, 380);	    
	    info.getJScrollPane().setLocation(5, 405);	    
	    displays.setBounds(700, 5, 290, 395);	    
	    map.setLocation(355, 400);	    
	    this.pack();
	    
	    /*directions.setBounds(0, 400, 350, 250);
	    playerInfo.setBounds(650, 400, 350, 250);*/

	    //Der Hintergrund wird geladen.
	    java.net.URL imgURL = getClass().getResource("resource/background.png");
	    try {
	    	background = javax.imageio.ImageIO.read(imgURL);
	    	background.addTileObserver(null); // TODO wieder entfernen
	    } catch(java.io.IOException e) {
	    	System.err.println("Es trat ein Fehler beim laden der Datei auf.");
	    	e.printStackTrace();
	    }

	    //setUndecorated(true);
	    //AWTUtilities.setWindowOpaque(this, true);

	    interpreter = new Interpreter(this);
	    interpreter.ueberpruefeBefehl("");

	    kombination = new KombinationsGUI(this, SpielWelt.WELT.getSpieler().getInventar());

	    KeyAdapter ka = new KeyAdapter() {

	    	public void keyTyped(KeyEvent evt) {
	    		if(evt.getKeyChar() == 'i' || evt.getKeyChar() == 'I') {
	    			boolean gefunden = false;
	    			for(Faehigkeit f : SpielWelt.WELT.getSpieler().getFaehigkeiten()) {
	    				if(f.getName().equalsIgnoreCase(text)) {
	    					info.zeigeFaehigkeitAn(f, SpielWelt.WELT.getSpieler());
		    				gefunden = true;
	    				}
	    			}
	    			if(Gegenstand.getGegenstand(text) != null) {
	    				info.printPrintable(Gegenstand.getGegenstand(text));
	    				gefunden = true;
	    			}
	    			if(!gefunden)
	    				info.clear();
	    		}
	    	}

	    };

	    anzeige.addCaretListener(this);
	    anzeige.addKeyListener(ka);
	    inventar.addCaretListener(this);
	    inventar.addKeyListener(ka);
	    faehigkeiten.addCaretListener(this);
	    faehigkeiten.addKeyListener(ka);
	    ausruestung.addCaretListener(this);
	    ausruestung.addKeyListener(ka);
	    info.addCaretListener(this);
	    info.addKeyListener(ka);
	    this.addKeyListener(ka);

	    text = "";
	    setVisible(true);
	}

	/**
	 *  Diese Methode uebergibt einen Befehl an das Spiel.
	 *  befehl: der Befehl, der an das Spiel uebergeben wird.
	 */
	public void uebergebeBefehl(String befehl) {
		interpreter.ueberpruefeBefehl(befehl);
	}

	// Diese Methode gibt die Anzeige zurueck.
	public Anzeige getAnzeige() {
		return anzeige;
	}
	// Diese Methode gibt die Map zurueck.
	public MiniMap getMap() {
	    return map;
	}

	// Diese Methode gibt das InventoryDisplay zurueck.
	public InventarAnzeige getInventarAnzeige() {
	    return inventar;
	}

	// Diese Methode gibt das SkilDisplay zurueck.
	public FaehigkeitenAnzeige getFaehigkeitenAnzeige() {
		return faehigkeiten;
	}
	
	public AusruestungsAnzeige getAusruestungsAnzeige() {
		return ausruestung;
	}

	// Diese Methode gibt das Eingabefeld zurueck.
	public Eingabefeld getEingabefeld() {
	    return eingabe;
	}

	/**           MOMENTAN NICHT SO WICHTIG
	 *  Die paint() wird ueberschrieben, sodass zuerst der Hintergrund gezeichnet werden kann.
	 *
	@Override
	public void paint(Graphics g) {
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = (Graphics2D)img.getGraphics();
	    g2d.drawImage(background, 0, 0, getWidth(), getHeight(), 0, 0, background.getWidth(), background.getHeight(), Color.RED, null);

	    // Das eigentliche Bild wird auf dem Bildschirm gezeichnet.
	    g.drawImage(img, 0, 0, null);
	    super.paintComponents(g);
	}*/

	/**
	 *  Diese Methode startet die KombinationsGUI.
	 */
	public void kombinationsGUIStarten() {
	    kombination.setInventar(SpielWelt.WELT.getSpieler().getInventar());
	}
	
	
	public void zeigeOptionenAn() {
		OptionsDialog dialog = new OptionsDialog();
	    int result = dialog.zeigeDialog(this);
	    if (result == OptionsDialog.OK) {
	    	Font font = dialog.getAusgewaehltenFont(); 
	    	System.out.println("Ausgewaehlter Font : " + font); //TODO spaeter wieder entfernen
	    }
	}
	
	@Override
	public void caretUpdate(CaretEvent evt) {
		text = ((JEditorPane)evt.getSource()).getSelectedText();
		if(text != null)
			text = text.trim();
		else
			text = "";
	}


	// MAIN METHODE
	/**
	 * Eine neue GUI wird gestartet mit dem Spiel.
	 */
	public static void main(String[] args) {
		new GUI();
	}

}