package org.usfirst.frc.team1640.robot;

public class DriveCore {
	DriveIO driveIO = new DriveIO();
	DriveInputs driveInputs = new DriveInputs();
	boolean isSpeedFull = false;
	boolean aPressed = false;
	double speed = Constants.SPEED;
	
	public void update() {
		if (driveInputs.getAButton() && !aPressed) {
			isSpeedFull = !isSpeedFull;
			aPressed = true;
		}
		else if (!driveInputs.getAButton()) aPressed = false;
		
		if (isSpeedFull) speed = 1.0;
		else speed = Constants.SPEED;
		
		double leftPower = driveInputs.getLeftAxis();
		double rightPower = driveInputs.getRightAxis();
		driveIO.setLeftMotor(leftPower * .35);
		driveIO.setRightMotor(rightPower * .35);
	}
}
