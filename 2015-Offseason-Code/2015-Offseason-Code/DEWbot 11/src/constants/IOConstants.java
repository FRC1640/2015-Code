package constants;

public class IOConstants {
	
	public static final int JOYSTICK_DRIVER = 0;
	public static final int JOYSTICK_OPERATOR = 1;
	
	public static final int DRIVE_MOTOR_FL = 8;
	public static final int DRIVE_MOTOR_FR = 9;
	public static final int DRIVE_MOTOR_BL = 6;
	public static final int DRIVE_MOTOR_BR = 7;
	
	public static final int STEER_MOTOR_FL = 2;
	public static final int STEER_MOTOR_FR = 3;
	public static final int STEER_MOTOR_BL = 0;
	public static final int STEER_MOTOR_BR = 1;
	
	public static final int STEER_RESOLVER_FL = 3;
	public static final int STEER_RESOLVER_FR = 0;
	
	public static final int STEER_RESOLVER_BL = 2;
	public static final int STEER_RESOLVER_BR = 1;
	
	public static final int DRIVE_COUNTER_FL = 8;
	public static final int DRIVE_COUNTER_FR = 9;
	public static final int DRIVE_COUNTER_BL = 6;
	public static final int DRIVE_COUNTER_BR = 7;

	public static final int ENCODER_LIFT_LEFT_A = 3;
	public static final int ENCODER_LIFT_LEFT_B = 2;
	public static final int ENCODER_LIFT_RIGHT_A = 5;
	public static final int ENCODER_LIFT_RIGHT_B = 4;
	
	public static final int LIFT_MOTOR_LEFT = 4;
	public static final int LIFT_MOTOR_RIGHT = 5; // TODO: Negate this one
	
	public static final int ROLLER_MOTOR_LEFT = 11; // PWM MXP 1;
	public static final int ROLLER_MOTOR_RIGHT = 10; // PWM MXP 0
	
	public static final int ROLLER_SOLENOID = 0; // TODO: CHECK THIS Solenoid 0
	
	public static final int LIFT_SWITCH_LEFT = 13; // DIO MXP 3
	public static final int LIFT_SWITCH_RIGHT = 0; // DIO_MXP_4 14 on prime, 0 on deux
	
	public static final int CAN_GRABBERS = 1;
	public static final int SERVO = 19;
	public static final int CAN_BAPPER = 2;
	
	public static final int LEFT_SONAR = 7;
		
}
