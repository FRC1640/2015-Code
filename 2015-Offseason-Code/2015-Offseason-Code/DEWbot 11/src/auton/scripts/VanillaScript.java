package auton.scripts;

import java.util.ArrayList;

import auton.AutonCommand;
import auton.DriveCommand;
import auton.SonarCommand;
import auton.WaitCommand;

public class VanillaScript extends AutonScript{
	private static VanillaScript vanilla;
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
	
	private VanillaScript(){
		script.add(new DriveCommand(30, 0, 0.5, false, "vanilla"));
		script.add(new WaitCommand(script.get(script.indexOf("vanilla")), "wait"));
		script.add(new SonarCommand(32, 0.25, "sonar"));
	}
	
	public static VanillaScript getInstance(){
		if(vanilla == null)
			vanilla = new VanillaScript();
		return vanilla;
	}
	
	@Override
	public ArrayList<AutonCommand> getScript(){
		return script;
	}

}
