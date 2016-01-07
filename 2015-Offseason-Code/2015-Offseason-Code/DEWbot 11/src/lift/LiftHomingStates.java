package lift;

import org.usfirst.frc.team1640.robot.Robot;

public class LiftHomingStates {
	
	private static LiftHomingStates liftHomingStates;
	
	private LiftHomingCore liftHomingCore = LiftHomingCore.getInstance();
	private LiftIO liftIO = LiftIO.getInstance();
	
	private boolean homed;
	private boolean stopHoming;
	private boolean doneIteration = false;
	private boolean started, donegoing = false;
	
	
	public static void init() {
		System.out.println("Homing Starting");
		if (liftHomingStates == null)
			liftHomingStates = new LiftHomingStates();
		
	}
	
	public static LiftHomingStates getInstance() {
		if (liftHomingStates != null)
			return liftHomingStates;
		else return liftHomingStates = new LiftHomingStates();
		
	}
	
	public void update() {
		boolean rightLimit = liftIO.getLimitSwitchRight();
		boolean leftLimit =  liftIO.getLimitSwitchLeft();
			
		if(!homed) {
			
			if (!doneIteration) {
				
				liftHomingCore.LiftHomingStart(-1000000);
				System.out.println("started Initial movement");
				doneIteration = true;
			}	
			
			if (rightLimit) {
				System.out.println("right limit hit");
				liftHomingCore.stopLiftHoming();
				stopHoming = true;
				liftIO.setMotorRight(0);
				liftIO.setMotorLeft(-0.40);
			}
			
			if (leftLimit) {
				System.out.println("left limit pressed");
				liftHomingCore.stopLiftHoming();
				stopHoming = true;
				liftIO.setMotorLeft(0);
				liftIO.setMotorRight(-0.40);
			}
				
			if (leftLimit && rightLimit) {
				liftIO.setMotorRight(0);
				liftIO.setMotorLeft(0);
				homed = true;
			
			}

		}
		
		else if (!donegoing){
				stopHoming = false;
				liftHomingCore.LiftHomingStart(0);
				liftIO.resetEncoders();
				liftHomingCore.LiftHomingStart(2000);
				donegoing = true;
				System.out.println("Done going true");
		}
		
		else if(!started && donegoing){
			started = !liftHomingCore.liftHomingDone();
			System.out.println("Started: " + started);
		}
		
		else if (liftHomingCore.liftHomingDone()){
			liftIO.resetEncoders();
			Robot.startLift();
		}
	}
	
	public boolean stopHoming() {
		return stopHoming;
	}
	


}
