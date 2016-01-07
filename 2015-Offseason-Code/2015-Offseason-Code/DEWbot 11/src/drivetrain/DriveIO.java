package drivetrain;

import static constants.Constants.JS_AXIS_LEFT_STICK_X;
import static constants.Constants.JS_AXIS_LEFT_STICK_Y;
import static constants.Constants.JS_AXIS_LEFT_TRIGGER;
import static constants.Constants.JS_AXIS_RIGHT_STICK_X;
import static constants.Constants.JS_AXIS_RIGHT_TRIGGER;
import static constants.Constants.JS_BUTTON_BACK;
import static constants.Constants.JS_BUTTON_START;
import static constants.Constants.MAX_VOLTAGE_BL;
import static constants.Constants.MAX_VOLTAGE_BR;
import static constants.Constants.MAX_VOLTAGE_FL;
import static constants.Constants.MAX_VOLTAGE_FR;
import static constants.Constants.MIN_VOLTAGE_BL;
import static constants.Constants.MIN_VOLTAGE_BR;
import static constants.Constants.MIN_VOLTAGE_FL;
import static constants.Constants.MIN_VOLTAGE_FR;
import static constants.IOConstants.DRIVE_COUNTER_BL;
import static constants.IOConstants.DRIVE_COUNTER_BR;
import static constants.IOConstants.DRIVE_COUNTER_FL;
import static constants.IOConstants.DRIVE_COUNTER_FR;
import static constants.IOConstants.DRIVE_MOTOR_BL;
import static constants.IOConstants.DRIVE_MOTOR_BR;
import static constants.IOConstants.DRIVE_MOTOR_FL;
import static constants.IOConstants.DRIVE_MOTOR_FR;
import static constants.IOConstants.STEER_MOTOR_BL;
import static constants.IOConstants.STEER_MOTOR_BR;
import static constants.IOConstants.STEER_MOTOR_FL;
import static constants.IOConstants.STEER_MOTOR_FR;
import static constants.IOConstants.STEER_RESOLVER_BL;
import static constants.IOConstants.STEER_RESOLVER_BR;
import static constants.IOConstants.STEER_RESOLVER_FL;
import static constants.IOConstants.STEER_RESOLVER_FR;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import utilities.Controllers;
import utilities.IMU;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;

public class DriveIO {
	
	private IMU gyro;
	private AHRS ahrs;
	
	//singleton instance
	private static DriveIO driveIO = null;
	
	//joystick instance
	private Joystick driver = Controllers.getDriverJoystick();
	
	//auton variables
	private boolean autonBack;
	private boolean autonStart;
	private double autonX1;
	private double autonY1;
	private double autonX2;
	
	//pivot instances
	private Pivot flPivot;
	private Pivot frPivot;
	private Pivot blPivot;
	private Pivot brPivot;
	
	private double gyroOffset;

	private DriveIO() {
		gyro = new IMU(new SerialPort(57600,SerialPort.Port.kMXP));
		
		
		flPivot = new Pivot(DRIVE_MOTOR_FL, STEER_MOTOR_FL, STEER_RESOLVER_FL, DRIVE_COUNTER_FL, MIN_VOLTAGE_FL, MAX_VOLTAGE_FL, 0.0, "fl");
		frPivot = new Pivot(DRIVE_MOTOR_FR, STEER_MOTOR_FR, STEER_RESOLVER_FR, DRIVE_COUNTER_FR, MIN_VOLTAGE_FR, MAX_VOLTAGE_FR, 0.0, "fr");
		blPivot = new Pivot(DRIVE_MOTOR_BL, STEER_MOTOR_BL, STEER_RESOLVER_BL, DRIVE_COUNTER_BL, MIN_VOLTAGE_BL, MAX_VOLTAGE_BL, 180.0, "bl");
		brPivot = new Pivot(DRIVE_MOTOR_BR, STEER_MOTOR_BR, STEER_RESOLVER_BR, DRIVE_COUNTER_BR, MIN_VOLTAGE_BR, MAX_VOLTAGE_BR, 180.0, "br");
		
		ahrs = new AHRS(SerialPort.Port.kMXP);
		
	}
	
