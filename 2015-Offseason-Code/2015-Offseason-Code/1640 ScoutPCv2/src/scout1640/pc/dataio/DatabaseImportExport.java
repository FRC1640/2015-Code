package scout1640.pc.dataio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.json.JSONObject;
import org.json.JSONWriter;

import scout1640.pc.core.Controller;
import scout1640.pc.display.SelectionFrame;

public class DatabaseImportExport {
	
	public static void importDBStage1 (Controller c) {
		
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("ScoutData.smpc"));
		switch (fc.showOpenDialog(null)) {
		
			case JFileChooser.APPROVE_OPTION: {
				File f = fc.getSelectedFile();
				
				if (!f.exists()) { JOptionPane.showMessageDialog(null, "Invalid file: " + f.getName(), "ScoutMobile PC Import", JOptionPane.ERROR_MESSAGE); return; }
				
				try {
					
					Scanner sc = new Scanner(f);
					JSONObject jo = null;
					if (sc.hasNext()) { jo = new JSONObject(sc.nextLine()); }
					sc.close();
					
					if (jo == null) { JOptionPane.showMessageDialog(null, "Empty file", "ScoutMobile PC Import", JOptionPane.ERROR_MESSAGE); sc.close(); return; }
					
					String[] tables = JSONObject.getNames(jo);
					
					new SelectionFrame("Select Tables to Import", tables, c, jo);
					
				} catch (FileNotFoundException e) {
					System.err.println("Non-existant file"); return;
				}
				
			} break;
			
			case JFileChooser.CANCEL_OPTION: {
				
			} break;
			
			case JFileChooser.ERROR_OPTION: {
				
			} break;
		
		}
		
	}
	
	public static void importDBStage2 (Controller c, String[] selectedTables, JSONObject jo) {
		for (String s : selectedTables) {
			String newName = s;
			String prev = "";
			while (c.searchTables(newName)) {
				prev = newName;
				newName = (String) JOptionPane.showInputDialog(null, "Existing table \"" + newName + "\" WILL be overwritten, type a new name if you don't want to overwrite your existing data\n"
						+ "", "Rename incoming data table", 
						JOptionPane.WARNING_MESSAGE, null, null, newName);
				if (newName.equals("")) { newName = prev; }
				else if (newName.equals(prev)) { break; }
			}
			JSONObject eo = jo.getJSONObject(s);
			if (c.searchTables(newName)) { if (!c.executeDataAlteringSQLQuery("DROP TABLE `" + newName + "`;")) { c.printToConsole("Unable to delete existing table `" + newName + "`"); return; } }
			if (DatabaseImportExport.createAndPopulateTable(newName, MatchData.matchDataFromJSONSet(c, eo), c)) { c.printToConsole("Successfully created and populated table `" + newName + "`"); }
		}
	}
	
	private static boolean createAndPopulateTable (String tableName, MatchData[] eventData, Controller c) {
		if (!c.createTable(tableName)) { c.printToConsole("Unable to create table `" + tableName + "`"); }
		for (MatchData md : eventData) {
			if (!c.executeDataAlteringSQLQuery(md.generateSQLInsertQuery(tableName))) { c.printToConsole("Unable to insert row"); return false; }
		}
		return true;
	}
	
	public static void exportDB (Controller c) {
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("ScoutData.smpc"));
		switch (fc.showSaveDialog(null)) {
		
			case JFileChooser.APPROVE_OPTION: {
				File f = fc.getSelectedFile();
				try {
					
					if (f.exists() && !f.delete()) { c.printToConsole("Unable to create file"); return; } 
					
					if (!f.createNewFile()) { c.printToConsole("Unable to create file"); return; }
					
					ResultSet rs;
					StringWriter sw = new StringWriter();
					JSONWriter jw = new JSONWriter(sw);
					ArrayList<String> columns = new ArrayList<String>();
					// package tables into JSON strings
					
					jw.object();
					for (String t : c.getTables()) {
						rs = c.executeSQLQuery("DESCRIBE `" + t + "`;");
						while (rs.next()) {
							columns.add(rs.getString(1));
						}
						
						jw.key(t).object();
						
						rs = c.executeSQLQuery("SELECT * FROM `" + t + "`;");
						while (rs.next()) {
							jw.key("record_" + rs.getInt("uid")).object();
							for (String col : columns) {
								jw.key(col);
								if (col.equals("event") || col.equals("scout initials")) { jw.value(rs.getString(col)); } 
								else { jw.value(rs.getInt(col)); }
							}
							jw.endObject();
						}
						
						jw.endObject();
						
						columns.clear();
					}
					jw.endObject();
					
					PrintWriter p = new PrintWriter(f);
					p.print(sw.toString());
					p.flush();
					p.close();
					
					c.printToConsole("Export of \"" + f.getAbsolutePath() + "\" complete");
					
				} catch (IOException e) {
					System.err.println("Unknown file error");
				} catch (SQLException e) {
					System.err.println("Error parsing table");
				}
			} break;
			
			case JFileChooser.CANCEL_OPTION: {
				
			} break;
			
			case JFileChooser.ERROR_OPTION: {
				
			} break;
			
		}
	}
	
	public static void exportCSV (Controller c, String csv) {
		
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File(c.getEventName() + ".csv"));
		
		switch (fc.showSaveDialog(null)) {
		
			case JFileChooser.APPROVE_OPTION: {
				
				File f = fc.getSelectedFile();
				
				try {
				
					if (f.exists() && !f.delete()) { c.printToConsole("Unable to create file"); return; } 
					
					if (!f.createNewFile()) { c.printToConsole("Unable to create file"); return; }
					
					PrintWriter pw = new PrintWriter(f);
					pw.print(csv);
					pw.flush();
					pw.close();
					
					c.printToConsole("Exported file: " + f.getAbsolutePath());
					
				} catch (IOException e) {
					c.printToConsole("Unknown file error");
				} 
				
			} break;
			
			case JFileChooser.CANCEL_OPTION: {
				
			} break;
			
			case JFileChooser.ERROR_OPTION: {
				
			} break;
		
		}
		
	}

}
