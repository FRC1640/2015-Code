package scout1640.pc.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JMenuBar;

import scout1640.pc.core.Controller;

public class ScoutMenuBar extends JMenuBar{

	private static final long serialVersionUID = -8581688699499086294L;
	
	private Controller controller;
	
	private boolean qrActive;
	private boolean dQR;
	
	public ScoutMenuBar (Controller c) {
		super();
		controller = c;
		qrActive = false;
		dQR = true;
		this.add(new ScoutMenuButton("Read QR Code"));
		this.add(new ScoutMenuButton("Add New Event"));
		this.add(new ScoutMenuButton("Reload Event"));
		this.add(new ScoutMenuButton("Export Dataset"));
		this.add(new ScoutMenuButton("Import Dataset"));
		this.add(new ScoutMenuButton("Export CSV"));
		this.add(new ScoutMenuButton("Clear Console"));
		this.add(new ScoutMenuButton("Quit"));
		controller.setMenuBar(this);
	}
	
	private class ScoutMenuButton extends JButton implements ActionListener {
		
		private static final long serialVersionUID = 5688396362275632863L;
		
		private String name;
		
		private ScoutMenuButton self = this;
		
		public ScoutMenuButton (String name) {
			super(name);
			this.name = name;
			this.addActionListener(this);
			if (name.equals("Read QR Code")) {
				
				this.addKeyListener(new KeyListener() {

					@Override
					public void keyPressed(KeyEvent e) {
						if ((e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) && dQR) {
							dQR = false;
							if (qrActive) { controller.setState(Controller.MAIN_SCREEN); self.setText(name); } 
							else { controller.setState(Controller.READ_QR); self.setText("Stop Reading QR"); }
							qrActive = !qrActive;
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) { dQR = true; } 
					}

					@Override
					public void keyTyped(KeyEvent e) { }
					
				});
				
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch (name) {
				case "Quit": {
					controller.setState(Controller.QUIT);
				} break;
				
				case "Read QR Code": {
					if (qrActive) { controller.setState(Controller.MAIN_SCREEN); this.setText(name); } 
					else { controller.setState(Controller.READ_QR); this.setText("Stop Reading QR"); }
					qrActive = !qrActive;
				} break;
				
				case "Add New Event": {
					controller.setState(Controller.ADD_NEW_EVENT);
				} break;
				
				case "Reload Event": {
					controller.setState(Controller.RELOAD_EVENT);
				} break;
				
				case "Export Dataset": {
				 	controller.setState(Controller.EXPORT_DATASET);
				} break;
				
				case "Import Dataset": {
					controller.setState(Controller.IMPORT_DATASET);
				} break;
				
				case "Clear Console": {
					controller.clearConsole();
				} break;
				
				case "Export CSV": {
					controller.setState(Controller.EXPORT_CSV);
				} break;
				
			}
			
		}
		
	}

}