	public static void init(){
		//if the singleton class hasn't been created yet, create one
		if (driveIO == null)
			driveIO = new DriveIO();
	}
	
	public static DriveIO getInstance() {
		//if the singleton class hasn't been created yet, create one
		if (driveIO == null) driveIO = new DriveIO();
		return driveIO;
	}
	
	private boolean checkState(State state) {
		return state == Robot.getState();
	}
				
	public Pivot[] getPivots(){
		return new Pivot[] {driveIO.flPivot, driveIO.frPivot, driveIO.blPivot, driveIO.brPivot};
	}
	
	//Joystick functions
	public Joystick getDriverJoystick() {
		return driveIO.driver;
	}
	
	public boolean getBack(){
		if(checkState(State.TELEOP))
			return driver.getRawButton(JS_BUTTON_BACK);
		else if (checkState(State.AUTON))
			return autonBack;
		else
			return false;
	}
	
	public boolean getStart(){
		if(checkState(State.TELEOP))
			return driver.getRawButton(JS_BUTTON_START);
		else if (checkState(State.AUTON))
			return autonStart;
		else
			return false;
	}
	
	public double getX1(){
		if (checkState(State.TELEOP))
			return driver.getRawAxis(JS_AXIS_LEFT_STICK_X);
		else if (checkState(State.AUTON))
			return autonX1;
		else
			return 0;
	}
	
	public double getY1(){
		if (checkState(State.TELEOP))
			return driver.getRawAxis(JS_AXIS_LEFT_STICK_Y);
		else if (checkState(State.AUTON))
			return autonY1;
		else
			return 0;
	}
	
	public double getX2(){
		if (checkState(State.TELEOP))
			return driver.getRawAxis(JS_AXIS_RIGHT_STICK_X);
		else if (checkState(State.AUTON))
			return autonX2;
		else
			return 0;
	}
	
	public int[] getEncoders(){
		int[] encoders = new int[4];
		encoders[0] = flPivot.getCounter();
		encoders[1] = frPivot.getCounter();
		encoders[2] = blPivot.getCounter();
		encoders[3] = brPivot.getCounter();
		return encoders;
	}
	
	public void resetEncoders(){
		flPivot.resetCounter();
		frPivot.resetCounter();
		blPivot.resetCounter();
		brPivot.resetCounter();
	}
	
	public double getLeftTrigger(){
		return driver.getRawAxis(JS_AXIS_LEFT_TRIGGER);
	}
	
	public double getRightTrigger(){
		return driver.getRawAxis(JS_AXIS_RIGHT_TRIGGER);
	}
	
	public void setBack(boolean backButton){
		if(checkState(State.AUTON))
			autonBack = backButton;
	}
	
	public void setStart(boolean startButton){
		if(checkState(State.AUTON))
			autonStart = startButton;
	}
	
	public void setX1(double x1){
		if(checkState(State.AUTON))
			autonX1 = x1;
	}
	
	public void setY1(double y1){
		if(checkState(State.AUTON))
			autonY1 = y1;
	}
	
	public void setX2(double x2){
		if (checkState(State.AUTON))
			autonX2 = x2;
	}
	
	//Gyro functions
	public void resetGyro() {
		//driveIO.gyro.zeroYaw();
		gyroOffset = driveIO.ahrs.getYaw();
	}
	public double getYaw() {
		//return (driveIO.gyro.getYaw() + 360) % 360;
		return ((driveIO.ahrs.getYaw() + 360) - gyroOffset) % 360;
	}
	public double getPitch() {
		//return driveIO.gyro.getPitch();
		return driveIO.ahrs.getPitch();
	}
	public double getRoll() {
		//return driveIO.gyro.getRoll();
		return driveIO.ahrs.getRoll();
	}
}


