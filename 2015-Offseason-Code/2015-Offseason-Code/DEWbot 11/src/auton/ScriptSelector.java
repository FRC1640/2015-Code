package auton;

import org.usfirst.frc.team1640.robot.Robot;
import org.usfirst.frc.team1640.robot.Robot.State;

import auton.scripts.Blank;
import auton.scripts.CanGrabberScript;
import auton.scripts.CanGrabberScript.Length;
import auton.scripts.CanGrabberScript.Opposition;
import auton.scripts.ThreeTote;
import auton.scripts.ThreeTote.ThreeToteType;
import auton.scripts.VanillaScript;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utilities.RobotTable;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class ScriptSelector {
	private static ScriptSelector scriptSelector;
	
	private boolean vanilla, threeToteCenter, threeToteLeft, canGrabberShortUOP, canGrabberUOP, canGrabberOP, canGrabberShortOP, blank;
	private ScriptRunner scriptRunner = ScriptRunner.getInstance();
	
	private ScriptSelector(){}
	
	public static ScriptSelector getInstance(){
		if(scriptSelector == null)
			scriptSelector = new ScriptSelector();
		return scriptSelector;
	}
	
	
	public void update(){
		
		if(Robot.getState() == State.DISABLED){
			/*if(SmartDashboard.getBoolean("Vanilla.txt") && !vanilla){
				scriptRunner.setScript(VanillaScript.getInstance());
				System.out.println("Vanilla");
				vanilla = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = blank = false;
			}
			else if(SmartDashboard.getBoolean("3toteCenter.txt") && !threeToteCenter){
				scriptRunner.setScript(ThreeTote.getInstance(ThreeToteType.CENTER));
				System.out.println("3Tote Center");
				threeToteCenter = true;
				vanilla = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = blank = false;
			}
			else if(SmartDashboard.getBoolean("3toteLeft.txt") && !threeToteLeft){
				scriptRunner.setScript(ThreeTote.getInstance(ThreeToteType.LEFT));
				System.out.println("3ToteLeft");
				threeToteLeft = true;
				threeToteCenter = vanilla = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = blank = false;
			}
			else if(SmartDashboard.getBoolean("CanGrabberShortUOP.txt") && !canGrabberShortUOP){
				scriptRunner.setScript(CanGrabberScript.getInstance(Length.SHORT, Opposition.UOP));
				System.out.println("CanGrabberShortUOP");
				canGrabberShortUOP = true;
				threeToteCenter = threeToteLeft = vanilla = canGrabberShortOP = canGrabberUOP = canGrabberOP = blank = false;
			}
			else if(SmartDashboard.getBoolean("CanGrabberUOP.txt") && !canGrabberUOP){
				scriptRunner.setScript(CanGrabberScript.getInstance(Length.LONG, Opposition.UOP));
				System.out.println("CanGrabberLongUOP");
				canGrabberUOP = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = vanilla = canGrabberOP = blank = false;
			}
			else if(SmartDashboard.getBoolean("CanGrabberShortOP.txt") && !canGrabberShortOP){
				scriptRunner.setScript(CanGrabberScript.getInstance(Length.SHORT, Opposition.OP));
				System.out.println("CanGRabberShortOP");
				canGrabberShortOP = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = vanilla = canGrabberUOP = canGrabberOP = blank = false;
			}
			else if(SmartDashboard.getBoolean("CanGrabberOP.txt") && !canGrabberOP){
				scriptRunner.setScript(CanGrabberScript.getInstance(Length.LONG, Opposition.OP));
				System.out.println("CanGrabberLongOp");
				canGrabberOP = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = vanilla = blank = false;
			}
			else if(!blank && !SmartDashboard.getBoolean("CanGrabberOP.txt") && !SmartDashboard.getBoolean("CanGrabberShortOP.txt") && !SmartDashboard.getBoolean("CanGrabberShortUOP.txt") && !SmartDashboard.getBoolean("CanGrabberUOP.txt") && !SmartDashboard.getBoolean("3toteLeft.txt") && !SmartDashboard.getBoolean("3toteCenter.txt") && !SmartDashboard.getBoolean("Vanilla.txt")){
				scriptRunner.setScript(Blank.getInstance());
				System.out.println("Blank");
				blank = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = vanilla = false;
			}
			*/
			
			switch (RobotTable.getState()) {
			
			case "CanGrabberShortUOP":
				scriptRunner.setScript(CanGrabberScript.getInstance(Length.SHORT, Opposition.UOP));
				System.out.println("CanGrabberShortUOP");
				canGrabberShortUOP = true;
				threeToteCenter = threeToteLeft = vanilla = canGrabberShortOP = canGrabberUOP = canGrabberOP = blank = false;
				
				break;
			
			case "CanGrabberLongUOP":
				scriptRunner.setScript(CanGrabberScript.getInstance(Length.LONG, Opposition.UOP));
				System.out.println("CanGrabberLongUOP");
				canGrabberUOP = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = vanilla = canGrabberOP = blank = false;
				
				break;
			
			case "CanGrabberShortOP":
				scriptRunner.setScript(CanGrabberScript.getInstance(Length.SHORT, Opposition.OP));
				System.out.println("CanGRabberShortOP");
				canGrabberShortOP = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = vanilla = canGrabberUOP = canGrabberOP = blank = false;
				
				break;
				
			case "CanGrabberLongOP":
				scriptRunner.setScript(CanGrabberScript.getInstance(Length.LONG, Opposition.OP));
				System.out.println("CanGrabberLongOp");
				canGrabberOP = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = vanilla = blank = false;
				
				break;
				
			case "ThreeToteCenter":
				scriptRunner.setScript(ThreeTote.getInstance(ThreeToteType.CENTER));
				System.out.println("3Tote Center");
				threeToteCenter = true;
				vanilla = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = blank = false;
				
				break;
			
			case "ThreeToteLeft":
				scriptRunner.setScript(ThreeTote.getInstance(ThreeToteType.LEFT));
				System.out.println("3ToteLeft");
				threeToteLeft = true;
				threeToteCenter = vanilla = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = blank = false;
				
				break;
				
			case "Vanilla":
				scriptRunner.setScript(VanillaScript.getInstance());
				System.out.println("Vanilla");
				vanilla = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = blank = false;
				
				break;
			
			case "default":
				scriptRunner.setScript(Blank.getInstance());
				System.out.println("Blank");
				blank = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = vanilla = false;
				
				break;
				
			default:
				scriptRunner.setScript(Blank.getInstance());
				System.out.println("Blank");
				blank = true;
				threeToteCenter = threeToteLeft = canGrabberShortUOP = canGrabberShortOP = canGrabberUOP = canGrabberOP = vanilla = false;
				
				
			}
		}
	}
}
