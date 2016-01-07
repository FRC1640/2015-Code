package utilities;

public class MotionProfile {
	//For explanation on the math in this class, please see the PDF "motion profile" in the repository
	//This code was inspired by the Cheesy Poof's Motion Profiling. The biggest difference, however is this math allows for different
	//acceleration and deceleration. 
	
	//There is also a live version of the Motion Profile, that will work with a joystick during teleop. It does not plan ahead for a 
	//deceleration, and only calculates for an acceleration
	
	//constants for each instance of a motion profile
	private final double posAccel;
	private final double negAccel;
	private final double posDecel;
	private final double negDecel;
	private final double posVelocity;
	private final double negVelocity;
	
	//given for each path
	private double distance;
	private double accel;
	private double decel;
	private double velocityCruise;
	
	//calculated for each path
	private double maxVelocity;
	private double timeAccel;
	private long timeCruise;
	private double timeDecel;
	private double distanceAccel;
	private double distanceCruise;
	private double initVelocity;
	private boolean triangle;
	
	

	public MotionProfile(double posAccel, double negAccel, double posDecel, double negDecel, double posVelocity, double negVelocity){
		//set all constants for this motion profile
		this.posAccel = posAccel;
		this.negAccel = negAccel;
		this.posDecel = posDecel;
		this.negDecel = negDecel;
		this.posVelocity = posVelocity;
		this.negVelocity = negVelocity;
	}
	
	public void createPath(double distance){
		//autonomous motion profiles
		//set the distance
		this.distance = distance;
		
		//select the correct velocity, accel, and decel based on direction
		velocityCruise = (distance > 0 ? posVelocity : negVelocity);
		accel = (distance > 0 ? posAccel : negAccel);
		decel = (distance > 0 ? posDecel : negDecel);

		//calculate the max velocity, if the path will be a triangle, and what velocity to use for the path
		maxVelocity = Math.sqrt((2 * accel * distance) / (1 + (Math.abs(accel) / Math.abs(decel))));
		maxVelocity = (distance > 0 ? maxVelocity : -maxVelocity);
		if(Math.abs(velocityCruise) > Math.abs(maxVelocity)) triangle = true;
		else triangle = false;
		velocityCruise = (distance > 0 ? Math.min(velocityCruise, maxVelocity) : Math.max(velocityCruise, maxVelocity));
		
		//calculate the distance covered during acceleration, and distance covered during cruise velocity
		distanceAccel = (Math.pow(velocityCruise, 2) / (2 * accel));
		if(!triangle) distanceCruise = distance + (Math.pow(velocityCruise, 2) / 2) * ((1 / decel) - (1 / accel));
		else distanceCruise = 0;
		
		//calculate the time spent accelerating, the time spent at cruise velocity, and the time decelerating
		timeAccel = velocityCruise / accel;
		if(!triangle) timeCruise = (long) distanceCruise / (long) velocityCruise;
		else timeCruise = 0;
		timeDecel = -velocityCruise / decel;
		
		//only used for live motion profiles
		initVelocity = 0;
	}
	
	public void createPath(double speed, double time){
		//live motion profiles
		
		//current velocity for math later
		initVelocity = getVelocity(time);
		
		//targetVelocity
		velocityCruise = maxVelocity = speed * posVelocity;
		
		//calculate time spent accelerating. Time spent cruising is as large as possible, since we wont decelerate until give the command to
		timeAccel = (velocityCruise - initVelocity) / posAccel;
		timeCruise = Long.MAX_VALUE;
		timeDecel = 0;
		
		//calculate distance covered while accelerating, distance cruise does not matter in a live MP
		distanceAccel = initVelocity * timeAccel + posAccel * Math.pow(timeAccel, 2) /  2;
		distanceCruise = 0;
		
		//TODO: Reset Encoders
	}
	
	public double getDistance(double time){
		//using the kinematic equation d = vi * t + 1/2 * a * t^2, return the distance covered after a given time
		if(time < timeAccel) { return initVelocity * time + accel * Math.pow(time, 2) / 2; }
		if(time < timeAccel + timeCruise) { return distanceAccel + velocityCruise * (time - timeAccel); }
		if(time <= timeAccel + timeCruise + timeDecel) { return distanceAccel + distanceCruise + velocityCruise * (time - timeAccel - timeCruise) + (decel * Math.pow(time - timeAccel - timeCruise, 2) / 2); }
		return distance;
	}
	
	public double getAccel(double time){
		//based on a given time, return the current acceleration
		if(time < timeAccel) { return accel; }
		if(time < timeAccel + timeCruise) { return 0; }
		if(time <= timeAccel + timeCruise + timeDecel) { return decel; }
		return 0;
	}
	
	public double getVelocity(double time){
		//based on a given time, return the correct velocity
		if(time < timeAccel) { return initVelocity + accel * time; }
		if(time < timeAccel + timeCruise) { return velocityCruise; }
		if(time <= timeAccel + timeCruise + timeDecel) { return velocityCruise + decel * (time - timeAccel - timeCruise); }
		return 0;
	}
	
	public boolean extendPath(double extension, double time){
		//if the extension is continuing the path in the correct direction and we haven't started decelerating
		if(((distance > 0 && extension > 0) || (distance < 0 && extension < 0)) && time < timeAccel + timeCruise){
			//create a new path with the extension
			createPath(distance + extension);
			return true;
		}
		else{
			System.out.println("Couldn't extend Path");
			return false;
		}
	}
	
	public boolean pathDone(double time){
		//if the time has passed than the total time of the motion profile, then the path is done
		return time >= timeAccel + timeCruise + timeDecel;
	}
}

