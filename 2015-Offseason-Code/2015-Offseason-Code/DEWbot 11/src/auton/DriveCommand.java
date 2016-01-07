package auton;

import utilities.Utilities;
import drivetrain.DriveIO;

import java.util.Arrays;

public class DriveCommand implements AutonCommand{
	//this class is the drive command for auton
	//it takes an input of distance(inches), angle(degrees), speed(-1 to 1), and if the script is a canGrabber
	private double distance, angle, speed;
	private boolean canGrabber, done;
	private boolean firstIteration = true;
	private String name;
	private int[] prevEncoders = {0, 0, 0, 0, 0, 0, 0, 0};

	public DriveCommand(int distance, int angle, double speed, boolean canGrabber, String name){
		//record inputs
		this.distance = distance;
		this.angle = 180 - angle;
		this.speed = speed;
		this.canGrabber = canGrabber;
		this.name = name;
	}
	
	@Override
	public void execute() {
		if(firstIteration){
			DriveIO.getInstance().resetEncoders();
			for(int i = 0; i < prevEncoders.length ; i++){
				prevEncoders[i] = 0;
			}
			firstIteration = false;
		}
		//get and sort encoders
		int[] encoders = DriveIO.getInstance().getEncoders();
		
		
		for(int i = 0; i < 4; i++){
			encoders[i] -= prevEncoders[i + 4];	
			int buffer = (encoders[i] >= 50 ? 20 : 7);
			if(encoders[i] - prevEncoders[i] >= buffer){
				prevEncoders[i + 4]  += (encoders[i] - prevEncoders[i]);
				//System.out.println("Buffer: " + buffer + " Error: " + prevEncoders[i + 4] + " Cur: " + encoders[i] + " i " + i);
			}
		}
		
		Arrays.sort(encoders);
		//select second smallest encoder
		int encoder = encoders[1];
		//constants for gaussian function
		int muConversion = 3;
		int sigmaConversion = 4;
		//convert encoder to inches
		double inches = ((encoder * 4 * Math.PI) / 24) * 0.375;
		
		//math for gaussian function
		double driveMotor =  speed * Math.pow(Math.E, -(Math.pow(inches - (distance / muConversion), 2)  / Math.pow(2 * (distance / sigmaConversion), 2)));
		//deadband motor
		driveMotor = (driveMotor < 0.35 ? 0.35 : driveMotor) > 1 ? 1 : driveMotor;
		//if this is a can grabber drive, ignore the gaussian function and just go at full speed
		driveMotor = inches <= distance / 2 && canGrabber ? 1 : driveMotor;
		//dont drive if we have surpassed the distance
		driveMotor = inches >= distance ? 0 : driveMotor;
		
		//convert speed & angle to joysticks, set joysticks for drive
		double[] joysticks = Utilities.polarToRect(angle, driveMotor);
		DriveIO.getInstance().setX1(joysticks[0]);
		DriveIO.getInstance().setY1(joysticks[1]);
		done = inches >= distance;
		
		for(int i = 0; i < 4; i++){
			prevEncoders[i] = encoders[i]; 
		}
	}

	@Override
	public boolean isRunning() {
		return !done;
	}

	@Override
	public boolean isDone() {
		return true;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof String){
			return o == name;
		}
		return false;
	}
	
	@Override 
	public void reset() {
		done = false;
		firstIteration = true;
	}

}
