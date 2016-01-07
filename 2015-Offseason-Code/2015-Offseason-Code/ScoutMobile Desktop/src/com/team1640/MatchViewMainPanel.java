package com.team1640;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class MatchViewMainPanel extends JPanel implements TableModelListener{
	
	private static final long serialVersionUID = 1L;
	
	private ScoutMobile main;
	
	private DefaultTableModel matchViewTableModel;
	private JTable matchViewTable;
	private JLabel matchViewTitle;
	private final String [] columnNames = {"Color", "Team", "Cans", "Totes", "No Show?", "Scout"};
	private JScrollPane scrollPane;
	private int columns;
	
	public MatchViewMainPanel(ScoutMobile m){
		
		main = m;
		
		setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		setLayout(new BorderLayout());
		
		columns = columnNames.length;
		
		matchViewTableModel = new DefaultTableModel(new Object[0][columns], columnNames);
		
		matchViewTable = new JTable(matchViewTableModel);
		matchViewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		matchViewTable.setFont(new Font(matchViewTable.getName(), Font.PLAIN, 16));
		matchViewTable.setRowHeight(matchViewTable.getRowHeight()+5);
		
		scrollPane = new JScrollPane(matchViewTable);
		
		matchViewTitle =  new JLabel("Match", JLabel.LEFT);
		matchViewTitle.setFont(new Font(matchViewTitle.getName(), Font.PLAIN, 18));
		
		add(matchViewTitle, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		
		addRow("Blue", 1640, 4, 28, false, "Matt L.");
		addRow("Blue", 1640, 4, 28, false, "Matt L.");

	}

	public void addRow(String color, int team, int cans, int totes, boolean noShow, String scout) {
		Object[] row = {color, team, cans, totes, noShow, scout};
		if (matchViewTableModel.getRowCount() < 6) matchViewTableModel.addRow(row);
	}
	
	public void removeRow(int index) {
		if (matchViewTableModel.getRowCount() > 0) matchViewTableModel.removeRow(index);
	}
	
	public void setMatchNum(int matchNum) {
		matchViewTitle.setText("Match " + Integer.toString(matchNum));
	}

	@Override
	public void tableChanged(TableModelEvent event) {
		int row = event.getFirstRow();
		int column = event.getColumn();
		Object value = matchViewTable.getValueAt(row, column);
		matchViewTableModel.setValueAt(value, row, column);
	}
}
