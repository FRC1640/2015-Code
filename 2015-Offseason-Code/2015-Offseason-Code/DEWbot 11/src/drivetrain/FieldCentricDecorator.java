package drivetrain;

public class FieldCentricDecorator extends DriveControlDecorator {
	//decorator class for drive that turns robot centric into field centric
	
	public FieldCentricDecorator(DriveControl driveControl){
		//construct the parent object with the original object
		super(driveControl);
	}
	
	private double[] fieldCentric(double x1, double y1, double x2) {
		//using the gyro, change the joysticks to reflect field centric behavior
		double temp;
		DriveIO driveIO = DriveIO.getInstance();
		//driveIO.resetGyro();
		temp = y1*Math.cos(Math.toRadians(driveIO.getYaw() ) ) + x1*Math.sin(Math.toRadians(driveIO.getYaw() ) );
		x1 =  -y1*Math.sin(Math.toRadians(driveIO.getYaw() ) ) + x1*Math.cos(Math.toRadians(driveIO.getYaw() ) );
		y1 = temp;
		double[] joysticks = {x1, y1, x2};
		return joysticks;
	}
	
	public void execute(double x1, double y1, double x2){
		//get the field centric joystick values
		double[] joysticks = fieldCentric(x1, y1, x2);
		//call the normal execute function, but with field centric joystick values instead
		super.execute(joysticks[0], joysticks[1], joysticks[2]); 
	}
}
