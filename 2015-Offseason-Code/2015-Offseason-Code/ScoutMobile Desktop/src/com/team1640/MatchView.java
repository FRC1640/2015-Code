package com.team1640;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MatchView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private ScoutMobile main;
	
	public MatchViewSidePanel matchViewSidePanel;
	public MatchViewMainPanel matchViewMainPanel;
	private JSplitPane splitPane;

	public MatchView(ScoutMobile m) {
		
		main = m;
		
		setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		setLayout(new BorderLayout(5, 5));
		
		matchViewSidePanel = new MatchViewSidePanel(main);
		matchViewSidePanel.setMinimumSize(new Dimension(0, 0));
		matchViewMainPanel = new MatchViewMainPanel(main);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, matchViewSidePanel, matchViewMainPanel);
		add(splitPane, BorderLayout.CENTER);
	}
}
