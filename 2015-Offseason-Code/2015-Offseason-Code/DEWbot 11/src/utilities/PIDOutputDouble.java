package utilities;

import edu.wpi.first.wpilibj.PIDOutput;

public class PIDOutputDouble implements PIDOutput{
	private double value;
	
	public double getValue(){
		return value;
	}

	@Override
	public void pidWrite(double output) {
		value = output;
	}

}
