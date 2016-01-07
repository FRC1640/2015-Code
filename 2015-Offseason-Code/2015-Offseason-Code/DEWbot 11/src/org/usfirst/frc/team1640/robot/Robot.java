
package org.usfirst.frc.team1640.robot;


import lift.Lift;
import lift.LiftHoming;
import lift.LiftIO;
import rollerArms.RollerArms;
import utilities.Controllers;
import utilities.ServoTest;
import utilities.SonarInches;
import auton.Auton;
import auton.ScriptSelector;
import canBapper.CanBapper;
import canGrabbers.CanGrabbers;
import constants.IOConstants;
import drivetrain.DriveIO;
import drivetrain.DriveMaster;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
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
	
	public static enum State { DISABLED, AUTON, TELEOP };
	
	private static State state, pState;


	private double delay;
	private double startTime;
	private DriveMaster drive;
	private CanGrabbers canGrabbers;
	private RollerArms rollerArms;
	private Auton auton;
	private static LiftHoming liftHoming;
	private static Lift lift;
	private static ServoTest servoTest;
	private static CanBapper canBapper;
	private SonarInches sonarInches;
	private int i;
	private boolean init = false;
	private DriveIO driveIO;
	
  
    public Robot() {
    	state = pState = State.DISABLED;
    	Controllers.initIO();
    	LiftIO.init();
    	//LiftRead.init();
    	//LiftStates.init();
    	drive = new DriveMaster();
    	drive.start(20);
    	canGrabbers = new CanGrabbers();
    	canGrabbers.start(20);
    	rollerArms = new RollerArms();
    	rollerArms.start(20);
 	   	//lift = new Lift();
 	   	//lift.start(20);
 	   	canBapper = new CanBapper();
 	   	canBapper.start(20);
 	   	liftHoming = new LiftHoming();
 	   	liftHoming.start(20);
 	   	sonarInches = new SonarInches(IOConstants.LEFT_SONAR);
    	//auton = new Auton();
    	//auton.start(20);
    	//servoTest = new ServoTest();
    	//servoTest.start(20);
    	Controllers.initIO();
    	driveIO = DriveIO.getInstance();
    	
    }
    
    public void robotMain(){
    	while (true) {
    		startTime = (double) System.nanoTime() / 1000000000;
    	
    		if (super.isAutonomous() && super.isEnabled()){
    			state = State.AUTON;
    			if (state != pState) { 
    				
    				//for(Pivot p : Controllers.getPivots()){ p.enable(); }
    				//LiftCore.update();
    				//LiftHome.update();
    			}
        	}
        	else if (super.isOperatorControl() && super.isEnabled()){
        		state = State.TELEOP;
        		if (state != pState) { 
        			//for(Pivot p : Controllers.getPivots()){ p.enable(); }
        		}
        		//DriveCore.update();
        		//LiftCore.update();
        		//LiftStates.update();
        		//LiftRead.update();
        		/*if(!liftHome.getHomed()){
        			liftHome.update();
        		}
        		else{ LiftStates.update(); }*/
        		//RollerArms.update();
        		//CanGrabbers.update();
        		
        		
        	}
        	else {
        		state = State.DISABLED;
    			//ScriptSelector.getInstance().update();
        		if (state != pState) { 
        			

        			//for(Pivot p : Controllers.getPivots()){ p.disable(); } 
        		}
        	}
    		pState = state;
    		    		
    		delay = 0.02 - ((double) System.nanoTime() / 1000000000 - startTime);
    		delay = delay <= 0 ? 0 : delay;
    		Timer.delay(delay);
    		i++;
    		//if (i%100 == 0) {System.out.println(delay);}
    		if(i > 100 && !init){
    	    	
    	    	init = true;
    		}
    	//	System.out.println(sonarInches.getInches());
    		//System.out.println("Gyro: " + driveIO.getYaw());
    		if(Controllers.getDriverJoystick().getRawButton(2)){
    			System.out.println("Resetting Gyro");
    			driveIO.resetGyro();
    		}
    		
    	}
    	
    }
   public static State getState(){
	   return state;
   }
   public static void startLift(){
	   System.out.println("lift was started");
	   liftHoming.stop();
	   lift = new Lift();
	   lift.start(20);
   }
    
}
