package scout1640.pc.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import scout1640.pc.core.Controller;

public class Display extends JPanel {
	
	private static final long serialVersionUID = -8159111308931097861L;
	
	private static final double PERCENTAGE_LEFT = .20;
	private static final double PERCENTAGE_OPERATION = .05;
	private static final double PERCENTAGE_CONSOLE = .20;
	
	public static final int DISPLAY_WIDTH = 1024;
	public static final int DISPLAY_HEIGHT = 768;
	
	// private final Display self = this;
	
	private SubDisplay  leftDisplay,
				rightDisplay,
				operationDisplay,
				teamDisplay,
				dataDisplay,
				consoleDisplay;
	
	private JPanel dataPanel;

	public Display (Controller c) {
		super(new BorderLayout());
		
		dataPanel = new JPanel(new BorderLayout()) {
			
			private static final long serialVersionUID = -110572615030780328L;

			@Override
			public void paintComponent (Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				leftDisplay.repaint();
				rightDisplay.repaint();
				g2.dispose();
			}
			
		};
		
		int leftWidth = (int) (DISPLAY_WIDTH * PERCENTAGE_LEFT);
		int leftHeight = DISPLAY_HEIGHT;
		
		int rightWidth = DISPLAY_WIDTH - leftWidth;
		int rightHeight = DISPLAY_HEIGHT;
		
		int operationWidth = leftWidth;
		int operationHeight = (int) (leftHeight * PERCENTAGE_OPERATION);
		
		int teamWidth = leftWidth;
		int teamHeight = leftHeight - operationHeight;
		
		int consoleWidth = rightWidth;
		int consoleHeight = (int) (rightHeight * PERCENTAGE_CONSOLE);
		
		int dataWidth = rightWidth;
		int dataHeight = rightHeight - consoleHeight;
		
		leftDisplay = new SubDisplay(c, SubDisplay.DISPLAY_LEFT, new Dimension(leftWidth, leftHeight));
		
		operationDisplay = new SubDisplay(c, SubDisplay.DISPLAY_OPERATIONS, new Dimension(operationWidth, operationHeight));
		leftDisplay.add(operationDisplay, BorderLayout.NORTH);
		
		teamDisplay = new SubDisplay(c, SubDisplay.DISPLAY_TEAMS, new Dimension(teamWidth, teamHeight));
		leftDisplay.add(teamDisplay, BorderLayout.SOUTH);
		
		dataPanel.add(leftDisplay, BorderLayout.WEST);
		
		rightDisplay = new SubDisplay(c, SubDisplay.DISPLAY_RIGHT, new Dimension(rightWidth, rightHeight));
		
		dataDisplay = new SubDisplay(c, SubDisplay.DISPLAY_DATA, new Dimension(dataWidth, dataHeight));
		rightDisplay.add(dataDisplay, BorderLayout.NORTH);
		
		consoleDisplay = new SubDisplay(c, SubDisplay.DISPLAY_CONSOLE, new Dimension(consoleWidth, consoleHeight));
		rightDisplay.add(consoleDisplay, BorderLayout.SOUTH);
		
		dataPanel.add(rightDisplay, BorderLayout.EAST);
		
		c.getFrame().add(this);
		c.getFrame().pack();
		
		this.add(dataPanel);
		
	}
	
	public void drawImage (BufferedImage img) {
		dataDisplay.drawImage(img);
	}
	
	public void resetDrawMode () {
		dataDisplay.resetDataDisplay();
	}
	
	public SubDisplay getSubDisplay (int subDisplay) {
		
		switch (subDisplay) {
		
			case SubDisplay.DISPLAY_LEFT: return leftDisplay;
				
			case SubDisplay.DISPLAY_RIGHT: return rightDisplay;
				
			case SubDisplay.DISPLAY_OPERATIONS: return operationDisplay;
				
			case SubDisplay.DISPLAY_TEAMS: return teamDisplay;
				
			case SubDisplay.DISPLAY_DATA: return dataDisplay;
				
			case SubDisplay.DISPLAY_CONSOLE: return consoleDisplay;
			
			default: return null;
		
		}
		
	}

}
