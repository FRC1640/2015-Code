package drivetrain;

import static constants.Constants.ROBOT_LENGTH_TO_RADIUS_RATIO;
import static constants.Constants.ROBOT_WIDTH_TO_RADIUS_RATIO;
import static utilities.Utilities.angle;
import static utilities.Utilities.magnitude;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

public interface DriveControl {
	void execute(double x1, double y1, double x2);
	
	default void ocelot(double x1, double y1, double x2){
		DriveIO driveIO = DriveIO.getInstance();
		double xPos = x1 + x2 * ROBOT_WIDTH_TO_RADIUS_RATIO;
		double xNeg = x1 - x2 * ROBOT_WIDTH_TO_RADIUS_RATIO;
		double yPos = y1 + x2 * ROBOT_LENGTH_TO_RADIUS_RATIO;
		double yNeg = y1 - x2 * ROBOT_LENGTH_TO_RADIUS_RATIO;
		
		double fld = magnitude(xPos, yPos);
		double frd = magnitude(xPos, yNeg);
		double bld = magnitude(xNeg, yPos);
		double brd = magnitude(xNeg, yNeg);
		
		double maxd = Math.max(fld, Math.max(frd, Math.max(bld, brd))); 
		
		if (maxd > 1){
			fld /= maxd;
			frd /= maxd;
			bld /= maxd;
			brd /= maxd;
		}
		
		double fls = angle(xPos, yPos);
		double frs = angle(xPos, yNeg);
		double bls = angle(xNeg, yPos);
		double brs = angle(xNeg, yNeg);
		
		Pivot fl = driveIO.getPivots()[0];
		Pivot fr = driveIO.getPivots()[1];
		Pivot bl = driveIO.getPivots()[2];
		Pivot br = driveIO.getPivots()[3];
		
		/*if(x1 != 0 || y1 != 0 || x2 != 0){
			fl.setTargetAngle(fls);
			fr.setTargetAngle(frs);
			bl.setTargetAngle(bls);
			br.setTargetAngle(brs);
			
		}*/
		
		if((x1 == 0 && y1 == 0 && x2 == 0)){
			fls = fl.getAngle();
			frs = fr.getAngle();
			bls = bl.getAngle();
			brs = br.getAngle();
		}
		
		fl.setTargetAngle(fls);
		fr.setTargetAngle(frs);
		bl.setTargetAngle(bls);
		br.setTargetAngle(brs);
		
		fl.setDrive(fld);
		fr.setDrive(frd);	
		bl.setDrive(bld);
		br.setDrive(brd);
	}
}
