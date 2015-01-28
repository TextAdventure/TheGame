package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

/**
 * Hier kann der verwendete Font ausgewaehlt werden, dieser wird dann auch lokal gespeichert, sodass er spaeter wieder aufgerufen werden kann.
 * @author Marvin
 */
public class OptionsDialog extends JComponent {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;
	
	/* --- Konstanten --- */
	
	public static final int OK = 0;
	
	public static final int ABBRECHEN = 1;
	
	private static final String[] FONT_GROESSEN = {
		"8", "9", "10", "11", "12", "14", "16", "18", "20"
	};
	
	private static final Font STANDARD_FONT = new Font("Arial", Font.PLAIN, 12);
	
	/* --- Variablen --- */
	
	private int dialogRueckgabe;
	
	private String[] fontNamen;
	
	private JTextField fontNamenAnzeige;
	private JTextField fontGroessenAnzeige;
	
	private JList<String> fontNamenListe;
	private JList<String> fontGroessenListe;
	
	private JPanel fontNamenPanel;
	private JPanel fontGroessenPanel;
	
	private JPanel vorschauPanel;
	private JTextField vorschauText;
	
	/**
	 * Erstellt ein neuen OptionsDialog, hier kann der Font des Spiels eingestellt werden.
	 * @param gui Der Frame, der diesen Dialog erstellt hat.
	 */
	public OptionsDialog() {	
		dialogRueckgabe = ABBRECHEN;
		
		fontNamen = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		// Der eigentliche Dialog wird nun zusammengesetzt
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(getFontNamenPanel());
		panel.add(getFontGroessenPanel());
		
		JPanel inhalt = new JPanel();
		inhalt.setLayout(new GridLayout(2, 1));
		inhalt.add(panel, BorderLayout.NORTH);
		inhalt.add(getVorschauPanel(), BorderLayout.CENTER);
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(inhalt);
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.waehleFontAus(STANDARD_FONT);
	}
	
