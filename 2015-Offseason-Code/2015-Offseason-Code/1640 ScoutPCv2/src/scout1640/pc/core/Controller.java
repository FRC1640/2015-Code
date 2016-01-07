package scout1640.pc.core;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import scout1640.pc.dataio.DatabaseImportExport;
import scout1640.pc.dataio.MatchData;
import scout1640.pc.dataio.ReadQR;
import scout1640.pc.display.Display;
import scout1640.pc.display.ScoutMenuBar;
import scout1640.pc.display.SubDisplay;

public class Controller {
	
	public static final String VERSION_NUMBER = "0.8.3";
	
	public static final int MAIN_SCREEN = 0;
	public static final int READ_QR = 1;
	public static final int ADD_NEW_EVENT = 2;
	public static final int RELOAD_EVENT = 3;
	public static final int IMPORT_DATASET = 4;
	public static final int EXPORT_DATASET = 5;
	public static final int EXPORT_CSV = 6;
	public static final int QUIT = 7;
	
	private int state;
	private JFrame frame;
	private Display display;
	private Connection sqlConnection;
	private String eventName;
	
	private LinkedList<ThreadKiller> activeThreadedClasses;
	private LinkedList<String> existingEvents;
	
	public Controller () {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/scoutdb", "root", "root");
		} catch (SQLException e) {
			System.err.println("More Database Problems");
			e.printStackTrace();
			System.exit(0);
		} catch (ClassNotFoundException e) {
			System.err.println("Database Problems");
			e.printStackTrace();
			System.exit(0);
		}
		
		activeThreadedClasses = new LinkedList<ThreadKiller>();
		existingEvents = new LinkedList<String>();
		eventName = "test event";
		
		populateExistingEvents();
		if (!this.searchTables(eventName)) { this.createTable(eventName); }
		
		frame = new JFrame("1640 ScoutPC " + VERSION_NUMBER + " - test event");
		display = new Display(this);
		
		new ScoutMenuBar(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		setState(MAIN_SCREEN);
		
		setState(RELOAD_EVENT);
	}
	
