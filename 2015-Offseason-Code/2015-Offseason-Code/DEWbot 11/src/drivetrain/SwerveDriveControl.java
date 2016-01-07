package drivetrain;

public class SwerveDriveControl implements DriveControl {
	public void execute(double x1, double y1, double x2){
		ocelot(x1, y1, x2);
	}
}
