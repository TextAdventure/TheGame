package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BattleScreen extends Canvas {
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	public BattleScreen() {
		
		this.setSize(370, 340);
	}

	@Override
	public void paint(Graphics g) {
		BufferedImage db = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) db.getGraphics();
		
		g2d.setColor(Color.ORANGE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(db, 0, 0, null);
	}
	
	/**
	 * Der BattleScreen wird neu gezeichnet.
	 */
	@Override
	public void repaint() {
		super.repaint();
	}
}
