package drivetrain;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;


public class DriveCore{
	
	private static DriveCore driveCore = new DriveCore();
	
	private DriveIO driveIO = DriveIO.getInstance();
	private boolean fieldCentric = false;
	private boolean antiTip = false;
	private boolean backIsDown = false;
	private boolean antiTipPrev, swervePrev, fieldCentricPrev, slowPrev, autonPrev = false;
	private double targetAngle, targetAnglePrev;
	private	DriveControl driveFunct = new SwerveDriveControl();
	
	private DriveCore(){driveIO.resetGyro(); }
	
	public static DriveCore init() {
		//if the singlecton class hasn't been created yet, create one
		if (driveCore == null) driveCore = new DriveCore();
		return driveCore;
	}
	
	public static DriveCore getInstance() {
		//if the singleton instance hasn't been created yet, create one
		if (driveCore == null) driveCore = new DriveCore();
		return driveCore;
	}
	
	public void setAntiTip(boolean antitip) {
		antiTip = antitip;
	}
	
	public void setTarget(double target){
		this.targetAngle = target;
	}
	
	private void isFieldCentric() {
		if (driveIO.getBack() && !backIsDown) { //BACK button on the remote
			driveIO.resetGyro();
			fieldCentric = !fieldCentric;
			backIsDown = true;
		}
		if (driveIO.getStart() ) driveIO.resetGyro();
		if(!driveIO.getBack() ) backIsDown = false;
	}
	
	public void update(){
		isFieldCentric(); //update field centric and gyro variables
		boolean swerve = !antiTip && Robot.getState() == State.TELEOP;
		boolean auton = Robot.getState() == State.AUTON && !antiTip;


		if(antiTip && !antiTipPrev){
			driveFunct = new AntiTipDriveControl();//if we are tipping, initalize it with anti tip
			System.out.println("Anti Tip");
		}
		else if(auton && !autonPrev){
			driveFunct = new GyroCorrectionDecorator(new FieldCentricDecorator(new SwerveDriveControl()));
		}
		else if(swerve && !swervePrev){
			driveFunct = new SlowDecorator(new SwerveDriveControl()); //if swerve, initialize with swerve
			System.out.println("Added Swerve");
		}
		if(swerve){	
			if(fieldCentric && !fieldCentricPrev) driveFunct = new FieldCentricDecorator(driveFunct); //if we want field centric, reinitalize with field centric decorator
			if(fieldCentricPrev && !fieldCentric){ //falling edge, field centric off
				driveFunct = new SlowDecorator(new SwerveDriveControl()); //remove all decorators
			}
		}	
		
		
		driveFunct.execute(driveIO.getX1(), -driveIO.getY1(), driveIO.getX2()); 
		autonPrev = auton;
		antiTipPrev = antiTip;
		swervePrev = swerve;
		fieldCentricPrev = fieldCentric;
	}
}
