package auton;

import utilities.SonarInches;
import constants.IOConstants;
import drivetrain.DriveIO;

public class SonarCommand implements AutonCommand{
	private int distance;
	private double speed;
	private SonarInches sonar = new SonarInches(IOConstants.LEFT_SONAR);
	private boolean firstIteration = true, movingLeft = false, done = false;
	private String name;
	
	public SonarCommand(int distance, double speed, String name){
		this.distance = distance;
		this.speed = speed;
		this.name = name;
	}

	@Override
	public void execute() {
		if(firstIteration) {
			if(sonar.getInches() >= distance){
				movingLeft = true;
			}
			firstIteration = false;
			speed = movingLeft ? -speed : speed;
		}
		done = movingLeft ? sonar.getInches() <= distance : sonar.getInches() >= distance;
		DriveIO.getInstance().setX1(done ? 0 : speed);
		if (done) System.out.println("*true, finished with sonar at " + sonar.getInches());
	}

	@Override
	public boolean isRunning(){
		return !done;
	}

	@Override
	public boolean isDone(){
		return true;
	}

	@Override
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
		firstIteration = true; 
		movingLeft = false; 
		done = false;
		System.out.println(done);
	}
}
