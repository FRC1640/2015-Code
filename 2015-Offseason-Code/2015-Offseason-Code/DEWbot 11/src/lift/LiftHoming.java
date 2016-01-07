package lift;

import utilities.Subsystem;

public class LiftHoming extends Subsystem {

	private LiftHomingStates liftHomingStates;
	private LiftHomingCore liftHomingCore;

	
	public void init() {
		LiftHomingStates.init();
		LiftHomingCore.init();
		LiftIO.init();
		
		liftHomingStates = LiftHomingStates.getInstance();
		liftHomingCore = LiftHomingCore.getInstance();
	}
	
	public void update() {
		
		liftHomingStates.update();
		//System.out.println("lift update method is running");
		if(!liftHomingStates.stopHoming()){
			liftHomingCore.update();
		}
		
	}
}
