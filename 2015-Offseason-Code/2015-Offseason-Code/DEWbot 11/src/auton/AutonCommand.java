package auton;

public interface AutonCommand {
	void execute();
	
	boolean isRunning();
	
	boolean isDone();
	
	String getName();
	
	void reset();
}
