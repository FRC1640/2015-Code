package utilities;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RobotTable {
	
	public static  NetworkTable table;
	
	public void init() {
		table = NetworkTable.getTable("autontable");
		table.putString("AutonomousSelection", "default");
		
		
	}
	
	public NetworkTable getInstance() {
		if(table == null) {
			init();
		}
		return table;
	}
	
	public static String getState(){ 
		 String autonSelection = table.getString("AutonomousSelection");
		 
		 return autonSelection;
	}

}
