package auton.scripts;

import java.util.ArrayList;

import auton.AutonCommand;
import auton.CanGrabberCommand;
import auton.DriveCommand;
import auton.TimeCommand;
import auton.WaitCommand;

public class CanGrabberScript extends AutonScript{
	public enum Length {SHORT, LONG};
	public enum Opposition {UOP, OP};
	private ArrayList<AutonCommand> script = new ArrayList<AutonCommand>(){
		@Override
		public int indexOf(Object o){
			if (o == null) {
		        for (int i = 0; i < script.size(); i++)
		            if (script.get(i) == null)
		                return i;
		    } else {
		        for (int i = 0; i < script.size(); i++)
		            if (script.get(i).equals(o))
		                return i;
		    }
			return -1;
		}
	};
	
	private static CanGrabberScript shortUOP, shortOP, longUOP, longOP;
	
	public static CanGrabberScript getInstance(Length length, Opposition opposition){
		if(length == Length.SHORT){
			if(opposition == Opposition.UOP){
				if(shortUOP == null)
					shortUOP = new CanGrabberScript(length, opposition);
				return shortUOP;
			}
			else if(opposition == Opposition.OP){
				if(shortOP == null)
					shortOP = new CanGrabberScript(length, opposition);
				return shortOP;
			}
		}
		
		if(length == Length.LONG){
			if(opposition == Opposition.UOP){
				if(longUOP == null)
					longUOP = new CanGrabberScript(length, opposition);
				return longUOP;
			}
			else if(opposition == Opposition.OP){
				if(longOP == null)
					longOP = new CanGrabberScript(length, opposition);
				return longOP;
			}
		}
		return null;
	}
	
	private CanGrabberScript(Length length, Opposition opposition){
		if(opposition == Opposition.UOP){
			script.add(new TimeCommand(7, "delay"));
		}
		if(opposition == Opposition.OP){
			script.add(new TimeCommand(1, "delay")); //fix delay
		}
		script.add(new WaitCommand(script.get(script.indexOf("delay")), "wait1"));
		script.add(new DriveCommand(5, 90, 0.5, false, "side1"));
		script.add(new WaitCommand(script.get(script.indexOf("side1")), "wait2"));
		script.add(new DriveCommand(10, 270, 0.5, false, "side2"));
		script.add(new WaitCommand(script.get(script.indexOf("side2")), "wait3"));
		if(length == Length.SHORT){
			script.add(new DriveCommand(100, 180, 1, true, "driveBack"));
		}
		if(length == Length.LONG){
			script.add(new DriveCommand(50, 180, 1, true, "driveBack")); //fix distance
		}
		script.add(new WaitCommand(script.get(script.indexOf("driveBack")), "wait4"));
		script.add(new CanGrabberCommand(false, "release"));
		script.add(new DriveCommand(30, 0, 0.5, false, "forward 1"));
		script.add(new WaitCommand(script.get(script.indexOf("forward 1")), "wait5"));
		script.add(new DriveCommand(30, 180, 0.5, false, "backward 1"));
		script.add(new WaitCommand(script.get(script.indexOf("backward 1")), "wait6"));
		script.add(new DriveCommand(30, 0, 0.5, false, "forward 2"));
		script.add(new WaitCommand(script.get(script.indexOf("forward 2")), "wait7"));
		script.add(new DriveCommand(30, 180, 0.5, false, "backward 2"));
		script.add(new WaitCommand(script.get(script.indexOf("backward 2")), "wait8"));
		
		
	}

	@Override
	public ArrayList<AutonCommand> getScript() {
		return script;
	}

}
