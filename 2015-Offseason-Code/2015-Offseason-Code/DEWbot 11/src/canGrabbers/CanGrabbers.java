package canGrabbers;

import utilities.Subsystem;

public class CanGrabbers extends Subsystem{
	private CanGrabbersIO canGrabbersIO = CanGrabbersIO.getInstance();
	
	public void init() { }
	
	public void update(){
		
		canGrabbersIO.setCanGrabbers();
		
	}

}
