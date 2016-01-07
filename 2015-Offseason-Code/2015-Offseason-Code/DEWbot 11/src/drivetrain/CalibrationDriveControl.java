package drivetrain;

public class CalibrationDriveControl implements DriveControl{
	private double maxFl, maxFr, maxBl, maxBr, minFl, minFr, minBl, minBr, prevMaxFl, prevMaxFr, prevMaxBl, prevMaxBr, prevMinFl, prevMinFr, prevMinBl, prevMinBr;
	private DriveIO driveIO = DriveIO.getInstance();

	public CalibrationDriveControl(){ 
		minFl = minFr = minBl = minBr = 4.5;
	}

	@Override
	public void execute(double x1, double y1, double x2) {
		maxFl = Math.max(maxFl, driveIO.getPivots()[0].getVoltage());
		maxFr = Math.max(maxFr, driveIO.getPivots()[1].getVoltage());
		maxBl = Math.max(maxBl, driveIO.getPivots()[2].getVoltage());
		maxBr = Math.max(maxBr, driveIO.getPivots()[3].getVoltage());
		
		minFl = Math.min(minFl, driveIO.getPivots()[0].getVoltage());
		minFr = Math.min(minFr, driveIO.getPivots()[1].getVoltage());
		minBl = Math.min(minBl, driveIO.getPivots()[2].getVoltage());
		minBr = Math.min(minBr, driveIO.getPivots()[3].getVoltage());
		
		if(maxFl != prevMaxFl)
			System.out.println("Max Fl: " + maxFl);
		if(maxFr != prevMaxFr)
			System.out.println("Max Fr: " + maxFr);
		if(maxBl != prevMaxBl)
			System.out.println("Max Bl: " + maxBl);
		if(maxBr != prevMaxBr)
			System.out.println("Max Br: " + maxBr);
		
		if(minFl != prevMinFl)
			System.out.println("Min Fl: " + minFl);
		if(minFr != prevMinFr)
			System.out.println("Min Fr: " + minFr);
		if(minBl != prevMinBl)
			System.out.println("Min Bl: " + minBl);
		if(minBr != prevMinBr)
			System.out.println("Min Br: " + minBr);
		
		prevMaxFl = maxFl;
		prevMaxFr = maxFr;
		prevMaxBl = maxBl;
		prevMaxBr = maxBr;
		
		prevMinFl = minFl;
		prevMinFr = minFr;
		prevMinBl = minBl;
		prevMinBr = minBr;
	}
	
	
}
