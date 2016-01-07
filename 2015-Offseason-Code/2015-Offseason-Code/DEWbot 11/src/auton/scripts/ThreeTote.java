package auton.scripts;

import java.util.ArrayList;

import auton.AutonCommand;
import auton.DriveCommand;
import auton.LiftCommand;
import auton.LiftCommand.Button;
import auton.RollerArmsCommand;
import auton.SonarCommand;
import auton.TimeCommand;
import auton.TurnCommand;
import auton.WaitCommand;

public class ThreeTote extends AutonScript{
	public enum ThreeToteType {CENTER, LEFT};
	private ArrayList<AutonCommand> script = new ArrayList<AutonCommand>(){
		@Override
		public int indexOf(Object o){
			if (o == null) {
		        for (int i = 0; i < script.size(); i++)
		            if (script.get(i) == null)
		                return i;
		    } else {
		        for (int i = 0; i < script.size(); i++)
		            if (script.get(i).equals(o))
		                return i;
		    }
			return -1;
		}
	};
	
	private static ThreeTote left, center;
	
	public static ThreeTote getInstance(ThreeToteType type){
		if(type == ThreeToteType.LEFT){
			if(left == null)
				left = new ThreeTote(ThreeToteType.LEFT);
			return left;
		}
		else if(type == ThreeToteType.CENTER){
			if(center == null)
				center = new ThreeTote(ThreeToteType.CENTER);
			return center;
		}
		return null;
	}
	
	private ThreeTote(ThreeToteType type){
		script.add(new RollerArmsCommand(true, false, false, "r1"));
		script.add(new TurnCommand(45, "tu1"));
		script.add(new WaitCommand(script.get(script.indexOf("tu1")), "w1"));
		script.add(new TurnCommand(0, "tu2"));
		script.add(new WaitCommand(script.get(script.indexOf("tu2")), "w2"));
		script.add(new TimeCommand(250, "t1"));
		script.add(new WaitCommand(script.get(script.indexOf("t1")), "w3" ));
		
		script.add(new DriveCommand(45, 0, 0.75, false, "d1"));
		script.add(new RollerArmsCommand(false, true, false, "r2"));
		script.add(new TimeCommand(500, "t2"));
		script.add(new WaitCommand(script.get(script.indexOf("t2")), "w4"));
		script.add(new RollerArmsCommand(true, false, false, "r3"));
		script.add(new LiftCommand(Button.MOVEUP, "l1"));
		script.add(new WaitCommand(script.get(script.indexOf("l1")), "w5"));
		script.add(new LiftCommand(Button.MOVEUP, "l2"));
		script.add(new WaitCommand(script.get(script.indexOf("l2")), "w6"));
		script.add(new WaitCommand(script.get(script.indexOf("d1")), "w7"));
		
		script.add(new RollerArmsCommand(false, true, false, "r3"));
		script.add(new TurnCommand(45, "tu3"));
		script.add(new WaitCommand(script.get(script.indexOf("tu3")), "w8"));
		script.add(new TurnCommand(0, "tu4"));
		script.add(new WaitCommand(script.get(script.indexOf("tu4")), "w9"));
		script.add(new TimeCommand(250, "t3"));
		script.add(new WaitCommand(script.get(script.indexOf("t3")), "w10"));
		script.add(new RollerArmsCommand(false, false, false, "r4"));
		//script.add(new CanBapperCommand(false, "b1"));
		script.add(new SonarCommand(32, 0.25, "s1"));
		script.add(new WaitCommand(script.get(script.indexOf("s1")), "w11"));

		script.add(new DriveCommand(45, 0, 0.65, false, "d2"));
		script.add(new LiftCommand(Button.PICKUP, "l3"));
		script.add(new WaitCommand(script.get(script.indexOf("l3")), "w12"));
		script.add(new WaitCommand(script.get(script.indexOf("d2")), "w13"));
		
		script.add(new RollerArmsCommand(false, true, false, "r4"));
		
		//TODO: Script here!!!
		if(type == ThreeToteType.CENTER){
			script.add(new TurnCommand(90, "turn"));
			script.add(new DriveCommand(105, 140, 1.0, false, "driveToPlace"));
		}
		else if(type == ThreeToteType.LEFT){
			script.add(new DriveCommand(105, 140, 1.0, false, "driveToPlace")); //fix this
		}
		
		script.add(new WaitCommand(script.get(script.indexOf("driveToPlace")), "wait14"));
		script.add(new LiftCommand(Button.PLACE, "place"));
		script.add(new DriveCommand(30, 270, 1, false, "backup"));
		script.add(new RollerArmsCommand(false, false, true, "outtake"));
		script.add(new WaitCommand(script.get(script.indexOf("outtake")), "final wait"));
	}

	@Override
	public ArrayList<AutonCommand> getScript() {
		return script;
	}
	
	
}
