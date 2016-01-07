package drivetrain;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import utilities.PIDOutputDouble;
import utilities.PIDSourceDouble;
import utilities.Utilities;
import edu.wpi.first.wpilibj.PIDController;

public class GyroCorrectionDecorator extends DriveControlDecorator{
	private PIDSourceDouble shortestDistance;
	private PIDOutputDouble correctedX2;
	private DriveIO driveIO = DriveIO.getInstance();
	private double gyroSetpoint, x2Prev;
	private PIDController gyroPID;
	

	public GyroCorrectionDecorator(DriveControl driveControl) {
		super(driveControl);
		shortestDistance = new PIDSourceDouble(0);
		correctedX2 = new PIDOutputDouble();
		gyroPID = new PIDController(0.015, 0.0002, -0.001, 0, shortestDistance, correctedX2, 0.02);//i1
		gyroPID.setOutputRange(-0.25, 0.25);
		gyroPID.enable();

		//System.out.println("Gyro: " + DriveIO.getInstance().getYaw() + " Target: " + gyroSetpoint);
	}
	
	public void execute(double x1, double y1, double x2){
		if(driveIO.getX2() == 0){
			if(x2Prev != 0){
				gyroSetpoint = driveIO.getYaw();
			}
			else if(driveIO.getStart()){
				gyroSetpoint = 0;
				System.out.println("reset");
			}
			shortestDistance.setValue(Utilities.shortestDistanceAbsolute(driveIO.getYaw(), gyroSetpoint));
			x2 = (correctedX2.getValue() < 0.05 && correctedX2.getValue() > -0.05 ? 0 : correctedX2.getValue());
		}
		super.execute(x1,y1,x2);
		x2Prev = driveIO.getX2();
	}

}
