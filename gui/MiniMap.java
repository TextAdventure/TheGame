package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.Ausgang;
import game.Ort;

public class MiniMap extends Canvas {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort, an dem sich der Spieler befindet.
	private Ort position;
	/*// Die alte Position des Spielers. TODO
	private Ort altePosition;
	// Die letzte Position
	private Point punkt;*/

	// ALLE IMAGES FUER DIE ANZEIGE???

	// Das Image fuer einen Punkt.
	transient private BufferedImage point = null;
	// Das Image fuer eine Durchgezogene Linie.
	transient private BufferedImage linieFull = null;
	// Das Image fuer eine fadende Linie.
	transient private BufferedImage linieFade = null;

	/**
	 *  Eine neue Map wird erstellt und ihr wird nichts uebergeben.
	 */
	public MiniMap(){
		// laden der Bilder
	    java.net.URL pointURL = getClass().getResource("resource/point.png");
	    java.net.URL fullURL = getClass().getResource("resource/connectorfull.png");
	    java.net.URL fadeURL = getClass().getResource("resource/connectorfade.png");
	    try{
	    	point = ImageIO.read(pointURL);
	    	linieFull = ImageIO.read(fullURL);
	    	linieFade = ImageIO.read(fadeURL);
	    }catch(java.io.IOException e){
	    	System.err.println("Es trat ein Fehler beim laden der Datei auf.");
	    	e.printStackTrace();
	    }

	    //punkt = new Point(0, 0);TODO

	    this.setSize(300, 240);
	}

	/**
	 *  Wird jedesmal ausgeloest, wenn die aktuelle Position des Spielers sich veraendert hat.
	 */
	public void updateMiniMap(Ort ort){
		// DIE MAP MUSS NEU GENERIERT WERDEN
	    //altePosition = position;TODO
	    position = ort;
	    repaint();                       // ???
	}

	/** @override
	 *  Die paint() wird ueberschrieben, sodass sie die Map neu zeichnet, wenn sie aufgerufen wird.
	 */
	public void paint(Graphics g){
		// Die Methode faengt in der Mitte an, bei der Spieler Position, und zeichnet die anderen Orte um die Spieler Position herum.
	    BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = (Graphics2D)img.getGraphics();

	    if(position != null){
	    	for(Ausgang a: position.getAusgaenge().toArray(new Ausgang[0])){
	    		Point p = zeichneVerbindung(getWidth() / 2, getHeight() / 2, a, g2d);
	    		if(a.getZielort().istBesucht() && p != null){
	    			int newX = (int)Math.round(p.getX() + Math.cos((9 - a.getRichtung()) * Math.PI / 4.0) * linieFull.getWidth());
	    			int newY = (int)Math.round(p.getY() - Math.sin((9 - a.getRichtung()) * Math.PI / 4.0) * linieFull.getWidth());
	    			for(Ausgang aus: a.getZielort().getAusgaenge().toArray(new Ausgang[0])){
	    				// So wird verhindert, dass der Ausgang zurueck nochmal gezeichnet wird.
	    				int alteRichtung = a.getRichtung() - 4;
	    				if(alteRichtung < 1) alteRichtung += 8;
	    				if(aus.getRichtung() != alteRichtung){
	    					// Es wird die zweite Stufe ueberprueft und gezeichnet.
	    					Point p2 = zeichneVerbindung(newX, newY, aus, g2d);
	    					if(aus.getZielort().istBesucht() && p2 != null){
	    						int secX = (int)Math.round(p2.getX() + Math.cos((9 - aus.getRichtung()) * Math.PI / 4.0) * linieFull.getWidth());
	    						int secY = (int)Math.round(p2.getY() - Math.sin((9 - aus.getRichtung()) * Math.PI / 4.0) * linieFull.getWidth());
	    						zeichnePunkt(Color.BLUE, secX, secY, g2d);
	    					}
	    				}
	    			}
	    			// Wird zum Schluss gezeichnet, sodass er oben ist.
	    			zeichnePunkt(Color.BLUE, newX, newY, g2d);
	    		}
	    	}
	    	// Die aktuelle Position des Spielers.
	    	zeichnePunkt(Color.GREEN, getWidth() / 2, getHeight() / 2, g2d);
	    }

	    g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
	}

	private void zeichnePunkt(Color farbe, int x, int y, Graphics2D g2d){
	    int xpos = x - point.getWidth() / 2;
	    int ypos = y - point.getHeight() / 2;
	    /*for(int i = 0; i < 3; i++){                     //SCHATTEN   ???
	      g2d.setColor(new Color(0 + 15 * i, 0 + 15 * i, 0 + 15 * i));
	      g2d.fillOval(xpos + i, ypos + i, punkt.getWidth() + 1, punkt.getHeight() + 1);                   //  * (i+18)/20
	    }*/
	    g2d.drawImage(point, xpos, ypos, null);
	    Color old = g2d.getColor();
	    g2d.setColor(new Color(farbe.getRed(), farbe.getGreen(), farbe.getBlue(), 128));
	    g2d.fillOval(xpos-1, ypos-1, point.getWidth() + 2, point.getHeight() + 2);
	    g2d.setColor(old);
	}

	private Point zeichneVerbindung(int xstart, int ystart, Ausgang richtung, Graphics2D g2d){
		BufferedImage img = linieFull;
	    if(!richtung.getZielort().istBesucht()) img = linieFade;
	    if(richtung.getRichtung() > 8 || richtung.getRichtung() == 0) {
	    	return null;
	    }
	    double angle = (richtung.getRichtung() - 1) * Math.PI / 4.0;

	    AffineTransform at = new AffineTransform();
	    //at.translate(xstart * 0.17, 0);        //MUSS VERBESSERT WERDEN
	    //at.shear(-0.2, 0.0);
	    at.rotate(angle, xstart, ystart);
	    g2d.setTransform(at);

	    g2d.drawImage(img, xstart, ystart - img.getHeight() / 2, null);
	    g2d.setTransform(new AffineTransform());
	    return new Point(xstart, ystart - img.getHeight() / 2);
	}
}
