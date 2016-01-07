package canBapper;

import utilities.Subsystem;

public class CanBapper extends Subsystem {
	
	private CanBapperIO canBapperIO = CanBapperIO.getInstance();
	
	public void init() {
		
	}
	
	public void update() {
		canBapperIO.setCanBapper();
	}

}
