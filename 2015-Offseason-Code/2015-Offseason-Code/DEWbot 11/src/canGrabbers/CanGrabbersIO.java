package canGrabbers;

import static constants.Constants.JS_BUTTON_A;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import utilities.Controllers;
import utilities.SonarInches;
import constants.IOConstants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

public class CanGrabbersIO {
	
	private Solenoid CanGrabbers = new Solenoid (IOConstants.CAN_GRABBERS);
	private Joystick driver = Controllers.getDriverJoystick();
	private boolean autonA;
	private static CanGrabbersIO canGrabbersIO;	
	private SonarInches sonar = new SonarInches(IOConstants.LEFT_SONAR);
	
	private CanGrabbersIO(){}
	
	public static CanGrabbersIO getInstance(){
		if(canGrabbersIO == null)
			canGrabbersIO = new CanGrabbersIO();
		return canGrabbersIO;
	}
	
	public void setCanGrabbers(){
				
		if (getA()){
			CanGrabbers.set(true);
		}
		
		else{
			CanGrabbers.set(false);	
		}
		
		//System.out.println("*" + sonar.getInches());
		
	}
	
	private boolean getA(){
		if(Robot.getState() == State.TELEOP)
			return driver.getRawButton(JS_BUTTON_A);
		return autonA;
		
	}
	
	public void setA(boolean autonA){
		if(Robot.getState() == State.AUTON)
			this.autonA = autonA;
	}
}
