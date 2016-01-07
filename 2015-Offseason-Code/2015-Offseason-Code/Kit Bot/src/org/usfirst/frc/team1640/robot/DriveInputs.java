package org.usfirst.frc.team1640.robot;

import edu.wpi.first.wpilibj.Joystick;

public class DriveInputs {
	
	Joystick joy;

	final int LEFT_AXIS_Y = 1;
	final int RIGHT_AXIS_Y = 5;
	
	public DriveInputs() {
		joy = new Joystick(0);
	}
	
	public boolean getAButton() {
		return joy.getRawButton(1);
	}
	
	public double getLeftAxis() {
		return joy.getRawAxis(5);
	}
	
	public double getRightAxis() {
		return -joy.getRawAxis(1);
	}
}
