package lift;

import java.util.ArrayList;

import constants.Constants;

public class LiftStates{
	//this class takes a list of actions provided by lift read, and turns them into target heights
	//it then sends the target height to liftCore
	
	//singleton instance
	private static LiftStates liftStates;
	
	private LiftCoreMaster liftCore = LiftCoreMaster.getInstance();
	//private LiftCore liftCore = LiftCore.getInstance();
	public enum Actions {MOVETOFLOOR, MOVETO1, MOVETO2, MOVETO3, PICKUP3, EXTENDTOFLOOR, EXTENDTO1, EXTENDTO2, EXTENDTO3, IDLE};
	private ArrayList<Actions> ListOfActions = new ArrayList<Actions>();
	private boolean LiftDone = true;
	private double LiftHeight = 0;
	private boolean waiting = false;
	private long startTime = System.nanoTime() / 1000000;
	
	//constructor is made private for singleton
	private LiftStates(){
		ListOfActions.add(Actions.IDLE);
		ListOfActions.add(Actions.IDLE);
	}
	
	public static void init(){
		//if it hasn't already been initialized, initialize the singleton instance
		if(liftStates == null)
			liftStates = new LiftStates();
	}
	
	public static LiftStates getInstance(){
		//if the singleton instance was initialized, return it
		if(liftStates != null)
			return liftStates;
		//otherwise, create a LiftStates object and return it
		else return liftStates = new LiftStates();
	}
	
	public void setActions(ArrayList<Actions> newActions){
		//if the que is finished, accept a new array
    	if (ListOfActions.isEmpty() || ListOfActions.get(0) == Actions.IDLE) { 
    		ListOfActions = new ArrayList<Actions>(newActions);
    		if(ListOfActions.get(0) != Actions.IDLE) System.out.println(ListOfActions.get(0) + " : " + ListOfActions.get(1));
    		//terminate the waiting case
    		waiting = false;
    	}
    }
      
    public void update(){
    	//if enough time has passed, execute the que
    	if(!waiting){
	    	if ((LiftDone && !ListOfActions.isEmpty()) || isExtending(ListOfActions.get(0))){
	    		//if the que isn't empty, and the lift is done, run the correct action
	    		switch(ListOfActions.get(0)){
	    			case MOVETOFLOOR: {moveToFloor(false); break;}
	    			case MOVETO1: {moveTo1(false); break;}
	    			case MOVETO2: {moveTo2(false); break;}
	    			case MOVETO3: {moveTo3(false); break;}
	    			case PICKUP3: {pickup3(false); break;}
	    			case EXTENDTOFLOOR: {moveToFloor(true); break;}
	    			case EXTENDTO1: {moveTo1(true); break;}
	    			case EXTENDTO2: {moveTo2(true); break;}
	    			case EXTENDTO3: {moveTo3(true); break;}
	    			case IDLE: default: {}
	    		}
	    		if(ListOfActions.get(0) != Actions.IDLE) System.out.println("Lift Height: " + LiftHeight);
	    		//remove the completed action, and fill the empty spot with idle
	    		ListOfActions.remove(0);
		    	ListOfActions.add(Actions.IDLE);
	    		//initiate the waiting case, and store the start time
	    		waiting = true;
	    		startTime = System.nanoTime() / 1000000;
	    	}
    	}
    	else{ //waiting case
    		if(ListOfActions.get(0) != Actions.IDLE) {//if the que is not finished, wait before executing the next action
    			if(System.nanoTime() / 1000000 - startTime > 250) 
    				waiting = false; //if time has passed, terminate the waiting case
    		}
    		else
    			waiting = false;
    	}
		LiftDone = liftCore.liftDone(); //update the state of the lift
    }
    
    private void moveToFloor(boolean extending){
    	if(extending) {
    		//if the path needs to be extended, extend the path
    		if(liftCore.extendPath(Constants.LIFT_POS_FLOOR)) {
    			//if the path was successfully extended, update the lift height
    			LiftHeight = 0;
    		}
    	}
    	else{
    		//if the path is not being extended, start the lift and update the lift height
    		liftCore.liftStart(Constants.LIFT_POS_FLOOR);
    		LiftHeight = 0;
    	}
    }
    
    private void moveTo1(boolean extending){
    	if(extending) {
        	//if the path needs to be extended, extend the path
    		if(liftCore.extendPath(Constants.LIFT_POS_1)){
    			//if the path was successfully extended, update the lift height
    			LiftHeight = 1;
    		}
    	}
    	else{
        	//if the path is not being extended, start the lift and update the lift height
    		liftCore.liftStart(Constants.LIFT_POS_1);
    		LiftHeight = 1;
    	}
    }
    
    private void moveTo2(boolean extending){
    	if(extending) {
        	//if the path needs to be extended, extend the path
    		if(liftCore.extendPath(Constants.LIFT_POS_2)){
    			//if the path was successfully extended, update the lift height
    			LiftHeight = 2;
    		}
    	}
    	else{
        	//if the path is not being extended, start the lift and update the lift height
    		liftCore.liftStart(Constants.LIFT_POS_2);
    		LiftHeight = 2;
    	}
    	System.out.println("Move to 2: " + extending);
    }
    
    private void moveTo3(boolean extending){
		if(extending) {
	    	//if the path needs to be extended, extend the path
			if(liftCore.extendPath(Constants.LIFT_POS_3)){
    			//if the path was successfully extended, update the lift height
				LiftHeight = 3;
			}
		}
		else{
	    	//if the path is not being extended, start the lift and update the lift height
			liftCore.liftStart(Constants.LIFT_POS_3);
			LiftHeight = 3;
		}
    }
    
    private void pickup3(boolean extending){
    	if(extending) {
        	//if the path needs to be extended, extend the path
    		if(liftCore.extendPath(Constants.LIFT_POS_PICKUP3)){
    			//if the path was successfully extended, update the lift height
    			LiftHeight = 1.5;
    		}
    	}
    	else{
        	//if the path is not being extended, start the lift and update the lift height
    		liftCore.liftStart(Constants.LIFT_POS_PICKUP3);
    		LiftHeight = 1.5;
    	}
    }
    
    public double getLiftHeight(){
    	return LiftHeight;
    }
    
    private boolean isExtending(Actions action){
    	//return if the given action is calling for an extension
    	if(action != null)
    		return action == Actions.EXTENDTOFLOOR || action == Actions.EXTENDTO1 || action == Actions.EXTENDTO2 || action == Actions.EXTENDTO3;
    	return false;
    }
    
    public boolean sequenceDone(){
    	return ListOfActions.isEmpty() || ListOfActions.get(0) == Actions.IDLE;
    }
    
}

