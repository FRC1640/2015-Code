package constants;

public class Constants {
	
	// TODO: VERIFY ROBOT DIMENSIONS
	public static final double ROBOT_LENGTH = 20.5;
	public static final double ROBOT_WIDTH = 22;
	public static final double ROBOT_RADIUS = Math.sqrt(Math.pow(ROBOT_WIDTH, 2) + Math.pow(ROBOT_LENGTH, 2));
	public static final double ROBOT_WIDTH_TO_RADIUS_RATIO = ROBOT_WIDTH / ROBOT_RADIUS;
	public static final double ROBOT_LENGTH_TO_RADIUS_RATIO = ROBOT_LENGTH / ROBOT_RADIUS;
	
	public static final double MIN_VOLTAGE_FL = 0.201416;
	public static final double MAX_VOLTAGE_FL = 4.7277827;
	public static final double MIN_VOLTAGE_FR = 0.2111816;
	public static final double MAX_VOLTAGE_FR = 4.738769;
	
	public static final double MIN_VOLTAGE_BL = 0.207519516;
	public static final double MAX_VOLTAGE_BL = 4.73144483;
	public static final double MIN_VOLTAGE_BR = 0.20507811;
	public static final double MAX_VOLTAGE_BR = 4.7497553825;
	
	public static final double LIFT_LEFT_MOTOR_NEG_ACCEL = -0.189466;
	public static final double LIFT_LEFT_MOTOR_POS_ACCEL = 0.1468848;
	public static final double LIFT_LEFT_MOTOR_NEG_DECEL = 0.1637056;
	public static final double LIFT_LEFT_MOTOR_POS_DECEL = -0.188808;
	public static final double LIFT_LEFT_MOTOR_NEG_VELOCITY = -14.4142;
	public static final double LIFT_LEFT_MOTOR_POS_VELOCITY = 14.5475;
	
	public static final double LIFT_RIGHT_MOTOR_NEG_ACCEL = -0.188012;
	public static final double LIFT_RIGHT_MOTOR_POS_ACCEL = 0.1447114;
	public static final double LIFT_RIGHT_MOTOR_NEG_DECEL = 0.1578142;
	public static final double LIFT_RIGHT_MOTOR_POS_DECEL = -0.1657944;
	public static final double LIFT_RIGHT_MOTOR_NEG_VELOCITY = -14.4405;
	public static final double LIFT_RIGHT_MOTOR_POS_VELOCITY = 14.0852;
	
	public static final double LEFT_LIFT_KA = 0.06;//7
	public static final double LEFT_LIFT_KP = 0.0008;//52
	public static final double LEFT_LIFT_KI = 0.000003;//1

	public static final double RIGHT_LIFT_KA = 0.05;//7
	public static final double RIGHT_LIFT_KP = 0.0008;//52
	public static final double RIGHT_LIFT_KI = 0.000003;//1
	
	public static final double HOMING_LIFT_KA = 0;
	public static final double HOMING_LIFT_KP = 0;
	
	public static final int LIFT_POS_FLOOR = 0;
	public static final int LIFT_POS_1 = 3252;//2052
	public static final int LIFT_POS_2 = 8000; //6566;
	public static final int LIFT_POS_3 = 12449;
	public static final int LIFT_POS_PICKUP3 = 3694;
	
	public static final double LIFT_HOME_SPEED = 0.35;
	
	//BUTTONS
	public static final int JS_BUTTON_A = 1;
	public static final int JS_BUTTON_B = 2;
	public static final int JS_BUTTON_X = 3;
	public static final int JS_BUTTON_Y = 4;
	public static final int JS_BUTTON_L = 5;
	public static final int JS_BUTTON_R = 6;
	public static final int JS_BUTTON_BACK = 7;
	public static final int JS_BUTTON_START = 8;
	public static final int JS_BUTTON_LEFT_STICK = 9;
	public static final int JS_BUTTON_RIGHT_STICK = 10;
	//AXES
	public static final int JS_AXIS_LEFT_STICK_X = 0; //-1 to 1
	public static final int JS_AXIS_LEFT_STICK_Y = 1; //-1 to 1
	public static final int JS_AXIS_LEFT_TRIGGER = 2; //0 to 1
	public static final int JS_AXIS_RIGHT_TRIGGER = 3; //0 to 1
	public static final int JS_AXIS_RIGHT_STICK_X = 4; //-1 to 1
	public static final int JS_AXIS_RIGHT_STICK_Y = 5; //-1 to 1
	//POV
	public static final int JS_POV_N = 0; //North
	public static final int JS_POV_NE = 45; //North East
	public static final int JS_POV_E = 90; //East
	public static final int JS_POV_SE = 135; //South East
	public static final int JS_POV_S = 180; //South
	public static final int JS_POV_SW = 225; //South West
	public static final int JS_POV_W = 270; //West
	public static final int JS_POV_NW = 315; //North West
	
	//Script reading commands
	public static final int AUTON_MAX_ARGUMENTS = 5;
	public static final int AUTON_MAX_COMMANDS = 12;
	public static final int AUTON_WAIT = 0;
	public static final int AUTON_MOVE = 1;
	public static final int AUTON_LIFT = 2;
	public static final int AUTON_MOVE_LIFT = 3;
	public static final int AUTON_INTAKE = 4;
	public static final int AUTON_OUTTAKE = 5;
	public static final int AUTON_CAN_GRABBERS = 6;
	public static final int AUTON_CAN_ARM = 7;
}
