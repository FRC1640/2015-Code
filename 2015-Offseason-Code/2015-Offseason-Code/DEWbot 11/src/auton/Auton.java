package auton;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import drivetrain.DriveIO;
import auton.LiftCommand.Button;
import utilities.Subsystem;


public class Auton extends Subsystem{
	private AutonCommand autonCommand;
	private boolean prevDone, prevRunning;

	@Override
	public void update() {
		
		if(Robot.getState() == State.AUTON && autonCommand.isRunning()){
			if(autonCommand.isRunning())
				autonCommand.execute();
			prevDone = autonCommand.isDone();
			prevRunning = autonCommand.isRunning();
		}
		
		if(Robot.getState() == State.DISABLED && !autonCommand.isRunning()){
			ScriptRunner.getInstance().reset();
		}
		
	}

	@Override
	public void init() {
		autonCommand = ScriptRunner.getInstance();
	}


}
