package drivetrain;

import utilities.Subsystem;


public class DriveMaster extends Subsystem{
	private DriveCore driveCore = DriveCore.getInstance();
	private AntiTip antiTip;
	
	public void init(){
		DriveIO.init();
		DriveCore.init();
		antiTip = AntiTip.getInstance();
	}
	
	public void update() {
		antiTip.update();
		driveCore.update();
	}
}
