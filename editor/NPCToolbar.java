package editor;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * GUI-Komponente der IDE. Stellt die Leite der Buttons über dem zentralen Panel dar. Enthält alle Tools,
 * die zum erstellen von NPCs benötigt werden. Für Tool zum erstellen einer Welt, siehe WorldToolbar.
 * Aktuell ausgewähltes Tool kann mit getSelectedTool() erfragt werden.
 * 
 * @author Felix
 *
 */
public class NPCToolbar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	ButtonGroup group;
	JToggleButton pointer, newStatus, newSchluessel, eraser;
	
	NPCToolbar() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		group = new ButtonGroup();
		int buttonSize = 30;
		
		pointer = new JToggleButton(new ImageIcon("EditorGraphics/pointer.png"));
		group.add(pointer);
		pointer.setToolTipText("Auswahl");
		add(pointer);
		pointer.setSelected(true);
		pointer.setBounds(0, 0, buttonSize, buttonSize);
		
		newStatus = new JToggleButton(new ImageIcon("EditorGraphics/pointer.png"));
		group.add(newStatus);
		newStatus.setToolTipText("neuer Gesprächszustand");
		add(newStatus);
		newStatus.setBounds(buttonSize, 0, buttonSize, buttonSize);
		
		newSchluessel = new JToggleButton(new ImageIcon("EditorGraphics/pointer.png"));
		group.add(newSchluessel);
		newSchluessel.setToolTipText("neues Schlüsselwort");
		add(newSchluessel);
		newSchluessel.setBounds(2*buttonSize, 0, buttonSize, buttonSize);
		
		eraser = new JToggleButton(new ImageIcon("EditorGraphics/eraser.png"));
		group.add(eraser);
		eraser.setToolTipText("löschen");
		add(eraser);
		eraser.setBounds(3*buttonSize, 0, buttonSize, buttonSize);
		
		
		setPreferredSize(new Dimension(4*buttonSize, buttonSize));
		
		
		
	}
	
	
	Tools getSelectedTool() {
		if(pointer.isSelected())
			return Tools.POINTER;
		else if(newStatus.isSelected()) 
			return Tools.NEW_STATUS;
		else if(newSchluessel.isSelected())
			return Tools.NEW_SCHLUESSEL;
		else
			return Tools.OTHER_WORLD;		
	}

}
