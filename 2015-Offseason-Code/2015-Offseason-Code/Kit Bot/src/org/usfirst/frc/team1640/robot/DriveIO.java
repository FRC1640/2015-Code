package org.usfirst.frc.team1640.robot;

import edu.wpi.first.wpilibj.Victor;

public class DriveIO {
	private Victor leftMotorF, leftMotorB, rightMotorB, rightMotorF;
	
	public DriveIO () {
		leftMotorF = new Victor(0);
		leftMotorB = new Victor(1);
		rightMotorF = new Victor(2);
		rightMotorB = new Victor(3);
	}
	
	public void setLeftMotor(double speed) {
		if(speed >= -1 && speed <= 1) {
			leftMotorF.set(speed);
			leftMotorB.set(speed);
			return;
		} else if(speed < -1) {
			leftMotorF.set(-1);
			leftMotorB.set(-1);
		} else {
			leftMotorF.set(1);
			leftMotorB.set(1);
		}
	}
	
	public void setRightMotor(double speed) {
		if(speed >= -1 && speed <= 1) {
			rightMotorF.set(speed);
			rightMotorB.set(speed);
			return;
		} else if(speed < -1) {
			rightMotorF.set(-1);
			rightMotorB.set(-1);
		} else {
			rightMotorF.set(1);
			rightMotorB.set(1);
		}
	}
	
	
	
}
