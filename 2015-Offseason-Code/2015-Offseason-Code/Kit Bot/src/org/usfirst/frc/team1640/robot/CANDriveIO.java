package org.usfirst.frc.team1640.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class CANDriveIO {
	private static CANDriveIO canDriveIO = new CANDriveIO();
	public static final boolean STOP_MODE_BRAKE = true, STOP_MODE_COAST = false;
	
	private static CANTalon leftMotorMaster, leftMotorSlave, rightMotorSlave, rightMotorMaster;
	
	
	/*Front Motor is Master
	 * Back Motor is Slave 
	 */
	private CANDriveIO(){
		leftMotorMaster = new CANTalon(3);
		leftMotorMaster.setPID(0.0, 0, 0, 0, 2, 0, 0);
		
		leftMotorSlave = new CANTalon(4);
		leftMotorSlave.changeControlMode(CANTalon.ControlMode.Follower);
		leftMotorSlave.set(leftMotorMaster.getDeviceID());
		
		rightMotorMaster = new CANTalon(2);
		rightMotorMaster.setPID(0.0, 0, 0, 0, 2, 0, 0);
		
		rightMotorSlave = new CANTalon(1);
		rightMotorSlave.changeControlMode(CANTalon.ControlMode.Follower);
		rightMotorSlave.set(rightMotorMaster.getDeviceID());
	}
	
	public void setLeftMotors (double speed) {
		if(speed >= -1 && speed <= 1) {
			leftMotorMaster.set(speed);
			return;
		} else if(speed < -1) {
			leftMotorMaster.set(-1);		
		} else {
			leftMotorMaster.set(1);
		}
	}
	
	public void setRightMotors (double speed) {
		speed *= -1;
		if(speed >= -1 && speed <= 1) {
			rightMotorMaster.set(speed);
			return;
		} else if(speed < -1) {
			rightMotorMaster.set(-1);
		} else {
			rightMotorMaster.set(1);
		}
	}
	
	public void setStopMode(boolean brake_mode) {
		rightMotorMaster.enableBrakeMode(brake_mode);
		leftMotorMaster.enableBrakeMode(brake_mode);
	}
	
	public static CANDriveIO getInstance() {
		return canDriveIO;
	}
}
