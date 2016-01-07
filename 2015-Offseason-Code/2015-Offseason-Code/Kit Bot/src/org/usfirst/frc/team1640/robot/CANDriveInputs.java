package org.usfirst.frc.team1640.robot;

import edu.wpi.first.wpilibj.Joystick;

public class CANDriveInputs {
	Joystick joy;

	final int LEFT_AXIS_Y = 1;
	final int RIGHT_AXIS_Y = 5;
	
	public CANDriveInputs() {
		joy = new Joystick(0);
	}
	
	public boolean getAButton() {
		return joy.getRawButton(1);
	}
	
	public double getLeftAxis() {
		return -joy.getRawAxis(LEFT_AXIS_Y);
	}
	
	public double getRightAxis() {
		return -joy.getRawAxis(RIGHT_AXIS_Y);
	}
}
