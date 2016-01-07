package com.team1640;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TeamView extends JPanel {

	private static final long serialVersionUID = 1L;

	private ScoutMobile main;

	public TeamViewSidePanel teamViewSidePanel;
	public TeamViewMainPanel teamViewMainPanel;
	public JSplitPane splitPane;

	public TeamView(ScoutMobile m) {

		main = m;

		setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		setLayout(new BorderLayout(5, 5));

		teamViewSidePanel = new TeamViewSidePanel(main);
		teamViewSidePanel.setMinimumSize(new Dimension(0, 0));
		teamViewMainPanel = new TeamViewMainPanel(main);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				teamViewSidePanel, teamViewMainPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}