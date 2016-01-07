package org.usfirst.frc.team1640.robot;

public class Auton {
	CANDriveIO canDriveIO = CANDriveIO.getInstance();
	//CANDriveCore canDriveCore = new CANDriveCore();
	long startTime;
	
	public void init() {
		startTime = System.nanoTime();
		canDriveIO.setStopMode(CANDriveIO.STOP_MODE_BRAKE);
	}
	
	public void update() {
		long currentTime = System.nanoTime();
		if((currentTime - startTime)/1000000000 < 5){
			canDriveIO.setRightMotors(0.5);
			canDriveIO.setLeftMotors(0.5);
		} 
		else {
			canDriveIO.setRightMotors(0);
			canDriveIO.setLeftMotors(0);
			System.out.println("Done");
		}
	}
}
