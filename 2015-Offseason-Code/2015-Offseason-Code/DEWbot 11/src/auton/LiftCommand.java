package auton;

import lift.LiftIO;
import lift.LiftStates;

public class LiftCommand implements AutonCommand {
	private boolean done, started, prevDone;
	public enum Button {PICKUP, PICKUPFLOOR, PLACE, MOVEUP, MOVEDOWN}; 
	private Button button;
	private LiftIO liftIO;
	private String name; 
	
	public LiftCommand(Button button, String name){
		this.button = button;
		this.name = name;
		liftIO = LiftIO.getInstance();
		liftIO.setA(false);
		liftIO.setB(false);
		liftIO.setPOV(-1);
	}
	
	@Override
	public void execute(){
		if(!started){
			switch(button){
				case PICKUP: {liftIO.setA(true); break;}
				case PICKUPFLOOR: {liftIO.setB(true); break;}
				case PLACE: {liftIO.setPOV(270); break;}
				case MOVEUP: {liftIO.setPOV(0); break;}
				case MOVEDOWN: {liftIO.setPOV(180); break;}
			}
			started = true;
		}
		else{
			done = LiftStates.getInstance().sequenceDone() && !prevDone;
		}
		prevDone = done;
	}
	
	@Override
	public boolean isRunning(){
		return !done;
	}
	
	@Override
	public boolean isDone(){
		return true;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof String){
			return o == name;
		}
		return false;
	}
	
	@Override 
	public void reset() {
		done = false;
		started = false;
		prevDone = false;
		
	}
}