	/* --- Methoden --- */
	
	
	public int zeigeDialog(Frame parent) {
		dialogRueckgabe = ABBRECHEN;
		JDialog dialog = erstelleDialog(parent);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dialogRueckgabe = ABBRECHEN;
			}
		});
		
		dialog.setVisible(true);
		dialog.dispose();
		dialog = null;
		
		return dialogRueckgabe;
	}
	
	public Font getAusgewaehltenFont() {
		return new Font(getFontNamenListe().getSelectedValue(), Font.PLAIN, Integer.valueOf(getFontGroessenListe().getSelectedValue()));
	}
	
	
	private JDialog erstelleDialog(Frame parent) {
		JDialog dialog = new JDialog(parent, "Wähle Font aus", true);
		
		Action okAktion = new DialogOKAktion(dialog);
		Action abbrechenAktion = new DialogAbbrechenAktion(dialog);
		
		JButton ok = new JButton(okAktion);
		ok.setFont(STANDARD_FONT);
		JButton abbrechen = new JButton(abbrechenAktion);
		abbrechen.setFont(STANDARD_FONT);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2, 1, 5, 5));
		buttons.add(ok);
		buttons.add(abbrechen);
		buttons.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 10));
		
		ActionMap actionMap = buttons.getActionMap();
		actionMap.put(abbrechenAktion.getValue(Action.DEFAULT), abbrechenAktion);
		actionMap.put(okAktion.getValue(Action.DEFAULT), okAktion);
		InputMap inputMap = buttons.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), abbrechenAktion.getValue(Action.DEFAULT));
		inputMap.put(KeyStroke.getKeyStroke("ENTER"), okAktion.getValue(Action.DEFAULT));
		
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		dialogPanel.add(buttons, BorderLayout.NORTH);
		
		dialog.getContentPane().add(this, BorderLayout.CENTER);
		dialog.getContentPane().add(dialogPanel, BorderLayout.EAST);
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		return dialog;
	}
	
	/**
	 * Aktualisiert die Vorschau, indem der Text angepasst wird.
	 */
	private void aktualisiereVorschau() {
		getVorschauText().setFont(getAusgewaehltenFont());
	}
	
	/**
	 * Waehlt einen Font aus, sodass die Anzeigen aktualisiert werden koennen.
	 * @param font Der neue Font, der angezeigt werden soll.
	 */
	private void waehleFontAus(Font font) {
		for(int i = 0; i < fontNamen.length; i++)
			if(fontNamen[i].equalsIgnoreCase(font.getFamily())) {
				getFontNamenListe().setSelectedIndex(i);
				getFontNamenAnzeige().setText(font.getFamily());
				break;
			}
		for(int i = 0; i < FONT_GROESSEN.length; i++)
			if(Integer.valueOf(FONT_GROESSEN[i]) == font.getSize()) {
				getFontGroessenListe().setSelectedIndex(i);
				getFontGroessenAnzeige().setText(Integer.toString(font.getSize()));
				break;
			}
		aktualisiereVorschau();
	}

	
	private JTextField getFontNamenAnzeige() {
		if(fontNamenAnzeige == null) {
			fontNamenAnzeige = new JTextField();
			fontNamenAnzeige.addFocusListener(new TextFeldFokusHandler(fontNamenAnzeige));
			fontNamenAnzeige.addKeyListener(new TextFeldTastenHandler(getFontNamenListe()));
			fontNamenAnzeige.getDocument().addDocumentListener(new ListenSucheHandler(getFontNamenListe()));
			fontNamenAnzeige.setFont(STANDARD_FONT);
		}
		return fontNamenAnzeige;
	}
	
	private JList<String> getFontNamenListe() {
		if(fontNamenListe == null) {
			fontNamenListe = new JList<String>(fontNamen);
			fontNamenListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			fontNamenListe.addListSelectionListener(new ListenAuswahlHandler(getFontNamenAnzeige()));
			fontNamenListe.setSelectedIndex(0);
			fontNamenListe.setFont(STANDARD_FONT);
			fontNamenListe.setFocusable(false);
		}
		return fontNamenListe;
	}
	
	private JPanel getFontNamenPanel() {
		if(fontNamenPanel == null) {
			fontNamenPanel = new JPanel();
			fontNamenPanel.setLayout(new BorderLayout());
			fontNamenPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			fontNamenPanel.setPreferredSize(new Dimension(180, 130));
			
			JScrollPane scrollN = new JScrollPane(getFontNamenListe());
			scrollN.getVerticalScrollBar().setFocusable(false);
			scrollN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			JPanel panelN = new JPanel();
			panelN.setLayout(new BorderLayout());
			panelN.add(getFontNamenAnzeige(), BorderLayout.NORTH);
			panelN.add(scrollN, BorderLayout.CENTER);
			
			JLabel labelN = new JLabel("Font Name");
			labelN.setHorizontalAlignment(JLabel.LEFT);
			labelN.setHorizontalTextPosition(JLabel.LEFT);
			labelN.setLabelFor(getFontNamenAnzeige());
			
			fontNamenPanel.add(labelN, BorderLayout.NORTH);
			fontNamenPanel.add(panelN, BorderLayout.CENTER);
		}
		return fontNamenPanel;
	}
	
	private JTextField getFontGroessenAnzeige() {
		if(fontGroessenAnzeige == null) {
			fontGroessenAnzeige = new JTextField();
			fontGroessenAnzeige.addFocusListener(new TextFeldFokusHandler(fontGroessenAnzeige));
			fontGroessenAnzeige.addKeyListener(new TextFeldTastenHandler(getFontGroessenListe()));
			fontGroessenAnzeige.getDocument().addDocumentListener(new ListenSucheHandler(getFontGroessenListe()));
			fontGroessenAnzeige.setFont(STANDARD_FONT);
		}
		return fontGroessenAnzeige;
	}
	
	private JList<String> getFontGroessenListe() {
		if(fontGroessenListe == null) {
			fontGroessenListe = new JList<String>(FONT_GROESSEN);
			fontGroessenListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			fontGroessenListe.addListSelectionListener(new ListenAuswahlHandler(getFontGroessenAnzeige()));
			fontGroessenListe.setSelectedIndex(0);
			fontGroessenListe.setFont(STANDARD_FONT);
			fontGroessenListe.setFocusable(false);
		}
		return fontGroessenListe;
	}
	
	private JPanel getFontGroessenPanel() {
		if(fontGroessenPanel == null) {
			fontGroessenPanel = new JPanel();
			fontGroessenPanel.setLayout(new BorderLayout());
			fontGroessenPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			fontGroessenPanel.setPreferredSize(new Dimension(70, 130));
			
			JScrollPane scrollG = new JScrollPane(getFontGroessenListe());
			scrollG.getVerticalScrollBar().setFocusable(false);
			scrollG.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			JPanel panelG = new JPanel();
			panelG.setLayout(new BorderLayout());
			panelG.add(getFontGroessenAnzeige(), BorderLayout.NORTH);
			panelG.add(scrollG, BorderLayout.CENTER);
			
			JLabel labelG = new JLabel("Font Größe");
			labelG.setHorizontalAlignment(JLabel.LEFT);
			labelG.setHorizontalTextPosition(JLabel.LEFT);
			labelG.setLabelFor(getFontGroessenAnzeige());
			
			fontGroessenPanel.add(labelG, BorderLayout.NORTH);
			fontGroessenPanel.add(panelG, BorderLayout.CENTER);
		}
		return fontGroessenPanel;
	}
	
	private JTextField getVorschauText() {
		if(vorschauText == null) {
			Border abgesenkt = BorderFactory.createLoweredBevelBorder();
			
			vorschauText = new JTextField("AaBbYyZz");
			vorschauText.setBorder(abgesenkt);
			vorschauText.setPreferredSize(new Dimension(300, 100));
		}
		return vorschauText;
	}
	
	private JPanel getVorschauPanel() {
		if(vorschauPanel == null) {
			Border titel = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Vorschau");
			Border leer = BorderFactory.createEmptyBorder(5, 5, 5, 5);
			Border rahmen = BorderFactory.createCompoundBorder(titel, leer);
			
			vorschauPanel = new JPanel();
			vorschauPanel.setLayout(new BorderLayout());
			vorschauPanel.setBorder(rahmen);
			vorschauPanel.add(getVorschauText(), BorderLayout.CENTER);
		}
		return vorschauPanel;
	}
	
	/* --- Aktionen & Listener --- */
	
	private class DialogOKAktion extends AbstractAction {
		
		// Die serielle Versionsnummer.
		private static final long serialVersionUID = 1L;

		private static final String ACTION_NAME = "OK";
		
		private JDialog dialog;
		
		protected DialogOKAktion(JDialog dialog) {
			this.dialog = dialog;
			putValue(Action.DEFAULT, ACTION_NAME);
			putValue(Action.ACTION_COMMAND_KEY, ACTION_NAME);
			putValue(Action.NAME, ACTION_NAME);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialogRueckgabe = OK;
			dialog.setVisible(false);
		}
	
	}
	
	private class DialogAbbrechenAktion extends AbstractAction {
		
		// Die serielle Versionsnummer.
		private static final long serialVersionUID = 1L;

		private static final String ACTION_NAME = "Abbrechen";
		
		private JDialog dialog;
		
		protected DialogAbbrechenAktion(JDialog dialog) {
			this.dialog = dialog;
			putValue(Action.DEFAULT, ACTION_NAME);
			putValue(Action.ACTION_COMMAND_KEY, ACTION_NAME);
			putValue(Action.NAME, ACTION_NAME);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialogRueckgabe = ABBRECHEN;
			dialog.setVisible(false);
		}
	}
	
	private class ListenAuswahlHandler implements ListSelectionListener {

		private JTextComponent komponent;
		
		protected ListenAuswahlHandler(JTextComponent komponent) {
			this.komponent = komponent;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting() == false) {
				JList<String> list = (JList<String>) e.getSource();
				String ausgewaehlt = list.getSelectedValue();
				
				String alt = komponent.getText();
				komponent.setText(ausgewaehlt);
				if(!alt.equalsIgnoreCase(ausgewaehlt)) {
					komponent.selectAll();
					komponent.requestFocus();
				}
				
				aktualisiereVorschau();
			}
		}
		
	}
	
	private class TextFeldFokusHandler extends FocusAdapter {
		
		private JTextComponent komponent;
		
		protected TextFeldFokusHandler(JTextComponent komponent) {
			this.komponent = komponent;
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			komponent.selectAll();
		}
		
		@Override
		public void focusLost(FocusEvent e) {
			komponent.select(0, 0);
			aktualisiereVorschau();
		}
	}
	
	private class TextFeldTastenHandler extends KeyAdapter {
		
		private JList<?> zielListe;
		
		public TextFeldTastenHandler(JList<?> liste) {
			this.zielListe = liste;
		}
		
		public void keyPressed(KeyEvent e) {
			int i = zielListe.getSelectedIndex();
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				i = zielListe.getSelectedIndex() - 1;
				if(i < 0)
					i = 0;
				zielListe.setSelectedIndex(i);
				break;
			case KeyEvent.VK_DOWN:
				i = zielListe.getSelectedIndex() + 1;
				if(i >= zielListe.getModel().getSize())
					i = zielListe.getModel().getSize() - 1;
				zielListe.setSelectedIndex(i);
				break;
			default:
				break;
			}
		}
	}
	
	private class ListenSucheHandler implements DocumentListener {

		private JList<?> zielListe;
		
		protected ListenSucheHandler(JList<?> liste) {
			this.zielListe = liste;
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			update(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			update(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			update(e);
		}
		
		private void update(DocumentEvent evt) {
			String neu = "";
			try {
				Document dokument = evt.getDocument();
				neu = dokument.getText(0, dokument.getLength());
			} catch(BadLocationException e) {
				e.printStackTrace();
			}
			
			if(neu.length() > 0) {
				int index = zielListe.getNextMatch(neu, 0, Position.Bias.Forward);
				if(index < 0)
					index = 0;
				zielListe.ensureIndexIsVisible(index);
				
				String uebereinstimmung = zielListe.getModel().getElementAt(index).toString();
				if(neu.equalsIgnoreCase(uebereinstimmung))
					if(index != zielListe.getSelectedIndex())
						SwingUtilities.invokeLater(new ListSelector(index));
			}
		}
		
		protected class ListSelector implements Runnable {
			
			private int index;
			
			protected ListSelector(int index) {
				this.index = index;
			}
			
			@Override
			public void run() {
				zielListe.setSelectedIndex(this.index);
			}
		}
		
	}

}