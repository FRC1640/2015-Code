package org.usfirst.frc.team1640.robot;

public class Script1 extends AutonScript {
	
	@Override
	public void init() {
		this.setDone(false);
		addCommand(new AutonDrive(new Time(1000), AutonDrive.FORWARD, 0.5, AutonDrive.BRAKE) );
		addCommand(new AutonWait(new Time(1500) ) );
		addCommand(new AutonDrive(new Time(2000), AutonDrive.BACKWARD, 0.25, AutonDrive.COAST) );
	}
}