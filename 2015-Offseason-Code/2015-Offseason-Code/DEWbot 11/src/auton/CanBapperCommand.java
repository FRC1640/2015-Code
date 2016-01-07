package auton;

import canBapper.CanBapperIO;

public class CanBapperCommand implements AutonCommand {
	private boolean down, done;
	private String name;
	
	public CanBapperCommand(boolean down, String name) {
		this.down = down;
		this.name = name;
		done = false;
	}
	
	@Override
	public void execute() {
		CanBapperIO.getInstance().setB(down);
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
