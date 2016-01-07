   package drivetrain;

import java.io.File;
import java.io.FileWriter;

import org.usfirst.frc.team1640.robot.Robot;

import utilities.Utilities;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

public class Pivot {
	private Talon drive;
	private Talon steer;
	private AnalogInput resolver;
	private Counter counter;
	public PIDController anglePID;
	private boolean enabled;
	private double minVoltage, dVoltage;
	private double offset;
	private boolean flipDrive;
	private double targetAngle;
	
	private File log;
	private FileWriter writer;
	private long start = System.nanoTime(), accError;
	

	// trivial change
	
	public Pivot(int driveChannel, int steerChannel, int resolverChannel, int counterChannel, double minVoltage, double maxVoltage, double offset, String name){
		//Setup all drive and sensors for the pivots
		drive = new Talon(driveChannel);
		steer = new Talon(steerChannel);
		resolver = new AnalogInput(resolverChannel){
			@Override
			public double pidGet(){
				//System.out.println(shortestDistance());
				return shortestDistance();
			}
		};
		counter = new Counter(counterChannel);
		
		this.minVoltage = minVoltage;
		this.dVoltage = maxVoltage - minVoltage;
		this.offset = offset;
		
		//setup angle PID
		anglePID = new PIDController(0.9, 0.0001, 0, 0, resolver, steer, 0.02);
		anglePID.setOutputRange(-1, 1);
		// anglePID.setContinuous();
		anglePID.setInputRange(-1, 1);
		anglePID.enable();
		anglePID.setSetpoint(0.0);
		
		enabled = true;
		flipDrive = false;
		targetAngle = 0.0;
		
		try{
			log = new File("/home/lvuser/log3.csv");
			if(log.exists()){
				log.delete();
			}
			
		
			log.createNewFile();
			writer = new FileWriter(log, true);
			
			writer.write("dt \t");
			writer.write("Current \t");
			writer.write("Target \t");
			writer.write("ShortestDistance \t");
			writer.write("Error \t");
			writer.write("Acc. Error \t");
			writer.write("State \t");
			writer.write("Pivot: " + name + "\t");
			writer.write("PID output \n");
			
		}catch(Exception e){System.out.println("e");}
	}
	
	public double shortestDistance() {
		double dAngle = targetAngle - getAngle();
		flipDrive = Utilities.inRange(90, 270, Math.abs(dAngle));
		try{
			//accError += anglePID.getError();
			long current = System.nanoTime();
			writer.write(current - start + "\t");
			start = current;
			writer.write(getAngle() + "\t");
			writer.write(targetAngle + "\t");
			writer.write(((flipDrive) ? Math.sin(Math.toRadians(dAngle)) : -Math.sin(Math.toRadians(dAngle))) + "\t");
			writer.write(0 + "\t");//writer.write(anglePID.getError() + "\t");
			writer.write(accError + "\t");
			writer.write(Robot.getState() + "\t");
			writer.write(0 + " \n");//writer.write(anglePID.get() + "\n");
			writer.flush();
		}catch(Exception e){
			System.out.println("log Error");
			System.out.println(e);
		}
		
		return (flipDrive) ? Math.sin(Math.toRadians(dAngle)) : -Math.sin(Math.toRadians(dAngle));
	}
	
	public void setTargetAngle(double angle){
		targetAngle = angle;
	}

	public void setDrive(double speed){
		if (enabled){
			drive.set(flipDrive ? -speed : speed);
		}
		else {
			drive.set(0);
		}
	}
	
	public void resetCounter(){
		counter.reset();
	}
	
	public double getAngle(){
		// return resolver.pidGet();
		return ((360.0 * (resolver.getVoltage() - minVoltage) / dVoltage) + 360.0 - offset) % 360;
	}
	
	public double getVoltage(){
		return resolver.getVoltage();
	}
	
	public int getCounter(){
		return (counter.get() * 2);
	}
	
	public void disable(){
		if (enabled){
			enabled = false;
			anglePID.disable();
			drive.set(0);
		}
	}
	
	public void enable(){
		if (!enabled){
			enabled = true;
			anglePID.enable();
		}
	}
	
}
