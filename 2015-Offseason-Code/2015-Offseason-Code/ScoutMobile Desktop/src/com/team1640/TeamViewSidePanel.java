package com.team1640;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TeamViewSidePanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private ScoutMobile main;
	
	private DefaultListModel<String> teamViewListModel;
	private JList<String> teamViewList;
	private JScrollPane teamViewScrollPane;
	private JPanel teamViewListPanel;
	private JButton addFilterButton;
	private JButton insertFilterButton;
	private JButton removeFilterButton;
	private GridBagConstraints c;
	private GridBagConstraints c2;
	
	TeamViewSidePanel(ScoutMobile m){
		main = m;
		
		setBackground(new Color(64, 64, 128));
		setPreferredSize(new Dimension(480, 0));
		setLayout(new GridBagLayout());
		
		teamViewListModel = new DefaultListModel<String>();
		
		teamViewList = new JList<String>(teamViewListModel);
		
		teamViewScrollPane = new JScrollPane(teamViewList);
		
		c = new GridBagConstraints();
		c2 = new GridBagConstraints();
		
		teamViewListPanel = new JPanel();
		
		addFilterButton = new JButton("Add Filter");
		addFilterButton.addActionListener(this);
		addFilterButton.setIcon(new ImageIcon());
		addFilterButton.setIconTextGap(25);
		addFilterButton.setBackground(new Color(160, 160, 192));
		addFilterButton.setToolTipText("Add Filter to End");
		
		insertFilterButton = new JButton("Insert Filter");
		insertFilterButton.addActionListener(this);
		insertFilterButton.setIcon(new ImageIcon());
		insertFilterButton.setIconTextGap(25);
		insertFilterButton.setBackground(new Color(160, 160, 192));
		insertFilterButton.setToolTipText("Insert Filter at Selected Index");
		
		removeFilterButton = new JButton("Remove Filter");
		removeFilterButton.addActionListener(this);
		removeFilterButton.setIcon(new ImageIcon());
		removeFilterButton.setIconTextGap(25);
		removeFilterButton.setBackground(new Color(160, 160, 192));
		removeFilterButton.setToolTipText("Remove Selected Filter");
		
		teamViewListPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.33;
		c.weighty = 1.0;
		teamViewListPanel.add(addFilterButton, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.33;
		c.weighty = 1.0;
		teamViewListPanel.add(insertFilterButton, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.33;
		c.weighty = 1.0;
		teamViewListPanel.add(removeFilterButton, c);
		
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.anchor = GridBagConstraints.SOUTH;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.weightx = 1.0;
		c2.weighty = 0.01;
		add(teamViewListPanel, c2);
		
		c2.fill = GridBagConstraints.BOTH;
		c2.anchor = GridBagConstraints.FIRST_LINE_START;
		c2.gridx = 0;
		c2.gridy = 1;
		c2.weightx = 1.0;
		c2.weighty = 0.9;
		add(teamViewScrollPane, c2);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String name = event.getActionCommand();
		int selected = teamViewList.getSelectedIndex();
		
		if (name.equals("Add Filter")){
			
		} else if (name.equals("Insert Filter") && selected >= 0) {
			
		} else if (name.equals("Remove Filter") && selected >= 0) {
			
		}
	}
}
