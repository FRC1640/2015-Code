package utilities;

import edu.wpi.first.wpilibj.PIDSource;

public class PIDSourceDouble implements PIDSource{
	private double value;
	
	public PIDSourceDouble(double value){
		this.value = value;
	}
	
	public void setValue(double value){
		this.value = value;
	}

	@Override
	public double pidGet() {
		return value;
	}

}
