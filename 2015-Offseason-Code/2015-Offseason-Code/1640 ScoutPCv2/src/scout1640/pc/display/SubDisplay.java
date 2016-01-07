package scout1640.pc.display;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import scout1640.pc.core.Controller;

public class SubDisplay extends JPanel {
	
	private static final long serialVersionUID = 8268649044465602426L;
	
	public static final int DISPLAY_LEFT = 0;
	public static final int DISPLAY_RIGHT = 1;
	public static final int DISPLAY_OPERATIONS = 2;
	public static final int DISPLAY_TEAMS = 3;
	public static final int DISPLAY_DATA = 4;
	public static final int DISPLAY_CONSOLE = 5;
	
	private int type;
	private Controller controller;
	private LinkedList<Container> additionalItems;
	
	private Dimension ps;
	
	private BufferedImage img;
	private static final String PANEL_DATA = "drawPanel";
	private static final String PANEL_IMG = "imgPanel";

	public SubDisplay (Controller c, int displayType, Dimension preferredSize) {
		// super((displayType == DISPLAY_OPERATIONS)?new FlowLayout() : new BorderLayout());
		super((displayType == DISPLAY_DATA) ? new CardLayout() : ((displayType == DISPLAY_OPERATIONS) ? new FlowLayout() : new BorderLayout()));
		additionalItems = new LinkedList<Container>();
		
		ps = preferredSize;
		
		controller = c;
		type = displayType;
		
		switch (displayType) {
		
			case DISPLAY_LEFT: { /* Intermediary, nothing to do here */ } break;
			
			case DISPLAY_RIGHT: { /* Intermediary, nothing to do here */ } break;
			
			case DISPLAY_OPERATIONS: {
				this.setPreferredSize(preferredSize);
				
				String[] buttons = {"Filter","Sort","Query"};
				for (String s : buttons) { additionalItems.add(new JButton(s)); }
				
				for (Container b : additionalItems) { 
					this.add(b);
					if (b instanceof JButton) {
						((JButton)(b)).addActionListener(new ActionListener() {
	
							@Override
							public void actionPerformed (ActionEvent e) {
								
								switch (((JButton)(b)).getText()) {
								
									case "Filter": {
										// TODO: Filtering
									} break;
									
									case "Sort": {
										// TODO: Sorting
									} break;
									
									case "Query": {
										// TODO: Stretch goal custom queries
										/*
										String query = JOptionPane.showInputDialog(null, "Enter custom SQL query");
										if (query == null) { return; }
										controller.getDisplay().getSubDisplay(DISPLAY_DATA).displayData(query);
										 */
									} break;
								
								}
								
							}
							
						});
					}
				}
			} break;
			
			case DISPLAY_TEAMS: {
				// TODO: Query database for `team number`
				JPanel teamPanel = new JPanel(new GridLayout(100,1));
				teamPanel.setBackground(Color.DARK_GRAY);
				JScrollPane jsp = new JScrollPane(teamPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				jsp.setPreferredSize(new Dimension((int) (preferredSize.getWidth()), (int) (preferredSize.getHeight())));
				jsp.setForeground(Color.BLACK);
				jsp.setBackground(Color.DARK_GRAY);
				additionalItems.add(teamPanel);
				additionalItems.add(jsp);
				this.add(jsp);
				firstUpdateTeamDisplay();
			} break;
			
			case DISPLAY_DATA: {
				JTextArea textArea = new JTextArea(0, 0);
				textArea.setBackground(Color.DARK_GRAY);
				textArea.setEditable(false);
				textArea.setForeground(Color.WHITE);
				textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
				JScrollPane jsp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				jsp.setPreferredSize(preferredSize);
				jsp.setBackground(Color.LIGHT_GRAY);
				jsp.setForeground(Color.DARK_GRAY);
				this.add(jsp, PANEL_DATA);
				additionalItems.add(jsp);
				additionalItems.add(textArea);
				
				JPanel imgPanel = new JPanel() {

					private static final long serialVersionUID = -939231153112533555L;
					
					@Override
					public void paintComponent (Graphics g) {
						Graphics2D g2 = (Graphics2D) g;
						g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), img.getWidth(), 0, 0, img.getHeight(), null);
						g2.dispose();
					}
					
				};
				this.add(imgPanel, PANEL_IMG);
				additionalItems.add(imgPanel);
			} break;
			
			case DISPLAY_CONSOLE: {
				JTextArea textArea = new JTextArea(0, 0);
				textArea.setBackground(Color.DARK_GRAY);
				textArea.setEditable(false);
				textArea.setForeground(Color.WHITE);
				JScrollPane jsp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jsp.setPreferredSize(preferredSize);
				jsp.setBackground(Color.BLACK);
				jsp.setForeground(Color.BLACK);
				this.add(jsp);
				additionalItems.add(jsp);
				additionalItems.add(textArea);
			} break;
		
		}
	}
	
	@Override
	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		switch (type) {
		
			case DISPLAY_LEFT: {
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				controller.getDisplay().getSubDisplay(DISPLAY_OPERATIONS).repaint();
				controller.getDisplay().getSubDisplay(DISPLAY_TEAMS).repaint();
			} break;
			
			case DISPLAY_RIGHT: {
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				controller.getDisplay().getSubDisplay(DISPLAY_DATA).repaint();
				controller.getDisplay().getSubDisplay(DISPLAY_CONSOLE).repaint();
			} break;
			
			case DISPLAY_OPERATIONS: {
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				for (Container c : additionalItems) { c.repaint(); }
			} break;
			
			case DISPLAY_TEAMS: {
				g2.setColor(Color.DARK_GRAY);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				for (Container c : additionalItems) { c.repaint(); }
			} break;
			
			case DISPLAY_DATA: {	
				g2.setColor(Color.DARK_GRAY);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				for (Container c : additionalItems) { c.repaint(); }
			} break;
			
			case DISPLAY_CONSOLE: {
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				for (Container c : additionalItems) { c.repaint(); }
			} break;
		
		}
		
		g2.dispose();
		
	}
	
	public void drawImage (BufferedImage img) {
		if (type != DISPLAY_DATA) { return; }
		this.img = img;
		((CardLayout)this.getLayout()).show(this, PANEL_IMG);
		repaint();
	}
	
	public void resetDataDisplay () {
		if (type != DISPLAY_DATA) { return; }
		((CardLayout)this.getLayout()).show(this, PANEL_DATA);
		repaint();
	}
	
	public void displayData (String query) {
		if (type != DISPLAY_DATA) { return; }
		ResultSet rs = controller.executeSQLQuery(query);
		if (rs == null) { return; }
		JTextArea jta = null; for (Container c : additionalItems) { if (c instanceof JTextArea) { jta = (JTextArea) c; } }
		if (jta == null) { return; }
		LinkedList<String> cols = new LinkedList<String>();
		int maxCol = 0;
		for (String s : controller.tableColumns(controller.getEventName())) { 
			if (query.contains(s) || query.contains("*")) { cols.add(s); } 
			if (s.length() > maxCol) { maxCol = s.length(); }
		}
		String text = "";
		String colRow = columnHeadings(maxCol+2, cols.toArray(new String[cols.size()]));
		text += colRow + "\n";
		for (int i = 0; i < colRow.length(); i++) { text += "-"; } text += "\n";
		try {
			while (rs.next()) {
				
			}
		} catch (SQLException e) {
			
		}
		jta.setText(text);
	}
	
	private String columnHeadings (int cellWidth, String[] cols) {
		String s = "";
		for (String c : cols) {
			int paddingNeeded = cellWidth - c.length();
			int leadPadding = paddingNeeded >> 1;
			int endPadding = paddingNeeded - leadPadding;
			s += "|";
			for (int i = 0; i < leadPadding; i++) { s += " "; }
			s += c;
			for (int i = 0; i < endPadding; i++) { s += " "; }
			s += "|";	
		}
		return s;
	}
	
	public void printToConsole (String s) {
		if (type != DISPLAY_CONSOLE) { return; }
		for (Container c : additionalItems) {
			if (!(c instanceof JTextArea)) { continue; }
			JTextArea jta = (JTextArea) c;
			String temp = jta.getText();
			if (temp == null) { jta.setText(s); }
			else { jta.setText(temp + s + "\n"); }
		}
	}
	
	public void clearConsole () {
		if (type != DISPLAY_CONSOLE) { return; }
		for (Container c : additionalItems) {
			if (!(c instanceof JTextArea)) { continue; }
			((JTextArea) c).setText("");
			
		}
	}
	
	public void updateTeamDisplay () {
		firstUpdateTeamDisplay();
		controller.getDisplay().repaint();
		controller.getFrame().pack();
		controller.getFrame().setVisible(true);
	}
	
	public void firstUpdateTeamDisplay () {
		if (type != DISPLAY_TEAMS) { return; }
		
		ResultSet rs = controller.executeSQLQuery("SELECT DISTINCT `team number` FROM `" + controller.getEventName() + "` ORDER BY `team number` ASC;");
		if (!(rs == null)) {
			JPanel panel = (JPanel) additionalItems.get(0);
			panel.removeAll();
			try {
				
				while (rs.next()) {
					JButton jb = new JButton("" + rs.getInt(1));
					jb.setPreferredSize(new Dimension(((int)(ps.getWidth())), ((int)(ps.getHeight() * .10))));
					jb.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							controller.getDisplay().getSubDisplay(SubDisplay.DISPLAY_DATA).displayData("SELECT * FROM `" + controller.getEventName() + "` ORDER BY `match number` ASC;");
						}
						
					});
					panel.add(jb);
				}
			} catch (SQLException e) {
				System.err.println("problem");
			}
		}
	}

}
