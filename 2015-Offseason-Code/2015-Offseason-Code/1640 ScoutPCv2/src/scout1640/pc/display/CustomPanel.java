package scout1640.pc.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class CustomPanel extends JPanel {
	
	private static final long serialVersionUID = 7549060580547681630L;
	
	public static final int DRAW_BLANK = 0;
	public static final int DRAW_IMAGE = 1;
	
	private BufferedImage img;
	private int drawState;

	public CustomPanel () {
		super();
		drawState = DRAW_BLANK;
	}
	
	public void drawImage (BufferedImage img) {
		this.img = img;
		drawState = DRAW_IMAGE;
		this.repaint();
	}
	
	public void drawBlank () {
		drawState = DRAW_BLANK;
		this.repaint();
	}
	
	@Override
	public void paint (Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		switch (drawState) {
			case DRAW_BLANK: {
				g2.setColor(Color.DARK_GRAY);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			} break;
			
			case DRAW_IMAGE: {
				// g2.drawImage(img, 0, 0, null);
				g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), img.getWidth(), 0, 0, img.getHeight(), null);
			} break;
			
			default: {
				g2.setColor(Color.DARK_GRAY);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			} break;
		}
		
		g2.dispose();
		
	}

}
