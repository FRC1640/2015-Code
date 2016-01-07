package org.usfirst.frc.team1640.robot;

public class Time extends EndCondition {
	long startTime;
	
	public Time(double value) {
		super(value);
	}
	
	@Override
	public void init() {
		startTime = System.nanoTime();
	}

	@Override
	public boolean checkParameter() {
		long currentTime = System.nanoTime();
		if((currentTime - startTime)/1000000 < getValue()){
			System.out.println((currentTime - startTime)/1000000);
			return false;
		} 
		else {
			return true;
		}
	}
}
