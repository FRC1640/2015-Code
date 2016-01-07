package scout1640.pc.core;

import org.opencv.core.Core;

public class ScoutPC {
	
	public ScoutPC () {
		new Controller();
	}

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new ScoutPC();
	}

}