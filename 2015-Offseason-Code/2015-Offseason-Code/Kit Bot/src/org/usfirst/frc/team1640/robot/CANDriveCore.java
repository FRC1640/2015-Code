package org.usfirst.frc.team1640.robot;

public class CANDriveCore {
	CANDriveIO canDriveIO = CANDriveIO.getInstance();
	CANDriveInputs canDriveInputs = new CANDriveInputs();
	boolean isSpeedFull = false;
	boolean aPressed = false;
	double speed = Constants.SPEED;
	
	public void init() {
		canDriveIO.setStopMode(CANDriveIO.STOP_MODE_COAST);
		//canDriveIO.setStopMode(CANDriveIO.STOP_MODE_BRAKE);
	}
	
	public void update() {
		if (canDriveInputs.getAButton() && !aPressed) {
			isSpeedFull = !isSpeedFull;
			aPressed = true;
		}
		else if (!canDriveInputs.getAButton()) aPressed = false;
		
		if (isSpeedFull) speed = 1.0;
		
		else speed = Constants.SPEED;
		
		double leftPower = canDriveInputs.getLeftAxis();
		double rightPower = canDriveInputs.getRightAxis();
		canDriveIO.setLeftMotors(leftPower * speed);
		canDriveIO.setRightMotors(rightPower * speed);

	}
}