package org.usfirst.frc.team1640.robot;

import java.util.ArrayList;

public abstract class AutonScript{
	private ArrayList<AutonCommand> commandList = new ArrayList<AutonCommand>();
	private boolean firstTime = true;
	private boolean isDone = true;
	
	protected void setDone(boolean isDone){
		this.isDone = isDone;
	}
	
	boolean getDone(){
		return isDone;
	}
	 
	public abstract void init();
	
	public boolean update() {
		if(!getDone()){
			setDone(runCommand());
		}
		return getDone();
	}
	
	protected void addCommand(AutonCommand command){
		commandList.add(command);
	}
	
	public boolean runCommand(){
		if(commandList.isEmpty() ){
			return true;
		}
		if(firstTime) {
			commandList.get(0).init();
			firstTime = false;
		}
		if(commandList.get(0).update()) {
			commandList.remove(0);
			firstTime = true;
		}
		return false;
	}
}
