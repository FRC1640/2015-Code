package canBapper;

import static constants.Constants.JS_BUTTON_B;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import constants.IOConstants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import utilities.Controllers;

public class CanBapperIO {
	
	private Solenoid CanBapper = new Solenoid (IOConstants.CAN_BAPPER);
	private Joystick driver = Controllers.getDriverJoystick();
	private boolean autonB;
	private static CanBapperIO canBapperIO;
	
	private CanBapperIO() {}
		public static CanBapperIO getInstance(){
			if(canBapperIO == null)
				canBapperIO = new CanBapperIO();
			return canBapperIO;
			
		}
	
	
		
	public void setCanBapper() {
		
		if (getA()){
			CanBapper.set(true);
			
		}
		else {
			CanBapper.set(false);
		}
		
	}
	
	private boolean getA() {
		if(Robot.getState() == State.TELEOP)
			return driver.getRawButton(JS_BUTTON_B);
		return autonB;
	}
	
	public void setB(boolean autonB) {
		if(Robot.getState() == State.AUTON)
			this.autonB = autonB;
	}

}
