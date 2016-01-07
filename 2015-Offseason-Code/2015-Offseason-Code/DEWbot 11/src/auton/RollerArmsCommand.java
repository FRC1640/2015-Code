package auton;

import rollerArms.RollerArmsIO;

public class RollerArmsCommand implements AutonCommand {
	
	private boolean toggle, in, out, done;
	private String name;
	
	public RollerArmsCommand(boolean toggle, boolean in, boolean out, String name) {
		this.toggle = toggle;
		this.in = in;
		this.out = out;
		this.name = name;
		done = false;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
			RollerArmsIO.getInstance().setLeftTrigger(toggle);
		
			RollerArmsIO.getInstance().setLeftBumper(in);
			
			RollerArmsIO.getInstance().setRightBumper(out);
			
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
