package auton;

public class WaitCommand implements AutonCommand {
	private AutonCommand command;
	private String name;
	
	public WaitCommand(AutonCommand command, String name){
		this.command = command;
		this.name = name;
	}

	@Override
	public void execute() { }

	@Override
	public boolean isRunning() {
		return command.isRunning();
	}

	@Override
	public boolean isDone() {
		return !command.isRunning();
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
	public void reset() {}
}
