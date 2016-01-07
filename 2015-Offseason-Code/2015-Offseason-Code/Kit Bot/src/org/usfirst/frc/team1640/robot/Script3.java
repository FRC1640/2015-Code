package org.usfirst.frc.team1640.robot;

public class Script3 extends AutonScript {

	@Override
	public void init() {
		this.setDone(false);
	
		addCommand(new AutonWait(new Time(2000)));
		addCommand(new AutonDrive(new Time(1500), AutonDrive.FORWARD, .5, AutonDrive.COAST));
		addCommand(new AutonTurn(new Time(3500), AutonTurn.RIGHT, .5, AutonDrive.BRAKE));
	}
}
