package lift;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import utilities.MotionProfile;
import constants.Constants;

public class LiftHomingCore {
	
	private static LiftHomingCore liftHomingCore;
	
	private LiftIO liftIO = LiftIO.getInstance();
	private MotionProfile pathLeft = new MotionProfile(Constants.LIFT_LEFT_MOTOR_POS_ACCEL, Constants.LIFT_LEFT_MOTOR_NEG_ACCEL, Constants.LIFT_LEFT_MOTOR_POS_DECEL, Constants.LIFT_LEFT_MOTOR_NEG_DECEL, Constants.LIFT_LEFT_MOTOR_POS_VELOCITY, Constants.LIFT_LEFT_MOTOR_NEG_VELOCITY);
	private MotionProfile pathRight = new MotionProfile(Constants.LIFT_RIGHT_MOTOR_POS_ACCEL, Constants.LIFT_RIGHT_MOTOR_NEG_ACCEL, Constants.LIFT_RIGHT_MOTOR_POS_DECEL, Constants.LIFT_RIGHT_MOTOR_NEG_DECEL, Constants.LIFT_RIGHT_MOTOR_POS_VELOCITY, Constants.LIFT_RIGHT_MOTOR_NEG_VELOCITY);
	private int startLeftEncoder;
	private int startRightEncoder;
	private long startTime;
	private long currentTime;
	private int targetPos;
	private boolean enabled;
	
	private LiftHomingCore() { }
	
	public static void init() {
		if (liftHomingCore == null)
			liftHomingCore = new LiftHomingCore();
	}
	
	public static LiftHomingCore getInstance() {
		if (liftHomingCore != null)
			return liftHomingCore;
		
		else return liftHomingCore = new LiftHomingCore();
		
	}
	
	
	public void LiftHomingStart(int targetPosition) {
		System.out.println(currentTime);
		System.out.println(liftIO.getEncoderLeft());
		System.out.println(liftIO.getEncoderRight());
		if(liftHomingDone()){
			System.out.println("path was created" + targetPosition);
			targetPos = targetPosition;
			
			//get right and left encoder counts
			int leftEncoder = liftIO.getEncoderLeft();
			int rightEncoder = liftIO.getEncoderRight();
			
			//start motion profiling
			pathLeft.createPath(targetPosition - leftEncoder);
			pathRight.createPath(targetPosition - rightEncoder);
			
			//store start time
			if(Robot.getState() == State.AUTON || Robot.getState() == State.TELEOP){
				enabled = true;
				startTime = System.nanoTime() / 1000000;
			}
			
			//store start encoders
			startLeftEncoder = leftEncoder;
			startRightEncoder = rightEncoder;
		}
		
		else{
			System.out.println("Already going: " + targetPosition);
		}
		
		
	}
	
	public void update() {		
		//calculate current time
			if(enabled){
				currentTime = System.nanoTime() / 1000000 - startTime;
				
				//get current encoders
				int leftEncoder = liftIO.getEncoderLeft();
				int rightEncoder = liftIO.getEncoderRight();
				
				//calculate error for right and left motors
				double leftError = (pathLeft.getDistance(currentTime) + startLeftEncoder) - leftEncoder;
				double rightError = (pathRight.getDistance(currentTime) + startRightEncoder) - rightEncoder;
				
				double leftMaxVelocity;
				double rightMaxVelocity;
				
				//determine whether to use positive or negative max velocity
				if (targetPos - leftEncoder >= 0) leftMaxVelocity = Constants.LIFT_LEFT_MOTOR_POS_VELOCITY;
				else leftMaxVelocity = Constants.LIFT_LEFT_MOTOR_NEG_VELOCITY;
				
				if (targetPos - rightEncoder >= 0) rightMaxVelocity = Constants.LIFT_RIGHT_MOTOR_POS_VELOCITY; 
				else rightMaxVelocity = Constants.LIFT_RIGHT_MOTOR_NEG_VELOCITY; 
				
				//set left and right motors by feeding forward
				//Equation: 1/maxVelocity(velocity) + KA(acceleration) + KP(error)	
				liftIO.setMotorLeft( 0.4 / Math.abs(leftMaxVelocity) * pathLeft.getVelocity(currentTime) + Constants.HOMING_LIFT_KA * pathLeft.getAccel(currentTime) + Constants.HOMING_LIFT_KP * leftError );//input from motion profile
				liftIO.setMotorRight( 0.4 / Math.abs(rightMaxVelocity) * pathRight.getVelocity(currentTime) + Constants.HOMING_LIFT_KA * pathRight.getAccel(currentTime) + Constants.HOMING_LIFT_KP * rightError );//input from motion profile
			}
			else if(Robot.getState() == State.AUTON || Robot.getState() == State.TELEOP){
				enabled = true;
				startTime = System.nanoTime() / 1000000;
			}
	}
	
	public boolean liftHomingDone() {
		
		return pathLeft.pathDone(currentTime) && pathRight.pathDone(currentTime);
		
	}
	
	public void stopLiftHoming(){
		//create paths with distances of 0 to stop the lift
		pathLeft.createPath(0);
		pathRight.createPath(0);
	}

}
