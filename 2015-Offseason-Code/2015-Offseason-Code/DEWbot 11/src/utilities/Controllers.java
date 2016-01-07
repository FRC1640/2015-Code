package utilities;

import static constants.IOConstants.JOYSTICK_DRIVER;
import static constants.IOConstants.JOYSTICK_OPERATOR;
import static utilities.Utilities.deadband;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {
	private static Controllers io = null; 
	
	private Joystick driver;
	private Joystick operator;
	
	private Controllers(){
		driver = new Joystick(JOYSTICK_DRIVER){
			@Override
			public double getRawAxis(int axis){
				if (axis == 0 || axis == 1) {
					return Utilities.deadband2(super.getRawAxis(0), super.getRawAxis(1), 0.23)[axis];
				} else if (axis == 4 || axis == 5) {
					return Utilities.deadband2(super.getRawAxis(4), super.getRawAxis(5), 0.23)[axis-4];
				} else {
					return deadband(super.getRawAxis(axis), 0.23);
				}
			}
		};
		operator = new Joystick(JOYSTICK_OPERATOR);
	}
		
	public static void initIO (){
		if (io == null)
			io = new Controllers();
		
	}
	
	public static Joystick getDriverJoystick(){
		return io.driver;
	}
	
	public static Joystick getOperatorJoystick(){
		return io.operator;
	}

}

