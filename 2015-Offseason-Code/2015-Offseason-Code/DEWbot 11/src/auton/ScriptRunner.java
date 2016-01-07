package auton;

import java.util.ArrayList;
import java.util.HashMap;

import auton.scripts.AutonScript;
import drivetrain.DriveIO;

public class ScriptRunner implements AutonCommand{
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
	private int position;
	private ArrayList<AutonCommand> executing = new ArrayList<AutonCommand>();
	private String name;
	//private HashMap<String, AutonCommand> script;
	private static ScriptRunner scriptRunner;
	
	public static ScriptRunner getInstance(){
		if(scriptRunner == null)
			scriptRunner = new ScriptRunner("name");
		return scriptRunner;
	}
	
	private ScriptRunner(String name){
		this.name = name;
		
		/*script.add(new RollerArmsCommand(true, false, false, "r1"));
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

		executing.add(script.get(0));*/
	}

	@Override
	public void execute() {
		if(position < script.size() - 1 && script.get(position).isDone() ){
			AutonCommand a = script.get(position + 1);
			executing.add(a);
			System.out.println("Position: " + position + " Adding " + a);
			position++;
		}
		for(AutonCommand command: executing){
			//System.out.println(command.toString());
			System.out.println("Executing: " + command);
			command.execute();
		}
		cleanUp();
	}

	@Override
	public boolean isRunning() {
		return !(position == script.size() - 1 && executing.isEmpty());
	}

	@Override
	public boolean isDone() {
		return isRunning();
	}
	
	private void cleanUp(){
		for(int i = 0; i < executing.size(); ){
			if(!executing.get(i).isRunning()){
				System.out.println("Removing: " + executing.get(i));
				executing.remove(i);
			}
			else
				i++;
		}
	}
	
	public String getName(){
		return name;
	}
	
	public void setScript(AutonScript script){
		this.script = script.getScript();
		executing.clear();
		position = 0;
		executing.add(this.script.get(0));
		System.out.println("Changed Script");
	}
	
	public void resetScript() {
		for(AutonCommand ac : script) {
			ac.reset();
		}
	}
	
	public void reset(){
		DriveIO.getInstance().resetGyro();
		executing.clear();
		position = 0;
		resetScript();
		executing.add(this.script.get(0));
		System.out.println("Doneness of Command 0: " + !this.script.get(0).isRunning());
	}
}
