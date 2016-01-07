package auton;

import utilities.PIDOutputDouble;
import utilities.PIDSourceDouble;
import utilities.Utilities;
import drivetrain.DriveIO;
import edu.wpi.first.wpilibj.PIDController;

public class TurnCommand implements AutonCommand {
	private boolean done, freed;
	private double angle, buffer;
	private PIDController turnPID;
	private PIDSourceDouble distance;
	private PIDOutputDouble turn;
	private DriveIO driveIO;
	private long start;
	private String name;
	
	public TurnCommand(double angle, String name){
		this.angle = angle;
		this.name = name;
		buffer = 2;
		driveIO = DriveIO.getInstance();
		distance = new PIDSourceDouble(0);
		turn = new PIDOutputDouble();
		turnPID = new PIDController(0.023, 0.038, 0.002, 0, distance, turn, 0.02); //Labview: 0.023, 0.0375, 0.002
		turnPID.setOutputRange(-0.5, 0.5);
		//turnPID.enable();
		turnPID.setSetpoint(0.0);
		System.out.println("Angle: " + angle + " Current: " + driveIO.getYaw());
		System.out.println("Shortest Distance: " + Utilities.shortestDistanceAbsolute(driveIO.getYaw(), angle));
	}

	@Override
	public void execute() {
		if(!done){
			turnPID.enable();
			distance.setValue(Utilities.shortestDistanceAbsolute(driveIO.getYaw(), angle));
			driveIO.setX2(done ? 0 : turn.getValue());
			done = Math.abs(Utilities.shortestDistanceAbsolute(driveIO.getYaw(), angle)) < buffer;
			//System.out.println(Utilities.shortestDistanceAbsolute(driveIO.getYaw(), angle) + " , " + driveIO.getYaw() + " , " + (System.nanoTime() / 1000000 - start));
			start = System.nanoTime() / 1000000;
		}
		else{
			turnPID.disable();
		}
	}

	@Override
	public boolean isRunning() {
		if(done && !freed){
			turnPID.free();
			freed = true;
			driveIO.setX2(0);
		}
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
		freed = false;
	}
}
