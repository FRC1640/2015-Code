package org.usfirst.frc.team1640.robot;

public class AutonDrive extends AutonCommand{
	CANDriveIO canDriveIO = CANDriveIO.getInstance();
	
	int direction = FORWARD;
	double speed = DEFAULT_SPEED;
	
	static final int FORWARD = 1, BACKWARD = -1;
	static final double DEFAULT_SPEED = 0.5;
	static final boolean BRAKE = CANDriveIO.STOP_MODE_BRAKE, COAST = CANDriveIO.STOP_MODE_COAST;
	
	AutonDrive(Time t, int direction, double speed, boolean stopMode) {
		setEndCondition(t);
		this.direction = direction;
		this.speed = speed;
		canDriveIO.setStopMode(stopMode);
	}
	
	@Override
	public void init() {
		this.setDone(false);
		getEndCondition().init();
	}

	@Override
	public boolean update() {
		if (!getDone()) {
			canDriveIO.setRightMotors(direction * speed);
			canDriveIO.setLeftMotors(direction * speed);
			setDone(getEndCondition().checkParameter());
		}
		if (getDone()) {
			canDriveIO.setRightMotors(0);
			canDriveIO.setLeftMotors(0);
		}
		return getDone();
	}
}
