package com.team1640;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ScanPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	ScoutMobile main;
	
	BufferedImage img = null;
	
	public ScanPanel(ScoutMobile main) {
		this.main = main;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if (img != null) {
			g2d.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), img.getWidth(), 0, 0, img.getHeight(), null);
		}
		g2d.dispose();
	}
	
	public void setImg(BufferedImage img) {
		this.img = img;
		repaint();
	}
}