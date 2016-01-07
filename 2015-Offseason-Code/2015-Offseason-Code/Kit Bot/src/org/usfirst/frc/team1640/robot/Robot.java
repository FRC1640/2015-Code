
package org.usfirst.frc.team1640.robot;

import edu.wpi.first.wpilibj.SampleRobot;



/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
	
	int mode;
	int prevMode;
	
	CANDriveCore canDriveCore = new CANDriveCore();
	AutonScript auton = new Script1();
	
	final int MODE_DISABLED = 0;
	final int MODE_AUTONOMOUS = 1;
	final int MODE_DRIVEROP = 2;
	
	public Robot() {
		mode = 0;
		prevMode = 0;
	}
	
	@Override
	public void robotMain() {
		while(true) {
			if(isAutonomous() && isEnabled()) {
				mode = MODE_AUTONOMOUS;
				if(prevMode != MODE_AUTONOMOUS) auton.init();
				else {while(!auton.update() && mode == MODE_AUTONOMOUS);}
			} else if (isOperatorControl() && isEnabled()) {
				mode = MODE_DRIVEROP;
				if(prevMode != MODE_DRIVEROP) canDriveCore.init();
				canDriveCore.update();
			} else {
				mode = MODE_DISABLED;
			}
			prevMode = mode;
		}
	}
}
