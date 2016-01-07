package org.usfirst.frc.team1640.robot;

public class AutonWait extends AutonCommand{

	AutonWait(Time t) {
		setEndCondition(t);
	}
	
	@Override
	public void init() {
		this.setDone(false);
		getEndCondition().init();
	}

	@Override
	public boolean update() {
		if (!getDone()) {
			setDone(getEndCondition().checkParameter());
		}
		return getDone();
	}
}
