package lift;

import java.util.ArrayList;

import lift.LiftStates.Actions;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;


public class LiftRead {
	//this class interprets the input from the controller, and sends a que of actions to liftStates
	
	//singleton instance
	private static LiftRead liftRead;
	
	//rising edge variables
	private boolean upPrev = false;
	private boolean downPrev = false;
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean pickupPrev = false;
	private boolean pickupFloorPrev = false;
	private boolean placePrev = false;
	private boolean prevDone = false;
	
	//the constructor is private so only this class can create instances of LiftRead
	private LiftRead() {}
	
	public static void init(){
		//if it hasnt been created already, create an instance of Lift Read
		if(liftRead == null)
			liftRead = new LiftRead();
	}
	
	public static LiftRead getInstance(){
		//if an instance of lift read has been created, return it
		if(liftRead != null)
			return liftRead;
		//otherwise, create one and return it
		else return liftRead = new LiftRead();
	}
	
	public void update(){
		//create an arraylist to be filled with the actions the lift will run
		ArrayList<Actions> userInput = new ArrayList<Actions>();
		//initialize the array to avoid an ArrayIndexOutOfBounds exception
		userInput.add(Actions.IDLE);
		userInput.add(Actions.IDLE);
		
		//get the instances of the other lift classes
		LiftIO liftIO = LiftIO.getInstance();
		LiftStates liftStates = LiftStates.getInstance();
		//LiftCore liftCore = LiftCore.getInstance();
		LiftCoreMaster liftCore = LiftCoreMaster.getInstance();
		
		//update controller inputs
		boolean pickup = liftIO.getA();
		boolean pickupFloor = liftIO.getB();
		int dpad = liftIO.getPOV();
		
		//if the lift has finished, we are done moving up or down
		if(liftCore.liftDone() && !prevDone){
			movingUp = false;
			movingDown = false;
		}
		
		//pickup
		if (pickup && !pickupPrev){ //rising edge
			switch((int)liftStates.getLiftHeight()*10){ //fill que with correct positions based on height of lift
				case 10: {
					userInput.set(0, Actions.MOVETOFLOOR);
					userInput.set(1, Actions.MOVETO1);
					break;
				}
				case 20: {
					userInput.set(0, Actions.MOVETOFLOOR);
					userInput.set(1, Actions.MOVETO2);
					break;
				}
				case 30: {
					userInput.set(0, Actions.PICKUP3);
					userInput.set(1, Actions.MOVETO3);
					break;
				}
				default: {
					userInput.set(0, Actions.IDLE);
					userInput.set(1, Actions.IDLE);
				}
			}
		}
		
		//pickup floor
		if (pickupFloor && !pickupFloorPrev){ //rising edge
			//fill que with pickup floor actions
			userInput.set(0, Actions.MOVETOFLOOR);
			userInput.set(1, Actions.MOVETO1);
		}
		
		//place
		if (dpad == 270 && !placePrev){ //rising edge
			//fill que with place actions
			userInput.set(0, Actions.MOVETOFLOOR);
			userInput.set(1, Actions.IDLE);
		}
		
		if(dpad == 0 && !upPrev){//if rising edge of up
			System.out.println("Up");
			if(movingUp){//if still in the process of moving up
				switch((int) liftStates.getLiftHeight() * 10){ //based on the current height, extend the path to a new target
					case 10: {userInput.set(0, Actions.EXTENDTO2); break;}
					case 20: {userInput.set(0, Actions.EXTENDTO3); break;}
					default: {userInput.set(0, Actions.IDLE); break;}
				}
			}
			else{ //if not still in the process of moving up
				switch((int) liftStates.getLiftHeight() * 10){ //based on the current height, set a new target
					case 0: {userInput.set(0, Actions.MOVETO1); break;}
					case 10: {userInput.set(0, Actions.MOVETO2); break;}
					case 20: {userInput.set(0, Actions.MOVETO3); break;}
					default: {userInput.set(0, Actions.IDLE); break;}
				}
			}
			//if we are not already at the top, then we are moving up
			if(liftStates.getLiftHeight() != 3) movingUp = true;
			userInput.set(1, Actions.IDLE);
		}
		
		
			
		
		if(dpad == 180 && !downPrev){ //if rising edge of down
			if(movingDown){ //if in the process of moving down
				switch((int) liftStates.getLiftHeight() * 10){ //based on the current height, extend the path to the new target
					case 20: {userInput.set(0, Actions.EXTENDTO1); break;}
					case 10: {userInput.set(0, Actions.EXTENDTOFLOOR); break;}
					default: {}
				}
			}
			else{ //if not in the process of moving down
				switch((int) liftStates.getLiftHeight() * 10){ //based on the current height, extend the path to the new target
					case 30: {userInput.set(0, Actions.MOVETO2); break;}
					case 20: {userInput.set(0, Actions.MOVETO1); break;}
					case 10: {userInput.set(0, Actions.MOVETOFLOOR); break;}
				}
			}
			//if we are not already at the bottom, we are moving down
			if(liftStates.getLiftHeight() != 0) movingDown = true;
			userInput.set(1, Actions.IDLE);
		}
		
		
		//set variables for rising / falling edge
		prevDone = liftCore.liftDone();

		if (dpad == 0)	{ upPrev = true;	}
		else	{ upPrev = false;	}
		
		if (dpad == 180)	{ downPrev = true;	} 
		else	{ downPrev = false;	}
		
		pickupPrev = pickup;
		
		pickupFloorPrev = pickupFloor;
		
		if (dpad == 270){ placePrev = true;}
		else{ placePrev = false;}
		
		liftStates.setActions(userInput); //send list of actions to list states class
		if(userInput.get(0) != Actions.IDLE) {
			System.out.println(userInput.get(0) + " | " + userInput.get(1));
		}
		userInput.set(0, Actions.IDLE); //reset list of actions
		userInput.set(1, Actions.IDLE);
	}
}