package drivetrain;

import edu.wpi.first.wpilibj.Servo;

public class SlowDecorator extends DriveControlDecorator {
	//decorator class that allows for a slower overall speed
	
	public SlowDecorator(DriveControl driveControl){
		//construct the parent object with the original object
		super(driveControl);
	}
	
	public void execute(double x1, double y1, double x2){
		double reduction = 0.5 + (Math.max(DriveIO.getInstance().getLeftTrigger(), DriveIO.getInstance().getRightTrigger()) / 2); //will be set to 0.5 + trigger axis
		if(reduction > 0.75)
			System.out.println("Slow Reduction: " + reduction);
		super.execute(x1 * reduction, y1 * reduction, x2 * reduction); //call the normal execute. but with a reduced speed
	}
}
