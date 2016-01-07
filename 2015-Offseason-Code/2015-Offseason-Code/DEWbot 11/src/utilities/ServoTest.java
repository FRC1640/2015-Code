package utilities;

import static constants.Constants.JS_BUTTON_B;

import constants.IOConstants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;

public class ServoTest extends Subsystem {
	
	private Servo TestServo = new Servo (IOConstants.SERVO);
	
	public void init() {
		
	}
	
	public void update() {
		Joystick driver = Controllers.getDriverJoystick();
		boolean bButton = driver.getRawButton(JS_BUTTON_B);
		
		if(bButton){
			TestServo.set(1);
		}
		
		else{
			TestServo.set(0);
		}
		
	}

}
