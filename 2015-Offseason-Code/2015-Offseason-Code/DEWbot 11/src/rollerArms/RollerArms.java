package rollerArms;

import utilities.Subsystem;

public class RollerArms extends Subsystem{
	private RollerArmsIO rollerArmsIO = RollerArmsIO.getInstance();
	
	public void init() { }
	
	public void update(){
		
		rollerArmsIO.setRollerArms();
		
	}
}