	public String[] tableColumns (String tableName) {
		try {
			ResultSet rs = sqlConnection.createStatement().executeQuery("DESCRIBE `" + tableName + "`;");
			LinkedList<String> columnNames = new LinkedList<String>();
			while (rs.next()) { columnNames.add(rs.getString(1)); }
			return columnNames.toArray(new String[columnNames.size()]);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public void printToConsole (String s) {
		this.getDisplay().getSubDisplay(SubDisplay.DISPLAY_CONSOLE).printToConsole(s);
	}
	
	public void clearConsole () {
		this.getDisplay().getSubDisplay(SubDisplay.DISPLAY_CONSOLE).clearConsole();
	}
	
	public String[] getTables () {
		return existingEvents.toArray(new String[existingEvents.size()]);
	}
	
	private void populateExistingEvents () {
		try {
			ResultSet rs = sqlConnection.createStatement().executeQuery("show tables");
			while (rs.next()) {
				existingEvents.add(rs.getString(1));
			}
		} catch (SQLException e) {
			System.err.println("Unable to load list of events");
		}
	}
	
	public boolean searchTables (String tableName) {
		for (String s : existingEvents) { if (s.equals(tableName)) { return true; } }
		return false;
	}
	
	public void setState (int newState) { 
		switch (newState) {
			case MAIN_SCREEN: {
				for (ThreadKiller k : activeThreadedClasses) { if (k instanceof ReadQR) { k.killThread(); } }
				display.resetDrawMode();
			} break;
			
			case READ_QR: {
				activeThreadedClasses.add(new ReadQR(this));
			} break;
			
			case ADD_NEW_EVENT: {
				String event = (String) JOptionPane.showInputDialog(null, "Add new event", 
						"Add Event Pane", JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (event != null) {
					if (this.searchTables(event)) {
						this.eventName = event;
						frame.setTitle("1640 ScoutPC " + VERSION_NUMBER + " - " + event);
					} else {
						if (this.createTable(event)) { 
							this.eventName = event;
							frame.setTitle("1640 ScoutPC - " + event);
						} else {
							System.err.println("Unable to add event");
						}
					}
				}
				setState(MAIN_SCREEN);
			} break;
			
			case RELOAD_EVENT: {
				String event = (String) JOptionPane.showInputDialog(null, "Select an event",
						"Select Event Pane", JOptionPane.PLAIN_MESSAGE, null,
						existingEvents.toArray(new String[existingEvents.size()]), null);
				
				if (event != null) { eventName = event; frame.setTitle("1640 ScoutPC - " + event); }
				setState(MAIN_SCREEN);
			} break;
			
			case IMPORT_DATASET: {
				// DatabaseImportExport.importDBStage1(this);
				setState(MAIN_SCREEN);
			} break;
			
			case EXPORT_DATASET: {
				DatabaseImportExport.exportDB(this);
				setState(MAIN_SCREEN);
			} break;
			
			case EXPORT_CSV: {
				MatchData[] matches = MatchData.matchDataFromRecord(this, this.getEventName());
				String outputCSV = matches[0].getCSVHeadings();
				for (MatchData m : matches) { outputCSV += m.generateCSVLine(); }
				DatabaseImportExport.exportCSV(this, outputCSV);
			} break;
			
			case QUIT: {
				for (ThreadKiller tk : activeThreadedClasses) { tk.killThread(); }
				try { sqlConnection.close(); } 
				catch (SQLException e) { System.err.println("Problem Closing Connection. Whatever."); }
				System.exit(0);
			} break;
			
			default: {
				this.setState(MAIN_SCREEN);
			} break;
		}
		
		state = newState;
	}
	
	public int getState () { return state; }
	
	public void setMenuBar (JMenuBar mb) { frame.setJMenuBar(mb); }
	
	public void displayImage (BufferedImage img) { display.drawImage(img); }
	
	public int getPanelWidth () { return display.getWidth(); }
	
	public int getPanelHeight () { return display.getHeight(); }
	
	public void removeThreadedClass (ThreadKiller tk) { activeThreadedClasses.remove(tk); }
	
	public String getEventName () { return eventName; }
	
	public Display getDisplay () { return display; }
	
	public JFrame getFrame () { return frame; }
	
	public ResultSet executeSQLQuery (String query) {
		try {
			return sqlConnection.createStatement().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean executeDataAlteringSQLQuery (String query) {
		try { sqlConnection.createStatement().executeUpdate(query); return true; } 
		catch (SQLException e) { System.err.println("Unable to insert into database."); e.printStackTrace(); return false; }
	}
	
	public boolean createTable (String tableName) {
		String query = "CREATE TABLE `" + tableName + "` ("
			+	"`uid` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
			+	"`event` VARCHAR(45),"
			+	"`team number` INT(11),"
			+	"`match number` INT(11),"
			+	"`scout initials` VARCHAR(20),"
			+	"`match type` INT(11),"
			+	"`alliance station` INT(11),"
			+	"`played` INT(11),"
			+	"`auton stack` INT(11),"
			+	"`auton data` INT(11),"
			+	"`teleop data` INT(11),"
			+	"`final score` INT(11),"
			+	"`final auton score` INT(11),"
			+	"`final tote score` INT(11),"
			+	"`final can score` INT(11),"
			+	"`final noodle score` INT(11),"
			+	"`ranking` INT(11),"
			+	"`average` INT(11),"
			+	"`stack count` INT(11),"
			+	"`stack 1` INT(11),"
			+	"`stack 2` INT(11),"
			+	"`stack 3` INT(11),"
			+	"`stack 4` INT(11),"
			+	"`stack 5` INT(11),"
			+	"`stack 6` INT(11),"
			+	"`stack 7` INT(11),"
			+	"`stack 8` INT(11),"
			+	"`stack 9` INT(11),"
			+	"`stack 10` INT(11)"
			+	");";
		try {
			sqlConnection.createStatement().executeUpdate(query);
			existingEvents.add(tableName);
			return true;
		} catch (SQLException e) {
			System.err.println("Unable to create table");
			e.printStackTrace();
			return false;
		}
	}

}
