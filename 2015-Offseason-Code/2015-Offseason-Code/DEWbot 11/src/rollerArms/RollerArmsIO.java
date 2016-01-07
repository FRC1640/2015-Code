package rollerArms;

import static constants.Constants.JS_AXIS_LEFT_TRIGGER;
import static constants.Constants.JS_BUTTON_L;
import static constants.Constants.JS_BUTTON_R;
import static constants.IOConstants.ROLLER_MOTOR_LEFT;
import static constants.IOConstants.ROLLER_MOTOR_RIGHT;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import constants.IOConstants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import utilities.Controllers;

public class RollerArmsIO {
	
	private Solenoid rollerArms = new Solenoid (IOConstants.ROLLER_SOLENOID);
	private boolean armToggle = false;
	private boolean darmToggle = false;
	private Talon rightRoller;
	private Talon leftRoller;
	private static RollerArmsIO rollerArmsIO;
	private Joystick operator = Controllers.getOperatorJoystick();
	private boolean autonLeftTrigger;
	private boolean autonLeftBumper;
	private boolean autonRightBumper;

	
	public static RollerArmsIO getInstance() {
		
		if(rollerArmsIO == null)
			rollerArmsIO = new RollerArmsIO();
		return rollerArmsIO;
		
	}
	
	private RollerArmsIO(){
		rightRoller = new Talon(ROLLER_MOTOR_RIGHT);
		leftRoller = new Talon(ROLLER_MOTOR_LEFT);
	}
	
	
	public void setRollerArms(){
		
		//Toggling in and out
		if (!getLeftTrigger()){
			darmToggle = true;
		}
		
		if(darmToggle && getLeftTrigger()){
			darmToggle = false;
			armToggle = !armToggle;
		}
		
		
		if(armToggle || getLeftBumper() || getRightBumper()){
			rollerArms.set(true);
		}
		else{
			rollerArms.set(false);
		}
		
		//Set direction of intake wheels
		if(getLeftBumper()){
			//In
			rightRoller.set(-1.0);
			leftRoller.set(1.0);
		}
		else if(getRightBumper()){
			//Out
			rightRoller.set(1.0);
			leftRoller.set(-1.0);
			//System.out.println("true");
		}
		else{
			//Stopped
			rightRoller.set(0);
			leftRoller.set(0);
		}
		
		
		
	}
	
	private boolean getLeftTrigger() {
		if (Robot.getState() == State.TELEOP)
		return operator.getRawAxis(JS_AXIS_LEFT_TRIGGER) >= .95;
		else return autonLeftTrigger;
	}
	
	public void setLeftTrigger(boolean autonLeftTrigger) {
		if (Robot.getState() == State.AUTON)
			this.autonLeftTrigger = autonLeftTrigger;
		System.out.println("Left Trigger: " + this.autonLeftTrigger);

	}
	
	private boolean getLeftBumper() {
		if (Robot.getState() == State.TELEOP)
		return operator.getRawButton(JS_BUTTON_L);
		else return autonLeftBumper;
	}
	
	public void setLeftBumper(boolean autonLeftBumper) {
		if (Robot.getState() == State.AUTON)
			this.autonLeftBumper = autonLeftBumper;
	}
	
	public boolean getRightBumper() {
		if (Robot.getState() == State.TELEOP)
		return operator.getRawButton(JS_BUTTON_R);
		else return autonRightBumper;
	}
	
	public void setRightBumper(boolean autonRightBumper) {
		if (Robot.getState() == State.AUTON)
			this.autonRightBumper = autonRightBumper;
	}

}
