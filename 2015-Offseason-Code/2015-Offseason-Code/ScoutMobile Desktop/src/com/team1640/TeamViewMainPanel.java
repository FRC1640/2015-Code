package com.team1640;

import java.awt.BorderLayout;
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

public class TeamViewMainPanel extends JPanel implements TableModelListener{

	private ScoutMobile main;
	
	private DefaultTableModel teamViewTableModel;
	private JTable teamViewTable;
	private JLabel teamViewTableLabel;
	private final String [] columnNames = {"Team", "Cans", "Totes", "Score"};
	private JScrollPane scrollPane;
	private int columns;
	private String teamViewTableTitle;
	
	public TeamViewMainPanel(ScoutMobile m) {
		main = m;
		
		setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		setLayout(new BorderLayout());
		
		columns = columnNames.length;
		
		teamViewTableModel = new DefaultTableModel(new Object[0][columns], columnNames);
		
		teamViewTable = new JTable(teamViewTableModel);
		teamViewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		teamViewTable.setFont(new Font(teamViewTable.getName(), Font.PLAIN, 16));
		teamViewTable.setRowHeight(teamViewTable.getRowHeight() + 5);
		
		scrollPane = new JScrollPane(teamViewTable);
		
		teamViewTableLabel = new JLabel("Table Title"); //TODO add in way to get title
		teamViewTableLabel.setFont(new Font(teamViewTableLabel.getName(), Font.PLAIN, 18));
		
		add(teamViewTableLabel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		
		addRow(1640, 4.1, 34.6, 300.01);
	}
	
	public void addRow(int team, double cans, double totes, double score){
		Object[] row = {team, cans, totes, score};
	}
	public void removeRow(){
		
	}
	public void setTeamViewTableTitle(String tableTitle){
		teamViewTableTitle = tableTitle;
	}
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
}