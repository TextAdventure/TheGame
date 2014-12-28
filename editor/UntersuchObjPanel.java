package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import game.Ort;
import game.UntersuchbaresObjekt;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Teil des OrtDialogs. Zweites Panel, das die M�glichkeit bietet, untersuchbare Objekte zu adden, zu bearbeiten
 * und zu entfernen. Jede �nderung, die in diesem Panel vorgenommen wird, wird sofort im bearbeiteten Ort
 * gespeichert. Es gibt keine M�glichkeit, die Ergbnisse abzuholen.
 * 
 * @author Felix
 *
 */
public class UntersuchObjPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Vector<UntersuchbaresObjekt> objekte;
	private JList<String> list;
	private DefaultListModel<String> model;
	
	private JButton add = new JButton("neu");
	private JButton remove = new JButton("entfernen");
	private JButton modify = new JButton("�ndern");
	
	private JPanel dialog;
	private JTextField name = new JTextField();
	private JTextArea desription = new JTextArea();
	
	private Ort ort;
	
	UntersuchObjPanel(UntersuchbaresObjekt[] obj, Ort ort) {
		setLayout(new BorderLayout());
		this.ort = ort;
		
		//List
		objekte = new Vector<UntersuchbaresObjekt>();
		model = new DefaultListModel<String>();		
		for(UntersuchbaresObjekt o : obj) {
			objekte.add(o);
			model.addElement(o.getName());		
		}
		list = new JList<String>(model);
		
		JScrollPane scroll = new JScrollPane(list);
		add(scroll, BorderLayout.CENTER);
		
		
		//Buttons
		add.addActionListener(this);
		remove.addActionListener(this);
		modify.addActionListener(this);
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p.add(add);
		p.add(remove);
		p.add(modify);
		add(p, BorderLayout.SOUTH);
		
		
		//prepare create-new-dialog
		dialog = new JPanel(new BorderLayout());
		p = new JPanel(new BorderLayout());
		p.add(new JLabel("Bezeichnung:"), BorderLayout.WEST);
		p.add(name, BorderLayout.CENTER);
		dialog.add(p, BorderLayout.NORTH);
		p = new JPanel(new BorderLayout());
		p.add(new JLabel("Beschreibung:"), BorderLayout.NORTH);
		scroll = new JScrollPane(desription);
		scroll.setPreferredSize(new Dimension(500, 200));
		p.add(scroll, BorderLayout.CENTER);
		dialog.add(p, BorderLayout.CENTER);
						
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == add) {
			name.setText("");
			desription.setText("");
			int result = JOptionPane.showConfirmDialog(this, dialog, "Untersuchb. Obj.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.OK_OPTION) {
				UntersuchbaresObjekt obj = new UntersuchbaresObjekt(name.getText(), desription.getText());
				objekte.add(obj);
				model.addElement(obj.getName());
				ort.addUntersuchbaresObjekt(obj);
			}
			
			
		} else if(src == remove) {
			for(int i : list.getSelectedIndices()) {
				model.remove(i);
				ort.removeUntersuchbaresObjekt(objekte.remove(i));				
			}
				
			
		} else if(src == modify) {
			int index = list.getSelectedIndex();
			UntersuchbaresObjekt obj = objekte.get(index);
			name.setText(obj.getName());
			desription.setText(obj.getBeschreibung());
			
			int result = JOptionPane.showConfirmDialog(this, dialog, "Untersuchb. Obj.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.OK_OPTION) {
				obj.setName(name.getText());
				obj.setBeschreibung(desription.getText());
				model.remove(index);
				model.add(index, name.getText());
			}
		}
		
	}
}