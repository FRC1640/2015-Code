package lift;

import utilities.Subsystem;

public class Lift extends Subsystem{
	//master class for lift thread
	
	//variables to hold instances of singleton lift classes
	private LiftRead liftRead;
	private LiftStates liftStates;
	private LiftCoreMaster liftCore;//private LiftCore liftCore;
	
	//when the thread is first called
	public void init(){
		//initialize all necessary lift classes
		LiftRead.init();
		LiftStates.init();
		LiftCoreMaster.init(); //LiftCore.init();
		LiftIO.init();
		
		//get the instances of all singleton lift classes
		liftRead = LiftRead.getInstance();
		liftStates = LiftStates.getInstance();
		liftCore = LiftCoreMaster.getInstance();//LiftCore.getInstance();
	}
	
	public void update(){
		//update all necessary lift classes
		liftRead.update();
		liftStates.update();
		liftCore.update();
	}
}
