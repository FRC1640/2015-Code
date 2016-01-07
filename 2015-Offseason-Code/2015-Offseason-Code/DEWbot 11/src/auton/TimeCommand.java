package auton;

public class TimeCommand implements AutonCommand{
	private long delay, startTime;
	private boolean firstIteration = true;
	private String name;
	
	public TimeCommand(long delay, String name){
		this.delay = delay;
		this.name = name;
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isRunning() {
		if (firstIteration){
			startTime = System.nanoTime() / 1000000;
			firstIteration = false;
		}
		return !((System.nanoTime() / 1000000) - startTime >= delay);
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
		firstIteration = true;
	}
}
