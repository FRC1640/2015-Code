package scout1640.pc.display;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.json.JSONObject;

import scout1640.pc.core.Controller;
import scout1640.pc.dataio.DatabaseImportExport;

public class SelectionFrame extends JFrame {
	
	private static final long serialVersionUID = 1880185103410585910L;
	
	private LinkedList<JCheckBox> checkBoxList;
	
	private SelectionFrame self = this;
	
	private LinkedList<String> selectedOptions;
	
	public SelectionFrame (String title, String[] options, Controller c, JSONObject jo) {
		super(title);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		selectedOptions = new LinkedList<String>();
		
		checkBoxList = new LinkedList<JCheckBox>();
		JPanel checkBoxPanel = new JPanel(new GridLayout(0,1));
		for (String s : options) { 
			JCheckBox jcb = new JCheckBox(s);
			checkBoxList.add(jcb);
			checkBoxPanel.add(jcb);
		}
		
		JScrollPane jsp = new JScrollPane(checkBoxPanel);
		jsp.setPreferredSize(null);
		
		mainPanel.add(jsp, BorderLayout.NORTH);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				self.setEnabled(false);
				self.setVisible(false);
				for (JCheckBox jcb : checkBoxList) { if (jcb.isSelected()) { selectedOptions.add(jcb.getText()); } }
				DatabaseImportExport.importDBStage2(c, selectedOptions.toArray(new String[selectedOptions.size()]), jo);
			}
			
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				self.setEnabled(false);
				self.setVisible(false);
				checkBoxList = null;
			}
			
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel);
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
}
