package lift;

import utilities.MotionProfile;
import constants.Constants;
//start and update motion profiling and relay the profile to the lift motors
public class LiftCore {
	//this class is in charge of creating and reading from the motion profiles from the lift
	
	//singleton instance
	private static LiftCore liftCore;
	
	private LiftIO liftIO = LiftIO.getInstance();
	private MotionProfile pathLeft = new MotionProfile(Constants.LIFT_LEFT_MOTOR_POS_ACCEL, Constants.LIFT_LEFT_MOTOR_NEG_ACCEL, Constants.LIFT_LEFT_MOTOR_POS_DECEL, Constants.LIFT_LEFT_MOTOR_NEG_DECEL, Constants.LIFT_LEFT_MOTOR_POS_VELOCITY, Constants.LIFT_LEFT_MOTOR_NEG_VELOCITY);
	private MotionProfile pathRight = new MotionProfile(Constants.LIFT_RIGHT_MOTOR_POS_ACCEL, Constants.LIFT_RIGHT_MOTOR_NEG_ACCEL, Constants.LIFT_RIGHT_MOTOR_POS_DECEL, Constants.LIFT_RIGHT_MOTOR_NEG_DECEL, Constants.LIFT_RIGHT_MOTOR_POS_VELOCITY, Constants.LIFT_RIGHT_MOTOR_NEG_VELOCITY);
	private int startLeftEncoder;
	private int startRightEncoder;
	private long startTime;
	private long currentTime;
	private int targetPos;
	private int i;
	private double leftErrorSum, rightErrorSum;
	private boolean first;
	//the constructor is private for the singleton
	private LiftCore(){	
		
	}
	
	public static void init(){
		//if the singleton instance has not already been initialized, initialize it
		if(liftCore == null)
			liftCore = new LiftCore();
	}
	
	public static LiftCore getInstance(){
		//if the singleton instance has been initialized, return it
		if(liftCore != null)
			return liftCore;
		//otherwise, initialize it and return it
		else return liftCore = new LiftCore();
	}
	
	public void liftStart(int targetPosition){
		//only accept new positions when lift is not moving
		if(liftDone()){
			targetPos = targetPosition;
			
			//get right and left encoder counts
			int leftEncoder = liftIO.getEncoderLeft();
			int rightEncoder = liftIO.getEncoderRight();
			
			//start motion profiling
			pathLeft.createPath(targetPosition - leftEncoder);
			pathRight.createPath(targetPosition - rightEncoder);
			
			//store start time
			startTime = System.nanoTime() / 1000000;
			
			//store start encoders
			startLeftEncoder = leftEncoder;
			startRightEncoder = rightEncoder;
			
			leftErrorSum = rightErrorSum = 0;
		}
	}
	
	public void update(){
		//calculate current time
		currentTime = System.nanoTime() / 1000000 - startTime;
		
		//get current encoders
		int leftEncoder = liftIO.getEncoderLeft();
		int rightEncoder = liftIO.getEncoderRight();
		
		//calculate error for right and left motors
		double leftError = (pathLeft.getDistance(currentTime) + startLeftEncoder) - leftEncoder;
		double rightError = (pathRight.getDistance(currentTime) + startRightEncoder) - rightEncoder;
		
		double leftMaxVelocity = 1.0;
		double rightMaxVelocity = 1.0;
		
		//determine whether to use positive or negative max velocity
		if (targetPos - leftEncoder >= 0) leftMaxVelocity = Constants.LIFT_LEFT_MOTOR_POS_VELOCITY;
		else leftMaxVelocity = Constants.LIFT_LEFT_MOTOR_NEG_VELOCITY;
		
		if (targetPos - rightEncoder >= 0) rightMaxVelocity = Constants.LIFT_RIGHT_MOTOR_POS_VELOCITY; 
		else rightMaxVelocity = Constants.LIFT_RIGHT_MOTOR_NEG_VELOCITY; 
		
		//set left and right motors by feeding forward
		//Equation: 1/maxVelocity(velocity) + KA(acceleration) + KP(error)	
		double leftSpeed = ( 1 / Math.abs(leftMaxVelocity) * pathLeft.getVelocity(currentTime) + Constants.LEFT_LIFT_KA * pathLeft.getAccel(currentTime) + Constants.LEFT_LIFT_KP * leftError + Constants.LEFT_LIFT_KI * leftErrorSum);//input from motion profile
		double rightSpeed = ( 1 / Math.abs(rightMaxVelocity) * pathRight.getVelocity(currentTime) + Constants.RIGHT_LIFT_KA * pathRight.getAccel(currentTime) + Constants.RIGHT_LIFT_KP * rightError + Constants.RIGHT_LIFT_KI * rightErrorSum);//input from motion profile
		
		leftSpeed = targetPos - startLeftEncoder < 0 ? 0.3 * leftSpeed : 0.75 * leftSpeed;
		rightSpeed = targetPos - startLeftEncoder < 0 ? 0.3 * rightSpeed : 0.75 * rightSpeed;
		
		liftIO.setMotorLeft(leftSpeed);
		liftIO.setMotorRight(rightSpeed);
		
		leftErrorSum += leftError;
		rightErrorSum += rightError;
		
		//if(i % 100 == 0)
		//	System.out.println("Left Error: " + leftErrorSum + "  Right Error:" + rightErrorSum);
		i++;
	}
	
	public boolean extendPath(int newPos){
		System.out.println("Extending Path Lift Core");
		//extend the path for the left side
		boolean extended = pathLeft.extendPath(newPos - targetPos, currentTime);
		//if the left path was successfully extended, then extend the right side
		if(extended) {
			if(!pathRight.extendPath(newPos - targetPos, currentTime)){
				//if the right side couldn't be extended (but the left side was), then stop the lift
				stopLift();
				extended = false;
				System.out.println("Had to stop lift due to extension");
			}
			else{targetPos = newPos;}
		}
		else{ 
			System.out.println("Didn't Extend Path");
		}
		return extended;
	}
	
	public boolean liftDone(){
		//if both paths are done, the lift is done
		return pathLeft.pathDone(currentTime) && pathRight.pathDone(currentTime);
	}
	
	private void stopLift(){
		//create paths with distances of 0 to stop the lift
		pathLeft.createPath(0);
		pathRight.createPath(0);
	}

}

