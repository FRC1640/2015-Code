package auton;

import canGrabbers.CanGrabbersIO;

public class CanGrabberCommand implements AutonCommand{
	private boolean down, done;
	private String name;
	
	public CanGrabberCommand(boolean down, String name){
		this.down = down;
		this.name = name;
		done = false;
	}

	@Override
	public void execute() {	
		CanGrabbersIO.getInstance().setA(down);
		done = true;
	}

	@Override
	public boolean isRunning() {
		return !done;
	}

	@Override
	public boolean isDone() {
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
	}
}
