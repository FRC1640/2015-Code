package utilities;

import edu.wpi.first.wpilibj.AnalogInput;

public class SonarInches {
	private final double VOLTS_PER_INCH = 0.0098;
	private static AnalogInput sonar;
	
	public SonarInches(int portNumber){
		if (sonar == null) sonar = new AnalogInput(portNumber);
	}
	
	public double getInches(){
		return sonar.getVoltage() / VOLTS_PER_INCH;
	}

}
