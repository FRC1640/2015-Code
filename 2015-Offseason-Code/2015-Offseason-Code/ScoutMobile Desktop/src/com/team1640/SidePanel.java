package com.team1640;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SidePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private ScoutMobile main;
	
	public DefaultListModel<String> matchViewListModel;
	
	private JList<String> matchViewList;
	private JScrollPane matchViewScrollPane;
	private JPanel matchViewListPanel;
	private JButton addMatchButton;
	private JButton insertMatchButton;
	private JButton removeMatchButton;

	public SidePanel(ScoutMobile m) {
		
		main = m;
		
		setBackground(new Color(64, 64, 128));
		setPreferredSize(new Dimension(480,0));
		setLayout(new GridBagLayout());
		
		matchViewListModel = new DefaultListModel<String>();
		
		matchViewList = new JList<String>(matchViewListModel);
		
		matchViewScrollPane = new JScrollPane(matchViewList);
		
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints c2 = new GridBagConstraints();
		
		matchViewListPanel = new JPanel();
		
		addMatchButton = new JButton("Add Match");
		addMatchButton.addActionListener(this);
		addMatchButton.setIcon(new ImageIcon("img/add.png"));
		addMatchButton.setIconTextGap(25);
		addMatchButton.setBackground(new Color(160, 160, 192));
		addMatchButton.setToolTipText("Add Match to End");
		
		insertMatchButton = new JButton("Insert Match");
		insertMatchButton.addActionListener(this);
		insertMatchButton.setIcon(new ImageIcon("img/insert.png"));
		insertMatchButton.setIconTextGap(25);
		insertMatchButton.setBackground(new Color(160, 160, 192));
		addMatchButton.setToolTipText("Insert Match at Selected Index");
		
		removeMatchButton = new JButton("Remove Match");
		removeMatchButton.addActionListener(this);
		removeMatchButton.setIcon(new ImageIcon("img/remove.png"));
		removeMatchButton.setIconTextGap(25);
		removeMatchButton.setBackground(new Color(160, 160, 192));
		removeMatchButton.setToolTipText("Remove Selected Match");
		
		matchViewListPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		matchViewListPanel.setLayout(new GridBagLayout());
		matchViewListPanel.setBackground(new Color(64, 64, 128));
		matchViewListPanel.setMaximumSize(new Dimension(380, 80));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.33;
		c.weighty = 1.0;
		matchViewListPanel.add(addMatchButton, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.33;
		c.weighty = 1.0;
		matchViewListPanel.add(insertMatchButton, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.33;
		c.weighty = 1.0;
		matchViewListPanel.add(removeMatchButton, c);
		
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.anchor = GridBagConstraints.SOUTH;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.weightx = 1.0;
		c2.weighty = 0.01;
		add(matchViewListPanel, c2);
		
		c2.fill = GridBagConstraints.BOTH;
		c2.anchor = GridBagConstraints.FIRST_LINE_START;
		c2.gridx = 0;
		c2.gridy = 1;
		c2.weightx = 1.0;
		c2.weighty = 0.9;
		add(matchViewScrollPane, c2);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String name = event.getActionCommand();
		Event currentEvent = main.getEvent();
		
		MatchData matchDataR1 = new MatchData();
		matchDataR1.set(1640, MatchData.TEAM_NUM);
		matchDataR1.set((int) (Math.random()*100), MatchData.CANS);
		matchDataR1.set(16, MatchData.TOTES);
		matchDataR1.set(true, MatchData.IS_PRESENT);
		matchDataR1.set("Matt", MatchData.SCOUT_NAME);
		
		MatchData matchDataR2 = new MatchData();
		matchDataR2.set((int) (Math.random()*1000), MatchData.TEAM_NUM);
		matchDataR2.set(3, MatchData.CANS);
		matchDataR2.set(17, MatchData.TOTES);
		matchDataR2.set(true, MatchData.IS_PRESENT);
		matchDataR2.set("Vineeth", MatchData.SCOUT_NAME);
		
		MatchData matchDataR3 = new MatchData();
		matchDataR3.set(1080, MatchData.TEAM_NUM);
		matchDataR3.set(0, MatchData.CANS);
		matchDataR3.set(0, MatchData.TOTES);
		matchDataR3.set(false, MatchData.IS_PRESENT);
		matchDataR3.set("Matt", MatchData.SCOUT_NAME);
		
		MatchData matchDataB1 = new MatchData();
		matchDataB1.set(1640, MatchData.TEAM_NUM);
		matchDataB1.set(2, MatchData.CANS);
		matchDataB1.set(16, MatchData.TOTES);
		matchDataB1.set(true, MatchData.IS_PRESENT);
		matchDataB1.set("Matt", MatchData.SCOUT_NAME);

		MatchData matchDataB2 = new MatchData();
		matchDataB2.set(1640, MatchData.TEAM_NUM);
		matchDataB2.set(2, MatchData.CANS);
		matchDataB2.set(16, MatchData.TOTES);
		matchDataB2.set(true, MatchData.IS_PRESENT);
		matchDataB2.set("Matt", MatchData.SCOUT_NAME);

		MatchData matchDataB3 = new MatchData();
		matchDataB3.set(1640, MatchData.TEAM_NUM);
		matchDataB3.set(2, MatchData.CANS);
		matchDataB3.set(16, MatchData.TOTES);
		matchDataB3.set(true, MatchData.IS_PRESENT);
		matchDataB3.set("Matt", MatchData.SCOUT_NAME);
		
		Match newMatch = new Match(currentEvent.numOfMatches());
		newMatch.setData(Match.RED_1, matchDataR1);
		newMatch.setData(Match.RED_2, matchDataR2);
		newMatch.setData(Match.RED_3, matchDataR3);
		newMatch.setData(Match.BLUE_1, matchDataB1);
		newMatch.setData(Match.BLUE_2, matchDataB2);
		newMatch.setData(Match.BLUE_3, matchDataB3);
		
		if (name.equals("Add Match")) {
			
			currentEvent.addMatch(newMatch);
			main.updateMatchListModel(currentEvent.numOfMatches());
			
			int size = matchViewListModel.size();
			matchViewList.setSelectedIndex(size-1);
			matchViewList.ensureIndexIsVisible(size-1);
			main.startScanning();
		}
		else if (name.equals("Insert Match")) {
			int selected = matchViewList.getSelectedIndex();
			if (selected >= 0) {
				
				currentEvent.insertMatch(selected, newMatch);
				main.updateMatchListModel(currentEvent.numOfMatches());
				
				matchViewList.setSelectedIndex(selected);
				matchViewList.ensureIndexIsVisible(selected);
				main.startScanning();
			}
		}
		else if (name.equals("Remove Match")) {
			int selected = matchViewList.getSelectedIndex();
			if (selected >= 0) {
				
				currentEvent.removeMatch(selected);
				main.updateMatchListModel(currentEvent.numOfMatches());
				
				matchViewList.setSelectedIndex(selected);
				matchViewList.ensureIndexIsVisible(selected);
			}
		}
		else if (name.equals("Match List")) {
			System.out.println(matchViewList.getSelectedValue());
		}
	}
}
