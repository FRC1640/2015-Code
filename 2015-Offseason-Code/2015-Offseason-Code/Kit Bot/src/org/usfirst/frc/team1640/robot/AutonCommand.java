package org.usfirst.frc.team1640.robot;

public abstract class AutonCommand {
	private boolean isDone = true;
	private EndCondition e;
	
	public abstract boolean update();
	
	public abstract void init();
	
	protected void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	public boolean getDone() {
		return isDone;
	}
	
	public void setEndCondition(EndCondition e) {
		this.e = e;
	}
	
	public EndCondition getEndCondition() {
		return e;
	}
}
