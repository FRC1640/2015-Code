package lift;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import constants.IOConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import utilities.Controllers;

public class LiftIO {
	//holds all input and output for the lift
	
	//singleton instance
	private static LiftIO liftIO;
	
	//controller variables
	private Joystick operator = Controllers.getOperatorJoystick();
	private boolean autonA = false;
	private boolean autonB = false;
	private int autonPOV = -1;
	
	//motor variables
	private Talon leftMotor;
	private Talon rightMotor;
	
	//sensor variables
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private DigitalInput leftLimit;
	private DigitalInput rightLimit;
	
	//this constructor is private so objects of LiftIO can only be created in this class
	private LiftIO(){
		//initialize all IO needed for lift. 
		//Left motor and right encoder must be negated for positive output to make the lift go up, with increasing encoders
		leftMotor = new Talon (IOConstants.LIFT_MOTOR_LEFT){
			@Override
			public void set (double speed){
				super.set(-speed);
			}
		};
		rightMotor = new Talon (IOConstants.LIFT_MOTOR_RIGHT);
		
		leftEncoder = new Encoder (IOConstants.ENCODER_LIFT_LEFT_A, IOConstants.ENCODER_LIFT_LEFT_B);
		rightEncoder = new Encoder (IOConstants.ENCODER_LIFT_RIGHT_A, IOConstants.ENCODER_LIFT_RIGHT_B, true);
		leftLimit = new DigitalInput (IOConstants.LIFT_SWITCH_LEFT);
		rightLimit = new DigitalInput (IOConstants.LIFT_SWITCH_RIGHT);
	}
	
	
	public static void init(){
		//if the singleton instance hasn't be created yet, then create it
		if(liftIO == null)
			liftIO = new LiftIO();
	}
	
	public static LiftIO getInstance(){
		//if the singleton has been created, return the instance
		if(liftIO != null)
			return liftIO;
		//otherwise, create the instance and return it
		else return liftIO = new LiftIO();
	}
	
	public boolean getA(){
		//return either the auton specified value, or the value of the controller depending on the state of the robot
		if(Robot.getState() == State.TELEOP)
			return operator.getRawButton(1);
		else if(Robot.getState() == State.AUTON){
			boolean temp = autonA;
			autonA = false;
			return temp;
		}
		else 
			return false;
	}
	
	public boolean getB(){
		//return either the auton specified value, or the value of the controller depending on the state of the robot
		if(Robot.getState() == State.TELEOP)
			return operator.getRawButton(2);
		else if(Robot.getState() == State.AUTON){
			boolean temp = autonB;
			autonB = false;
			return temp;
		}
		else 
			return false;
	}
	
	public int getPOV(){
		//return either the auton specified value, or the value of the controller depending on the state of the robot
		if(Robot.getState() == State.TELEOP)
			return operator.getPOV();
		else if(Robot.getState() == State.AUTON){
			int temp = autonPOV;
			autonPOV = -1;
			if(temp != -1){
				System.out.println("Temp: " + temp);
			}
			return temp;
		}
		else 
			return -1;
	}
	
	public void setA(boolean aButton){
		//in auton, set the value of the A button to be used
		if(Robot.getState() == State.AUTON)
			autonA = aButton;
	}
	
	public void setB(boolean bButton){
		//in auton, set the value of the B button to be used
		if(Robot.getState() == State.AUTON)
			autonB = bButton;
	}
	
	public void setPOV(int POV){
		//in auton, set the value of the POV to be used
		if(Robot.getState() == State.AUTON){
			autonPOV = POV;
			System.out.println(autonPOV);
		}
	}

	public boolean getLimitSwitchLeft(){
		//return the value of the left Limit Switch
		return !leftLimit.get();
	}
	
	public boolean getLimitSwitchRight(){
		//return the value of the right Limit Switch
		return !rightLimit.get();
	}
	
	public int getEncoderLeft(){
		//return the value of the left encoder
		return leftEncoder.get();
	}
	
	public int getEncoderRight(){
		//return the value of the right encoder
		return -rightEncoder.get();
	}
	
	public void setMotorLeft(double speed){
		//set a speed to the left lift motor
		leftMotor.set(speed);
	}
	
	public void setMotorRight(double speed){
		//set a speed to the right lift motor
		rightMotor.set(speed);
	}
	
	public void resetEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}

}
