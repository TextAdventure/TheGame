package editor;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * GUI-Komponente der IDE. Stellt die Leiste der Buttons über dem zentralen Panel dar. Enthält alle Werkzeuge
 * zum Bearbeiten eines Welt-Objekts. Für Tools zum erstellen von NPCs siehe NPCToolbar. Aktuell ausgewähltes 
 * Tool kann mit getSelectedTool() erfragt werden.
 * 
 * @author Felix
 *
 */
public class WorldToolbar extends JPanel {
	private static final long serialVersionUID = 1L;
		
	ButtonGroup group;
	JToggleButton pointer, newOrt, newAusgang, eraser, startOrt, otherWorld;
	JButton gegenstaende;

	
	WorldToolbar() {
		setLayout(null);
		
		group = new ButtonGroup();
		int buttonSize = 30;
				
		pointer = new 	JToggleButton(new ImageIcon("EditorGraphics/pointer.png"));
		group.add(pointer);
		pointer.setToolTipText("Auswahl");
		add(pointer);
		pointer.setSelected(true);
		pointer.setBounds(0, 0, buttonSize, buttonSize);
		
		newOrt = new 	JToggleButton(new ImageIcon("EditorGraphics/ort.png"));
		group.add(newOrt);
		newOrt.setToolTipText("neuer Ort");
		add(newOrt);
		newOrt.setBounds(buttonSize, 0, buttonSize, buttonSize);
		
		newAusgang = new 	JToggleButton(new ImageIcon("EditorGraphics/ausgang.png"));
		group.add(newAusgang);
		newAusgang.setToolTipText("neuer Ausgang");
		add(newAusgang);
		newAusgang.setBounds(2*buttonSize, 0, buttonSize, buttonSize);
		
		eraser = new 	JToggleButton(new ImageIcon("EditorGraphics/eraser.png"));
		group.add(eraser);
		eraser.setToolTipText("Löschen");
		add(eraser);
		eraser.setBounds(3*buttonSize, 0, buttonSize, buttonSize);
		
		startOrt = new JToggleButton(new ImageIcon("EditorGraphics/arrowDown.png"));
		group.add(startOrt);
		startOrt.setToolTipText("Startposition festlegen");
		add(startOrt);
		startOrt.setBounds(4*buttonSize, 0, buttonSize, buttonSize);
		
		otherWorld = new JToggleButton(new ImageIcon("EditorGraphics/blackBox.png"));
		group.add(otherWorld);
		otherWorld.setToolTipText("andere Welt anbinden");
		add(otherWorld);
		otherWorld.setBounds(5*buttonSize, 0, buttonSize, buttonSize);
		
		gegenstaende = new JButton(new ImageIcon("EditorGraphics/gegenstand.png"));
		gegenstaende.setToolTipText("Gegenstände verwalten...");
		gegenstaende.setBounds(6*buttonSize+10, 0, buttonSize, buttonSize);
		add(gegenstaende);
		
		
		setPreferredSize(new Dimension(7*buttonSize+10, buttonSize));
		
		
	}
	
	Tools getSelectedTool() {
		if(pointer.isSelected())
			return Tools.POINTER;
		else if(newOrt.isSelected())
			return Tools.NEW_ORT;
		else if(newAusgang.isSelected())
			return Tools.NEW_AUSGANG;
		else if(eraser.isSelected())  
			return Tools.ERASER;
		else if(startOrt.isSelected()) 
			return Tools.START_ORT;
		else
			return Tools.OTHER_WORLD;
		
	}
	
	void gegenstandActionListener(ActionListener al) {
		gegenstaende.addActionListener(al);
	}

}
