package lift;

import utilities.PIDOutputDouble;
import utilities.PIDSourceDouble;
import edu.wpi.first.wpilibj.PIDController;

public class LiftCoreMaster {
	private static LiftCoreMaster liftCore;
	
	private PIDSourceDouble masterSource = new PIDSourceDouble(0), slaveSource = new PIDSourceDouble(0);
	private PIDOutputDouble masterOutput = new PIDOutputDouble(), slaveOutput = new PIDOutputDouble(); 
	private PIDController master = new PIDController(0.0005, 0.0000001, -0.00018, 0, masterSource, masterOutput, .020);
	private PIDController slave = new PIDController(0.0006, 0, 0, 0, slaveSource, slaveOutput, .020);
	private double speed, prevSpeed, targetPosition;
	private boolean done;
	private LiftIO liftIO = LiftIO.getInstance();
	
	private LiftCoreMaster(){
		master.enable();
		slave.enable();
	}
	
	public static LiftCoreMaster getInstance(){
		if(liftCore == null)
			liftCore = new LiftCoreMaster();
		return liftCore;
	}
	
	public static void init(){
		if(liftCore == null)
			liftCore = new LiftCoreMaster();
	}
	
	public void liftStart(int targetPosition){
		System.out.println("Target Position: " + targetPosition);
		this.targetPosition = targetPosition;
		master.setSetpoint(targetPosition/* * 273.6*/);
		slave.setSetpoint((masterOutput.getValue() * 1800 + liftIO.getEncoderLeft()));
		setSpeed(targetPosition - liftIO.getEncoderLeft() < 0 ? 0.6 : 0.75);
		System.out.println("Speed: " + (targetPosition - liftIO.getEncoderLeft() < 0 ? 0.6 : 0.75));
	}
	
	public void update(){
		slaveSource.setValue(liftIO.getEncoderRight());
		masterSource.setValue(liftIO.getEncoderLeft());
		liftIO.setMotorLeft(masterOutput.getValue());
		liftIO.setMotorRight(-slaveOutput.getValue());
		slave.setSetpoint(-(masterOutput.getValue() * 1800 + liftIO.getEncoderLeft()));

		//System.out.println("Motor Speed: " + masterOutput.getValue() + " " + slaveOutput.getValue());
		
		done = Math.abs(targetPosition - (liftIO.getEncoderLeft())) < 700;		
	}
	
	public boolean extendPath(int newPosition){
		liftStart(newPosition);
		return true;
	}
	
	public boolean liftDone(){
		return done;
	}
	
	private void setSpeed(double speed){
		if(this.speed != speed){
			master.setOutputRange(-speed, speed);
			prevSpeed = this.speed;
			this.speed = speed;
		}
	}

}
