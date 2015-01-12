package gui;

import java.awt.Dialog;

import javax.swing.JDialog;

/**
 * Hier kann der verwendete Font ausgewaehlt werden, dieser wird dann auch lokal gespeichert, sodass er spaeter wieder aufgerufen werden kann.
 * @author Marvin
 */
public class OptionsDialog extends JDialog {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/**
	 * Erstellt ein neuen OptionsDialog, hier kann der Font des Spiels eingestellt werden.
	 * @param gui Der Frame, der diesen Dialog erstellt hat.
	 */
	public OptionsDialog(GUI gui) {
		super(gui, "Optionen", Dialog.DEFAULT_MODALITY_TYPE);
		this.setSize(400, 250);
	}

}