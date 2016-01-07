package auton.scripts;

import java.util.ArrayList;

import auton.AutonCommand;
import auton.RollerArmsCommand;

public class Blank extends AutonScript {
	private static Blank blank;
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
	
	private Blank(){
		script.add(new RollerArmsCommand(true, false, false, "blank"));
	}
	
	public static Blank getInstance(){
		if(blank == null)
			blank = new Blank();
		return blank;
	}

	@Override
	public ArrayList<AutonCommand> getScript() {
		return script;
	}

}
