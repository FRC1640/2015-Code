package org.usfirst.frc.team1640.robot;

public abstract class EndCondition {
	private double value;
	
	protected EndCondition(double value) {
		this.value = value;
	}
	
	protected abstract void init();
	
	public abstract boolean checkParameter();
	
	public double getValue() {
		return value;
	}
}
