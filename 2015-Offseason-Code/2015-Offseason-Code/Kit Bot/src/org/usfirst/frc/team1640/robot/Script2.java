package org.usfirst.frc.team1640.robot;

public class Script2 extends AutonScript {

	@Override
	public void init() {
		//forward 2 sec .5 speed, wait for 1 sec, go backwards 3 seconds .25 speed
		this.setDone(false);
		addCommand(new AutonDrive(new Time(2000), AutonDrive.FORWARD, .5, AutonDrive.COAST));
		addCommand(new AutonWait(new Time(1000)));
		addCommand(new AutonDrive(new Time(3000), AutonDrive.BACKWARD, .25, AutonDrive.BRAKE));
	}
}