package com.team1640;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ScoutMobile extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//State Variables
	private int view;
	
	//View States
	private final int MATCH_VIEW = 0;
	private final int TEAM_VIEW = 1;
	
	//Panels
	public MatchView matchView;
	public TeamView teamView;
	
	//Menu Bar
	private MenuBar menuBar;
	
	//Scanning Frame
	public QRScan qrScan;
	
	public static void main(String[] args) throws InterruptedException{
		new ScoutMobile();
	}
	
	private ScoutMobile() throws InterruptedException {
		
		// Set Frame Title
		super("ScoutMobile Desktop");
		
		// Set Frame Properties
		setProperties();
		
		// Add elements
		createMenuBar();
		createView();
		
		// Refresh
		refresh();
	}
	
	private void setProperties() {
		view = MATCH_VIEW;
	
		// Set Look and Feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		
		// Set Other Properties
		setVisible(true);
		setSize(800, 600);
		setExtendedState(MAXIMIZED_BOTH);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("img/icon.png").getImage());
	}
	
	private void createMenuBar() {
		if (menuBar == null) menuBar = new MenuBar(this);
		setJMenuBar(menuBar);
	}
	
	private void createView() {
		if (matchView == null) matchView = new MatchView(this);
		if (teamView == null) teamView = new TeamView(this);
		if (view == TEAM_VIEW) {
			getContentPane().add(teamView);
		} else if (view == MATCH_VIEW){
			getContentPane().add(matchView);
		}
	}
	
	private void refresh() {
		revalidate();
		repaint();
	}

	public void switchViews() {
		// Switch views
		if (view == TEAM_VIEW) { 		//Team View
			view = MATCH_VIEW;
			getContentPane().remove(teamView);
			getContentPane().add(matchView);
		}
		else if (view == MATCH_VIEW) { 	//Match View
			view = TEAM_VIEW;
			getContentPane().remove(matchView);
			getContentPane().add(teamView);
		}
		refresh();
	}
	
	public void startScanning() {
		if (qrScan == null) {
			qrScan = new QRScan(this);
			qrScan.init();
		}
		else if(qrScan.closed) qrScan.init();
	}
}