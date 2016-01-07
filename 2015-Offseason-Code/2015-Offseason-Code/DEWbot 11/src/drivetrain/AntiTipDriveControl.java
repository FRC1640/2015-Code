package drivetrain;

public class AntiTipDriveControl implements DriveControl {
	//this is a form of drive control that will be called when we start tipping
	
	public void execute(double x1, double y1, double x2){
		ocelot(0, -1, 0); //ignore the inputs and run with anti-tip values
	}
}
