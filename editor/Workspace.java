package editor;

import game.UntersuchbaresObjekt;
import game.items.Gegenstand;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

/**
 * GUI-Komponente der IDE. Stellt die Arbeitsfläche dar, auf der die Welt gebaut wird. Enthält insbesondere
 * die modifizierte paint(Graphics) Methode, die die Welt zeichnet. 
 * 
 * @author Felix
 *
 */
public class Workspace extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private WeltObjekt welt;
	private File file;
	
	Workspace(IDE ide, WeltObjekt welt) {
		this(ide, null, welt);
	}
	Workspace(IDE ide, File file, WeltObjekt welt) {
		this.file = file;
		this.welt = welt;
		
		MouseAdapter ausgang = new WorkspaceMouseAusgang(welt, this, ide);
		MouseAdapter eraser = new WorkspaceMouseEraser(welt, this, ide);
		MouseAdapter ort = new WorkspaceMouseOrt(welt, this, ide); 
		MouseAdapter pointer = new WorkspaceMousePointer(welt, this, ide);
		MouseAdapter world = new WorkspaceMouseStart(welt, this, ide);
		
		addMouseListener(ausgang);
		addMouseListener(eraser);
		addMouseListener(ort);
		addMouseListener(pointer);
		addMouseListener(world);
		
		addMouseMotionListener(ausgang);
		addMouseMotionListener(eraser);
		addMouseMotionListener(ort);
		addMouseMotionListener(pointer);
		addMouseMotionListener(world);
	}
	
	/**
	 * Gibt die Datei zrück, in der dieser Workspace gespeichert ist, oder null, wenn dieser Workspace noch nie
	 * gespeichert wurde.
	 * @return
	 */
	File getFile() {
		return file;
	}
	/**
	 * Legt fest, in welcher Datei dieser Workspace von nun an gespeichert werden soll.
	 * @param file
	 */
	void setFile(File file) {
		this.file = file;
	}
	
	
	/**
	 * Gibt die aktuell bearbeitete Welt zurück.
	 * @return
	 */
	WeltObjekt getWorld() {
		return welt;
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000, 1000);
	}
	
	
	/**
	 * Gibt die Position des hotizontalen Rollbalkens an, falls dieser Workspace in einem JSccrollPane angezeigt wird.
	 * 0 andernfalls.
	 * @return
	 */
	int getXPos() {		
		//if this panel is displayed in a scroll pane just draw the necessary part of it
		//--> increase performance!!!
		Container parent = this.getParent().getParent();
		if(parent instanceof JScrollPane) {			//this should definitely be the case!
			return ((JScrollPane) parent).getHorizontalScrollBar().getValue();
		}
		
		return 0;
	}
	
	/**
	 * Gibt die Position des vertikalen Rollbalkens an, falls dieser Workspace in einem JSccrollPane angezeigt wird.
	 * 0 andernfalls.
	 * @return
	 */
	int getYPos() {		
		//if this panel is displayed in a scroll pane just draw the necessary part of it
		//--> increase performance!!!
		Container parent = this.getParent().getParent();
		if(parent instanceof JScrollPane) {			//this should definitely be the case!
			return ((JScrollPane) parent).getVerticalScrollBar().getValue();
		}
		
		return 0;
	}
	
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	/**
	 * Zeichne die Welt auf Basisi der Informationen des WeltObjekts.
	 */
	@Override
	public void paint(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		int xPos = 0, yPos = 0;
		
		
		//if this panel is displayed in a scroll pane just draw the necessary part of it
		//--> increase performance!!!
		Container parent = this.getParent().getParent();
		if(parent instanceof JScrollPane) {			//this should definitely be the case!
			xPos = ((JScrollPane) parent).getHorizontalScrollBar().getValue();
			yPos = ((JScrollPane) parent).getVerticalScrollBar().getValue();
			width = ((JScrollPane) parent).getViewport().getWidth(); 
			height = ((JScrollPane) parent).getViewport().getHeight();
		}
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graph = img.createGraphics();
		graph.setColor(Color.WHITE);
		graph.fillRect(0, 0, width, height);
		
		OrtErweitert[] orte = welt.getOrte();
		AusgangErweitert[] ausgaenge = welt.getAusgaenge();
		
		//draw Orte
		graph.setColor(Color.BLACK);
		graph.setStroke(new BasicStroke(2));
		for(OrtErweitert o : orte)
			paintOrt(o, graph, xPos, yPos, width, height);
		
		
		//highllite start ort
		OrtErweitert start = welt.getStartOrt();
		if(start != null) {
			graph.setColor(Color.RED);
			paintOrt(start, graph, xPos, yPos, width, height);
			graph.setColor(Color.BLACK);
		}
		
		
		//draw Ausgänge
		for(int i = 0; i < ausgaenge.length; i++) {
			Point[] points = ausgaenge[i].points;
			graph.fillOval(points[0].x-5-xPos, points[0].y-5-yPos, 10, 10);
			int last = points.length - 1;
			graph.fillOval(points[last].x-5-xPos, points[last].y-5-yPos, 10, 10);
			for(int j = 0; j < points.length-1; j++) 
				graph.drawLine(points[j].x-xPos, points[j].y-yPos, points[j+1].x-xPos, points[j+1].y-yPos);			
		}
		
		
		graph.dispose();
		g.drawImage(img, xPos, yPos, this);
	}
	
	
	/**
	 * Zeichne einen Ort.
	 * @param ort Der Ort, der gezeichnet werden soll.
	 * @param graph Das Graphics-Objekt, mit dem gezeichnet werden soll.
	 * @param xPos Position der Horizontalen ScrollBar.
	 * @param yPos Position der Vertikalen ScrollBar.
	 * @param width	Breite des angezeigten Bildausschnitts.
	 * @param height Höhe des angezeigten Bildausschnitts.
	 */
	private void paintOrt(OrtErweitert ort, Graphics graph, int xPos, int yPos, int width, int height) {
		int x = ort.position.x;
		int y = ort.position.y;
		int w = ort.groesse.width;
		int h = ort.groesse.height;
		int xGap = 10, yGap = 20;			//lokale Konstanten, die angeben, wie viel Abstand beim zeichnen des Texts vom Rand gehalten wird.
		int beschrLength = 100;		//Anz. der char der Beschreibung, die noch dargestellt werden.
		
		if(x < xPos + width && x + w > xPos && y < yPos + height && y + h > yPos) {		//draw only if necessary
			//Rahmen zeichnen
			graph.drawRect(x-xPos, y-yPos, w, h);
			
			//Text erstellen
			//Name
			String text = ort.ort.getName() + "\n";			
			
			//Beschreibung
			String beschreibung = ort.ort.getBeschreibung();
			if(beschreibung.length() <= beschrLength) {
				text += beschreibung + "\n\n";
			} else {
				text += beschreibung.substring(0, beschrLength)+ "...\n\n";
			}
			
			//Untersuchbare Objekte
			text += "Untersuch. Obj.:   ";
			UntersuchbaresObjekt[] untersuchObjs = ort.ort.getUnteruschbareObjekte();
			for(int i = 0; i < untersuchObjs.length; i++) {
				text += untersuchObjs[i].getName() + ", ";
			}
			
			//Gegenstände
			text = text.substring(0, text.length()-2) + "\nGegenstände:    ";
			Gegenstand.GEGENSTAENDE = welt.getGegenstaende();
			Gegenstand[] gegenstaende = ort.ort.getGegenstaende();
			for(int i = 0; i < gegenstaende.length; i++) {
				int anz = ort.ort.getGegenstandAnzahl(gegenstaende[i]);
				if(anz > 1) 
					text += anz + " " + gegenstaende[i].getPlural() + ", ";
				else 
					text += gegenstaende[i].getName() + ", ";
			}
			text = text.substring(0, text.length()-2);
			
			
			//Text zeichnen
			int stringY = y-yPos+yGap;
			FontMetrics metrics = graph.getFontMetrics();
			
			for (String line : text.split("\n")) {
				int stringX = x-xPos+xGap;
				
				String[] woerter = line.split(" ");
				int index = 0;

				while(index < woerter.length && stringY < y-yPos+h) {
					if(stringX + metrics.stringWidth(woerter[index]) > x-xPos+w-xGap) {
						//Zeilenumbruch
						stringY += metrics.getHeight();
						stringX = x-xPos+xGap;
						
					} else {
						//zeichne Wort
						graph.drawString(woerter[index] + " ", stringX, stringY);
						stringX += metrics.stringWidth(woerter[index]+" ");
						index++;
					}
					
				}
				
				//springe zu nächster Zeile
				stringY += metrics.getHeight();
			}

		}		
	}
	
	
}
