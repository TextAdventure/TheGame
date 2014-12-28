package gui;

import game.SpielTest;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 *  Diese GUI beinhaltet alle fuer das Spiel relevanten Dinge.
 */
public class GUI extends JFrame {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	// Der Hintergrund fuer den Frame.
	private BufferedImage background;
	// Die Anzeige fuer das Spielgeschehen.
	private Anzeige anzeige;
	// Das Eingabefeld fuer den Spieler.
	private Eingabefeld eingabe;
	// Die Karte der Spielumgebung.
	private MiniMap map;
	// Das eigentliche Inventar, es wird erst in ein JScrollPane gepackt.
	private InventoryDisplay inventory;
	// Die GUI, in der kombiniert werden kann.
	private KombinationsGUI kombination;
	  
	// Dsa Spiel wird hiermit gestartet.
	private SpielTest test;

	/**
	 *  Eine neue GUI.
	 */
	public GUI(){
	    super("Super Text Adventure");

	    anzeige = new Anzeige();
	    eingabe = new Eingabefeld(this);                                                       //
	    inventory = new InventoryDisplay();                                                    //
	    map = new MiniMap();                                                                   //
	    /*directions = new JPanel();                                                           //
	    playerInfo = new JPanel();*/                                                           //
	                                                                                           //
	    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);                               //
	    //setPreferredSize(new Dimension(1000, 600));                                          //
	    setBounds(5, 50, 1000, 655);                                                            //
	    setLayout(null);                                                                       //
	                                                                                          //
	    add(anzeige.getJScrollPane());                                                      //
	    add(eingabe);                                                                     //
	    add(inventory.getJScrollPane());                                              //
	    //add(map);                                                  MAP WIEDER ADDEN
	    /*add(directions);                                                            //
	    add(playerInfo);*/                                                                //
	                                                                                        //
	    anzeige.getJScrollPane().setLocation(5, 5);                                           //
	    eingabe.setLocation(5, 380);                                                           //
	    inventory.getJScrollPane().setLocation(700, 5);                                        //
	    map.setLocation(355, 400);                                                             //
	    /*directions.setBounds(0, 400, 350, 250);                                              //
	    playerInfo.setBounds(650, 400, 350, 250);*/                                            //
	                                                                                           //
	    // Der Hintergrund wird geladen.                                                       //
	    java.net.URL imgURL = getClass().getResource("resource/background.png");               //
	    try{
	    	background = javax.imageio.ImageIO.read(imgURL);
	    }catch(java.io.IOException e){
	    	System.err.println("Es trat ein Fehler beim laden der Datei auf.");
	    	e.printStackTrace();
	    }

	    //setUndecorated(true);
	    //AWTUtilities.setWindowOpaque(this, true);
	    
	    test = new SpielTest(this);
	    test.ueberpruefeBefehl("");
	    
	    kombination = new KombinationsGUI(this, test.getWelt().getSpieler().getInventar().clone());

	    setResizable(false);
	    setVisible(true);
	}
	  
	/**
	 *  Diese Methode uebergibt einen Befehl an das Spiel.
	 *  befehl: der Befehl, der an das Spiel uebergeben wird.
	 */
	public void uebergebBefehl(String befehl){
		test.ueberpruefeBefehl(befehl);
	}

	// Diese Methode gibt die Anzeige zurueck.
	public Anzeige getAnzeige(){
		return anzeige;
	}
	// Diese Methode gibt die Map zurueck.
	public MiniMap getMap(){
	    return map;
	}

	// Diese Methode gibt das InventoryDisplay zurueck.
	public InventoryDisplay getInventoryDisplay(){
	    return inventory;
	}
	  
	// Diese Methode gibt das Eingabefeld zurueck.
	public Eingabefeld getEingabefeld(){
	    return eingabe;
	}
	  
	// Diese Methode gibt den SpielTest zurueck.
	public SpielTest getTest(){
		return test;
	}

	/** @override               MOMENTAN NICHT SO WICHTIG
	 *  Die paint() wird ueberschrieben, sodass zuerst der Hintergrund gezeichnet werden kann.
	 *
	public void paint(Graphics g){
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = (Graphics2D)img.getGraphics();
	    g2d.drawImage(background, 0, 0, getWidth(), getHeight(), 0, 0, background.getWidth(), background.getHeight(), Color.RED, null);
	    
	    // Das eigentliche Bild wird auf dem Bildschirm gezeichnet.
	    g.drawImage(img, 0, 0, null);
	    System.out.println("TEST");
	    //super.paintComponents(g);
	}*/
	
	/**
	 *  Diese Methode startet die KombinationsGUI.
	 */
	public void kombinationsGUIStarten(){
	    kombination.setInventory(test.getWelt().getSpieler().getInventar().clone());
	}
	  
	// MAIN_METHODE
	/**
	 *  Eine neue GUI wird gestartet mit dem Spiel.
	 */
	public static void main(String[] args){
	    GUI gui = new GUI();
	}
}